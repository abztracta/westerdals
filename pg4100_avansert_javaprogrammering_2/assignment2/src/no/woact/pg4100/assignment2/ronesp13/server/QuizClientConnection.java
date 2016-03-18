package no.woact.pg4100.assignment2.ronesp13.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

/**
* @author Espen Rønning - ronesp13@student.westerdals.no
*/
public class QuizClientConnection implements Runnable {

    private static final Logger log = Logger.getLogger(QuizClientConnection.class.getName());
    private Socket socket;

    public QuizClientConnection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            QuizProtocol protocol = new QuizProtocol();
            String output = protocol.handleInput(null);
            out.writeObject(output);
            while (true) {
                output = protocol.handleInput((String) in.readObject());
                out.writeObject(output);
                if (output.equals("Takk for at du deltok!")) {
                    break;
                }
            }
            log.info("A client disconnected from the server.");
        } catch (EOFException e) {
            log.info("Something unexpected happened. Client disconnected from server.");
        } catch (IOException | ClassNotFoundException e) {
            log.severe("An unexpected error happened. " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
