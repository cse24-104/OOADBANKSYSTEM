import java.io.Serializable;

public class InvestmentAccount extends Account implements InterestBearing{
    public InvestmentAccount(double initialBalance, String accountNumber, Customer customer, String branch) {
        super(initialBalance, accountNumber, customer, branch);
        if (initialBalance < 500){
            throw new IllegalArgumentException("Investment account requires minimum BW500.00 initial deposit");
        }
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <=balance) {
            balance = balance - amount;
            System.out.println("Withdrew: " + amount + "From Investment Account. New balance: " + balance);
            return true;
        }else{
            System.out.println("Invalid withdrawal amount");
            return false;
        }
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance = balance + amount;
            System.out.println("Deposited: " + amount + "to Investment Account. New balance: " + balance);
        }else{
            System.out.println("Invalid deposit amount");
        }
    }

    @Override
    public void applyInterest() {
        double interest = balance * 0.05;//0.05% monthly interest
        balance = balance + interest;
        System.out.println("Applied Interest of " + interest + "to Investment Account. New balance: " + balance);

    }
}