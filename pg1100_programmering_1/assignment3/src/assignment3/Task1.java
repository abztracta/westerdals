package assignment3;

import java.util.Scanner;

/**
 * @author Espen Rønning (RONESP13)
 */
public class Task1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Oppgi tallet som skal sjekkes: ");
        long endurance = getInput(scanner);
        System.out.println("Tallet " + endurance + " har utholdenhet: " + endurance(endurance));

        System.out.print("Oppgi nedre grense: ");
        long lowerLimit = getInput(scanner);
        System.out.print("Oppgi øvre grense: ");
        long upperLimit = getInput(scanner);
        long maxEndurance = maxEndurance(lowerLimit, upperLimit);
        System.out.println("Tallet " + maxEndurance + " har størst utholdenhet: " + endurance(maxEndurance));

        scanner.close();
    }

    public static long endurance(long value) {
        long endurance = 0;
        while (value != 1) {
            if (value % 2 == 0) {
                value /= 2;
            } else {
                value = (value * 3) + 1;
            }
            endurance++;
        }
        return endurance;
    }

    public static long maxEndurance(long lowerLimit, long upperLimit) {
        long value = 0;
        long maxEndurance = 0;
        for (long i = lowerLimit; i <= upperLimit; i++) {
            long endurance = endurance(i);
            if (endurance > maxEndurance) {
                maxEndurance = endurance;
                value = i;
            }
        }
        return value;
    }

    public static long getInput(Scanner scanner) {
        boolean valid = false;
        long value = 0;
        while (!valid) {
            if (scanner.hasNextLong()) {
                value = scanner.nextLong();
                scanner.nextLine(); // clear buffer;
                valid = true;
            } else {
                System.out.println("Feil: Du må skrive et heltall.");
                scanner.nextLine();
                System.out.print("Prøv på nytt: ");
            }
        }
        return value;
    }
}
