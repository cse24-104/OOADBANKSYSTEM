package com.example.banksystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private String accountNumber;
    private TransactionType type;
    private double amount;
    private String description;
    private LocalDateTime date;

    public Transaction(String accountNumber, TransactionType type, double amount, String description) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = LocalDateTime.now();
    }

    public String getAccountNumber() { return accountNumber; }
    public TransactionType getType() { return type; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public LocalDateTime getDate() { return date; }

    @Override
    public String toString() {
        return accountNumber + "," + type + "," + amount + "," + description + "," + date;
    }

    public String getFormattedTransaction() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[%s] %s: %.2f BWP (%s)", date.format(fmt), type, amount, description);
    }
}
