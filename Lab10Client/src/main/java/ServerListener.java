import java.io.BufferedReader;
import java.io.IOException;

public class ServerListener implements Runnable {
    private final BufferedReader input;

    public ServerListener(BufferedReader input) {
        this.input = input;
    }

    @Override
    public void run() {
        try {
            String message;

            while ((message = input.readLine()) != null) {
                System.out.println(message);
            }

        } catch (IOException e) {
            System.out.println("Conexiune la server kaput.");
        }
    }
}