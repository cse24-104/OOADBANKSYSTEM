public class ChequeAccount extends Account {
    private String employer;
    private String companyAddress;

    public ChequeAccount(double initialBalance, String accountNumber, Customer customer, String branch) {
        super(initialBalance, accountNumber, customer, branch);
        this.employer = employer;
        this.companyAddress = companyAddress;

    }

    public ChequeAccount(double balance, String accountNumber, Customer customer, String branch, String employer, String address) {
        super();
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance = balance - amount;
            System.out.println("withdraw " + amount + " from Cheque Account. New balance: " + balance);
            return true;
        }else {
            System.out.println("Invalid withdrawal amount");
            return false;
        }
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0){
            balance = balance + amount;
            System.out.println("Deposited " + amount + " to Cheque Account. New balance: " + balance);
        }else{
            System.out.println("Invalid deposit amount");
        }
    }

    public String getEmployer() {
        return employer;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    @Override
    public String toString() {
        return super.toString() + "[employer=" + employer + ", companyAddress=" + companyAddress + "]";
    }
}
