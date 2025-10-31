package com.example.thesystem;

import com.example.thesystem.Account;
import com.example.thesystem.Customer;
import com.example.thesystem.Individual;
import com.example.thesystem.database.DataStorage;

import java.util.ArrayList;
import java.util.List;

public class BankDatabase {
    private static final List<Customer> customers = new ArrayList<>(DataStorage.loadCustomers());
    private static final List<Account> accounts = new ArrayList<>();
    private static Customer loggedInCustomer;

    static {
        // load accounts and attach to customers
        accounts.addAll(DataStorage.loadAccounts(customers));
    }

    public static List<Customer> getCustomers() { return customers; }

    public static List<Account> getAccounts() { return accounts; }

    public static boolean login(String username, String password) {
        for (Customer c : customers) {
            if (c instanceof Individual ind) {
                if (ind.getUsername().equals(username) && ind.getPassword().equals(password)) {
                    loggedInCustomer = ind;
                    return true;
                }
            }
        }
        return false;
    }

    public static Customer getLoggedInCustomer() { return loggedInCustomer; }

    public static void logout() {
        loggedInCustomer = null;
    }

    public static void addCustomer(Customer customer) {
        customers.add(customer);
        DataStorage.saveCustomers(customers);
    }

    public static void addAccount(Account account) {
        accounts.add(account);
        DataStorage.saveAccounts(accounts);
    }

    public static List<Account> getAccountsForCustomer(Customer customer) {
        List<Account> out = new ArrayList<>();
        for (Account a : accounts) {
            if (a.getCustomer() != null && a.getCustomer().equals(customer)) out.add(a);
        }
        return out;
    }

    public static void saveAll() {
        DataStorage.saveCustomers(customers);
        DataStorage.saveAccounts(accounts);
    }

    public static void setLoggedInCustomer(Object o) {
    }

    public static List<String> getTransactionHistory(Object username) {
        return List.of();
    }

    public static List<Account> getCustomerAccounts(Object username) {
        return List.of();
    }
}
