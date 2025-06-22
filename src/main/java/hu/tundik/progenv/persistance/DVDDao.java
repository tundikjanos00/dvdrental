package hu.tundik.progenv.persistance;

import hu.tundik.progenv.model.DVD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DVDDao {

    public void save(DVD dvd) throws SQLException {
        String sql = "INSERT INTO dvd (title, genre, movie_length, daily_price, available) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dvd.getTitle());
            stmt.setString(2, dvd.getGenre());
            stmt.setInt(3, dvd.getMovieLength());
            stmt.setDouble(4, dvd.getDailyPrice());
            stmt.setBoolean(5, dvd.isAvailable());
            stmt.executeUpdate();
        }
    }

    public List<DVD> findAll() throws SQLException {
        List<DVD> list = new ArrayList<>();
        String sql = "SELECT * FROM dvd";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public DVD findById(int id) throws SQLException {
        String sql = "SELECT * FROM dvd WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    public void update(DVD dvd) throws SQLException {
        String sql = "UPDATE dvd SET title=?, genre=?, movie_length=?, daily_price=?, available=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dvd.getTitle());
            stmt.setString(2, dvd.getGenre());
            stmt.setInt(3, dvd.getMovieLength());
            stmt.setDouble(4, dvd.getDailyPrice());
            stmt.setBoolean(5, dvd.isAvailable());
            stmt.setInt(6, dvd.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM dvd WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<DVD> findByFilters(String genre, int minLength, int maxLength, double maxPrice) throws SQLException {
        List<DVD> result = new ArrayList<>();
        String sql = """
            SELECT * FROM dvd
            WHERE available = TRUE
            AND genre = ?
            AND movie_length BETWEEN ? AND ?
            AND daily_price <= ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, genre);
            stmt.setInt(2, minLength);
            stmt.setInt(3, maxLength);
            stmt.setDouble(4, maxPrice);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(map(rs));
                }
            }
        }
        return result;
    }

    private DVD map(ResultSet rs) throws SQLException {
        return new DVD(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("genre"),
                rs.getInt("movie_length"),
                rs.getDouble("daily_price"),
                rs.getBoolean("available")
        );
    }
}
