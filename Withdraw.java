import java.time.LocalDate;

public class Withdraw {
    private double amount;
    private LocalDate date;

    public Withdraw(double amount) {
        this.amount = amount;
        this.date = LocalDate.now();
    }

    public void performWithdraw(Account account) {
        if (account.withdraw(amount)) {
            System.out.println("✅ Withdrawal of BW" + amount + " completed on " + date);
        } else {
            System.out.println("❌ Withdrawal failed. Check balance or account type.");
        }
    }

    @Override
    public String toString() {
        return "Withdrawal of BW" + amount + " on " + date;
    }
}
