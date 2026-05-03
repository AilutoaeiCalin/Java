package model;

import java.io.Serializable;


public class Cell implements Serializable {
    private final int row;
    private final int col;

    private boolean topWall;
    private boolean rightWall;
    private boolean bottomWall;
    private boolean leftWall;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.topWall = true;
        this.rightWall = true;
        this.bottomWall = true;
        this.leftWall = true;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean hasTopWall() {
        return topWall;
    }

    public void setTopWall(boolean topWall) {
        this.topWall = topWall;
    }

    public boolean hasRightWall() {
        return rightWall;
    }

    public void setRightWall(boolean rightWall) {
        this.rightWall = rightWall;
    }

    public boolean hasBottomWall() {
        return bottomWall;
    }

    public void setBottomWall(boolean bottomWall) {
        this.bottomWall = bottomWall;
    }

    public boolean hasLeftWall() {
        return leftWall;
    }

    public void setLeftWall(boolean leftWall) {
        this.leftWall = leftWall;
    }

    public boolean hasWall(Direction direction) {
        return switch (direction) {
            case TOP -> topWall;
            case RIGHT -> rightWall;
            case BOTTOM -> bottomWall;
            case LEFT -> leftWall;
        };
    }

    public void setWall(Direction direction, boolean value) {
        switch (direction) {
            case TOP -> topWall = value;
            case RIGHT -> rightWall = value;
            case BOTTOM -> bottomWall = value;
            case LEFT -> leftWall = value;
        }
    }
}
