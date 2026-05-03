package actors;

import game.GameState;
import model.Position;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class Robot extends Actor {
    private final int robotId;

    public Robot(int robotId, GameState gameState, int delayMillis) {
        super("Robot-" + robotId, gameState, delayMillis);
        this.robotId = robotId;
    }

    @Override
    protected void takeTurn() {
        List<Position> possibleMoves = gameState.getPossibleMovesForRobot(robotId);
        Position nextPosition = chooseRandomMove(possibleMoves);
        if (nextPosition != null) {
            gameState.moveRobot(robotId, nextPosition);
        }
    }

    private Position chooseRandomMove(List<Position> possibleMoves) {
        if (possibleMoves.isEmpty()) {
            return null;
        }

        int index = ThreadLocalRandom.current().nextInt(possibleMoves.size());
        return possibleMoves.get(index);
    }
}
