import java.awt.*;
import java.util.ArrayList;

public class Customer {
    String firstName;
    String surname;
    String address;
    boolean employed;
    List<Account> accounts;

    public Customer(String firstName, String surname, String address, boolean employed) {
        this.firstName = firstName;
        this.surname = surname;
        this.address = address;
        this.employed = employed;
        this.accounts = new ArrayList<>();
    }
}