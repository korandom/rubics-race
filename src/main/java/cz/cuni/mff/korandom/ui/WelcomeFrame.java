package cz.cuni.mff.korandom.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Class WelcomeFrame extends JFrame and is responsible setting up UI for initial Frame and collecting a name from the user. 
 */
public class WelcomeFrame extends JFrame {
    private JTextField nameField;

    /**
     * Constructor of class WelcomeFrame, creates a layout for welcome labels and input name field.
     */
    public WelcomeFrame() {
        setTitle("Welcome to Rubics Race");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        Font font = new Font("Dialog", Font.BOLD, 20);

        JLabel welcomeLabel = new JLabel("Greetings sir.");
        welcomeLabel.setFont(font);
        add(welcomeLabel, gbc);

        gbc.gridy++;
        JLabel nameLabel = new JLabel("What name do you want to be remembered by?");
        nameLabel.setFont(font);
        add(nameLabel, gbc);

        gbc.gridy++;
        nameField = new JTextField(20);
        nameField.setFont(font);
        add(nameField, gbc);

        gbc.gridy++;
        JButton readyButton = new JButton("Ready to Race");
        readyButton.setFont(font);
        ReadyButtonAction readyListener = new ReadyButtonAction();
        readyButton.addActionListener(readyListener);
        add(readyButton, gbc);

        nameField.addActionListener(readyListener);
        pack();
        setLocationRelativeTo(null);
    }
    /**
     * ReadyButtonAction is invoked when ReadyButton is pressed and creates a new GameFrame with the name inputted by the user.
     */
    private class ReadyButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            SwingUtilities.invokeLater(() -> {
                GameFrame gameFrame = new GameFrame(name);
                gameFrame.setVisible(true);
            });
            dispose(); // Close this frame
        }
    }
    /**
     * Starting point of the aplication.
     * @param args arguments, they are not used.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WelcomeFrame welcomeFrame = new WelcomeFrame();
            welcomeFrame.setVisible(true);
        });
    }
}
