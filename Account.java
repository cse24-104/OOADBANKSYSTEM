package com.example.thesystem;

import com.example.thesystem.Customer;
import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    private String accountNumber;
    private double balance;
    private String branch;
    private Customer customer;
    private final List<String> transactionHistory = new ArrayList<>();

    public Account(String accountNumber, String branch, Customer customer) {
        this.accountNumber = accountNumber;
        this.branch = branch;
        this.customer = customer;
        this.balance = 0.0;
    }

    // extra constructor for loading with balance
    public Account(String accountNumber, String branch, Customer customer, double balance) {
        this(accountNumber, branch, customer);
        this.balance = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getBranch() { return branch; }
    public double getBalance() { return balance; }
    public Customer getCustomer() { return customer; }

    protected void setBalance(double newBalance) { this.balance = newBalance; }

    public List<String> getTransactionHistory() { return transactionHistory; }

    public void addTransaction(String desc) {
        transactionHistory.add(desc);
    }

    public void deposit(double amount) {
        if (amount <= 0) return;
        balance += amount;
        String record = String.format("Deposit,%.2f,%s", amount, java.time.LocalDateTime.now());
        addTransaction(record);
    }

    public abstract void withdraw(double amount);

    @Override
    public String toString() {
        return accountNumber + " - " + this.getClass().getSimpleName() + " - BWP " + String.format("%.2f", balance);
    }
}