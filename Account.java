import java.util.Scanner;
import java.time.LocalDate;

//interface for accounts that earn interest
interface InterestBearing{
    void applyInterest();
}

//abstract Account class
    abstract class Account {
    String accountNumber;
    double balance;
    String branch;
    Customer customer;
    LocalDate openDate;

    public Account(double initialBalance, String accountNumber, Customer customer, String branch) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.branch = branch;
        this.customer = customer;
        this.openDate = LocalDate.now();
    }
    //abstract methods to be implemented by subclasses
    public abstract boolean withdraw(double amount);
    public abstract void deposit(double amount);

    //common method for all accounts
    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[accountNumber=" + accountNumber + ", balance=" + balance + ", branch=" + branch+"]";
    }
}