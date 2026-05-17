package model;

public class Question {
    private final String text;
    private final String correctAnswer;

    public Question(String text, String correctAnswer) {
        this.text = text;
        this.correctAnswer = correctAnswer;
    }

    public String getText() {
        return text;
    }

    public boolean isCorrect(String answer) {
        return correctAnswer.equalsIgnoreCase(answer.trim());
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}