package no.woact.pg4100.assignment2.ronesp13.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
* @author Espen Rønning - ronesp13@student.westerdals.no
*/
public class QuizClient {

    private static final String HOSTNAME = "localhost";
    private static final int PORT = 2390;

    public QuizClient() {
        listen();
    }

    public void listen() {
        try (Socket socket = new Socket(HOSTNAME, PORT);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String input = (String) in.readObject();
                System.out.println(input);
                if (input.equals("Takk for at du deltok!")) {
                    out.writeObject("shutdown");
                    break;
                }
                String output = scanner.nextLine();
                out.writeObject(output);
            }
        } catch (NoSuchElementException e) {
            System.out.println("Avslutter.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Tilkoblingen til server har blitt avsluttet.");
        }
    }
}
