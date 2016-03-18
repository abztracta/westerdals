package no.woact.pg4100.assignment1.ronesp13.models;

/**
 * @author Espen Rønning - ronesp13@student.westerdals.no
 */
public class RentalCar {

    private String registrationNumber;
    private Customer customer;

    public RentalCar(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    @Override
    public String toString() {
        return registrationNumber;
    }
}
