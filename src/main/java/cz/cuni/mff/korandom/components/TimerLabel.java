package cz.cuni.mff.korandom.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class TimerLabel extends JLabel {
    private Timer timer;
    private long startTime;
    private long elapsedTime;
    private boolean isRunning;

    public TimerLabel(Font font) {
        setFont(font);
        setForeground(Color.RED);
        setHorizontalAlignment(SwingConstants.CENTER);
        setText("0:00");

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isRunning) {
                    elapsedTime = System.currentTimeMillis() - startTime;
                    setText(formatTime(elapsedTime));
                }
            }
        });
        timer.start();
        start();
    }

    public void start() {
        if (!isRunning) {
            isRunning = true;
            startTime = System.currentTimeMillis();
        }
    }

    public void stop() {
        if (isRunning) {
            isRunning = false;
            elapsedTime = System.currentTimeMillis() - startTime;
        }
    }

    private String formatTime(long elapsedMillis) {
        int totalSeconds = (int) (elapsedMillis / 1000);
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}