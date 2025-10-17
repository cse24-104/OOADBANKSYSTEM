public class SavingsAccount extends Account implements InterestBearing {
    public SavingsAccount(double initialBalance, String accountNumber, Customer customer, String branch) {
        super(initialBalance, accountNumber, customer, branch);
    }

    @Override
    public boolean withdraw(double amount) {
        System.out.println("❌ Withdrawals not allowed from Savings Account.");
        return false;
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("✅ Deposited BW" + amount + " to Savings Account. New balance: BW" + balance);
        } else {
            System.out.println("❌ Invalid deposit amount.");
        }
    }

    @Override
    public double applyInterest() {
        double interest = balance * 0.0005;
        balance += interest;
        System.out.println("💰 Interest of BW" + interest + " applied. New balance: BW" + balance);
        return interest;
    }
}

