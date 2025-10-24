package com.example.thesystem;

import com.example.thesystem.Account;
import java.util.ArrayList;
import java.util.List;

public abstract class Customer {
    protected String firstName;
    protected String lastName;
    protected String address;
    protected int phoneNumber;
    protected String email;
    protected List<Account> accounts = new ArrayList<>();

    public Customer(String firstName, String lastName, String address, int phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Customer() { }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public int getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(int phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public void addAccount(Account account) { accounts.add(account); }
    public List<Account> getAccounts() { return accounts; }
}
