package no.woact.pg4100.assignment1.ronesp13.models;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Espen Rønning - ronesp13@student.westerdals.no
 */
public class CarRental {

    private List<RentalCar> cars;
    private Lock lock;
    private Condition condition;

    public CarRental(List<RentalCar> rentalCars) {
        cars = rentalCars;
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    public void rentCar(Customer customer) {
        lock.lock();
        try {
            RentalCar car;
            while ((car = getAvailableCar()) == null) {
                printBusyMessage(customer);
                condition.await();
            }
            car.setCustomer(customer);
            rentReport(customer, car);
            statusReport();
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void returnCar(Customer customer) {
        lock.lock();
        try {
            RentalCar car = getCarWithOwner(customer);
            car.setCustomer(null);
            returnReport(customer, car);
            statusReport();
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    private RentalCar getAvailableCar() {
        Optional<RentalCar> optional = cars.stream().filter(rentalCar -> rentalCar.getCustomer() == null).findFirst();
        return optional.isPresent() ? optional.get() : null;
    }

    private RentalCar getCarWithOwner(Customer customer) {
        Optional<RentalCar> optional = cars.stream().filter(rentalCar -> rentalCar.getCustomer() == customer).findFirst();
        return optional.isPresent() ? optional.get() : null;
    }

    private void printBusyMessage(Customer customer) {
        System.out.println(customer.getName() + " må vente til det er biler tilgjengelig.");
    }

    private void rentReport(Customer customer, RentalCar car) {
        System.out.println(customer.getName() + " har lånt " + car.getRegistrationNumber() + ".");
    }

    private void returnReport(Customer customer, RentalCar car) {
        System.out.println(customer.getName() + " leverte inn " + car.getRegistrationNumber() + ".");
    }

    private void statusReport() {
        System.out.println("*********** Status for utleiebilene *****************\n" +
                statusReportCar() + "\n" +
                "*********** Status slutt ****************************\n");
    }

    private String statusReportCar() {
        String report = "";
        for (RentalCar car : cars) {
            report += car.getRegistrationNumber() + " - ";
            if (car.getCustomer() == null) {
                report += "ledig\n";
            } else {
                report += "leid ut til " + car.getCustomer() + "\n";
            }
        }
        return report.substring(0, report.length() - 1);
    }
}
