package no.woact.pg4100.assignment1.ronesp13.models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class CarRentalTest {

    private CarRental rental;
    private List<RentalCar> rentalCars;
    private List<Customer> customers;

    @Before
    public void setUp() throws Exception {
        rentalCars = Arrays.asList(
                new RentalCar("TEST-12345"),
                new RentalCar("TEST-23456"),
                new RentalCar("TEST-34567"),
                new RentalCar("TEST-45678"),
                new RentalCar("TEST-56789")
        );
        rental = new CarRental(rentalCars);
        customers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            customers.add(new Customer(rental, null, "Customer " + 1));
        }
    }

    @Test
    public void testRentFirstCar() throws Exception {
        rental.rentCar(customers.get(0));
        assertNotNull(rentalCars.get(0));
    }

    @Test
    public void testReturnCar() throws Exception{
        rental.rentCar(customers.get(0));
        rental.returnCar(customers.get(0));
    }

}