package seedu.address.model.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.StringUtil.getCurrencyFormat;
import static seedu.address.commons.util.StringUtil.isValidCurrencyValue;

public class DisposableIncome extends NumberComparable<DisposableIncome> implements OptionalStringBasedField {
    public static final String MESSAGE_CONSTRAINTS =
        "Disposable Income numbers should be a positive integer only";
    public static final String DEFAULT_VALUE = "0.00";

    public final String value;
    public final String valueWithSymbol;

    /**
     * Constructs a {@code Risk Appetite}.
     *
     * @param disposableIncome A valid risk appetite number.
     */
    public DisposableIncome(String disposableIncome) {
        requireNonNull(disposableIncome);
        checkArgument(isValidDisposableIncome(disposableIncome), MESSAGE_CONSTRAINTS);
        if (disposableIncome.isEmpty()) {
            disposableIncome = DEFAULT_VALUE;
        }
        valueWithSymbol = getCurrencyFormat(disposableIncome, true);
        value = getCurrencyFormat(disposableIncome, false);

    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidDisposableIncome(String test) {
        return (IS_BLANK_VALUE_ALLOWED && test.isEmpty()) || isValidCurrencyValue(test);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DisposableIncome // instanceof handles nulls
            && value.equals(((DisposableIncome) other).value)); // state check
    }

    @Override
    public String toString() {
        return value;
    }
}
