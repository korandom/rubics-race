package cz.cuni.mff.korandom.components;

import java.awt.*;
import javax.swing.*;

public class Square extends JComponent {
    private Color fillColor;
    private Color outlineColor;
    private static final int outlineThickness = 2;

    public Square(int x, int y, int width) {
        setBounds(x, y, width, width);
        fillColor = Color.BLACK;
        outlineColor = Color.BLACK;
    }

    public void changeColor(int index) {
        fillColor = Board.colorMap.get(index);
    }

    public void changeColor(Color color) {
        fillColor = color;
    }

    public Color getColor() {
        return fillColor;
    }

    public boolean isEmpty() {
        return fillColor.equals(Color.BLACK);
    }

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
