package com.example.banksystem;

import java.util.ArrayList;
import java.util.List;

public class CompanyCustomer extends Customer {
    private String registrationNumber;
    private String contactPerson;
    private boolean employed; // new property
    private List<Account> accounts = new ArrayList<>();

    public CompanyCustomer(String customerId, String name, String email, String phone,
                           String registrationNumber, String contactPerson, boolean employed) {
        super(customerId, name, email, phone);
        this.registrationNumber = registrationNumber;
        this.contactPerson = contactPerson;
        this.employed = employed;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public boolean isEmployed() {
        return employed;
    }

    public void setEmployed(boolean employed) {
        this.employed = employed;
    }

    @Override
    public String getCustomerType() {
        return "";
    }

    @Override
    public List<Account> getAccounts() {
        return accounts;
    }

    @Override
    public void addAccount(Account account) {
        accounts.add(account);
    }
}
