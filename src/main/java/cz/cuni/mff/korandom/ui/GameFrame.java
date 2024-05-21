package cz.cuni.mff.korandom.ui;

import javax.swing.*;

import cz.cuni.mff.korandom.components.Board;
import cz.cuni.mff.korandom.components.LeaderBoard;
import cz.cuni.mff.korandom.components.Target;
import cz.cuni.mff.korandom.components.TimerLabel;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Class GameFrame extends JFrame and is responsible for presenting the Board, Target and buttons needing for managing the game flow.
 */
public class GameFrame extends JFrame {
    private Board board;
    private Target target;
    private TimerLabel timer;
    private LeaderBoard leaderBoard;
    private String name;
    private JButton nameButton;
    /**
     * Constructor of the GameFrame, setting up the components and managing their interactions.
     * @param userName Name given by the user to be used in the leaderBoard.
     */
    public GameFrame(String userName) {
        setTitle("Rubiks Race");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        // Initialize components
        name = userName;
        String filename = "leader-board.txt";
        leaderBoard = new LeaderBoard(filename);
        target = new Target(250);
        board = new Board(600);

        // Set up layout and add components
        addComponents(board, target);

        pack(); // Ensure layout is applied before calculating insets
        setLocationRelativeTo(null);
    }
    /**
     * Add the Board to the Frame and create a TargetPanel with the Target and buttons that manage possible actions.
     * @param board Board to be displayed.
     * @param target Target to be displayed.
     */
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

    /**
     * Create a TargetPanel that displays the goal state -Target and the buttons needed for operating the game state.
     * @param target Target - the goal state to be displayed.
     * @return The created JPanel TargetPanel.
     */
    private JPanel createTargetPanel(Target target) {
        JPanel targetPanel = new JPanel(new GridBagLayout());
        GridBagConstraints panelGbc = new GridBagConstraints();
        panelGbc.insets = new Insets(10, 20, 10, 20); // Add padding

        // Add TimerLabel to the panel
        Font font = new Font("Dialog", Font.BOLD, 30);
        timer = new TimerLabel(font);
        panelGbc.fill = GridBagConstraints.HORIZONTAL;
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        targetPanel.add(timer, panelGbc);

        // Add Target component to the panel
        panelGbc.fill = GridBagConstraints.NONE;
        panelGbc.gridy++;
        targetPanel.add(target, panelGbc);
        font = new Font("Dialog", Font.BOLD, 25);

        // Add buttons to the panel
        addButtonToPanel(targetPanel, panelGbc, "NEW", font, ++panelGbc.gridy, new NewButtonAction());
        addButtonToPanel(targetPanel, panelGbc, "RESET", font, ++panelGbc.gridy, new ResetButtonAction());
        addButtonToPanel(targetPanel, panelGbc, "EVALUATE", font, ++panelGbc.gridy, new EvaluateButtonAction());
        nameButton = addButtonToPanel(targetPanel, panelGbc, "I'm not " + name , font, ++panelGbc.gridy, new ChangeNameButtonAction());

        return targetPanel;
    }

    /**
     * Add Button to a panel with GridBagLayout.
     * 
     * @param panel Panel, where the button will be added.
     * @param gbc GridBagConstraints to be used when adding to the Layout.
     * @param text Text of the button.
     * @param font Font of the text of the button.
     * @param gridy Y Placement on the grid.
     * @param actionListener Action to be invoked on the press of the button.
     * @return
     */
    private JButton addButtonToPanel(JPanel panel, GridBagConstraints gbc, String text, Font font, int gridy, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.addActionListener(actionListener);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = gridy;
        panel.add(button, gbc);
        return button;
    }

    /**
     * Restart the game with new board state and goal state.
     */
    public void restart(){
        board.regenerate();
        target.regenerate();
        timer.reset();
    }

    /**
     * Reset the board to the original starting state.
     */
    public void reset(){
        board.reset();
        timer.reset();
    }
    /**
     * NewButtonAction invoked when NewButton is clicked, restarting the game.
     */
    private class NewButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            restart();
        }
    }

    /**
     * ResetButtonAction invoked when ResetButton is clicked, bringing the board to the original state and reseting the time.
     */
    private class ResetButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            reset();
        }
    }

    /**
     * EvaluateButtonAction invoked when EvaluateButton is clicked, evaluating the board against the target and when succesful showing the leaderBoardFrame and adding a new entry. Else nothing.
     */
    private class EvaluateButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(board.evaluate(target)){
                timer.stop();
                leaderBoard.addEntry(name, timer.getText());
                SwingUtilities.invokeLater(() -> {
                    LeaderBoardFrame leaderBoardFrame= new LeaderBoardFrame(leaderBoard, GameFrame.this);
                    leaderBoardFrame.setVisible(true);
                });
                dispose();
            }
        }
    }  

    /**
     * Private class ChangeNameDialog extends JDialog and provides functionality for changing users name.
     */
    private class ChangeNameDialog extends JDialog {
        public String name;
        private JTextField textField;

        /**
         * Constructor of the class ChangeNameDialog, setting up the UI for inputing a new name.
         * @param parent GameFrame where the new name will be used.
         */
        public ChangeNameDialog(GameFrame parent) {
            super(parent, "Name changing shenanigans", true); 
            setLocationRelativeTo(parent);

            Font font = new Font("Dialog", Font.BOLD, 20);
            JLabel label = new JLabel("Hi, my name is:");
            label.setFont(font);
            textField = new JTextField(20); 
            textField.setFont(font);

            JPanel panel = new JPanel(new FlowLayout());
            panel.add(label);
            panel.add(textField);
            add(panel, BorderLayout.CENTER);

            JButton OKButton = new JButton("OK");
            OKButton.setFont(font);
            OKButton.addActionListener(new OKButtonAction());
            add(OKButton, BorderLayout.SOUTH);
            pack();
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }

        /**
         * OKButtonAction is invoked when OKButton is pressed, saves the name from the textField and closes the dialog window.
         */
        private class OKButtonAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameFrame.ChangeNameDialog.this.name = textField.getText();
                dispose();
            }
        }
    }

    /**
     * ChangeNameButtonAction is invoked whe ChangeNameButton is pressed, opens a new ChangeNameDialog and saves new name.
     */
    private class ChangeNameButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            timer.stop();
            ChangeNameDialog dialog = new ChangeNameDialog(GameFrame.this);
            dialog.setVisible(true);
            if(!dialog.name.isEmpty()){
                name = dialog.name;
                nameButton.setText("I'm not " + name);
                pack();
            }
            timer.start();
        }
    }
}