package com.example.thesystem;

import com.example.thesystem.Account;
import com.example.thesystem.Customer;
import com.example.thesystem.InterestBearing;

public class SavingsAccount extends Account implements InterestBearing {
    public SavingsAccount(String accountNumber, String branch, Customer customer) {
        super(accountNumber, branch, customer);
    }
    public SavingsAccount(String accountNumber, String branch, Customer customer, double balance) {
        super(accountNumber, branch, customer, balance);
    }

    @Override
    public void applyInterest() {
        double rate = 0.0005; // 0.05%
        double interest = getBalance() * rate;
        setBalance(getBalance() + interest);
        addTransaction(String.format("Interest,%.2f,%s", interest, java.time.LocalDateTime.now()));
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
}
