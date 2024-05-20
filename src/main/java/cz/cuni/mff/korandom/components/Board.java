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


public class Board extends JPanel {
    private static final int size = 5;
    private Square[][] squares = new Square[size][size];
    private List<Integer> startingColors = new ArrayList<>();
    private int width;
    public static final List<Color> colorMap = Arrays.asList(
            Color.BLACK,
            Color.RED,
            Color.BLUE,
            Color.YELLOW,
            Color.GREEN,
            Color.WHITE,
            new Color(255, 165, 0)
    );

    public Board(int width) {
        this.width = width;
        setLayout(new GridLayout(size, size));  
        //setPreferredSize(new Dimension(width, width));
        generateStartingColors();
        generateSquares();
        // for correct resizing
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

    private void generateStartingColors() {
        startingColors.add(0);
        for (int i = 1; i < colorMap.size(); i++) {
            for (int j = 0; j < 4; j++) {
                startingColors.add(i);
            }
        }
        shuffleColors();
    }

    private void shuffleColors() {
        Collections.shuffle(startingColors, new Random());
    }

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

    private void applyStartingColors() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                squares[i][j].changeColor(startingColors.get(i * size + j));
            }
        }
        revalidate();
        repaint();
    }

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

    public void reset() {
        applyStartingColors();
    }

    public void regenerate() {
        shuffleColors();
        applyStartingColors();
    }

    public void handleMouseClick(Point mousePos) {
        int squareWidth = getWidth() / size;
        int row = (int) mousePos.getY() / squareWidth;
        int column = (int) mousePos.getX() / squareWidth;

        moveSquareToEmpty(row, column);
    }

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
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, width); // Ensure the board remains square
    }
    
    private void updateBoardSize(int height) {
        if (width != height) {
            width = height;
            revalidate();
        }
    }
}

