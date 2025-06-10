package flappygame;

public class ScoreInfo {
    private final String name;
    private final int score;
    private final String difficulty;

    // constructor to store the player info
    public ScoreInfo(String name, int score, String difficulty) {
        this.name = name;
        this.score = score;
        this.difficulty = difficulty;
    }

    // helper functions
    public String getName() { return name; }
    public int getScore() { return score; }
    public String getDifficulty() { return difficulty; }
}