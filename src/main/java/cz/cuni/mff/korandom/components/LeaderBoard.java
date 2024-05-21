package cz.cuni.mff.korandom.components;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Class LeaderBoard holds and manages information about entries to the leader board.
 */
public class LeaderBoard {
    /**
     * All entries, should be ordered by time.
     */
    public List<LeaderBoardEntry> entries;
    private String fileName;

    /**
     * Constructor of the class LeaderBoard, loads previous entries from filename and sorts them.
     * @param filename file with textual information about the entries.
     */
    public LeaderBoard(String filename) {
        entries = new ArrayList<>();
        fileName = filename;
        loadEntries();
        sortEntries();
    }

    /**
     * Load All entries from the filename.
     */
    private void loadEntries() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                addEntryToList(parts[0], parts[1]);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Sort the entries according to the lowest time.
     */
    private void sortEntries() {
        entries.sort(Comparator.comparingInt(LeaderBoardEntry::getTime));
    }

    /**
     * Add a new entry to the List<LeaderBoardEntry> entries.
     * @param name Name of the sir, who finished the game.
     * @param time Time in which the game was finished.
     */
    private void addEntryToList(String name, String time){
        String[] timeParts = time.split(":");
        int minutes = Integer.parseInt(timeParts[0]);
        int seconds = Integer.parseInt(timeParts[1]);
        int totalSeconds = 60 * minutes + seconds;
        entries.add(new LeaderBoardEntry(name, totalSeconds));
    }

    /**
     * Add new entry, both to the file and the object representation in entries.
     * @param name Name of the sir, who finished the game.
     * @param time Time in which the game was finished.
     */
    public void addEntry(String name, String time){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.write(name + " " + time);
            bw.newLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        addEntryToList(name, time);
        sortEntries();
    }

}
