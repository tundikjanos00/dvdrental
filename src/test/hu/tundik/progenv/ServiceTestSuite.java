package hu.tundik.progenv;

import hu.tundik.progenv.service.RentalServiceTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        DatabaseConnectionTest.class,
        RentalServiceTest.class
})
public class ServiceTestSuite {
    // Ez az osztály üres maradhat, csak a tesztek csoportosítására szolgál
}