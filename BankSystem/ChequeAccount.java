package com.example.banksystem;

public class ChequeAccount extends Account {
    public ChequeAccount(Customer owner, String branch, double initialDeposit) {
        super(owner, branch, initialDeposit);
        if (!owner.isEmployed()) {
            throw new IllegalArgumentException("Cheque account requires employed customer");
        }
    }

    @Override
    public double getMonthlyRate() { return 0; }

    @Override
    public void applyMonthlyInterest() {}
}
