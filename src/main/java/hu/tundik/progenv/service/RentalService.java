package hu.tundik.progenv.service;

import hu.tundik.progenv.model.DVD;
import hu.tundik.progenv.model.Rental;
import hu.tundik.progenv.persistance.DVDDao;
import hu.tundik.progenv.persistance.RentalDao;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;


public class RentalService {

    private final DVDDao dvdDao = new DVDDao();
    private final RentalDao rentalDao = new RentalDao();

    public boolean rentDVD(int customerId, int dvdId) throws SQLException {
        DVD dvd = dvdDao.findById(dvdId);
        if (dvd == null || !dvd.isAvailable()) {
            return false;
        }

        Rental rental = new Rental(0, customerId, dvdId, new Date(System.currentTimeMillis()), null);
        rentalDao.save(rental);

        dvd.setAvailable(false);
        dvdDao.update(dvd);
        return true;
    }

    public boolean returnDVD(int rentalId) throws SQLException {
        List<Rental> rentals = rentalDao.findAll();
        for (Rental rental : rentals) {
            if (rental.getId() == rentalId && rental.getReturnDate() == null) {
                rental.setReturnDate(new Date(System.currentTimeMillis()));
                rentalDao.updateReturnDate(rental.getId(), rental.getReturnDate());

                DVD dvd = dvdDao.findById(rental.getDvdId());
                if (dvd != null) {
                    dvd.setAvailable(true);
                    dvdDao.update(dvd);
                }
                return true;
            }
        }
        return false;
    }
}
