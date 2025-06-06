package flappygame;

import java.sql.*;
import java.time.LocalDate;

public class ScoreDAO {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/leaderboard";
    private static final String USER = "root";
    private static final String PASS = "1234";

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
}
