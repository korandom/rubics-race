package cz.cuni.mff.korandom.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import cz.cuni.mff.korandom.components.LeaderBoard;
import cz.cuni.mff.korandom.components.LeaderBoardEntry;

/**
 * Class LeaderBoardFrame extends JFrame and is responsible for creating an UI to showcase the LeaderBoard.
 */
public class LeaderBoardFrame extends JFrame{
    private GameFrame main;

    /**
     * Constructor of the LeaderBoardFrame, setting up the UI and its actions.
     * @param leaderBoard LeaderBoard with entries, that are to be displayed in a table.
     * @param gameFrame GameFrame instance that is to be returned to.
     */
    public LeaderBoardFrame(LeaderBoard leaderBoard, GameFrame gameFrame){
        setTitle("Leader Board of Racers");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        main = gameFrame;

        Font font = new Font("Dialog", Font.PLAIN, 30);
        Font headerfont = new Font("Dialog", Font.BOLD, 37);

        // Creating Table
        JTable table = createLeaderTable(leaderBoard, font, headerfont);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Creating Header Label
        JLabel congrats = new JLabel("YOU RACED WELL!!");
        congrats.setFont(headerfont);
        add(congrats, BorderLayout.NORTH);

        // Creating button back to the game
        JButton button = new JButton("BACK TO BUSINESS");
        button.setFont(headerfont);
        button.addActionListener(new BackButtonAction());
        add(button, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }
    /**
     * Create a table with the entries from the leader board, with columns: Order_number, Name, Time.
     * @param leaderBoard LeaderBoard with the entries.
     * @param font Font to be used for the table rows.
     * @param headerfont Font to be used for the table column names.
     * @return The created table.
     */
    private JTable createLeaderTable(LeaderBoard leaderBoard, Font font, Font headerfont){
        // initializing table
        String[] columnNames = {"#", "Name", "Time"};
        DefaultTableModel  tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        // Formatting table
        TableColumnModel columnModel = table.getColumnModel();
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setFont(headerfont);
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setHeaderRenderer(headerRenderer);
        }
        table.setFont(font);
        table.setRowHeight(36);
        table.setEnabled(false);  // Make the table non-editable

        // Creating table entries
        int i = 0;
        for (LeaderBoardEntry entry : leaderBoard.entries) {
            String name = entry.getName();
            int totalSeconds = entry.getTime();
            int minutes = totalSeconds / 60;
            int seconds = totalSeconds % 60;
            String time = String.format("%d:%02d", minutes, seconds);
            tableModel.addRow(new Object[]{++i, name, time});
        }
        return table;
    }

    /**
     * BackButtonAction invoked when BackButton is clicked, returning back to the GameFrame and disposing of this frame.
     */
    private class BackButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(() -> {
                main.reset();
                main.setVisible(true);
            });
            dispose();
        }
    }
}