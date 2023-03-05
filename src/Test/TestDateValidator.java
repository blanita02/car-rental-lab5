package Test;
import Validator.*;

import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TestDateValidator {
    @Test
    public void testDateValidator(){
        DateTimeFormatter dateFormatter = DateTimeFormatter.BASIC_ISO_DATE;
        DateValidator validator = new DateValidatorUsingLocalDate(dateFormatter);

        assertTrue(validator.isValidDate("20190228"));
        assertFalse(validator.isValidDate("20190229s"));
    }
}
