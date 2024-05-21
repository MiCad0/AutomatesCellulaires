import javax.swing.*;
import java.awt.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOfLife {
    private int rows;
    private int cols;
    private boolean[][] grid;
    private boolean isPaused;

    public GameOfLife(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new boolean[rows][cols];
        this.isPaused = false;
    }

    public void setCell(int row, int col, boolean alive) {
        grid[row][col] = alive;
    }

    public boolean isCellAlive(int row, int col) {
        return grid[row][col];
    }

    public void nextGeneration() {
        if (isPaused) {
            return; // Skip generation if paused
        }

        boolean[][] newGrid = new boolean[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int neighbors = countNeighbors(row, col);

                if (grid[row][col]) {
                    // Any live cell with fewer than two live neighbors dies
                    // Any live cell with two or three live neighbors lives on
                    // Any live cell with more than three live neighbors dies
                    newGrid[row][col] = neighbors == 2 || neighbors == 3;
                } else {
                    // Any dead cell with exactly three live neighbors becomes a live cell
                    newGrid[row][col] = neighbors == 3;
                }
            }
        }

        grid = newGrid;
    }

    private int countNeighbors(int row, int col) {
        int count = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                int neighborRow = row + i;
                int neighborCol = col + j;

                if (neighborRow >= 0 && neighborRow < rows && neighborCol >= 0 && neighborCol < cols) {
                    if (grid[neighborRow][neighborCol]) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public static void main(String[] args) {
        int rows = 100;
        int cols = 100;

        GameOfLife game = new GameOfLife(rows, cols);

        // Set some initial cells as alive
        game.setCell(1, 2, true);
        game.setCell(2, 3, true);
        game.setCell(3, 1, true);
        game.setCell(3, 2, true);
        game.setCell(3, 3, true);

        // Create the GUI
        JFrame frame = new JFrame("Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int cellSize = Math.min(getWidth() / cols, getHeight() / rows);

                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < cols; col++) {
                        if (game.isCellAlive(row, col)) {
                            g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                        }
                    }
                }
            }
        };

        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.isPaused = !game.isPaused;
                pauseButton.setText(game.isPaused ? "Resume" : "Pause");
            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int cellSize = Math.min(panel.getWidth() / cols, panel.getHeight() / rows);
                int row = e.getY() / cellSize;
                int col = e.getX() / cellSize;

                game.setCell(row, col, !game.isCellAlive(row, col));
                panel.repaint();
            }
        });

        frame.add(panel);
        frame.add(pauseButton, BorderLayout.SOUTH);
        frame.setVisible(true);

        // Run the game loop
        while (true) {
            game.nextGeneration();
            panel.repaint();

            try {
                Thread.sleep(50); // Delay between generations
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}