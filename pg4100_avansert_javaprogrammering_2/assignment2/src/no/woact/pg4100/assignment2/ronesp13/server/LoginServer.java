package no.woact.pg4100.assignment2.ronesp13.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
* @author Espen Rønning - ronesp13@student.westerdals.no
*/
public class LoginServer implements Runnable {

    private static final Logger log = Logger.getLogger(LoginServer.class.getName());
    private static final int PORT = 2390;

    private ExecutorService executor;

    public LoginServer() {
        executor = Executors.newFixedThreadPool(10);
        executor.execute(this);
    }

    @Override
    public void run() {
        try (ServerSocket server = new ServerSocket(PORT)) {
            log.info("Server connection established.");
            while (true) {
                Socket socket = server.accept();
                log.info("Accepted connection from " + socket.getInetAddress().getHostName() + ".");
                QuizClientConnection client = new QuizClientConnection(socket);
                executor.execute(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
