package com.example.thesystem;

import  com.example.thesystem.Account;
import com.example.thesystem.Customer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChequeAccount extends Account {
    private String employerName;
    private String employerAddress;

    public ChequeAccount(String accountNumber, String branch, Customer customer, String employerName, String employerAddress) {
        super(accountNumber, branch, customer);
        this.employerName = employerName;
        this.employerAddress = employerAddress;
    }

    public ChequeAccount(String accountNumber, String branch, Customer customer, String employerName, String employerAddress, double balance) {
        super(accountNumber, branch, customer, balance);
        this.employerName = employerName;
        this.employerAddress = employerAddress;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) return;
        if (amount <= getBalance()) {
            setBalance(getBalance() - amount);
            addTransaction(String.format("Withdraw,%.2f,%s", amount, java.time.LocalDateTime.now()));
        } else {
            addTransaction(String.format("FailedWithdraw,%.2f,%s", amount, java.time.LocalDateTime.now()));
        }
    }

    public String getEmployerName() { return employerName; }
    public String getEmployerAddress() { return employerAddress; }
}
