package ui;

import model.Cell;
import model.Maze;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class DrawingPanel extends JPanel {
    private final MainFrame frame;

    public DrawingPanel(MainFrame frame) {
        this.frame = frame;
        setBackground(Color.WHITE);
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
}