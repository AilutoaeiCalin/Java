import java.util.*;

public class Game {
    private final List<Question> questions;
    private final Map<ClientThread, Player> players;

    private int currentQuestionIndex;
    private long questionStartTime;
    private boolean gameStarted;

    private static final long TIME_LIMIT = 10_000;

    public Game(List<Question> questions) {
        this.questions = questions;
        this.players = new HashMap<>();
        this.currentQuestionIndex = 0;
        this.gameStarted = false;
    }

    public synchronized void addPlayer(ClientThread client, String name) {
        players.put(client, new Player(name));
        broadcast(name + " a intrat.");
    }

    public synchronized void removePlayer(ClientThread client) {
        Player player = players.get(client);

        if (player != null) {
            broadcast(player.getName() + " a iesit.");
            players.remove(client);
        }
    }

    public synchronized void startGame() {
        if (gameStarted) {
            broadcast("Jocu a inceput.");
            return;
        }

        if (players.isEmpty()) {
            broadcast("Nu pot incepe, nu sunt jucatori.");
            return;
        }

        gameStarted = true;
        currentQuestionIndex = 0;
        sendCurrentQuestion();
    }

    private synchronized void sendCurrentQuestion() {
        if (currentQuestionIndex >= questions.size()) {
            finishGame();
            return;
        }

        Question question = questions.get(currentQuestionIndex);
        questionStartTime = System.currentTimeMillis();

        broadcast("");
        broadcast("Question " + (currentQuestionIndex + 1) + ": " + question.getText());
        broadcast("Ai 10 secunde. Baga: ANSWER rasp");
    }

    public synchronized void submitAnswer(ClientThread client, String answer) {
        if (!gameStarted) {
            client.sendMessage("Jocu n-a inceput");
            return;
        }

        Player player = players.get(client);

        if (player == null) {
            client.sendMessage("Trebuie sa dai JOIN mai intai");
            return;
        }

        long responseTime = System.currentTimeMillis() - questionStartTime;

        if (responseTime > TIME_LIMIT) {
            player.addWrongAnswer(responseTime);
            client.sendMessage("Prea tarziu.");
        } else {
            Question question = questions.get(currentQuestionIndex);

            if (question.isCorrect(answer)) {
                player.addCorrectAnswer(responseTime);
                client.sendMessage("Raspuns corect");
            } else {
                player.addWrongAnswer(responseTime);
                client.sendMessage("Raspuns gresit. Raspuns corect: " + question.getCorrectAnswer());
            }
        }

        currentQuestionIndex++;
        sendCurrentQuestion();
    }

    public synchronized void showScores(ClientThread client) {
        if (players.isEmpty()) {
            client.sendMessage("No players.");
            return;
        }

        client.sendMessage("Scoruri:");

        for (Player player : players.values()) {
            client.sendMessage(
                    player.getName()
                            + " | scor: "
                            + player.getScore()
                            + " | total time: "
                            + player.getTotalResponseTime()
                            + " ms"
            );
        }
    }

    private synchronized void finishGame() {
        gameStarted = false;


        broadcast("Game finished.");

        Player winner = null;

        for (Player player : players.values()) {
            if (winner == null) {
                winner = player;
            } else if (player.getScore() > winner.getScore()) {
                winner = player;
            } else if (
                    player.getScore() == winner.getScore()
                            && player.getTotalResponseTime() < winner.getTotalResponseTime()
            ) {
                winner = player;
            }
        }

        if (winner != null) {
            broadcast("Winner: " + winner.getName()
                    + " | scor: " + winner.getScore()
                    + " | time: " + winner.getTotalResponseTime() + " ms");
        }
    }

    public synchronized void broadcast(String message) {
        for (ClientThread client : players.keySet()) {
            client.sendMessage(message);
        }
    }
}