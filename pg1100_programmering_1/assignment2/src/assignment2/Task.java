package assignment2;

import java.util.Random;
import java.util.Scanner;

/**
 * In this assignment I've renamed the methods that were
 * given in Norwegian. The print-out still remains Norwegian.
 *
 * @author Espen Rønning (RONESP13)
 */
public class Task {

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        commandLine();
    }

    public static void printNumbersA(int limit) {
        for (int i = 1; i <= limit; i++) {
            System.out.printf("%5s", i);
        }
        System.out.println();
    }

    public static void printNumbersB(int limit) {
        int textWidth = 1;
        int numbersInLine = 0;

        for (int i = 1; i <= limit; i++) {
            System.out.printf("%" + textWidth + "s", i);
            numbersInLine++;
            if (numbersInLine == textWidth) {
                numbersInLine = 0;
                textWidth++;
                System.out.println();
            } else if (i == limit) { // add a newline for consistency
                System.out.println();
            }
        }
    }

    public static void temperatures(Scanner scanner) {
        double lowestValue = 0;
        double highestValue = 0;
        double sum = 0;
        int registrations = 0;
        double input;

        System.out.println("Skriv inn temperaturer - avslutt med -100");
        do {
            System.out.print("-> ");
            input = scanner.nextDouble();
            scanner.nextLine(); // clear buffer

            if (input != -100) {
                if (registrations == 0) {
                    lowestValue = input;
                    highestValue = input;
                } else {
                    if (lowestValue > input) {
                        lowestValue = input;
                    }
                    if (highestValue < input) {
                        highestValue = input;
                    }
                }
                sum += input;
                registrations++;
            }
        } while (input != -100);
        if (registrations == 0) {
            System.out.println("Ingen verdier registrert.");
        } else {
            System.out.printf("Høyeste temperatur: %.1f\n", highestValue);
            System.out.printf("Laveste temperatur: %.1f\n", lowestValue);
            System.out.printf("Gjennomsnittstemperatur: %.1f\n", (sum / registrations));
            System.out.println("Antall registrerte temperaturer: " + registrations);
        }
    }

    public static long sixes(int value) {
        final int SIX = 6;
        int sixesInARow = 0;
        long tosses = 0;

        while (sixesInARow < value) {
            int toss = RANDOM.nextInt(SIX) + 1;
            if (toss == SIX) {
                sixesInARow++;
            } else {
                sixesInARow = 0;
            }
            tosses++;
        }
        return tosses;
    }

    /**
     * In this task I'm making the assumption that the last word in a text should be ended with a punctuation mark.<br/>
     *
     * To generate a text, this method makes use of the helper-methods<br/>
     * <code>createText()</code>,<br/>
     * <code>createLine()</code>,<br/>
     * <code>createWord()</code> and<br/>
     * <code>createCharacter()</code>
     */
    public static void bogusText() {
        System.out.print(createText());
    }

    private static String createText() {
        String text = "";
        int lines = RANDOM.nextInt(3) + 10;
        for (int i = 0; i < lines; i++) {
            text = createLine(text) + "\n";
        }
        if (!text.endsWith(".\n")) {
            text = text.substring(0, text.length() - 1);
            text += ".\n";
        }
        return text;
    }

    private static String createLine(String text) {
        int wordsInLine = RANDOM.nextInt(3) + 7;
        for (int i = 0; i < wordsInLine; i++) {
            text = createWord(text);
            if (RANDOM.nextInt(5) == 0) {
                text += ".";
            }
            if (i != wordsInLine - 1) {
                text += " ";
            }
        }
        return text;
    }

    private static String createWord(String text) {
        int charactersInWord = RANDOM.nextInt(8) + 3;
        for (int i = 0; i < charactersInWord; i++) {
            if (text.length() == 0 || text.endsWith(". ") || text.endsWith(".\n")) {
                text += createCharacter(true);
            } else {
                text += createCharacter(false);
            }
        }
        return text;
    }

    private static char createCharacter(boolean isUpperCase) {
        char character = (char) RANDOM.nextInt(26);
        if (isUpperCase) {
            return character += 'A'; // returns a letter in the range A-Z
        } else {
            return character += 'a'; // returns a letter in the range a-z
        }
    }

    private static void menu() {
        System.out.println("\t\tMeny");
        System.out.println("---------------");
        System.out.println("q.\tAvslutt");
        System.out.println("0.\tMeny");
        System.out.println("1.\tprintNumbersA");
        System.out.println("2.\tprintNumbersB");
        System.out.println("3.\ttemperaturer");
        System.out.println("4.\tseksere");
        System.out.println("5.\ttulletekst");
    }

    public static void commandLine() {
        Scanner scanner = new Scanner(System.in);
        menu();
        String input;

        do {
            System.out.print("Velg handling - (Eks: 1 for å velge printNumbersA): ");
            input = scanner.nextLine();
            if (input.equals("0")) {
                menu();
            } else if (input.equals("1")) {
                System.out.print("Oppgi øvre grense: ");
                int value = scanner.nextInt();
                scanner.nextLine(); // clear buffer
                printNumbersA(value);
            } else if (input.equals("2")) {
                System.out.print("Oppgi øvre grense: ");
                int value = scanner.nextInt();
                scanner.nextLine(); // clear buffer
                printNumbersB(value);
            } else if (input.equals("3")) {
                temperatures(scanner);
            } else if (input.equals("4")) {
                System.out.print("Hvor mange seksere på rad: ");
                int value = scanner.nextInt();
                scanner.nextLine(); // clear buffer
                long tosses = sixes(value);
                System.out.println("Det tok " + tosses + " kast før det kom " + value + " seksere på rad.");
            } else if (input.equals("5")) {
                bogusText();
            } else if (input.equals("q")) {
                System.out.println("Avslutter program.");
            } else {
                System.out.println("Ugyldig handling.");
            }
        } while (!input.equals("q"));
    }
}