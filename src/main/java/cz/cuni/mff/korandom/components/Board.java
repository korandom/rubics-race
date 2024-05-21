package cz.cuni.mff.korandom.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.*;

/**
 * Class Board extends JPanel, manages squares, their color and their movement.
 */
public class Board extends JPanel {
    private static final int size = 5;
    private Square[][] squares = new Square[size][size];
    private List<Integer> startingColors = new ArrayList<>();
    private int width;
    /** 
     * Array of colors being used in the Board and Target class,
     * Black color has to always be first, but other colors can be changed.
     */
    public static final List<Color> colorMap = Arrays.asList(
            Color.BLACK,
            Color.RED,
            Color.BLUE,
            Color.YELLOW,
            Color.GREEN,
            Color.WHITE,
            new Color(255, 165, 0)
    );

    /**
     *  Board constructor
     * @param width The original width of the board, since the board is square, effectively the height as well
     */
    public Board(int width) {
        this.width = width;

        // Set up squares
        setLayout(new GridLayout(size, size));  
        generateStartingColors();
        generateSquares();

        // for correct resizing of the board when the window is resized
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateBoardSize(e.getComponent().getHeight());
            }
        });

        // for catching mouse clicks
        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                handleMouseClick(getMousePosition());
            }
        });
    }

    /**
     * Populate the array of colors with all the colors and generate random order.
     */
    private void generateStartingColors() {
        startingColors.add(0);
        for (int i = 1; i < colorMap.size(); i++) {
            for (int j = 0; j < 4; j++) {
                startingColors.add(i);
            }
        }
        shuffleColors();
    }

    /**
     * Shuffle the array of colors to generate new board starting point.
     */
    private void shuffleColors() {
        Collections.shuffle(startingColors, new Random());
    }
    
    /**
     * Initialize the squares according to the colors and add them to the Board Panel.
     */
    private void generateSquares() {
        int squareWidth = width / size;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int xpos = j * squareWidth;
                int ypos = i * squareWidth;
                squares[i][j] = new Square(xpos, ypos, squareWidth);
                squares[i][j].changeColor(startingColors.get(i * size + j));
                add(squares[i][j]);
            }
        }
    }
    /**
     * Apply the starting colors - used when restarting a run with the same board state or when changing states.
     */
    private void applyStartingColors() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                squares[i][j].changeColor(startingColors.get(i * size + j));
            }
        }
        revalidate();
        repaint();
    }

    /**
     * Evaluate the board.
     * @param target The Target (solution of the board) used for evaluating.
     * @return boolean value indicating, whether the Board is succesfully solved according to the target.
     */
    public boolean evaluate(Target target) {
        for (int i = 1; i < size - 1; i++) {
            for (int j = 1; j < size - 1; j++) {
                if (!target.squares[i - 1][j - 1].getColor().equals(squares[i][j].getColor())) {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * Reset the board back to the starting colors.
     * This makes it possible to play the same random game multiple times.
     */
    public void reset() {
        applyStartingColors();
    }

    /**
     * Generate new board state, practically shuffling the colors of squares.
     */
    public void regenerate() {
        shuffleColors();
        applyStartingColors();
    }

    /**
     * Handle mouse clicking on the board.
     * If a square that is capable of moving was clicked, the change is made, else board stays unchanged.
     * @param mousePos Point where the mouse was clicked.
     */
    public void handleMouseClick(Point mousePos) {
        int squareWidth = getWidth() / size;
        int row = (int) mousePos.getY() / squareWidth;
        int column = (int) mousePos.getX() / squareWidth;

        moveSquareToEmpty(row, column);
    }

    /**
     * Move the square on row 'row' and column 'column' into the empty spot, if possible.
     * @param row Row of the square.
     * @param column Column of the square.
     */
    private void moveSquareToEmpty(int row, int column) {
        Optional<Point> empty = getEmptyIfNext(row, column);
        if (empty.isPresent()) {
            Point p = empty.get();
            squares[p.x][p.y].changeColor(squares[row][column].getColor());
            squares[p.x][p.y].revalidate();
            squares[p.x][p.y].repaint();
            squares[row][column].changeColor(Color.BLACK);
            squares[row][column].revalidate();
            squares[row][column].repaint();
        }
    }

    /**
     * Get the position of the empty square, if it is next next to (over, under, right to, left to) [row, column].
     * @param row Row in question
     * @param column Column in question
     * @return Optional<Point> of the empty square, if the empty square isnt next to [row, column], no Point is present.
     */
    private Optional<Point> getEmptyIfNext(int row, int column) {
        List<Point> possibleIndexes = Arrays.asList(
                new Point(row - 1, column),
                new Point(row + 1, column),
                new Point(row, column + 1),
                new Point(row, column - 1)
        );

        for (Point p : possibleIndexes) {
            if (p.x >= 0 && p.y >= 0 && p.x < size && p.y < size) {
                if (squares[p.x][p.y].isEmpty()) {
                    return Optional.of(p);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    /**
     * Returns the preffered size of the board, that is always square.
     * @return Dimension of the preffered size.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, width); // Ensure the board remains square
    }
    
    /**
     * Updates the board size according to the new height of the window.
     * @param height new height of the window.
     */
    private void updateBoardSize(int height) {
        if (width != height) {
            width = height;
            revalidate();
        }
    }
}

