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
    private final JTextField startRowField;
    private final JTextField startColField;
    private final JTextField endRowField;
    private final JTextField endColField;
    private final JButton drawGridButton;

    public ConfigPanel(MainFrame frame) {
        this.frame = frame;

        setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel rowsLabel = new JLabel("Rows:");
        rowsField = new JTextField("10", 5);

        JLabel colsLabel = new JLabel("Cols:");
        colsField = new JTextField("10", 5);

        JLabel startRowLabel = new JLabel("Start row:");
        startRowField = new JTextField("0", 3);

        JLabel startColLabel = new JLabel("Start col:");
        startColField = new JTextField("0", 3);

        JLabel endRowLabel = new JLabel("End row:");
        endRowField = new JTextField("9", 3);

        JLabel endColLabel = new JLabel("End col:");
        endColField = new JTextField("9", 3);

        drawGridButton = new JButton("Draw Grid");

        add(rowsLabel);
        add(rowsField);
        add(colsLabel);
        add(colsField);
        add(startRowLabel);
        add(startRowField);
        add(startColLabel);
        add(startColField);
        add(endRowLabel);
        add(endRowField);
        add(endColLabel);
        add(endColField);
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
            setDefaultStartAndEnd(rows, cols);
            frame.setMaze(maze);
        } catch (NumberFormatException ex) {
            System.out.println("Rows and Cols must be valid integers.");
        }
    }

    public int getStartRow() {
        return Integer.parseInt(startRowField.getText().trim());
    }

    public int getStartCol() {
        return Integer.parseInt(startColField.getText().trim());
    }

    public int getEndRow() {
        return Integer.parseInt(endRowField.getText().trim());
    }

    public int getEndCol() {
        return Integer.parseInt(endColField.getText().trim());
    }

    public void updateFieldsForMaze(int rows, int cols) {
        rowsField.setText(String.valueOf(rows));
        colsField.setText(String.valueOf(cols));
        setDefaultStartAndEnd(rows, cols);
    }

    private void setDefaultStartAndEnd(int rows, int cols) {
        startRowField.setText("0");
        startColField.setText("0");
        endRowField.setText(String.valueOf(rows - 1));
        endColField.setText(String.valueOf(cols - 1));
    }
}
