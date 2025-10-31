package com.example.thesystem;

import com.example.thesystem.Account;
import java.util.ArrayList;
import java.util.List;

public abstract class Customer {
    protected String firstName;
    protected String lastName;
    protected String address;
    protected String phone;
    protected String email;
    protected List<Account> accounts = new ArrayList<>();

    public Customer(String firstName, String lastName, String address, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public Customer() {}

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }

    public List<Account> getAccounts() { return accounts; }
    public void addAccount(Account account) { accounts.add(account); }

    // helper display name used in files
    public String getOwnerKey() {
        // For Individuals we use username (overridden), for Company use companyName (overridden)
        return firstName + " " + lastName;
    }

    public char[] getPhoneNumber() {
        return new char[0];
    }

    public Object getUsername() {
        return null;
    }
}
