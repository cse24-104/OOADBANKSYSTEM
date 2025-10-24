package com.example.thesystem;

import com.example.thesystem.Account;
import com.example.thesystem.Customer;

public class Company extends Customer {
    private String companyName;
    private int registrationNumber;
    private String registrationDate;

    public Company(String companyName, String address, int phoneNumber, String email,
                   int registrationNumber, String registrationDate) {
        super(companyName, "", address, phoneNumber, email);
        this.companyName = companyName;
        this.registrationNumber = registrationNumber;
        this.registrationDate = registrationDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(int registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }
}
