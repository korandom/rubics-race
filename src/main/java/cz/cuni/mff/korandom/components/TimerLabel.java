package cz.cuni.mff.korandom.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

/**
 * Class TimerLabel extends JLabel.
 * TimerLabel is a timer, that counts elapsed time and presents it as a label.
 */
public class TimerLabel extends JLabel {
    private Timer timer;
    private long startTime;
    private long elapsedTime;
    private boolean isRunning;
    /**
     * Constructor of TimerLabel, starting the timer and setting label properties.
     * @param font Font of the created label with time.
     */
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
    /**
     * Start the timer, if it is not already running.
     */
    public void start() {
        if (!isRunning) {
            isRunning = true;
            startTime = System.currentTimeMillis();
        }
    }

    /**
     * Stop the timer, if it is not already stopped.
     */
    public void stop() {
        if (isRunning) {
            isRunning = false;
            elapsedTime = System.currentTimeMillis() - startTime;
        }
    }

    /**
     * Reset the timer, starts running  again from 0:00.
     */
    public void reset(){
        setText("0:00");
        isRunning = true;
        startTime = System.currentTimeMillis();
    }

    /**
     * Formats the time from long in miliseconds to a string with format "min:sec".
     * @param elapsedMillis The milliseconds that elapsed.
     * @return String representation of the elapsed time in "min:sec" format.
     */
    private String formatTime(long elapsedMillis) {
        int totalSeconds = (int) (elapsedMillis / 1000);
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}