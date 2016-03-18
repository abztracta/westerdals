package assignment1;

/**
 * @author Espen Rønning (RONESP13)
 */
public class Task1 {

    // constant variable declarations
    public static final int NUMBER_OF_THROWS = 30;

    public static void main(String[] args) {

        double sum = 0; // double to avoid integer division.
        System.out.println("Programmet kaster terning " + NUMBER_OF_THROWS + " ganger.");
        System.out.println("Resultat:");

        // generate, store and print random number
        for (int i = 0; i < NUMBER_OF_THROWS; i++) {
            int value = (int) (Math.random() * 6 + 1);
            sum += value;
            System.out.print(value + " ");
        }

        System.out.println();
        System.out.printf("Snittet for alle kastene er: " + "%.2f" + "\n", sum / NUMBER_OF_THROWS);
    }
}