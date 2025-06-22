package hu.tundik.progenv.service;

import hu.tundik.progenv.model.DVD;
import hu.tundik.progenv.persistance.DatabaseConnection;
import hu.tundik.progenv.persistance.DVDDao;
import hu.tundik.progenv.service.RentalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class RentalServiceTest {

    private RentalService service;
    private DVDDao dvdDao;

    @BeforeEach
    void setUp() {
        service = new RentalService();
        dvdDao = new DVDDao();
    }

    @AfterEach
    void tearDown() {
        DatabaseConnection.closeConnection();
    }

    @Test
    void testRentUnavailableDVD() {
        try {
            boolean result = service.rentDVD(1, 9999);
            assertFalse(result, "Nem létező DVD kölcsönzése sikertelen kell legyen");
        } catch (SQLException e) {
            fail("Váratlan hiba a kölcsönzés során: " + e.getMessage());
        }
    }

    @Test
    void testRentAndReturnDVD() {
        try {
            // Tesztadat létrehozása
            DVD testDvd = new DVD(0, "Test Movie", "Action", 120, 1000.0, true);
            dvdDao.save(testDvd);

            // Kölcsönzés tesztelése
            boolean rentResult = service.rentDVD(1, testDvd.getId());
            assertTrue(rentResult, "A kölcsönzésnek sikeresnek kell lennie");

            // DVD állapotának ellenőrzése
            DVD rentedDvd = dvdDao.findById(testDvd.getId());
            assertFalse(rentedDvd.isAvailable(), "A DVD-nek foglaltnak kell lennie");

            // Visszahozás tesztelése
            // Note: Itt feltételezzük, hogy ismerjük a rental ID-t,
            // valós implementációban ezt a kölcsönzéskor kellene visszakapnunk
            boolean returnResult = service.returnDVD(/* rental_id */ 1);
            assertTrue(returnResult, "A visszahozásnak sikeresnek kell lennie");

            // Takarítás
            dvdDao.delete(testDvd.getId());

        } catch (SQLException e) {
            fail("Váratlan hiba: " + e.getMessage());
        }
    }
}