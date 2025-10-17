import java.time.LocalDate;

public class Deposit {
    private double amount;
    private LocalDate date;

    public Deposit(double amount) {
        this.amount = amount;
        this.date = LocalDate.now();
    }

    public void performDeposit(Account account) {
        if (amount >= 500 || account.getBalance() > 0) {
            account.deposit(amount);
            System.out.println("✅ Deposit successful! BW" + amount + " on " + date);
        } else {
            System.out.println("❌ Minimum opening deposit is BW500.00");
        }
    }

    @Override
    public String toString() {
        return "Deposit of BW" + amount + " on " + date;
    }
}

