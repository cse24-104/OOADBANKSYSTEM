public class Company extends Customer {
    private String registrationNumber;
    private String registrationDate;

    public Company(String name, String address, String contactNumber, String registrationNumber, String registrationDate) {
        super(name, address, contactNumber);
        this.registrationNumber = registrationNumber;
        this.registrationDate = registrationDate;
    }

    @Override
    public String getCustomerType() {
        return "Company";
    }

    @Override
    public String toString() {
        return super.toString() + ", Reg No: " + registrationNumber + ", Reg Date: " + registrationDate;
    }
}
