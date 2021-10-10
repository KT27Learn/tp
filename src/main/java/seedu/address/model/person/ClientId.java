package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class ClientId {

    public static final String VALIDATION_REGEX = "\\d{1,}";
    public static final String MESSAGE_CONSTRAINTS = "ClientId should only contain positive integers";
    public final String value;

    /**
     * Constructs an {@code ClientId}.
     *
     * @param clientId ClientId
     */
    public ClientId(String clientId) {
        requireNonNull(clientId);
        checkArgument(isValidClientId(clientId), MESSAGE_CONSTRAINTS);
        this.value = clientId;
    }

    /**
     * Returns if a given string is a valid email.
     * @param test
     */
    public static boolean isValidClientId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns if a given person has the same ClientId
     * @param personToCheck
     */
    public boolean personHasSameClientId(Person personToCheck) {
        return this.equals(personToCheck.getClientId());
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClientId // instanceof handles nulls
                && value.equals(((ClientId) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
}
