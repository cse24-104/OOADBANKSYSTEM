package com.example.thesystem.database;

import com.example.thesystem.Account;
import com.example.thesystem.Customer;
import com.example.thesystem.Individual;
import com.example.thesystem.Company;
import com.example.thesystem.SavingsAccount;
import com.example.thesystem.ChequeAccount;
import com.example.thesystem.InvestmentAccount;
import com.example.thesystem.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DataStorage {
    private static final String CUSTOMER_FILE = "customers.txt";
    private static final String ACCOUNT_FILE = "accounts.txt";
    private static final String TRANSACTION_FILE = "transactions.txt";

    // Ensure files exist
    public static void ensureFilesExist() {
        try {
            Files.createDirectories(Paths.get("."));
            new File(CUSTOMER_FILE).createNewFile();
            new File(ACCOUNT_FILE).createNewFile();
            new File(TRANSACTION_FILE).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save customers
    // Format per line:
    // Individual,first,last,address,phone,email,username,password
    // Company,companyName,address,phone,email,registrationNumber,registrationDate
    public static void saveCustomers(List<Customer> customers) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(CUSTOMER_FILE))) {
            for (Customer c : customers) {
                if (c instanceof Individual ind) {
                    w.write(String.join(",",
                            "Individual",
                            escape(ind.getFirstName()),
                            escape(ind.getLastName()),
                            escape(ind.getAddress()),
                            escape(ind.getPhone()),
                            escape(ind.getEmail()),
                            escape(ind.getUsername()),
                            escape(ind.getPassword())
                    ));
                    w.newLine();
                } else if (c instanceof Company comp) {
                    w.write(String.join(",",
                            "Company",
                            escape(comp.getCompanyName()),
                            escape(comp.getAddress()),
                            escape(comp.getPhone()),
                            escape(comp.getEmail()),
                            String.valueOf(comp.getRegistrationNumber()),
                            escape(comp.getRegistrationDate())
                    ));
                    w.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load customers
    public static List<Customer> loadCustomers() {
        List<Customer> list = new ArrayList<>();
        File f = new File(CUSTOMER_FILE);
        if (!f.exists()) return list;

        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            String line;
            int idCounter = 1;
            while ((line = r.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = splitCsv(line);
                if (parts.length == 0) continue;
                if (parts[0].equals("Individual") && parts.length >= 8) {
                    Individual ind = new Individual(
                            unescape(parts[1]),
                            unescape(parts[2]),
                            unescape(parts[3]),
                            unescape(parts[4]),
                            unescape(parts[5]),
                            idCounter++,
                            "1990-01-01",
                            "NotSpecified",
                            "Single",
                            unescape(parts[6]),
                            unescape(parts[7])
                    );
                    list.add(ind);
                } else if (parts[0].equals("Company") && parts.length >= 7) {
                    Company comp = new Company(
                            unescape(parts[1]),
                            unescape(parts[2]),
                            unescape(parts[3]),
                            unescape(parts[4]),
                            Integer.parseInt(parts[5]),
                            unescape(parts[6])
                    );
                    list.add(comp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Save accounts
    // Format per line:
    // AccountType,accountNumber,branch,balance,ownerKey
    public static void saveAccounts(List<Account> accounts) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(ACCOUNT_FILE))) {
            for (Account a : accounts) {
                String type = a.getClass().getSimpleName();
                String line = String.join(",",
                        type,
                        escape(a.getAccountNumber()),
                        escape(a.getBranch()),
                        String.format(Locale.US,"%.2f", a.getBalance()),
                        escape(a.getCustomer() == null ? "" : a.getCustomer().getOwnerKey())
                );
                w.write(line);
                w.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load accounts and attach to customers by ownerKey (username or company name)
    public static List<Account> loadAccounts(List<Customer> customers) {
        List<Account> accounts = new ArrayList<>();
        File f = new File(ACCOUNT_FILE);
        if (!f.exists()) return accounts;

        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = r.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = splitCsv(line);
                if (parts.length < 5) continue;

                String type = parts[0];
                String acctNum = unescape(parts[1]);
                String branch = unescape(parts[2]);
                double balance = Double.parseDouble(parts[3]);
                String ownerKey = unescape(parts[4]);

                Customer owner = findCustomerByOwnerKey(customers, ownerKey);

                Account acc = null;
                if (type.equals("SavingsAccount") || type.equals("Savings")) {
                    acc = new SavingsAccount(acctNum, branch, owner, balance);
                } else if (type.equals("ChequeAccount") || type.equals("Cheque")) {
                    // No employer info stored here — create with nulls
                    acc = new ChequeAccount(acctNum, branch, owner, "", "", balance);
                } else if (type.equals("InvestmentAccount") || type.equals("Investment")) {
                    acc = new InvestmentAccount(acctNum, branch, owner, balance, balance);
                } else {
                    // default to savings
                    acc = new SavingsAccount(acctNum, branch, owner, balance);
                }

                // load transactions for this account
                List<String> txs = getTransactions(acctNum);
                for (String t : txs) acc.getTransactionHistory().add(formatTransactionLineToShort(t));

                accounts.add(acc);
                if (owner != null) owner.addAccount(acc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    // Save a single transaction (append)
    // Format: accountNumber,type,amount,date,description
    public static void saveTransaction(String accountNumber, String type, double amount, String description) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(TRANSACTION_FILE, true))) {
            String line = String.join(",",
                    escape(accountNumber),
                    escape(type),
                    String.format(Locale.US,"%.2f", amount),
                    escape(java.time.LocalDateTime.now().toString()),
                    escape(description)
            );
            w.write(line);
            w.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get raw lines for account transactions
    public static List<String> getTransactions(String accountNumber) {
        List<String> list = new ArrayList<>();
        File f = new File(TRANSACTION_FILE);
        if (!f.exists()) return list;
        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = r.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = splitCsv(line);
                if (parts.length < 5) continue;
                if (unescape(parts[0]).equals(accountNumber)) {
                    list.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Helpers

    private static Customer findCustomerByOwnerKey(List<Customer> customers, String key) {
        if (key == null || key.isEmpty()) return null;
        for (Customer c : customers) {
            if (key.equals(c.getOwnerKey())) return c;
        }
        return null;
    }

    // Basic CSV escape (commas)
    private static String escape(String s) {
        if (s == null) return "";
        return s.replaceAll("\\r?\\n", " ").replace(",", "\\,");
    }
    private static String unescape(String s) {
        if (s == null) return "";
        return s.replace("\\,", ",");
    }

    // split by commas but treat \, as escaped comma
    private static String[] splitCsv(String line) {
        List<String> parts = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean esc = false;
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (ch == '\\' && i + 1 < line.length() && line.charAt(i + 1) == ',') {
                cur.append(',');
                i++; // skip next
                continue;
            }
            if (ch == ',') {
                parts.add(cur.toString());
                cur = new StringBuilder();
            } else {
                cur.append(ch);
            }
        }
        parts.add(cur.toString());
        return parts.toArray(new String[0]);
    }

    private static String formatTransactionLineToShort(String rawLine) {
        // rawLine: accountNumber,type,amount,date,description
        String[] p = splitCsv(rawLine);
        if (p.length < 5) return rawLine;
        String t = unescape(p[1]);
        String amt = unescape(p[2]);
        String date = unescape(p[3]);
        String desc = unescape(p[4]);
        return String.format("%s | BWP %s | %s | %s", t, amt, date, desc);
    }
}
