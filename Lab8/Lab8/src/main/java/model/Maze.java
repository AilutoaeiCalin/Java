package model;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
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

    public boolean isInside(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public void toggleTopWall(int row, int col) {
        Cell current = cells[row][col];
        boolean newValue = !current.hasTopWall();
        current.setTopWall(newValue);

        if (row > 0) {
            cells[row - 1][col].setBottomWall(newValue);
        }
    }

    public void toggleRightWall(int row, int col) {
        Cell current = cells[row][col];
        boolean newValue = !current.hasRightWall();
        current.setRightWall(newValue);

        if (col < cols - 1) {
            cells[row][col + 1].setLeftWall(newValue);
        }
    }

    public void toggleBottomWall(int row, int col) {
        Cell current = cells[row][col];
        boolean newValue = !current.hasBottomWall();
        current.setBottomWall(newValue);

        if (row < rows - 1) {
            cells[row + 1][col].setTopWall(newValue);
        }
    }

    public void toggleLeftWall(int row, int col) {
        Cell current = cells[row][col];
        boolean newValue = !current.hasLeftWall();
        current.setLeftWall(newValue);

        if (col > 0) {
            cells[row][col - 1].setRightWall(newValue);
        }
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

    public boolean isReachable(int startRow, int startCol, int endRow, int endCol) {
        if (!isInside(startRow, startCol) || !isInside(endRow, endCol)) {
            return false;
        }

        boolean[][] visited = new boolean[rows][cols];
        Deque<int[]> queue = new ArrayDeque<>();

        visited[startRow][startCol] = true;
        queue.addLast(new int[]{startRow, startCol});

        while (!queue.isEmpty()) {
            int[] currentPosition = queue.removeFirst();
            int row = currentPosition[0];
            int col = currentPosition[1];

            if (row == endRow && col == endCol) {
                return true;
            }

            Cell currentCell = cells[row][col];

            if (!currentCell.hasTopWall() && row > 0 && !visited[row - 1][col]) {
                visited[row - 1][col] = true;
                queue.addLast(new int[]{row - 1, col});
            }

            if (!currentCell.hasRightWall() && col < cols - 1 && !visited[row][col + 1]) {
                visited[row][col + 1] = true;
                queue.addLast(new int[]{row, col + 1});
            }

            if (!currentCell.hasBottomWall() && row < rows - 1 && !visited[row + 1][col]) {
                visited[row + 1][col] = true;
                queue.addLast(new int[]{row + 1, col});
            }

            if (!currentCell.hasLeftWall() && col > 0 && !visited[row][col - 1]) {
                visited[row][col - 1] = true;
                queue.addLast(new int[]{row, col - 1});
            }
        }

        return false;
    }
}
