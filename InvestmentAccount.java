package com.example.thesystem;

import com.example.thesystem.InterestBearing;
import com.example.thesystem.Account;
import com.example.thesystem.Customer;

public class InvestmentAccount extends Account implements InterestBearing {

    public InvestmentAccount(String accountNumber, String branch, Customer customer, double initialDeposit) {
        super(accountNumber, branch, customer);
        if (initialDeposit >= 500.0) {
            deposit(initialDeposit);
        } else {
            throw new IllegalArgumentException("Minimum deposit for Investment Account is BWP500.00");
        }
    }

    @Override
    public void applyInterest() {
        balance += balance * 0.05; // 5%
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactions.add("Withdrew BWP" + amount);
        }
    }
}
