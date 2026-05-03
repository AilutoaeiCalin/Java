package ui;

import model.Cell;
import model.Maze;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawingPanel extends JPanel {
    private static final int WALL_TOLERANCE = 8;

    private final MainFrame frame;

    public DrawingPanel(MainFrame frame) {
        this.frame = frame;
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleWallClick(e.getX(), e.getY());
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Maze maze = frame.getMaze();
        if (maze == null) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(40, 70, 140));
        g2.setStroke(new BasicStroke(2));

        int rows = maze.getRows();
        int cols = maze.getCols();

        int availableWidth = getWidth() - 80;
        int availableHeight = getHeight() - 80;

        int cellSize = Math.min(availableWidth / cols, availableHeight / rows);
        if (cellSize <= 0) {
            return;
        }

        int gridWidth = cols * cellSize;
        int gridHeight = rows * cellSize;

        int startX = (getWidth() - gridWidth) / 2;
        int startY = (getHeight() - gridHeight) / 2;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cell = maze.getCell(i, j);

                int x = startX + j * cellSize;
                int y = startY + i * cellSize;

                g2.setColor(new Color(235, 242, 255));
                g2.fillRect(x, y, cellSize, cellSize);

                g2.setColor(new Color(40, 70, 140));

                if (cell.hasTopWall()) {
                    g2.drawLine(x, y, x + cellSize, y);
                }
                if (cell.hasRightWall()) {
                    g2.drawLine(x + cellSize, y, x + cellSize, y + cellSize);
                }
                if (cell.hasBottomWall()) {
                    g2.drawLine(x, y + cellSize, x + cellSize, y + cellSize);
                }
                if (cell.hasLeftWall()) {
                    g2.drawLine(x, y, x, y + cellSize);
                }
            }
        }
    }

    private void handleWallClick(int mouseX, int mouseY) {
        Maze maze = frame.getMaze();
        if (maze == null) {
            return;
        }

        int rows = maze.getRows();
        int cols = maze.getCols();

        int availableWidth = getWidth() - 80;
        int availableHeight = getHeight() - 80;

        int cellSize = Math.min(availableWidth / cols, availableHeight / rows);
        if (cellSize <= 0) {
            return;
        }

        int gridWidth = cols * cellSize;
        int gridHeight = rows * cellSize;

        int startX = (getWidth() - gridWidth) / 2;
        int startY = (getHeight() - gridHeight) / 2;

        if (mouseX < startX || mouseX > startX + gridWidth || mouseY < startY || mouseY > startY + gridHeight) {
            return;
        }

        int relativeX = mouseX - startX;
        int relativeY = mouseY - startY;

        int col = Math.min(relativeX / cellSize, cols - 1);
        int row = Math.min(relativeY / cellSize, rows - 1);

        int localX = relativeX - col * cellSize;
        int localY = relativeY - row * cellSize;

        int topDistance = localY;
        int rightDistance = cellSize - localX;
        int bottomDistance = cellSize - localY;
        int leftDistance = localX;

        int minDistance = Math.min(Math.min(topDistance, rightDistance), Math.min(bottomDistance, leftDistance));
        if (minDistance > WALL_TOLERANCE) {
            return;
        }

        if (minDistance == topDistance) {
            maze.toggleTopWall(row, col);
        } else if (minDistance == rightDistance) {
            maze.toggleRightWall(row, col);
        } else if (minDistance == bottomDistance) {
            maze.toggleBottomWall(row, col);
        } else {
            maze.toggleLeftWall(row, col);
        }

        repaint();
    }
}
