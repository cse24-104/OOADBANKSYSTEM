package com.example.thesystem;

import com.example.thesystem.Account;

public interface Withdraw {
    public static void process(Account account, double amount) {
        account.withdraw(amount);
    }
}
