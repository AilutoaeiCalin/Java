package ui;

import model.Maze;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.FlowLayout;

public class ControlPanel extends JPanel {
    private final MainFrame frame;

    private final JButton createButton;
    private final JButton resetButton;
    private final JButton validateButton;
    private final JButton saveButton;
    private final JButton loadButton;
    private final JButton exitButton;

    public ControlPanel(MainFrame frame) {
        this.frame = frame;

        setLayout(new FlowLayout(FlowLayout.CENTER));

        createButton = new JButton("Create");
        resetButton = new JButton("Reset");
        validateButton = new JButton("Validate");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        exitButton = new JButton("Exit");

        add(createButton);
        add(resetButton);
        add(validateButton);
        add(saveButton);
        add(loadButton);
        add(exitButton);

        createButton.addActionListener(e -> createMazeStructure());
        resetButton.addActionListener(e -> resetMaze());
        validateButton.addActionListener(e -> frame.validateMazePath());
        saveButton.addActionListener(e -> frame.saveMaze());
        loadButton.addActionListener(e -> frame.loadMaze());
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void createMazeStructure() {
        Maze maze = frame.getMaze();
        if (maze == null) {
            return;
        }

        maze.randomlyRemoveWalls();
        frame.getDrawingPanel().repaint();
    }

    private void resetMaze() {
        Maze maze = frame.getMaze();
        if (maze == null) {
            return;
        }

        maze.resetAllWalls();
        frame.getDrawingPanel().repaint();
    }
}
