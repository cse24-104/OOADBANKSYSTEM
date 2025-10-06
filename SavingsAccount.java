public class SavingsAccount extends Account implements interestBearing{
    public SavingsAccount(double initialBalance, String accountNumber, Customer customer, String branch) {
        super(initialBalance, accountNumber, customer, branch);
    }

    @Override
    public boolean withdraw(double amount) {
        System.out.println("Withdrawals not allowed from SavingsAccount");
        return false;
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount + " to SavingsAccount.New balance: " + balance);
        }else{
            System.out.println("invalid deposit amount");
        }
    }
     @Override
    public void applyInterest() {
         double interest = balance * 0.0005;
        balance += interest;
        System.out.println("Interest applied to SavingsAccount");
    }
}