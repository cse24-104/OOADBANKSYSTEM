package com.example.thesystem;

import  com.example.thesystem.Customer;
import java.util.ArrayList;


public class Company extends Customer {
    private String companyName;
    private int registrationNumber;
    private String registrationDate;

    public Company(String companyName, String address, String phone, String email,
                   int registrationNumber, String registrationDate) {
        super(companyName, "", address, phone, email);
        this.companyName = companyName;
        this.registrationNumber = registrationNumber;
        this.registrationDate = registrationDate;
    }

    public String getCompanyName() { return companyName; }
    public int getRegistrationNumber() { return registrationNumber; }
    public String getRegistrationDate() { return registrationDate; }

    @Override
    public String getOwnerKey() {
        return companyName;
    }
}
