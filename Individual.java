package com.example.thesystem;

import com.example.thesystem.Customer;

public class Individual extends Customer {
    private String username;
    private String password;
    private int customerID;
    private String dateOfBirth;
    private String gender;
    private String maritalStatus;

    public Individual(String firstName, String lastName, String address, String phone, String email,
                      int customerID, String dateOfBirth, String gender, String maritalStatus,
                      String username, String password) {
        super(firstName, lastName, address, phone, email);
        this.customerID = customerID;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getCustomerID() { return customerID; }

    @Override
    public String getOwnerKey() {
        return username; // use username to match accounts.txt owner field
    }
}
