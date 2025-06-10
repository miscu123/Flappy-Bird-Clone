package flappygame;

public class ScoreInfo {
    private final String name;
    private final int score;
    private final String difficulty;

    /**
     * Constructor to store the player info
     * @param name the name of the player
     * @param score the score achieved by the player
     * @param difficulty the difficulty level played
     */
    public ScoreInfo(String name, int score, String difficulty) {
        this.name = name;
        this.score = score;
        this.difficulty = difficulty;
    }

    /** Helper functions */

    /**
     * Gets the player's name
     * @return the name of the player
     */
    public String getName() { return name; }

    /**
     * Gets the player's score
     * @return the score achieved by the player
     */
    public int getScore() { return score; }

    /**
     * Gets the difficulty level
     * @return the difficulty level played
     */
    public String getDifficulty() { return difficulty; }
}