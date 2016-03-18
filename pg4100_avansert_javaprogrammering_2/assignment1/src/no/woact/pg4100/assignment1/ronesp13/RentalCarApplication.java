package no.woact.pg4100.assignment1.ronesp13;

import no.woact.pg4100.assignment1.ronesp13.models.CarRental;
import no.woact.pg4100.assignment1.ronesp13.models.Customer;
import no.woact.pg4100.assignment1.ronesp13.models.RentalCar;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * @author Espen Rønning - ronesp13@student.westerdals.no
 */
public class RentalCarApplication {

    private static final int MINIMUM_CUSTOMER_THREADS = 5;

    private static CarRental carRental;
    private static CountDownLatch countDownLatch;
    private static ExecutorService executorService;

    public static void main(String[] args) {
        executorService = Executors.newCachedThreadPool();
        List<RentalCar> rentalCars = Arrays.asList(
                new RentalCar("RF1111"),
                new RentalCar("RF2222"),
                new RentalCar("RF3333"),
                new RentalCar("RF4444"),
                new RentalCar("RF5555")
        );
        carRental = new CarRental(rentalCars);
        countDownLatch = new CountDownLatch(MINIMUM_CUSTOMER_THREADS);
        orderLoop();
        System.out.println("*** Programmet avslutter ***");
        System.exit(0);
    }

    private static void orderLoop() {
        while (true) {
            String input = showInputDialog(null, "Skriv inn et kundenavn:\n(exit avslutter programmet)", "PG4100 Assignment 1", PLAIN_MESSAGE);
            if (input == null) {
                showMessageDialog(null, "Du må skrive inn et navn eller avslutte!", "PG4100 Assignment 1", ERROR_MESSAGE);
            } else if (input.equals("exit")) {
                break;
            } else {
                startCustomer(input);
            }
        }
    }

    private static void startCustomer(String name) {
        executorService.execute(new Customer(carRental, countDownLatch, name));
    }
}
