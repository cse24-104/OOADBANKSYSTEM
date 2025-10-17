import java.time.LocalDate;

public abstract class Customer {
    int id;
    String firstname;
    String surname;
    String address;
    String phone;
    String email;

    public Customer(int id, String firstname, String surname, String address, String phone) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public Customer(String firstname, String address) {
    }

    public Customer(String name) {
    }

    public Customer(String name, String address, String contactNumber) {
    }

    public Customer(String name, String id, LocalDate dob, String address, String phone) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public abstract String getCustomerType();

    @Override
    public String toString(){
        return firstname + " " + surname + " " + address + " " + phone;
    }
}
