package com.example.thesystem;

import com.example.thesystem.InterestBearing;
import com.example.thesystem.Account;
import com.example.thesystem.Customer;

public class SavingsAccount extends Account implements InterestBearing {

    public SavingsAccount(String accountNumber, String branch, Customer customer) {
        super(accountNumber, branch, customer);
    }

    @Override
    public void applyInterest() {
        balance += balance * 0.0005; // 0.05%
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactions.add("Withdrew BWP" + amount);
        }
    }
}

