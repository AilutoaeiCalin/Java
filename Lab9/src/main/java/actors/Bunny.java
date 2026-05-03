package actors;

import game.GameState;
import model.Position;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class Bunny extends Actor {
    public Bunny(GameState gameState, int delayMillis) {

        super("Bunny", gameState, delayMillis);
    }

    @Override
    protected void takeTurn() {
        List<Position> possibleMoves = gameState.getPossibleMovesForBunny();
        Position nextPosition = chooseRandomMove(possibleMoves);
        if (nextPosition != null) {
            gameState.moveBunny(nextPosition);
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
