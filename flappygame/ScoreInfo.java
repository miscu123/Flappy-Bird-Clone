package flappygame;

public class ScoreInfo {
    private int id;
    private String name;
    private int score;
    private String difficulty;

    public ScoreInfo(String name, int score, String difficulty) {
        this.name = name;
        this.score = score;
        this.difficulty = difficulty;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getScore() { return score; }
    public String getDifficulty() { return difficulty; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setScore(int score) { this.score = score; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    @Override
    public String toString() {
        return name + " - " + score + " (" + difficulty + ")";
    }
}
