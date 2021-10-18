package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.StringUtil.isValidDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LastMet implements OptionalPersonNonStringField {
    public static final String MESSAGE_CONSTRAINTS = "LastMet should be in the form of Day-Month-Year, "
        + "where Day, month and year should be numerical values.";

    public final LocalDate value;
    public final String dateInString;

    /**
     * Constructs an {@code Email}.
     *
     * @param lastMetDate date agent last meets a client
     */
    public LastMet(String lastMetDate) {
        if (!IS_NULL_VALUE_ALLOWED) {
            requireNonNull(lastMetDate);
        }
        if (lastMetDate == null) {
            lastMetDate = "";
        }

        checkArgument(isValidDate(lastMetDate), MESSAGE_CONSTRAINTS);
        dateInString = lastMetDate;

        if (lastMetDate.isEmpty()) {
            value = null;
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            value = LocalDate.parse(lastMetDate, formatter);
        }
    }

    @Override
    public String toString() {

        if (value == null) {
            return DEFAULT_VALUE;
        } else {
            return this.dateInString;
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof LastMet // instanceof handles nulls
            && value.equals(((LastMet) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
