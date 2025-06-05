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

            ps.setString(1, scoreInfo.getName());
            ps.setInt(2, scoreInfo.getScore());
            ps.setString(3, scoreInfo.getDifficulty());

            // Data de azi
            ps.setDate(4, Date.valueOf(LocalDate.now()));

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
