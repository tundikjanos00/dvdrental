package hu.tundik.progenv.test;

import hu.tundik.progenv.model.DVD;
import hu.tundik.progenv.persistence.DVDDao;
import hu.tundik.progenv.service.RentalService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class RentalServiceTest {

    private static RentalService service;
    private static DVDDao dvdDao;

    @BeforeAll
    static void setup() {
        service = new RentalService();
        dvdDao = new DVDDao();
    }

    @Test
    void testRentUnavailableDVD() throws SQLException {
        boolean result = service.rentDVD(1, 9999); // Nem létező DVD
        assertFalse(result);
    }

    @Test
    void testRentValidAvailableDVD() throws SQLException {
        List<DVD> available = dvdDao.findByFilters("Sci-Fi", 100, 200, 5.00);
        assumeTrue(!available.isEmpty());
        DVD dvd = available.get(0);

        boolean success = service.rentDVD(1, dvd.getId());
        assertTrue(success);

        DVD updated = dvdDao.findById(dvd.getId());
        assertFalse(updated.isAvailable());
    }
}
