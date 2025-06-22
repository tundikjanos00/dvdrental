package hu.tundik.progenv.service;

import hu.tundik.progenv.model.Rental;
import hu.tundik.progenv.persistance.RentalDao;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.io.*;
import java.text.SimpleDateFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVFormat;

public class RentalExportService {
    private final RentalDao rentalDao;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public RentalExportService(RentalDao rentalDao) {
        this.rentalDao = rentalDao;
    }

    public void exportToCSV(String path) throws ServiceException {
        if (path == null || path.trim().isEmpty()) {
            throw new IllegalArgumentException("Az elérési út nem lehet üres");
        }

        try {
            List<Rental> rentals = rentalDao.findAll();
            try (CSVPrinter printer = new CSVPrinter(new FileWriter(path),
                    CSVFormat.DEFAULT.withHeader("ID", "CustomerID", "DVDID", "RentalDate", "ReturnDate"))) {

                for (Rental rental : rentals) {
                    printer.printRecord(
                            rental.getId(),
                            rental.getCustomerId(),
                            rental.getDvdId(),
                            rental.getRentalDate() != null ? dateFormat.format(rental.getRentalDate()) : "",
                            rental.getReturnDate() != null ? dateFormat.format(rental.getReturnDate()) : ""
                    );
                }
            }
        } catch (SQLException e) {
            throw new ServiceException("Adatbázis hiba történt: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new ServiceException("Fájl írási hiba történt: " + e.getMessage(), e);
        }
    }
}