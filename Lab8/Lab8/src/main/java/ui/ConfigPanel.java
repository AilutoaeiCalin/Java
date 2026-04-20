package ui;

import model.Maze;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.FlowLayout;

public class ConfigPanel extends JPanel {
    private final MainFrame frame;

    private final JTextField rowsField;
    private final JTextField colsField;
    private final JButton drawGridButton;

    public ConfigPanel(MainFrame frame) {
        this.frame = frame;

        setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel rowsLabel = new JLabel("Rows:");
        rowsField = new JTextField("10", 5);

        JLabel colsLabel = new JLabel("Cols:");
        colsField = new JTextField("10", 5);

        drawGridButton = new JButton("Draw Grid");

        add(rowsLabel);
        add(rowsField);
        add(colsLabel);
        add(colsField);
        add(drawGridButton);

        drawGridButton.addActionListener(e -> createMaze());
    }

    private void createMaze() {
        try {
            int rows = Integer.parseInt(rowsField.getText().trim());
            int cols = Integer.parseInt(colsField.getText().trim());

            if (rows <= 0 || cols <= 0) {
                return;
            }

            Maze maze = new Maze(rows, cols);
            frame.setMaze(maze);
        } catch (NumberFormatException ex) {
            System.out.println("Rows and Cols must be valid integers.");
        }
    }
}