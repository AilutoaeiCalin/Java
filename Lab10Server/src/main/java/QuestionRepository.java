import java.io.*;
import java.util.*;

public class QuestionRepository {
    public static List<Question> loadQuestions(String fileName) {
        List<Question> questions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                if (parts.length == 2) {
                    questions.add(new Question(parts[0].trim(), parts[1].trim()));
                }
            }

        } catch (IOException e) {
            System.out.println("Could not load questions: " + e.getMessage());
        }

        return questions;
    }
}