package com.example.banksystem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Account {
    protected String accountNumber;
    protected Customer owner;
    protected String branch;
    protected double balance;
    protected List<Transaction> transactions = new ArrayList<>();

    public Account(Customer owner, String branch, double initialDeposit) {
        this.accountNumber = "ACC" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        this.owner = owner;
        this.branch = branch;
        this.balance = Math.max(initialDeposit, 0); // ensure non-negative
        if (initialDeposit > 0) {
            transactions.add(new Transaction(accountNumber, TransactionType.DEPOSIT, initialDeposit, "Initial Deposit"));
        }
    }

    // --- Getters ---
    public String getAccountNumber() { return accountNumber; }
    public Customer getOwner() { return owner; }
    public String getBranch() { return branch; }
    public double getBalance() { return balance; }
    public List<Transaction> getTransactions() { return transactions; }

    // --- Deposit ---
    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Deposit must be positive");
        balance += amount;
        transactions.add(new Transaction(accountNumber, TransactionType.DEPOSIT, amount, "Deposit"));
    }

    // --- Withdraw ---
    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal must be positive");
        if (amount > balance) throw new IllegalArgumentException("Insufficient balance");
        balance -= amount;
        transactions.add(new Transaction(accountNumber, TransactionType.WITHDRAWAL, amount, "Withdrawal"));
    }

    // --- Interest ---
    public abstract double getMonthlyRate();
    public abstract void applyMonthlyInterest();

    @Override
    public String toString() {
        return accountNumber + "," + owner.getCustomerId() + "," + this.getClass().getSimpleName() + "," +
                branch + "," + balance;
    }
}

