package assignment3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * This program assumes that the file read is of a valid format.
 *
 * @author Espen Rønning (RONESP13)
 */
public class Task2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        presentation();
        System.out.print("Ønsker du å prøve dette? ");
        String response = scanner.nextLine();
        if (doTry(response)) {
            boolean run = true;
            while (run) {
                File file = getFileName(scanner);
                int verbsCount = getNumberOfVerbs(scanner);
                execute(scanner, file, verbsCount);
                System.out.print("Vil du prøve en gang til? ");
                if (!doTry(scanner.nextLine())) {
                    run = false;
                }
            }
        }
        scanner.close();
        System.out.println("Program avslutter.");
    }

    public static void presentation() {
        System.out.println("Velkommen til programmet som tester deg i bøyning av engelske verb. \n" +
                "Du kan velge å bli testet i opp til 88 verb. \n" +
                "Du vil få en karakter når testen er ferdig.\n");
    }

    public static boolean doTry(String response) {
        if (response.equalsIgnoreCase("ja")) {
            return true;
        } else {
            return false;
        }
    }

    public static File getFileName(Scanner scanner) {
        File file = null;
        boolean valid = false;
        while (!valid) {
            System.out.print("Oppgi navn på verbfil: ");
            String filename = scanner.nextLine();
            file = new File(filename);
            if (file.canRead()) {
                valid = true;
            } else {
                System.out.println("Filen kan ikke leses.");
                valid = false;
            }
        }
        return file;
    }

    public static int getNumberOfVerbs(Scanner scanner) {
        boolean valid = false;
        int verb = 0;
        while (!valid) {
            System.out.print("Oppgi ønsket antall verb: ");
            if (scanner.hasNextInt()) {
                valid = true;
                verb = scanner.nextInt();
            } else {
                System.out.println("Du må oppgi et heltall.");
            }
            scanner.nextLine(); // clear buffer
        }
        return verb;
    }

    public static void execute(Scanner keyboard, File file, int lines) {
        try {
            Random random = new Random();
            Scanner scanner = new Scanner(file);
            int linesRead = 0;
            int count = 0;
            String[] verbs = new String[3];
            int correctAnswers = 0;

            while (scanner.hasNext()) {
                verbs[count++] = scanner.next(); // reads a token
                if (count == 3) { // when third token is read
                    linesRead++;
                    count = 0;

                    int index = random.nextInt(3);
                    printWords(verbs, index);
                    correctAnswers += validateAnswer(keyboard, verbs, index);
                }
                if (linesRead == lines) {
                    break;
                }
            }
            System.out.println();
            String username = requestUsername(keyboard);
            printResult(username, (double) correctAnswers / linesRead);

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void printWords(String[] verbs, int index) {
        for (int i = 0; i < verbs.length; i++) {
            if (i == index) {
                System.out.print("...");
            } else {
                System.out.print(verbs[i]);
            }
            if (i < verbs.length - 1) {
                System.out.print(" - ");
            }
        }
        System.out.println();
    }

    public static int validateAnswer(Scanner scanner, String[] verbs, int index) {
        System.out.print("Skriv inn formen som mangler: ");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase(verbs[index])) {
            return 1;
        } else {
            System.out.println("Feil: " + verbs[index]);
            return 0;
        }
    }

    public static String requestUsername(Scanner scanner) {
        System.out.print("Oppgi navnet ditt: ");
        return scanner.nextLine();
    }

    public static void printResult(String username, double score) {
        int percent = (int) (score * 100);
        System.out.println(username);
        System.out.println("Score: " + percent + "%");
        System.out.print("Karakter: ");
        if (percent >= 90) {
            System.out.println("A");
        } else if (percent >= 80 && percent < 90) {
            System.out.println("B");
        } else if (percent >= 70 && percent < 80) {
            System.out.println("C");
        } else if (percent >= 60 && percent < 70) {
            System.out.println("D");
        } else if (percent >= 50 && percent < 60) {
            System.out.println("E");
        } else {
            System.out.println("F");
        }
    }
}
