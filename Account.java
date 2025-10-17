import java.util.Scanner;
import java.time.LocalDate;

//abstract Account class
public abstract class Account {
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

    public Account() {

    }

    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getBranch() {
        return branch;
    }
    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getOpenDate() {
        return openDate;
    }
    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[accountNumber=" + accountNumber + ", balance=" + balance + ", branch=" + branch+"]";
    }

    public abstract boolean withdraw(double amount);

    public abstract void deposit(double amount);
}