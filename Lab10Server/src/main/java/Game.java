import model.Player;
import model.Question;
import entity.GameResult;
import repository.GameResultRepository;
import java.util.*;


import java.util.*;

public class Game {

    private final List<Question> questions;
    private final Map<ClientThread, Player> players;

    private final Set<ClientThread> answeredPlayers;

    private int currentQuestionIndex;
    private long questionStartTime;
    private boolean gameStarted;

    private static final long TIME_LIMIT = 10_000;

    public Game(List<Question> questions) {
        this.questions = questions;
        this.players = new HashMap<>();
        this.answeredPlayers = new HashSet<>();

        this.currentQuestionIndex = 0;
        this.gameStarted = false;
    }

    public synchronized void addPlayer(ClientThread client, String name) {
        players.put(client, new Player(name));
        broadcast(name + " joined the game.");
    }

    public synchronized void removePlayer(ClientThread client) {
        Player player = players.get(client);

        if (player != null) {
            broadcast(player.getName() + " left the game.");

            players.remove(client);
            answeredPlayers.remove(client);
        }
    }

    public synchronized void startGame() {
        if (gameStarted) {
            broadcast("Game already started.");
            return;
        }

        if (players.isEmpty()) {
            broadcast("Cannot start game. No players connected.");
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

        answeredPlayers.clear();

        Question question = questions.get(currentQuestionIndex);

        questionStartTime = System.currentTimeMillis();

        broadcast("");
        broadcast("Question " + (currentQuestionIndex + 1) + ": " + question.getText());
        broadcast("You have 10 seconds.");
        broadcast("Use: ANSWER your_answer");
    }

    public synchronized void submitAnswer(ClientThread client, String answer) {

        if (!gameStarted) {
            client.sendMessage("Game has not started yet.");
            return;
        }

        Player player = players.get(client);

        if (player == null) {
            client.sendMessage("You must JOIN first.");
            return;
        }

        if (answeredPlayers.contains(client)) {
            client.sendMessage("You already answered this question.");
            return;
        }

        long responseTime =
                System.currentTimeMillis() - questionStartTime;

        if (responseTime > TIME_LIMIT) {

            player.addWrongAnswer(responseTime);

            client.sendMessage("Too late. Time limit exceeded.");

        } else {

            Question question = questions.get(currentQuestionIndex);

            if (question.isCorrect(answer)) {

                player.addCorrectAnswer(responseTime);

                client.sendMessage("Correct answer.");

            } else {

                player.addWrongAnswer(responseTime);

                client.sendMessage(
                        "Wrong answer. Correct answer was: "
                                + question.getCorrectAnswer()
                );
            }
        }

        answeredPlayers.add(client);

        broadcast(player.getName() + " answered.");

        if (answeredPlayers.size() == players.size()) {

            currentQuestionIndex++;

            sendCurrentQuestion();

        } else {

            client.sendMessage("Waiting for the other players...");
        }
    }

    public synchronized void showScores(ClientThread client) {

        if (players.isEmpty()) {
            client.sendMessage("No players.");
            return;
        }

        client.sendMessage("Scores:");

        for (Player player : players.values()) {

            client.sendMessage(
                    player.getName()+ " | score: " + player.getScore() + " | total time: " + player.getTotalResponseTime() + " ms"
            );
        }
    }

    private synchronized void finishGame() {
        gameStarted = false;
        broadcast("");
        broadcast("Game finished.");
        GameResultRepository repository =
                new GameResultRepository();
        Player winner = null;
        for (Player player : players.values()) {
            GameResult result =
                    new GameResult(
                            player.getName(),
                            player.getScore(),
                            player.getTotalResponseTime()
                    );

            repository.create(result);

            if (winner == null) {

                winner = player;

            } else if (
                    player.getScore() > winner.getScore()
            ) {

                winner = player;

            } else if (
                    player.getScore() == winner.getScore()
                            &&
                            player.getTotalResponseTime()
                                    < winner.getTotalResponseTime()
            ) {

                winner = player;
            }
        }

        repository.close();

        if (winner != null) {

            broadcast(
                    "Winner: "
                            + winner.getName()
                            + " | score: "
                            + winner.getScore()
                            + " | time: "
                            + winner.getTotalResponseTime()
                            + " ms"
            );
        }

        broadcast("Results saved in database.");
    }

    public synchronized void broadcast(String message) {

        for (ClientThread client : players.keySet()) {

            client.sendMessage(message);
        }
    }
}