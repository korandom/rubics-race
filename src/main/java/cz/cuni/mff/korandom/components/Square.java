package cz.cuni.mff.korandom.components;

import java.awt.*;
import javax.swing.*;

/**
 * Class representing a single colored square on the board or in the target.
 */
public class Square extends JComponent {
    private Color fillColor;
    private Color outlineColor;
    private static final int outlineThickness = 2;

    /**
     * Constructor of Square, setting the position and size.
     * @param x X relative position of the square.
     * @param y Y relative position of the square.
     * @param width Initial width, same as height, determines the size.
     */
    public Square(int x, int y, int width) {
        setBounds(x, y, width, width);
        fillColor = Color.BLACK;
        outlineColor = Color.BLACK;
    }
    /**
     * Change color of the square.
     * @param index Index of the new color in the Board.colorMap.
     */
    public void changeColor(int index) {
        fillColor = Board.colorMap.get(index);
    }
    /**
     * Change the color of the square.
     * @param color New color of the square.
     */
    public void changeColor(Color color) {
        fillColor = color;
    }

    /**
     * Get the color of the square.
     * @return Color of the square.
     */
    public Color getColor() {
        return fillColor;
    }

    /**
     * Is this square empty? When a square is empty, it has black color.
     * @return Boolean value indicating, whether the square is empty.
     */
    public boolean isEmpty() {
        return fillColor.equals(Color.BLACK);
    }

    /**
     * Paint the square.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(fillColor);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setColor(outlineColor);
        g2d.setStroke(new BasicStroke(outlineThickness));
        g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }
}
