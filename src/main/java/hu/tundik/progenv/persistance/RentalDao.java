package hu.tundik.progenv.persistence;

import hu.tundik.progenv.model.Rental;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalDao {

    public void save(Rental rental) throws SQLException {
        String sql = "INSERT INTO rental (customer_id, dvd_id, rental_date, return_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, rental.getCustomerId());
            stmt.setInt(2, rental.getDvdId());
            stmt.setDate(3, rental.getRentalDate());
            stmt.setDate(4, rental.getReturnDate());
            stmt.executeUpdate();
        }
    }

    public List<Rental> findAll() throws SQLException {
        List<Rental> list = new ArrayList<>();
        String sql = "SELECT * FROM rental";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public void updateReturnDate(int rentalId, Date returnDate) throws SQLException {
        String sql = "UPDATE rental SET return_date = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, returnDate);
            stmt.setInt(2, rentalId);
            stmt.executeUpdate();
        }
    }

    private Rental map(ResultSet rs) throws SQLException {
        return new Rental(
                rs.getInt("id"),
                rs.getInt("customer_id"),
                rs.getInt("dvd_id"),
                rs.getDate("rental_date"),
                rs.getDate("return_date")
        );
    }
}
