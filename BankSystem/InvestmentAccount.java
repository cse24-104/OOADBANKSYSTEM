package com.example.banksystem;

public class InvestmentAccount extends Account implements InterestBearing {
    private static final double MONTHLY_RATE = 0.05;

    public InvestmentAccount(Customer owner, String branch, double initialDeposit) {
        super(owner, branch, initialDeposit);
        if (initialDeposit < 500) {
            throw new IllegalArgumentException("Investment requires minimum 500 BWP");
        }
    }

    @Override
    public double getMonthlyRate() { return MONTHLY_RATE; }

    @Override
    public void applyMonthlyInterest() {
        double interest = balance * MONTHLY_RATE;
        balance += interest;
        transactions.add(new Transaction(accountNumber, TransactionType.INTEREST, interest, "Investment Monthly Interest"));
    }
}
