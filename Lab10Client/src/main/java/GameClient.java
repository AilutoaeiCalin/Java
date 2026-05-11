import java.io.*;
import java.net.Socket;

public class GameClient {
    private final String serverAddress;
    private final int port;

    public GameClient(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    public void start() {
        try {
            Socket socket = new Socket(serverAddress, port);

            BufferedReader input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            Thread listenerThread = new Thread(new ServerListener(input));
            Thread keyboardThread = new Thread(new KeyboardReader(output));

            listenerThread.start();
            keyboardThread.start();

            keyboardThread.join();

            socket.close();

        } catch (IOException e) {
            System.out.println("Could not connect to server.");
        } catch (InterruptedException e) {
            System.out.println("Client interrupted.");
        }
    }

    public static void main(String[] args) {
        GameClient client = new GameClient("localhost", 4444);
        client.start();
    }
}