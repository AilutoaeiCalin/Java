package model;

import java.io.Serializable;
import java.util.Random;

public class Maze implements Serializable {
    private final int rows;
    private final int cols;
    private final Cell[][] cells;

    public Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.cells = new Cell[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

    public void resetAllWalls() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cell = cells[i][j];
                cell.setTopWall(true);
                cell.setRightWall(true);
                cell.setBottomWall(true);
                cell.setLeftWall(true);
            }
        }
    }

    public void randomlyRemoveWalls() {
        resetAllWalls();
        Random random = new Random();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell current = cells[i][j];

                if (j < cols - 1 && random.nextBoolean()) {
                    Cell rightNeighbor = cells[i][j + 1];
                    current.setRightWall(false);
                    rightNeighbor.setLeftWall(false);
                }

                if (i < rows - 1 && random.nextBoolean()) {
                    Cell bottomNeighbor = cells[i + 1][j];
                    current.setBottomWall(false);
                    bottomNeighbor.setTopWall(false);
                }
            }
        }
    }
}