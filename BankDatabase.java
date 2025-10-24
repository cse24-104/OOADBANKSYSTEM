package com.example.thesystem;

import com.example.thesystem.Customer;
import com.example.thesystem.Individual;
import java.util.ArrayList;
import java.util.List;

public class BankDatabase {
    private static List<Customer> customers = new ArrayList<>();
    private static Customer loggedInCustomer;

    public static void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public static List<Customer> getCustomers() {
        return customers;
    }

    public static Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }

    public static void setLoggedInCustomer(Customer customer) {
        loggedInCustomer = customer;
    }

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
}
