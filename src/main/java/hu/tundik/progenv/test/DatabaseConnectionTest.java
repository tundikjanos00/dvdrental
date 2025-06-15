package hu.tundik.progenv.test;

import hu.tundik.progenv.persistence.DatabaseConnection;
import org.junit.Test;
//import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;
//import static org.junit.jupiter.api.Assertions.*;

public class DatabaseConnectionTest
{

    @Test
    void testConnectionIsValid()
    {
        try
        {
            Connection conn = DatabaseConnection.getConnection();
            assertNotNull(conn);
            assertTrue(conn.isValid(2));
        }
        catch (SQLException e)
        {
            fail("Sikertelen adatbázis kapcsolat: " + e.getMessage());
        }
    }
}
