package flappygame;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ScoreDAO {
    // making the connection to the database
    // using a mysql-connector (can be found in the 'lib' folder)
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/leaderboard";
    private static final String USER = "root";
    private static final String PASS = "1234";

    // insert the values into the database
    public void insertScore(ScoreInfo scoreInfo) {
        String sql = "INSERT INTO leaderboard (PlayerName, Score, Difficulty, Date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Player Name
            ps.setString(1, scoreInfo.getName());
            // Player Score
            ps.setInt(2, scoreInfo.getScore());
            // The difficulty the player played
            ps.setString(3, scoreInfo.getDifficulty());
            // The date the player played in
            ps.setDate(4, Date.valueOf(LocalDate.now()));

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not connect to database " + e.getMessage());
        }
    }

    // function that returns the scores and sorts them in non-increasing order
    public List<ScoreInfo> getAllScores() {
        List<ScoreInfo> scores = new ArrayList<>();
        String sql = "SELECT PlayerName, Score, Difficulty FROM leaderboard ORDER BY Score DESC";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("PlayerName");
                int score = rs.getInt("Score");
                String difficulty = rs.getString("Difficulty");
                scores.add(new ScoreInfo(name, score, difficulty));
            }

        } catch (SQLException e) {
            System.out.println("Could not retrieve scores: " + e.getMessage());
        }

        return scores;
    }
}