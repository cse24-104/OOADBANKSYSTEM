package com.example.thesystem;
import com.example.thesystem.Account;
import com.example.thesystem.Customer;

public class Individual extends Customer {
    private int customerID;
    private String dateOfBirth;
    private String gender;
    private String maritalStatus;
    private String password;
    private String username;

    public Individual(String firstName, String lastName, String address, int phoneNumber, String email,
                      int customerID, String dateOfBirth, String gender, String maritalStatus,
                      String username, String password) {
        super(firstName, lastName, address, phoneNumber, email);
        this.customerID = customerID;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.username = username;
        this.password = password;
    }

    public int getCustomerID() { return customerID; }
    public String getDateOfBirth() { return dateOfBirth; }
    public String getGender() { return gender; }
    public String getMaritalStatus() { return maritalStatus; }
    public String getPassword() { return password; }
    public String getUsername() { return username; }
}
