import java.io.*;
import java.net.Socket;

public class ClientThread implements Runnable {
    private final Socket socket;
    private final GameServer server;
    private PrintWriter output;

    public ClientThread(Socket socket, GameServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try (
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                )
        ) {
            output = new PrintWriter(socket.getOutputStream(), true);

            sendMessage("Conectat la server.");
            sendMessage("Comenzi: JOIN nume | START | ANSWER rasp | SCORE | EXIT | STOP");

            String command;

            while ((command = input.readLine()) != null) {
                handleCommand(command);
            }

        } catch (IOException e) {
            System.out.println("Client deconectat.");
        } finally {
            server.getGame().removePlayer(this);
            closeSocket();
        }
    }

    private void handleCommand(String command) {
        if (command.startsWith("JOIN ")) {
            String name = command.substring(5).trim();
            server.getGame().addPlayer(this, name);
        } else if (command.equalsIgnoreCase("START")) {
            server.getGame().startGame();
        } else if (command.startsWith("ANSWER ")) {
            String answer = command.substring(7).trim();
            server.getGame().submitAnswer(this, answer);
        } else if (command.equalsIgnoreCase("SCORE")) {
            server.getGame().showScores(this);
        } else if (command.equalsIgnoreCase("EXIT")) {
            sendMessage("Goodbye.");
            closeSocket();
        } else if (command.equalsIgnoreCase("STOP")) {
            sendMessage("Server oprit.");
            server.stop();
        } else {
            sendMessage("Comanda nu exista.");
        }
    }

    public void sendMessage(String message) {
        if (output != null) {
            output.println(message);
        }
    }

    private void closeSocket() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("N-am putut sa inchid socketul.");
        }
    }
}