package model;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class Maze implements Serializable {
    private final int rows;
    private final int cols;
    private final Cell[][] cells;

    public Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.cells = new Cell[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                cells[row][col] = new Cell(row, col);
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

    public void resetAllWalls() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Cell cell = cells[row][col];
                cell.setTopWall(true);
                cell.setRightWall(true);
                cell.setBottomWall(true);
                cell.setLeftWall(true);
            }
        }
    }

    public void generatePerfectMaze() {
        resetAllWalls();
        Random random = new Random();
        boolean[][] visited = new boolean[rows][cols];
        Deque<Position> stack = new ArrayDeque<>();

        Position start = new Position(random.nextInt(rows), random.nextInt(cols));
        visited[start.getRow()][start.getCol()] = true;
        stack.push(start);

        while (!stack.isEmpty()) {
            Position current = stack.peek();
            List<Direction> possibleDirections = new ArrayList<>();

            for (Direction direction : Direction.values()) {
                int nextRow = current.getRow() + direction.getRowOffset();
                int nextCol = current.getCol() + direction.getColOffset();

                if (isInside(nextRow, nextCol) && !visited[nextRow][nextCol]) {
                    possibleDirections.add(direction);
                }
            }

            if (possibleDirections.isEmpty()) {
                stack.pop();
                continue;
            }

            Direction direction = possibleDirections.get(random.nextInt(possibleDirections.size()));
            Position next = new Position(
                    current.getRow() + direction.getRowOffset(),
                    current.getCol() + direction.getColOffset()
            );

            removeWallBetween(current, next);
            visited[next.getRow()][next.getCol()] = true;
            stack.push(next);
        }
    }

    public boolean canMove(Position from, Position to) {
        if (from == null || to == null || !from.isOrthogonallyAdjacentTo(to)) {
            return false;
        }
        if (!isInside(from.getRow(), from.getCol()) || !isInside(to.getRow(), to.getCol())) {
            return false;
        }

        Direction direction = directionBetween(from, to);
        return isOpenOnBothSides(from, to, direction);
    }

    public List<Position> getReachableNeighbors(Position position) {
        List<Position> reachableNeighbors = new ArrayList<>();

        for (Direction direction : Direction.values()) {
            Position nextPosition = move(position, direction);
            if (!isInside(nextPosition.getRow(), nextPosition.getCol())) {
                continue;
            }

            if (canMove(position, nextPosition)) {
                reachableNeighbors.add(nextPosition);
            }
        }

        return reachableNeighbors;
    }

    public boolean hasClearLine(Position start, Position end) {
        if (start.getRow() == end.getRow()) {
            return hasClearHorizontalLine(start, end);
        }

        if (start.getCol() == end.getCol()) {
            return hasClearVerticalLine(start, end);
        }

        return false;
    }

    public Position getRandomPositionExcluding(Set<Position> forbiddenPositions, Random random) {
        List<Position> freePositions = new ArrayList<>();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Position candidate = new Position(row, col);
                if (!forbiddenPositions.contains(candidate)) {
                    freePositions.add(candidate);
                }
            }
        }

        if (freePositions.isEmpty()) {
            throw new IllegalArgumentException("Nu mai exista pozitii libere.");
        }

        return freePositions.get(random.nextInt(freePositions.size()));
    }

    public String render(Position bunnyPosition, Position exitPosition, Map<Integer, Position> robotPositions) {
        StringBuilder text = new StringBuilder();
        Map<Integer, Position> orderedRobots = new LinkedHashMap<>(robotPositions);

        for (int row = 0; row < rows; row++) {
            appendTopWalls(text, row);
            appendRowContent(text, row, bunnyPosition, exitPosition, orderedRobots);
        }

        for (int col = 0; col < cols; col++) {
            text.append('+');
            text.append(cells[rows - 1][col].hasBottomWall() ? "---" : "   ");
        }
        text.append("+");

        return text.toString();
    }

    private void appendTopWalls(StringBuilder text, int row) {
        for (int col = 0; col < cols; col++) {
            text.append('+');
            text.append(cells[row][col].hasTopWall() ? "---" : "   ");
        }
        text.append("+\n");
    }

    private void appendRowContent(
            StringBuilder text,
            int row,
            Position bunnyPosition,
            Position exitPosition,
            Map<Integer, Position> robotPositions
    ) {
        for (int col = 0; col < cols; col++) {
            Cell currentCell = cells[row][col];
            Position currentPosition = new Position(row, col);
            if (currentCell.hasLeftWall()) {
                text.append("|");
            } else {
                text.append(" ");
            }
            text.append(padToken(tokenFor(currentPosition, bunnyPosition, exitPosition, robotPositions)));
        }

        text.append(cells[row][cols - 1].hasRightWall() ? "|\n" : " \n");
    }

    private void removeWallBetween(Position first, Position second) {
        Direction direction = directionBetween(first, second);
        cells[first.getRow()][first.getCol()].setWall(direction, false);
        cells[second.getRow()][second.getCol()].setWall(direction.opposite(), false);
    }

    private boolean isOpenOnBothSides(Position from, Position to, Direction direction) {
        Cell sourceCell = cells[from.getRow()][from.getCol()];
        Cell targetCell = cells[to.getRow()][to.getCol()];

        boolean sourceAllowsPassage = !sourceCell.hasWall(direction);
        boolean targetAllowsPassage = !targetCell.hasWall(direction.opposite());

        return sourceAllowsPassage && targetAllowsPassage;
    }

    private boolean hasClearHorizontalLine(Position start, Position end) {
        int step = start.getCol() < end.getCol() ? 1 : -1;
        Position current = start;

        while (current.getCol() != end.getCol()) {
            Position next = new Position(current.getRow(), current.getCol() + step);
            if (!canMove(current, next)) {
                return false;
            }
            current = next;
        }

        return true;
    }

    private boolean hasClearVerticalLine(Position start, Position end) {
        int step = start.getRow() < end.getRow() ? 1 : -1;
        Position current = start;

        while (current.getRow() != end.getRow()) {
            Position next = new Position(current.getRow() + step, current.getCol());
            if (!canMove(current, next)) {
                return false;
            }
            current = next;
        }

        return true;
    }

    private Position move(Position from, Direction direction) {
        return new Position(
                from.getRow() + direction.getRowOffset(),
                from.getCol() + direction.getColOffset()
        );
    }

    private Direction directionBetween(Position from, Position to) {
        int dr = to.getRow() - from.getRow();
        int dc = to.getCol() - from.getCol();

        for (Direction direction : Direction.values()) {
            if (direction.getRowOffset() == dr && direction.getColOffset() == dc) {
                return direction;
            }
        }

        throw new IllegalArgumentException("Pozitiile nu sunt vecine.");
    }

    private String tokenFor(
            Position position,
            Position bunnyPosition,
            Position exitPosition,
            Map<Integer, Position> robotPositions
    )
    {
        Integer robotId = getRobotIdAt(position, robotPositions);
        boolean bunnyHere = position.equals(bunnyPosition);

        if (bunnyHere && robotId != null) {
            return "X";
        }
        if (bunnyHere) {
            return "B";
        }
        if (robotId != null) {
            return "R" + robotId;
        }
        if (position.equals(exitPosition)) {
            return "E";
        }
        return ".";
    }

    private Integer getRobotIdAt(Position position, Map<Integer, Position> robotPositions) {
        for (Map.Entry<Integer, Position> entry : robotPositions.entrySet()) {
            if (position.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    private String padToken(String token) {
        if (token.length() >= 3) {
            return token.substring(0, 3);
        }
        return String.format("%-3s", token);
    }
}
