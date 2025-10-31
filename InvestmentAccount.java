package com.example.thesystem;

import com.example.thesystem.Account;
import com.example.thesystem.Customer;
import com.example.thesystem.InterestBearing;

public class InvestmentAccount extends Account implements InterestBearing {
    public InvestmentAccount(String accountNumber, String branch, Customer customer, double initialDeposit) {
        super(accountNumber, branch, customer);
        if (initialDeposit < 500.0) throw new IllegalArgumentException("Minimum deposit for Investment Account is BWP500.00");
        deposit(initialDeposit);
    }
    public InvestmentAccount(String accountNumber, String branch, Customer customer, double initialDeposit, double balance) {
        super(accountNumber, branch, customer, balance);
    }

    @Override
    public void applyInterest() {
        double rate = 0.05; // 5%
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
