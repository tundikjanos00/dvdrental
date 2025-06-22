package hu.tundik.progenv;

import hu.tundik.progenv.persistance.DatabaseConnection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

public class DatabaseConnectionTest {

    @BeforeEach
    void setUp() {
        DatabaseConnection.closeConnection();
    }

    @AfterEach
    void tearDown() {
        DatabaseConnection.closeConnection();
    }

    @Test
    void testConnectionIsValid() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            assertNotNull(conn, "Az adatbázis kapcsolatnak nem szabadna null-nak lennie");
            assertTrue(conn.isValid(2), "A kapcsolatnak érvényesnek kell lennie");
        } catch (SQLException e) {
            fail("Sikertelen adatbázis kapcsolat: " + e.getMessage());
        }
    }

    @Test
    void testConnectionClose() {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            assertNotNull(conn, "Az adatbázis kapcsolatnak nem szabadna null-nak lennie");
            DatabaseConnection.closeConnection();
            assertTrue(conn.isClosed(), "A kapcsolatnak zártnak kell lennie");
        } catch (SQLException e) {
            fail("Hiba a kapcsolat kezelése során: " + e.getMessage());
        }
    }
}