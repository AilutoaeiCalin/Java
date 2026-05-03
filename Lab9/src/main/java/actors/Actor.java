package actors;

import game.GameState;


public abstract class Actor extends Thread {
    protected final GameState gameState;
    private final int delayMillis;

    protected Actor(String name, GameState gameState, int delayMillis) {
        super(name);
        this.gameState = gameState;
        this.delayMillis = delayMillis;
    }

    @Override
    public final void run() {
        while (!gameState.isGameOver()) {
            takeTurn();
            pauseBetweenTurns();
        }
    }

    protected abstract void takeTurn();

    private void pauseBetweenTurns() {
        try {
            Thread.sleep(delayMillis);
        } catch (InterruptedException exception) {
            interrupt();
        }
    }
}
