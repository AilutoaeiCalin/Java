package game;

import model.Position;


public class SharedMemory {
    private Position lastKnownBunnyPosition;
    private int lastReporterId = -1;

    public synchronized void reportBunnyPosition(int robotId, Position position) {
        lastKnownBunnyPosition = position;
        lastReporterId = robotId;
    }

    public synchronized Position getLastKnownBunnyPosition() {
        return lastKnownBunnyPosition;
    }

    public synchronized int getLastReporterId() {
        return lastReporterId;
    }

    public synchronized boolean hasBunnyInformation() {
        return lastKnownBunnyPosition != null;
    }
}
