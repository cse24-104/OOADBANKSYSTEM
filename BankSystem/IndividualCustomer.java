package com.example.banksystem;

import java.time.LocalDate;

public class IndividualCustomer extends Customer {
    private String nationalId;
    private LocalDate dateOfBirth;

    public IndividualCustomer(String customerId, String name, String email, String phone,
                              String nationalId, LocalDate dateOfBirth) {
        super(customerId, name, email, phone);
        this.nationalId = nationalId;
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationalId() { return nationalId; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }

    @Override
    public String getCustomerType() { return "Individual"; }

    @Override
    public String toString() {
        return "IndividualCustomer{" +
                "ID='" + customerId + '\'' +
                ", Name='" + name + '\'' +
                ", Email='" + email + '\'' +
                ", Phone='" + phone + '\'' +
                ", NationalId='" + nationalId + '\'' +
                ", DOB=" + dateOfBirth +
                ", Employed=" + employed +
                '}';
    }
}
