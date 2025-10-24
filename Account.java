package com.example.thesystem;
import com.example.thesystem.Customer;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    String accountNumber;
    double balance;
    String branch;
    Customer customer;
    protected List<String> transactions = new ArrayList<>();

    public Account(String accountNumber, String branch, Customer customer) {
        this.accountNumber = accountNumber;
        this.branch = branch;
        this.customer = customer;
        this.balance = 0.0;
    }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactions.add("Deposited BWP" + amount);
        }
    }

    public abstract void withdraw(double amount);

    public List<String> getTransactions() { return transactions; }

    @Override
    public String toString() {
        return accountNumber + " (BWP" + balance + ")";
    }
}
