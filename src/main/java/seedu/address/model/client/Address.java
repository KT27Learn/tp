package seedu.address.model.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.StringUtil.isWithinLengthLimit;

/**
 * Represents a Client's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address extends StringComparable<Address> implements OptionalStringBasedField, LongerFieldLength {

    public static final String MESSAGE_CONSTRAINTS = "Addresses can take any values, and it can be blank "
            + " (Character limit: 100)";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    // TODO: use Optional
    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param address A valid address.
     */
    public Address(String address) {
        requireNonNull(address);
        checkArgument(isValidAddress(address), MESSAGE_CONSTRAINTS);
        if (address.isEmpty()) {
            address = DEFAULT_VALUE;
        }
        value = address;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidAddress(String test) {
        return (IS_BLANK_VALUE_ALLOWED && test.isEmpty())
                || (test.matches(VALIDATION_REGEX) && isWithinLengthLimit(test, MAX_LENGTH));
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Address // instanceof handles nulls
            && value.equals(((Address) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
