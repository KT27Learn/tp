package seedu.address.model.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.StringUtil.isWithinLengthLimit;

/**
 * Represents a Client's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone extends NumberComparable<Phone> implements OptionalStringBasedField, StandardFieldLength {

    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should only contain numbers, and it should be at least 3 digits long "
                + "(Character limit: 30)";

    public static final String VALIDATION_REGEX = "\\d{3,}";

    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
        if (phone.isEmpty()) {
            phone = DEFAULT_VALUE;
        }
        value = phone;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
        return (IS_BLANK_VALUE_ALLOWED && test.isEmpty())
            || (test.matches(VALIDATION_REGEX) && isWithinLengthLimit(test, MAX_LENGTH));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Phone // instanceof handles nulls
            && value.equals(((Phone) other).value)); // state check
    }

    @Override
    public String toString() {
        return value;
    }

}
