package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class NextMeetingTest {

    @Test
    public void constructor_invalidNextMeeting_throwsIllegalArgumentException() {
        String validDate = "12-12-2021";
        String validStartTime = "08:00";
        String validEndTime = "13:00";
        String location = "NUS";
        String name = "keith";

        String invalidDate = "2021-02-19";
        String passedDate = "2021-02-19";
        String invalidTime = "2302";

        // invalid string
        assertThrows(IllegalArgumentException.class, () -> new NextMeeting(invalidDate, validStartTime,
            validEndTime, location, name));
        assertThrows(IllegalArgumentException.class, () -> new NextMeeting(validDate, invalidTime,
            validEndTime, location, name));

        // invalid time duration
        assertThrows(IllegalArgumentException.class, () -> new NextMeeting(validDate, validStartTime,
            validStartTime, location, name));
        assertThrows(IllegalArgumentException.class, () -> new NextMeeting(validDate, validEndTime,
            validStartTime, location, name));

        // meeting is in the past
        assertThrows(IllegalArgumentException.class, () -> new NextMeeting(passedDate, validStartTime,
            validEndTime, location, name));
    }

    @Test
    public void isDurationValid() {
        // empty string
        assertTrue(NextMeeting.isDurationValid("", ""));

        // invalid time string
        assertFalse(NextMeeting.isDurationValid("00:61", "02:00"));
        assertFalse(NextMeeting.isDurationValid("00:00", "25:00"));

        // end time < start time
        assertFalse(NextMeeting.isDurationValid("13:00", "08:00"));

        // start time < end time
        assertTrue(NextMeeting.isDurationValid("08:00", "13:00"));
    }

    @Test
    public void isNotPastMeeting() {
        String validDate = "24-12-2050";
        String validEndTime = "13:00";

        // empty string
        assertTrue(NextMeeting.isNotPastMeeting("", ""));

        // invalid strings
        assertFalse(NextMeeting.isNotPastMeeting("24-20-2050", "02:00"));
        assertFalse(NextMeeting.isNotPastMeeting("24-12-2050", "25:00"));
        assertFalse(NextMeeting.isNotPastMeeting("24-12-2050", "23:61"));

        // meeting datetime is in the past
        assertFalse(NextMeeting.isNotPastMeeting("24-12-2020", "00:00"));

        // meeting datetime is in the future
        assertTrue(NextMeeting.isNotPastMeeting(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
            "23:59"));
        assertTrue(NextMeeting.isNotPastMeeting("24-12-2050", "00:00"));
    }

    @Test
    public void isValidNextMeetingString() {
        // null
        assertThrows(NullPointerException.class, () -> NextMeeting.isValidNextMeeting(null));

        // blank
        assertFalse(NextMeeting.isValidNextMeeting(" ")); // empty string

        // missing parts
        assertFalse(NextMeeting.isValidNextMeeting("20-30")); // missing local part
        assertFalse(NextMeeting.isValidNextMeeting("24-12-2021, Starbucks @ UTown"));
        assertFalse(NextMeeting.isValidNextMeeting("24-12-2021 (10:00~12:00),    "));
        assertFalse(NextMeeting.isValidNextMeeting("24-12-2021 (10:00~12:00), very very very long"
                + "very very very long very very very long very very very long very very very long very very very long"
                + "very very very long very very very long very very very long")); // exceed char limit (100)

        // valid next meeting
        assertTrue(NextMeeting.isValidNextMeeting("24-12-2050 (10:00~12:00), Starbucks @ UTown"));
        assertTrue(NextMeeting.isValidNextMeeting("25-12-2050 (14:00~17:00), null"));
    }

    @Test
    public void isMeetingOver() {
        String date1 = "09-10-2022";
        String date2 = "10-10-2022";
        String date3 = "11-10-2022";
        String startTime1 = "11:00";
        String startTime2 = "13:00";
        String endTime1 = "11:30";
        String endTime2 = "13:30";
        String location = "Zoom";
        String name = "ben";

        LocalDate checkDate = LocalDate.of(2022, 10, 10);
        LocalTime checkTime = LocalTime.of(12, 0);

        assertTrue(new NextMeeting(date1, startTime1, endTime1, location, name).isMeetingOver(checkDate, checkTime));
        assertTrue(new NextMeeting(date2, startTime1, endTime1, location, name).isMeetingOver(checkDate, checkTime));
        assertFalse(new NextMeeting(date2, startTime2, endTime2, location, name).isMeetingOver(checkDate, checkTime));
        assertFalse(new NextMeeting(date3, startTime1, endTime1, location, name).isMeetingOver(checkDate, checkTime));
    }

    @Test
    public void convertToLastMet() {
        // LastMet can only be a date that has passed/today's date
        // while NextMeeting has to have Date/Time that has not passed, so we will test with CurrentDate
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String startTime = "23:58";
        String endTime = "23:59";
        String location = "Zoom";
        String name = "ben";

        LastMet expectedLastMet = new LastMet(date);
        NextMeeting nextMeeting = new NextMeeting(date, startTime, endTime, location, name);

        assertEquals(expectedLastMet, nextMeeting.convertToLastMet());
    }

    @Test
    public void toString_returnsCorrectRepresentation() {
        // null meeting
        NextMeeting nullMeeting = new NextMeeting(null, null, null, null, null);
        assertEquals(nullMeeting.toString(), NextMeeting.NO_NEXT_MEETING);

        // non-empty meeting
        NextMeeting nextMeeting = new NextMeeting("18-10-2022", "18:00", "19:00", "Zoom", "alice");
        assertEquals(nextMeeting.toString(), "18-10-2022 (18:00~19:00), Zoom");
    }

    @Test
    public void isEqual() {
        String date1 = "18-10-2022";
        String date2 = "19-10-2022";
        String startTime1 = "18:00";
        String startTime2 = "19:00";
        String endTime1 = "18:30";
        String endTime2 = "19:30";
        String location1 = "Zoom";
        String location2 = "NUS";
        String name1 = "ben";
        String name2 = "dom";

        // same value
        assertTrue(new NextMeeting(date1, startTime1, endTime1, location1, name1).equals(
            new NextMeeting(date1, startTime1, endTime1, location1, name1))
        );

        // different date
        assertFalse(new NextMeeting(date1, startTime1, endTime1, location1, name1).equals(
            new NextMeeting(date2, startTime1, endTime1, location1, name1))
        );

        // different startTime
        assertFalse(new NextMeeting(date1, startTime1, endTime2, location1, name1).equals(
            new NextMeeting(date1, startTime2, endTime2, location1, name1))
        );

        // different endTime
        assertFalse(new NextMeeting(date1, startTime1, endTime1, location1, name1).equals(
            new NextMeeting(date1, startTime1, endTime2, location1, name1))
        );

        // different location
        assertFalse(new NextMeeting(date1, startTime1, endTime1, location1, name1).equals(
            new NextMeeting(date1, startTime1, endTime1, location2, name1))
        );

        // different name
        assertFalse(new NextMeeting(date1, startTime1, endTime1, location1, name1).equals(
                new NextMeeting(date1, startTime1, endTime1, location1, name2))
        );
    }
}
