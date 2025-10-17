
public class Individual extends Customer {
    private String nationalId;
    private String dateOfBirth;

    public Individual(String name, String address, String contactNumber, String nationalId, String dateOfBirth) {
        super(name, address, contactNumber);
        this.nationalId = nationalId;
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String getCustomerType() {
        return "Individual";
    }

    @Override
    public String toString() {
        return super.toString() + ", National ID: " + nationalId + ", Date of Birth: " + dateOfBirth;
    }
}
