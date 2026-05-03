import actors.Actor;
import actors.Bunny;
import actors.Robot;
import game.GameState;
import model.Maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final int MINIMUM_CELLS = 3;
    private static final int DEFAULT_ROWS = 8;
    private static final int DEFAULT_COLS = 8;
    private static final int DEFAULT_ROBOTS = 3;
    private static final int BUNNY_DELAY_MS = 250;
    private static final int ROBOT_DELAY_MS = 350;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int rows = readPositiveInt(scanner, "Numar de linii", DEFAULT_ROWS);
        int cols = readPositiveInt(scanner, "Numar de coloane", DEFAULT_COLS);

        if (!hasEnoughCells(rows, cols)) {
            System.out.println("Labirintul trebuie sa aiba cel putin 3 celule.");
            return;
        }

        int robotCount = readRobotCount(scanner, rows * cols - 2, DEFAULT_ROBOTS);

        Maze maze = createMaze(rows, cols);

        GameState gameState = new GameState(maze, robotCount);
        gameState.printState("Starea initiala");

        List<Actor> actors = createActors(gameState, robotCount);
        startActors(actors);

        if (!waitForActors(actors)) {
            printThreadMessage("Simularea a fost intrerupta.");
            return;
        }

        printThreadMessage("Joc terminat: " + gameState.getFinishReason());
    }

    private static int readPositiveInt(Scanner scanner, String label, int defaultValue) {
        while (true) {
            System.out.print(label + " [" + defaultValue + "]: ");
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                return defaultValue;
            }

            if (isUnsignedInteger(line)) {
                int value = Integer.parseInt(line);
                if (value > 0) {
                    return value;
                }
            }

            System.out.println("Introdu un numar pozitiv.");
        }
    }

    private static int readRobotCount(Scanner scanner, int maxRobots, int defaultValue) {
        int suggestedValue = Math.min(defaultValue, maxRobots);

        while (true) {
            System.out.print("Numar de roboti [1-" + maxRobots + "] [" + suggestedValue + "]: ");
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                return suggestedValue;
            }

            if (isUnsignedInteger(line)) {
                int value = Integer.parseInt(line);
                if (value >= 1 && value <= maxRobots) {
                    return value;
                }
            }

            System.out.println("Introdu un numar intre 1 si " + maxRobots + ".");
        }
    }

    private static boolean hasEnoughCells(int rows, int cols) {
        return rows * cols >= MINIMUM_CELLS;
    }

    private static Maze createMaze(int rows, int cols) {
        Maze maze = new Maze(rows, cols);
        maze.generatePerfectMaze();
        return maze;
    }

    private static List<Actor> createActors(GameState gameState, int robotCount) {
        List<Actor> actors = new ArrayList<>();
        actors.add(new Bunny(gameState, BUNNY_DELAY_MS));

        for (int robotId = 1; robotId <= robotCount; robotId++) {
            actors.add(new Robot(robotId, gameState, ROBOT_DELAY_MS));
        }

        return actors;
    }

    private static void startActors(List<Actor> actors) {
        for (Actor actor : actors) {
            actor.start();
        }
    }

    private static boolean waitForActors(List<Actor> actors) {
        for (Actor actor : actors) {
            try {
                actor.join();
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return true;
    }

    private static boolean isUnsignedInteger(String text) {
        if (text.isEmpty()) {
            return false;
        }

        for (int index = 0; index < text.length(); index++) {
            if (!Character.isDigit(text.charAt(index))) {
                return false;
            }
        }

        return true;
    }

    private static void printThreadMessage(String message) {
        System.out.println();
        Thread currentThread = Thread.currentThread();
        System.out.println(
                "[" + currentThread.getName() + " | id=" + currentThread.threadId() + "] " + message
        );
    }
}
