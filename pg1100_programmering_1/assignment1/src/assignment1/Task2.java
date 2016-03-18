package assignment1;

/**
 * @author Espen Rønning (RONESP13)
 */
public class Task2 {

    // constant variable declarations
    public static final int ROW = 9;
    public static final int COLUMN = 9;

    public static void main(String[] args) {

        // handle printing column numbers
        System.out.print("\t\t");
        for (int i = 0; i <= COLUMN; i++) {
            System.out.print(i + "\t");
        }
        System.out.println();

        // handle printing horizontal border
        System.out.print("\t-----");
        for (int i = 0; i <= COLUMN; i++) {
            System.out.print("----");
        }
        System.out.println();

        // handle printing row numbers and vertical border
        for (int i = 0; i <= ROW; i++) {
            System.out.print(i + "\t|\t");
            // nested loop, creating the multiplication table
            for (int j = 0 ; j <= COLUMN; j++) {
                System.out.print((i * j) + "\t");
            }
            System.out.println();
        }
    }
}
