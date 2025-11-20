package com.example.banksystem;

public class SavingsAccount extends Account implements InterestBearing {
    private static final double MONTHLY_RATE = 0.0005;

    public SavingsAccount(Customer owner, String branch, double initialDeposit) {
        super(owner, branch, initialDeposit);
    }

    @Override
    public double getMonthlyRate() { return MONTHLY_RATE; }

    @Override
    public void applyMonthlyInterest() {
        double interest = balance * MONTHLY_RATE;
        balance += interest;
        transactions.add(new Transaction(accountNumber, TransactionType.INTEREST, interest, "Monthly Interest"));
    }

    @Override
    public void withdraw(double amount) {
        throw new UnsupportedOperationException("Savings account does not allow withdrawals");
    }
}
