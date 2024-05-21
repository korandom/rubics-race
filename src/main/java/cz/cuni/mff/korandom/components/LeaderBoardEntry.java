package cz.cuni.mff.korandom.components;

/**
 * Class representing an entry in the leader board.
 */
public class LeaderBoardEntry {
    private String name;
    private int time;

    /**
     * Constructor of LeaderBoardEntry.
     * @param name Name of the sir, who finished the game.
     * @param time Integer time in seconds, in which the game has been finished.
     */ 
    public LeaderBoardEntry(String name, int time) {
        this.name = name;
        this.time = time;
    }
    
    /**
     * Get the name from the entry.
     * @return Name of the sir, who finished the game.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the time of the entry.
     * @return Integer time of seconds, in which the game has been finished.
     */
    public int getTime() {
        return time;
    }
}
