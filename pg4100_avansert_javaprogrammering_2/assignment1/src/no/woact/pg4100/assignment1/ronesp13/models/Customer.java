package no.woact.pg4100.assignment1.ronesp13.models;

import no.woact.pg4100.assignment1.ronesp13.util.Rand;

import java.util.concurrent.CountDownLatch;

/**
 * @author Espen Rønning - ronesp13@student.westerdals.no
 */
public class Customer implements Runnable {

    private static final int MINIMUM_WAIT_FOR_CONTACT = 1000;
    private static final int MAXIMUM_WAIT_FOR_CONTACT = 10000;
    private static final int MINIMUM_WAIT_FOR_RETURN = 1000;
    private static final int MAXIMUM_WAIT_FOR_RETURN = 3000;

    private CarRental carRental;
    private String name;
    private CountDownLatch countDownLatch;

    public Customer(CarRental carRental, CountDownLatch countDownLatch, String name) {
        this.carRental = carRental;
        this.countDownLatch = countDownLatch;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        countDownLatch.countDown();
        try {
            countDownLatch.await();
            while (true) {
                Thread.sleep(Rand.nextInt(MINIMUM_WAIT_FOR_CONTACT, MAXIMUM_WAIT_FOR_CONTACT) + 1);
                carRental.rentCar(this);
                Thread.sleep(Rand.nextInt(MINIMUM_WAIT_FOR_RETURN, MAXIMUM_WAIT_FOR_RETURN) + 1);
                carRental.returnCar(this);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
