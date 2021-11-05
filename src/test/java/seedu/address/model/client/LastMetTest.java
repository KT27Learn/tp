package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class LastMetTest {

    @Test
    public void constructor_invalidLastMet_throwsIllegalArgumentException() {
        String invalidLastMet = "hello";
        assertThrows(IllegalArgumentException.class, () -> new LastMet(invalidLastMet));

        String futureDate = "25-12-3000";
        assertThrows(IllegalArgumentException.class, () -> new LastMet(futureDate));
    }

    @Test
    public void isValidLastMet() {
        // null
        assertThrows(NullPointerException.class, () -> LastMet.isValidLastMet(null));

        // blank string
        assertFalse(LastMet.isValidLastMet(" ")); // empty string

        // missing parts
        assertFalse(LastMet.isValidLastMet("20-30")); // missing local part

        // invalid parts
        assertFalse(LastMet.isValidLastMet("20-50-5050")); // invalid domain name
        assertFalse(LastMet.isValidLastMet("60-08-2010"));
        assertFalse(LastMet.isValidLastMet("5654-08-12"));

        // valid date
        assertTrue(LastMet.isValidLastMet("20-12-1999"));
        assertTrue(LastMet.isValidLastMet("20-09-2021"));
        assertTrue(LastMet.isValidLastMet(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))); // today
    }

    @Test
    public void getLaterLastMet() {
        // cases where the other LastMet is lastMetNull will not occur as NullMeeting will never be converted
        // to LastMet
        LastMet lastMetNull = new LastMet("");
        LastMet lastMetA = new LastMet("23-10-2021");
        LastMet lastMetB = new LastMet("13-10-2021");

        // this is null
        assertEquals(lastMetNull.getLaterLastMet(lastMetA), lastMetA);

        // non-null lastMet
        assertEquals(lastMetA.getLaterLastMet(lastMetB), lastMetA);
        assertEquals(lastMetB.getLaterLastMet(lastMetA), lastMetA);
        assertEquals(lastMetB.getLaterLastMet(lastMetB), lastMetB);
    }

    @Test
    public void isNotFutureDate() {
        // used in conjunction with isValidDate, so assume dates to be valid

        // empty string
        assertTrue(LastMet.isNotFutureDate(""));

        // past date
        assertTrue(LastMet.isNotFutureDate("20-12-1999"));
        assertTrue(LastMet.isNotFutureDate("20-09-2021"));

        // current date
        assertTrue(LastMet.isNotFutureDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));

        // future date
        assertFalse(LastMet.isNotFutureDate("20-12-2050"));
    }
}
