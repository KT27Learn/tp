package seedu.address.model.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.StringUtil.isWithinLengthLimit;

public class CurrentPlan extends StringComparable<CurrentPlan> implements OptionalStringBasedField, LongerFieldLength {

    public static final String MESSAGE_CONSTRAINTS = "Current plan can take any values, and it can be blank"
            + " (Character limit: 100)";

    /*
     * The first character of the current plan must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code CurrentPlan}.
     *
     * @param currentPlan valid current plan.
     */
    public CurrentPlan(String currentPlan) {
        requireNonNull(currentPlan);
        checkArgument(isValidCurrentPlan(currentPlan), MESSAGE_CONSTRAINTS);
        if (currentPlan.isEmpty()) {
            currentPlan = DEFAULT_VALUE;
        }
        value = currentPlan;
    }

    /**
     * Returns true if a given string is a valid current plan.
     */
    public static boolean isValidCurrentPlan(String test) {
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
            || (other instanceof CurrentPlan // instanceof handles nulls
            && value.equals(((CurrentPlan) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
