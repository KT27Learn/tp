package seedu.address.model.client;

/**
 * Represents an optional client field whose stored value is not a string.
 */
public interface OptionalNonStringBasedField extends Field {

    boolean IS_BLANK_VALUE_ALLOWED = true;
    boolean IS_NULL_VALUE_ALLOWED = true; // be careful with this since it requires you to handle null case
    String DEFAULT_VALUE = "";
    boolean IS_EDITABLE = true;
}
