package ui;

import model.Maze;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainFrame extends JFrame {
    private final ConfigPanel configPanel;
    private final DrawingPanel drawingPanel;
    private final ControlPanel controlPanel;

    private Maze maze;

    public MainFrame() {
        super("Maze Generator");

        setLayout(new BorderLayout());

        drawingPanel = new DrawingPanel(this);
        configPanel = new ConfigPanel(this);
        controlPanel = new ControlPanel(this);

        add(configPanel, BorderLayout.NORTH);
        add(drawingPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public Maze getMaze() {
        return maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
        drawingPanel.repaint();
    }

    public DrawingPanel getDrawingPanel() {
        return drawingPanel;
    }

    public void validateMazePath() {
        if (maze == null) {
            JOptionPane.showMessageDialog(this, "Mai intai fa un labirint.");
            return;
        }

        try {
            int startRow = configPanel.getStartRow();
            int startCol = configPanel.getStartCol();
            int endRow = configPanel.getEndRow();
            int endCol = configPanel.getEndCol();

            if (!maze.isInside(startRow, startCol) || !maze.isInside(endRow, endCol)) {
                JOptionPane.showMessageDialog(this, "Coordonatele tre sa fie in interiorul labirintului");
                return;
            }

            boolean reachable = maze.isReachable(startRow, startCol, endRow, endCol);
            String message;
            if (reachable) {
                message = "Exista cale prin Maze";
            }
            else
            {
                message="Nu exista cale prin Maze";
            }

            JOptionPane.showMessageDialog(this, message);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Coordonatele trebuie sa fie valide");
        }
    }

    public void saveMaze() {
        if (maze == null) {
            JOptionPane.showMessageDialog(this, "Fa un labirint mai intai");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        try (ObjectOutputStream outputStream =
                     new ObjectOutputStream(new FileOutputStream(fileChooser.getSelectedFile()))) {
            outputStream.writeObject(maze);
            JOptionPane.showMessageDialog(this, "Labirint salvat");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Labirintul nu a putut fi salvat");
        }
    }

    public void loadMaze() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        try (ObjectInputStream inputStream =
                     new ObjectInputStream(new FileInputStream(fileChooser.getSelectedFile()))) {
            Maze loadedMaze = (Maze) inputStream.readObject();
            setMaze(loadedMaze);
            configPanel.updateFieldsForMaze(loadedMaze.getRows(), loadedMaze.getCols());
            JOptionPane.showMessageDialog(this, "Labirint incarcat");
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Labirintul nu a putut fi incarcat");
        }
    }
}
