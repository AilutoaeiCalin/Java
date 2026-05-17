package model;

public class Player {
    private final String name;
    private int score;
    private long totalResponseTime;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.totalResponseTime = 0;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public long getTotalResponseTime() {
        return totalResponseTime;
    }

    public void addCorrectAnswer(long responseTime) {
        score++;
        totalResponseTime += responseTime;
    }

    public void addWrongAnswer(long responseTime) {
        totalResponseTime += responseTime;
    }
}