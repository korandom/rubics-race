package cz.cuni.mff.korandom.ui;

import javax.swing.*;

import cz.cuni.mff.korandom.components.Board;
import cz.cuni.mff.korandom.components.Target;
import cz.cuni.mff.korandom.components.TimerLabel;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {
    private Board board;
    private Target target;
    private String name;

    public MainFrame(String userName) {
        setTitle("Rubiks Race");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        // Initialize components
        name = userName;
        target = new Target(250);
        board = new Board(600);

        // Set up layout and add components
        addComponents(board, target);

        pack(); // Ensure layout is applied before calculating insets
        setLocationRelativeTo(null);
    }

    private void addComponents(Board board, Target target) {
        // Create and configure GridBagConstraints for the main frame
        GridBagConstraints frameGbc = new GridBagConstraints();

        // Add the Board component
        frameGbc.fill = GridBagConstraints.VERTICAL;
        frameGbc.gridx = 0;
        frameGbc.gridy = 0;
        frameGbc.weightx = 0.0;
        frameGbc.weighty = 1.0; // Board should take all vertical space
        add(board, frameGbc);

        // Create and configure TargetPanel
        JPanel targetPanel = createTargetPanel(target);
        frameGbc.fill = GridBagConstraints.NONE;
        frameGbc.gridx = 1;
        frameGbc.gridy = 0;
        add(targetPanel, frameGbc);
    }

    private JPanel createTargetPanel(Target target) {
        JPanel targetPanel = new JPanel(new GridBagLayout());
        GridBagConstraints panelGbc = new GridBagConstraints();
        panelGbc.insets = new Insets(10, 20, 10, 20); // Add padding

        // Add TimerLabel to the panel
        Font font = new Font("Dialog", Font.BOLD, 30);
        TimerLabel timer = new TimerLabel(font);
        panelGbc.fill = GridBagConstraints.HORIZONTAL;
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        targetPanel.add(timer, panelGbc);

        // Add Target component to the panel
        panelGbc.fill = GridBagConstraints.NONE;
        panelGbc.gridy++;
        targetPanel.add(target, panelGbc);

        // Add buttons to the panel
        addButtonToPanel(targetPanel, panelGbc, "NEW", font, ++panelGbc.gridy, new NewButtonAction());
        addButtonToPanel(targetPanel, panelGbc, "RESET", font, ++panelGbc.gridy, new ResetButtonAction());
        addButtonToPanel(targetPanel, panelGbc, "EVALUATE", font, ++panelGbc.gridy, new EvaluateButtonAction());

        return targetPanel;
    }

    private void addButtonToPanel(JPanel panel, GridBagConstraints gbc, String text, Font font, int gridy, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.addActionListener(actionListener);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = gridy;
        panel.add(button, gbc);
    }

    // Action listeners for the buttons
    private class NewButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            board.regenerate();
            target.regenerate();
        }
    }

    private class ResetButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            board.reset();
        }
    }

    private class EvaluateButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(board.evaluate(target)){
            System.out.println("Evaluate button clicked");
            }
        }
    }
}
