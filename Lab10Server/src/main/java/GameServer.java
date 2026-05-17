import model.Question;
import repository.QuestionRepository;
import repository.GameResultRepository;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {
    private final int port;
    private ServerSocket serverSocket;
    private ExecutorService pool;
    private Game game;
    private boolean running;

    public GameServer(int port) {
        this.port = port;
    }

    public Game getGame() {
        return game;
    }

    public void start() {
        List<Question> questions = QuestionRepository.loadQuestions("questions.txt");

        if (questions.isEmpty()) {
            System.out.println("N-am gasit intrebari, nu am pornit serveru.");
            return;
        }

        game = new Game(questions);
        pool = Executors.newFixedThreadPool(10);
        running = true;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server pornit pe port: " + port);

            while (running) {
                Socket socket = serverSocket.accept();
                System.out.println("Client conectat: " + socket);

                ClientThread clientThread = new ClientThread(socket, this);
                pool.execute(clientThread);
            }

        } catch (IOException e) {
            if (running) {
                System.out.println("Server error: " + e.getMessage());
            }
        } finally {
            stop();
        }
    }

    public void stop() {
        running = false;

        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.out.println("N-am putut inchide socketul.");
        }

        if (pool != null) {
            pool.shutdown();
        }

        System.out.println("Server oprit.");
    }

    public static void main(String[] args) {
        GameResultRepository test = new GameResultRepository();
        test.close();

        GameServer server = new GameServer(4444);
        server.start();
    }
}