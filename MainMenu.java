import java.time.LocalDate;
import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Account account = null;

        System.out.println("====== Welcome to SmartBank System ======");
        System.out.println("Open Account Type:");
        System.out.println("1. Individual");
        System.out.println("2. Company");
        System.out.print("Enter choice: ");
        int type = input.nextInt();
        input.nextLine();

        Customer customer = null;
        if (type == 1) {
            System.out.print("Enter Full Name: ");
            String name = input.nextLine();
            System.out.print("Enter ID Number: ");
            String id = input.nextLine();
            System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
            LocalDate dob = LocalDate.parse(input.nextLine());
            System.out.print("Enter Address: ");
            String address = input.nextLine();
            System.out.print("Enter Phone: ");
            String phone = input.nextLine();
            customer = new Customer(name, id, dob, address, phone) {
                @Override
                public String getCustomerType() {
                    return "";
                }
            };
        } else {
            System.out.print("Enter Company Name: ");
            String name = input.nextLine();
            System.out.print("Enter Registration Number: ");
            String reg = input.nextLine();
            System.out.print("Enter Registration Date (YYYY-MM-DD): ");
            LocalDate regDate = LocalDate.parse(input.nextLine());
            System.out.print("Enter Address: ");
            String address = input.nextLine();
            System.out.print("Enter Phone: ");
            String phone = input.nextLine();
            customer = new Customer(name, reg, regDate, address, phone) {
                @Override
                public String getCustomerType() {
                    return "";
                }
            };
        }

        System.out.print("Enter Account Number: ");
        String accNo = input.nextLine();
        System.out.print("Enter Branch: ");
        String branch = input.nextLine();
        System.out.print("Enter Initial Deposit (Minimum BW500): ");
        double initial = input.nextDouble();

        if (initial < 500) {
            System.out.println("❌ You must deposit at least BW500 to open an account!");
            return;
        }

        System.out.println("Choose Account Type:");
        System.out.println("1. Savings");
        System.out.println("2. Cheque");
        System.out.println("3. Investment");
        int accType = input.nextInt();
        input.nextLine();

        switch (accType) {
            case 1:
                account = new SavingsAccount(initial, accNo, customer, branch);
                break;
            case 2:
                System.out.print("Enter Employer: ");
                String emp = input.nextLine();
                System.out.print("Enter Company Address: ");
                String compAddr = input.nextLine();
                account = new ChequeAccount(initial, accNo, customer, branch, emp, compAddr);
                break;
            case 3:
                account = new InvestmentAccount(initial, accNo, customer, branch);
                break;
            default:
                System.out.println("Invalid account type!");
                return;
        }

        // Transaction Menu
        int choice;
        do {
            System.out.println("\n====== ACCOUNT MENU ======");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Apply Interest");
            System.out.println("4. View Details");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Deposit Amount: ");
                    double dep = input.nextDouble();
                    new Deposit(dep).performDeposit(account);
                    break;
                case 2:
                    System.out.print("Enter Withdrawal Amount: ");
                    double w = input.nextDouble();
                    new Withdraw(w).performWithdraw(account);
                    break;
                case 3:
                    if (account instanceof InterestBearing) {
                        ((InterestBearing) account).applyInterest();
                    } else {
                        System.out.println("❌ This account type does not earn interest.");
                    }
                    break;
                case 4:
                    System.out.println(account);
                    break;
                case 0:
                    System.out.println("👋 Thank you for banking with SmartBank!");
                    break;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        } while (choice != 0);
    }
}
