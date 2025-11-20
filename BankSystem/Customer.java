package com.example.banksystem;

import java.util.ArrayList;
import java.util.List;

public abstract class Customer {
    protected String customerId;
    protected String name;
    protected String email;
    protected String phone;
    protected List<Account> accounts = new ArrayList<>();
    protected boolean employed; // moved here to be common

    public Customer(String customerId, String name, String email, String phone) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.employed = false;
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    public List<Account> getAccounts() { return accounts; }
    public void addAccount(Account account) { if (account != null) accounts.add(account); }

    public boolean isEmployed() { return employed; }
    public void setEmployed(boolean employed) { this.employed = employed; }

    public abstract String getCustomerType();
}
