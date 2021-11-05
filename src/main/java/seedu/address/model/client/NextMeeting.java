package seedu.address.model.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.StringUtil.convertEmptyStringIfNull;
import static seedu.address.commons.util.StringUtil.isValidDate;
import static seedu.address.commons.util.StringUtil.isValidTime;
import static seedu.address.commons.util.StringUtil.isWithinLengthLimit;
import static seedu.address.commons.util.StringUtil.parseToLocalDate;
import static seedu.address.commons.util.StringUtil.parseToLocalTime;

import java.time.LocalDate;
import java.time.LocalTime;

public class NextMeeting implements OptionalNonStringBasedField, IgnoreNullComparable<NextMeeting>, LongerFieldLength {

    public static final String DATE_MESSAGE_CONSTRAINTS = "Next meeting date should be in the form of Day-Month-Year, "
            + "where Day, Month and Year should be valid numerical values.";
    public static final String START_TIME_MESSAGE_CONSTRAINTS = "Start time should be in the 24-hour format, "
            + "where Hour and Minutes should be valid numerical values.";
    public static final String END_TIME_MESSAGE_CONSTRAINTS = "End time should be in the 24-hour format, "
            + "where Hour and Minutes should be valid numerical values.";
    public static final String MESSAGE_INVALID_MEETING_STRING = "String representation of Next Meeting is not correct\n"
            + "It should be in the form of m/dd-MM-yyyy (hh:mm~hh:mm), {location}\n"
            + "(Character limit: 100)";
    public static final String MESSAGE_INVALID_TIME_DURATION = "End Time should be after Start Time.";
    public static final String MESSAGE_MEETING_DATE_OVER = "NextMeeting should not be in the past.";
    public static final String NO_NEXT_MEETING = "No meeting planned";
    public static final NextMeeting NULL_MEETING = new NextMeeting(null, null, null,
            null, null);

    public static final String VALID_MEETING_STRING =
            "([0-9]{2})-([0-9]{2})-([0-9]{4}) \\(([0-9]{2}):([0-9]{2})~([0-9]{2}):([0-9]{2})\\),(.|\\s)*\\S(.|\\s)*";

    public final LocalDate date;
    public final LocalTime startTime;
    public final LocalTime endTime;

    public final String dateInString;
    public final String startTimeInString;
    public final String endTimeInString;
    public final String location;
    private Name withWho;

    /**
     * Constructs a {@code NextMeeting}.
     *
     * @param date date agent next meets a client
     */
    public NextMeeting(String date, String startTime, String endTime, String location, String withWho) {
        if (!IS_NULL_VALUE_ALLOWED) {
            requireAllNonNull(date, startTime, endTime, location, withWho);
        }

        date = convertEmptyStringIfNull(date);
        startTime = convertEmptyStringIfNull(startTime);
        endTime = convertEmptyStringIfNull(endTime);
        this.location = convertEmptyStringIfNull(location);
        withWho = convertEmptyStringIfNull(withWho);

        checkArgument(isValidNextMeetingDate(date), DATE_MESSAGE_CONSTRAINTS);
        dateInString = date;

        checkArgument(isValidNextMeetingTime(startTime), START_TIME_MESSAGE_CONSTRAINTS);
        startTimeInString = startTime;

        checkArgument(isValidNextMeetingTime(endTime), END_TIME_MESSAGE_CONSTRAINTS);
        endTimeInString = endTime;

        checkArgument(isDurationValid(startTime, endTime), MESSAGE_INVALID_TIME_DURATION);
        checkArgument(isNotPastMeeting(date, endTime), MESSAGE_MEETING_DATE_OVER);

        this.withWho = withWho.isEmpty() ? null : new Name(withWho);

        this.date = parseToLocalDate(date);
        this.startTime = parseToLocalTime(startTime);
        this.endTime = parseToLocalTime(endTime);
    }

    /**
     * Returns a boolean of whether the given {@code startTime} is before the {@code endTime}.
     */
    public static boolean isDurationValid(String startTime, String endTime) {
        if (startTime.trim().isEmpty() || endTime.trim().isEmpty()) {
            return true;
        }
        if (!isValidTime(startTime) || !isValidTime(endTime)) {
            return false;
        }
        return parseToLocalTime(endTime).isAfter(parseToLocalTime(startTime));
    }

    /**
     * Returns a boolean of whether the meeting has passed based on the current date and time,
     * as well as {@code dateString} and {@code endString}
     */
    public static boolean isNotPastMeeting(String dateString, String endString) {
        if (dateString.trim().isEmpty() || endString.trim().isEmpty()) {
            return true;
        }
        if (!isValidDate(dateString) || !isValidTime(endString)) {
            return false;
        }
        LocalDate dateNow = LocalDate.now();
        LocalTime timeNow = LocalTime.now();
        LocalDate date = parseToLocalDate(dateString);
        return date.isAfter(dateNow) || date.isEqual(dateNow) && parseToLocalTime(endString).isAfter(timeNow);
    }

    public static NextMeeting getNullMeeting() {
        return NULL_MEETING;
    }

    public static boolean isValidNextMeetingDate(String test) {
        return (IS_NULL_VALUE_ALLOWED && test.isEmpty()) || isValidDate(test);
    }

    public static boolean isValidNextMeetingTime(String test) {
        return (IS_NULL_VALUE_ALLOWED && test.isEmpty()) || isValidTime(test);
    }

    /**
     * Returns a boolean if the given {@code test} is a valid NextMeeting string
     */
    public static boolean isValidNextMeeting(String test) {
        return (IS_NULL_VALUE_ALLOWED && test.isEmpty())
            || (test.matches(VALID_MEETING_STRING) && isWithinLengthLimit(test, MAX_LENGTH));
    }

    public void setWithWho(Name withWho) {
        this.withWho = withWho;
    }

    public String getClientName() {
        return this.withWho.fullName;
    }

    public boolean isNullMeeting() {
        return this.equals(NULL_MEETING);
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isSameDay(LocalDate comparison) {
        return comparison.equals(this.date);
    }

    /**
     * Returns the a boolean of whether this {@code NextMeeting} falls before
     * the given {@code checkDate} and {@code checkTime}
     */
    public boolean isMeetingOver(LocalDate checkDate, LocalTime checkTime) {
        if (date == null || endTime == null) {
            return false;
        }
        return date.isBefore(checkDate) || date.isEqual(checkDate) && endTime.isBefore(checkTime);
    }

    /**
     * Converts this {@code NextMeeting} to a {@code LastMet}
     */
    public LastMet convertToLastMet() {
        requireNonNull(date);
        return new LastMet(dateInString);
    }

    /**
     * Returns a new Next Meeting with the same details
     */
    public NextMeeting copyNextMeeting() {
        String newName = withWho != null ? withWho.fullName : "";
        return new NextMeeting(dateInString, startTimeInString, endTimeInString, location, newName);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NextMeeting // instanceof handles nulls
                && dateInString.equals(((NextMeeting) other).dateInString) // state check
                && startTimeInString.equals(((NextMeeting) other).startTimeInString)
                && endTimeInString.equals(((NextMeeting) other).endTimeInString)
                && location.equals(((NextMeeting) other).location)
                && ((withWho == null && ((NextMeeting) other).withWho == null)
                || withWho.equals(((NextMeeting) other).withWho)));
    }

    @Override
    public String toString() {
        if (date == null) {
            return NO_NEXT_MEETING;
        }
        return String.format("%s (%s~%s), %s", dateInString, startTimeInString, endTimeInString, location);

    }

    @Override
    public int compareWithDirection(NextMeeting o, SortDirection sortDirection) {
        if (this.equals(NULL_MEETING) && o.equals(NULL_MEETING)) {
            return 0;
        }

        if (o.equals(NULL_MEETING)) {
            return -1;
        }

        if (this.equals(NULL_MEETING)) {
            return 1;
        }

        NextMeeting x = sortDirection.isAscending() ? this : o;
        NextMeeting y = sortDirection.isAscending() ? o : this;

        int compareDate = x.date.compareTo(y.date);
        if (compareDate != 0) {
            return compareDate;
        }
        int compareStartTime = x.startTime.compareTo(y.startTime);
        int compareEndTime = x.endTime.compareTo(y.endTime);

        return compareStartTime != 0 ? compareStartTime : compareEndTime;
    }
}
