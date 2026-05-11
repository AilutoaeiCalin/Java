import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class KeyboardReader implements Runnable {
    private final PrintWriter output;

    public KeyboardReader(PrintWriter output) {
        this.output = output;
    }

    @Override
    public void run() {
        try (
                BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in))) {
            String command;

            while ((command = keyboard.readLine()) != null) {
                output.println(command);

                if (command.equalsIgnoreCase("EXIT")) {
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Keyboard error.");
        }
    }
}