package cz.cuni.mff.korandom.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;
import java.awt.*;

public class Target extends JPanel {
    private List<Integer> colors = new ArrayList<>();
    private static final int size = 3;
    private int width;
    protected Square[][] squares = new Square[size][size];

    public Target(int width) {
        this.width = width;
        setLayout(new GridLayout(size, size));    
        fillColors();
        generateSquares();
    }

    private void generateSquares() {
        int squareWidth = width / size;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int xpos = j * squareWidth;
                int ypos = i * squareWidth;
                squares[i][j] = new Square(xpos, ypos, squareWidth);
                squares[i][j].changeColor(colors.get(i * size + j));
                add(squares[i][j]);
            }
        }
    }

    private void fillColors() {
        for (int i = 1; i < Board.colorMap.size(); i++) {
            for (int j = 0; j < 4; j++) {
                colors.add(i);
            }
        }
        shuffleColors();
    }

    private void shuffleColors() {
        Collections.shuffle(colors, new Random());
    }

    public void regenerate() {
        shuffleColors();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                squares[i][j].changeColor(colors.get(i * size + j));
            }
        }
        revalidate();
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, width); // Ensure the board remains square
    }
}
