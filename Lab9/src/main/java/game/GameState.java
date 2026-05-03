package game;

import model.Maze;
import model.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class GameState {
    private static final String RUNNING_MESSAGE = "Jocul ruleaza.";

    private final Maze maze;
    private final SharedMemory sharedMemory = new SharedMemory();
    private final Map<Integer, Position> robotPositions = new LinkedHashMap<>();
    private final Random random = new Random();

    private final Position exitPosition;
    private Position bunnyPosition;
    private boolean gameOver;
    private String finishReason = RUNNING_MESSAGE;
    private long moveCounter;

    public GameState(Maze maze, int robotCount) {
        this.maze = maze;
        validateRobotCount(robotCount);

        Set<Position> occupied = new HashSet<>();
        exitPosition = maze.getRandomPositionExcluding(occupied, random);
        occupied.add(exitPosition);

        bunnyPosition = maze.getRandomPositionExcluding(occupied, random);
        occupied.add(bunnyPosition);

        placeRobots(robotCount, occupied);
    }

    public synchronized boolean isGameOver() {
        return gameOver;
    }

    public synchronized String getFinishReason() {
        return finishReason;
    }

    public synchronized List<Position> getPossibleMovesForBunny() {
        return removeCellsOccupiedByRobots(maze.getReachableNeighbors(bunnyPosition), -1);
    }

    public synchronized List<Position> getPossibleMovesForRobot(int robotId) {
        Position currentPosition = robotPositions.get(robotId);
        return removeCellsOccupiedByRobots(maze.getReachableNeighbors(currentPosition), robotId);
    }

    public synchronized boolean moveBunny(Position nextPosition) {
        if (!isValidBunnyMove(nextPosition)) {
            return false;
        }

        bunnyPosition = nextPosition;
        moveCounter++;

        Integer robotId = getRobotIdAt(bunnyPosition);
        if (robotId != null) {
            finishGame("Iepurele a intrat in celula robotului " + robotId + ".");
        } else if (bunnyPosition.equals(exitPosition)) {
            finishGame("Iepurele a ajuns la iesire.");
        }

        printState("Mutarea " + moveCounter + ": iepurele s-a mutat in " + nextPosition);
        return true;
    }

    public synchronized boolean moveRobot(int robotId, Position nextPosition) {
        if (!isValidRobotMove(robotId, nextPosition)) {
            return false;
        }

        robotPositions.put(robotId, nextPosition);
        moveCounter++;

        if (nextPosition.equals(bunnyPosition)) {
            sharedMemory.reportBunnyPosition(robotId, bunnyPosition);
            finishGame("Robotul " + robotId + " a prins iepurele.");
        }

        shareBunnyPositionIfRobotCanSeeIt(robotId, nextPosition);
        printState("Mutarea " + moveCounter + ": robotul " + robotId + " s-a mutat in " + nextPosition);
        return true;
    }

    public synchronized void printState(String event) {
        System.out.println();
        printThreadLine(event);
        System.out.println(maze.render(bunnyPosition, exitPosition, robotPositions));
        printSharedMemoryState();

        if (gameOver) {
            printThreadLine("Rezultat: " + finishReason);
        }
    }

    private void validateRobotCount(int robotCount) {
        if (robotCount < 1) {
            throw new IllegalArgumentException("Trebuie sa existe cel putin un robot.");
        }
        if (robotCount + 2 > maze.getRows() * maze.getCols()) {
            throw new IllegalArgumentException("Nu exista suficiente celule.");
        }
    }

    private void placeRobots(int robotCount, Set<Position> occupiedPositions) {
        for (int robotId = 1; robotId <= robotCount; robotId++) {
            Position robotPosition = maze.getRandomPositionExcluding(occupiedPositions, random);
            occupiedPositions.add(robotPosition);
            robotPositions.put(robotId, robotPosition);
        }
    }

    private boolean isValidBunnyMove(Position nextPosition) {
        if (gameOver || nextPosition == null) {
            return false;
        }
        return maze.canMove(bunnyPosition, nextPosition);
    }

    private boolean isValidRobotMove(int robotId, Position nextPosition) {
        if (gameOver || nextPosition == null) {
            return false;
        }

        Position currentPosition = robotPositions.get(robotId);
        if (currentPosition == null || currentPosition.equals(nextPosition)) {
            return false;
        }
        if (!maze.canMove(currentPosition, nextPosition)) {
            return false;
        }

        return !isOccupiedByRobot(nextPosition, robotId);
    }

    private List<Position> removeCellsOccupiedByRobots(List<Position> positions, int movingRobotId) {
        List<Position> freePositions = new ArrayList<>();

        for (Position position : positions) {
            if (!isOccupiedByRobot(position, movingRobotId)) {
                freePositions.add(position);
            }
        }

        return freePositions;
    }

    private boolean isOccupiedByRobot(Position position, int movingRobotId) {
        Integer robotId = getRobotIdAt(position);
        return robotId != null && robotId != movingRobotId;
    }

    private Integer getRobotIdAt(Position position) {
        for (Map.Entry<Integer, Position> entry : robotPositions.entrySet()) {
            if (position.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    private boolean canSeeBunny(Position robotPosition) {
        return maze.hasClearLine(robotPosition, bunnyPosition);
    }

    private void shareBunnyPositionIfRobotCanSeeIt(int robotId, Position robotPosition) {
        if (canSeeBunny(robotPosition)) {
            sharedMemory.reportBunnyPosition(robotId, bunnyPosition);
        }
    }

    private void printSharedMemoryState() {
        if (!sharedMemory.hasBunnyInformation()) {
            printThreadLine("Memorie comuna: niciun robot nu a vazut iepurele inca.");
            return;
        }

        printThreadLine("Memorie comuna: iepurele a fost vazut ultima data la "+ sharedMemory.getLastKnownBunnyPosition() + " de robotul " + sharedMemory.getLastReporterId() + ".");
    }

    private void printThreadLine(String message) {
        System.out.println(buildThreadMessage(message));
    }

    private String buildThreadMessage(String message) {
        Thread currentThread = Thread.currentThread();
        return "[" + currentThread.getName() + " | id=" + currentThread.threadId() + "] " + message;
    }

    private void finishGame(String reason) {
        gameOver = true;
        finishReason = reason;
    }
}
