package com.example.banksystem;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStorage {
    private static final String CUSTOMERS_FILE = "customers.txt";
    private static final String ACCOUNTS_FILE = "accounts.txt";
    private static final String TRANSACTIONS_FILE = "transactions.txt";

    // --- Save Customers ---
    public static void saveCustomers(List<Customer> customers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMERS_FILE))) {
            for (Customer c : customers) {
                if (c instanceof IndividualCustomer ic) {
                    writer.write(String.join(",", "Individual", ic.getCustomerId(), ic.getName(), ic.getEmail(),
                            ic.getPhone(), ic.getNationalId(), ic.getDateOfBirth().toString(), Boolean.toString(ic.isEmployed())));
                } else if (c instanceof CompanyCustomer cc) {
                    writer.write(String.join(",", "Company", cc.getCustomerId(), cc.getName(), cc.getEmail(),
                            cc.getPhone(), cc.getRegistrationNumber(), cc.getContactPerson(), Boolean.toString(cc.isEmployed())));
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- Load Customers ---
    public static List<Customer> loadCustomers() {
        List<Customer> customers = new ArrayList<>();
        File file = new File(CUSTOMERS_FILE);
        if (!file.exists()) return customers;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals("Individual") && parts.length >= 8) {
                    IndividualCustomer ic = new IndividualCustomer(parts[1], parts[2], parts[3], parts[4],
                            parts[5], LocalDate.parse(parts[6]));
                    ic.setEmployed(Boolean.parseBoolean(parts[7]));
                    customers.add(ic);
                } else if (parts[0].equals("Company") && parts.length >= 8) {
                    CompanyCustomer cc = new CompanyCustomer(parts[1], parts[2], parts[3], parts[4],
                            parts[5], parts[6], Boolean.parseBoolean(parts[7]));
                    customers.add(cc);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return customers;
    }

    // --- Save Accounts ---
    public static void saveAccounts(List<Account> accounts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE))) {
            for (Account a : accounts) {
                writer.write(String.join(",", a.getAccountNumber(), a.getOwner().getCustomerId(),
                        a.getClass().getSimpleName(), a.getBranch(), Double.toString(a.getBalance())));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- Load Accounts ---
    public static List<Account> loadAccounts(List<Customer> customers) {
        List<Account> accounts = new ArrayList<>();
        File file = new File(ACCOUNTS_FILE);
        if (!file.exists()) return accounts;

        Map<String, Customer> customerMap = new HashMap<>();
        for (Customer c : customers) customerMap.put(c.getCustomerId(), c);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 5) continue;

                String accNum = parts[0];
                String custId = parts[1];
                String type = parts[2];
                String branch = parts[3];
                double balance = Double.parseDouble(parts[4]);
                Customer owner = customerMap.get(custId);
                if (owner == null) continue;

                Account a = null;
                try {
                    switch (type) {
                        case "SavingsAccount" -> a = new SavingsAccount(owner, branch, balance);
                        case "ChequeAccount" -> a = new ChequeAccount(owner, branch, balance);
                        case "InvestmentAccount" -> a = new InvestmentAccount(owner, branch, balance);
                        default -> a = new ChequeAccount(owner, branch, balance);
                    }
                } catch (IllegalArgumentException ex) {
                    System.err.println("Skipping account " + accNum + ": " + ex.getMessage());
                    continue;
                }

                owner.addAccount(a);
                accounts.add(a);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    // --- Save Transactions ---
    public static void saveTransactions(List<Account> accounts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE))) {
            for (Account a : accounts) {
                for (Transaction t : a.getTransactions()) {
                    writer.write(t.toString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- Load Transactions ---
    public static void loadTransactions(List<Account> accounts) {
        File file = new File(TRANSACTIONS_FILE);
        if (!file.exists()) return;

        Map<String, Account> accountMap = new HashMap<>();
        for (Account a : accounts) accountMap.put(a.getAccountNumber(), a);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 5);
                if (parts.length < 5) continue;

                String accNum = parts[0];
                TransactionType type = TransactionType.valueOf(parts[1]);
                double amount = Double.parseDouble(parts[2]);
                String desc = parts[3];
                Account acc = accountMap.get(accNum);
                if (acc != null) {
                    acc.getTransactions().add(new Transaction(accNum, type, amount, desc));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
