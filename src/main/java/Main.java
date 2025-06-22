package hu.tundik.progenv;

import hu.tundik.progenv.model.DVD;
import hu.tundik.progenv.persistence.DVDDao;
import hu.tundik.progenv.service.RentalService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DVDDao dvdDao = new DVDDao();
        RentalService rentalService = new RentalService();

        while (true) {
            System.out.println("\n--- DVD Rental Menü ---");
            System.out.println("1. Listázd a DVD-ket");
            System.out.println("2. DVD kölcsönzése");
            System.out.println("3. DVD visszahozása");
            System.out.println("4. DVD keresés szűrőfeltételekkel");
            System.out.println("0. Kilépés");
            System.out.print("Választás: ");
            int valasztas = scanner.nextInt();

            try {
                switch (valasztas) {
                    case 1 -> {
                        List<DVD> dvds = dvdDao.findAll();
                        dvds.forEach(System.out::println);
                    }
                    case 2 -> {
                        System.out.print("Ügyfél ID: ");
                        int customerId = scanner.nextInt();
                        System.out.print("DVD ID: ");
                        int dvdId = scanner.nextInt();
                        if (rentalService.rentDVD(customerId, dvdId)) {
                            System.out.println("Sikeres kölcsönzés.");
                        } else {
                            System.out.println("Nem sikerült kölcsönözni (DVD nem elérhető?).");
                        }
                    }
                    case 3 -> {
                        System.out.print("Kölcsönzés ID: ");
                        int rentalId = scanner.nextInt();
                        if (rentalService.returnDVD(rentalId)) {
                            System.out.println("Sikeres visszahozatal.");
                        } else {
                            System.out.println("Nem sikerült visszahozni.");
                        }
                    }
                    case 4 -> {
                        scanner.nextLine();
                        System.out.print("Műfaj: ");
                        String genre = scanner.nextLine();
                        System.out.print("Min. hossz (perc): ");
                        int minLength = scanner.nextInt();
                        System.out.print("Max. hossz (perc): ");
                        int maxLength = scanner.nextInt();
                        System.out.print("Max. napi díj: ");
                        double maxPrice = scanner.nextDouble();

                        List<DVD> szurt = dvdDao.findByFilters(genre, minLength, maxLength, maxPrice);
                        if (szurt.isEmpty()) {
                            System.out.println("Nincs találat.");
                        } else {
                            szurt.forEach(System.out::println);
                        }
                    }
                    case 0 -> {
                        System.out.println("Kilépés...");
                        return;
                    }
                    default -> System.out.println("Ismeretlen opció.");
                }
            } catch (SQLException e) {
                System.err.println("Adatbázis hiba: " + e.getMessage());
            }
        }
    }
}
