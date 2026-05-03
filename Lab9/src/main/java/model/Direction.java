package model;


public enum Direction {
    TOP(-1, 0),
    RIGHT(0, 1),
    BOTTOM(1, 0),
    LEFT(0, -1);

    private final int rowOffset;
    private final int colOffset;

    Direction(int rowOffset, int colOffset) {
        this.rowOffset = rowOffset;
        this.colOffset = colOffset;
    }

    public int getRowOffset() {
        return rowOffset;
    }

    public int getColOffset() {
        return colOffset;
    }

    public Direction opposite() {
        return switch (this) {
            case TOP -> BOTTOM;
            case RIGHT -> LEFT;
            case BOTTOM -> TOP;
            case LEFT -> RIGHT;
        };
    }
}
