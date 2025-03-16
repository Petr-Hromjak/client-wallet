package com.clientwallet.validation;

import com.clientwallet.model.Currency;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * Validator class for ensuring that a {@link Currency} value is valid.
 * <p>
 * This class implements the {@link ConstraintValidator} interface and is used in combination with
 * a custom validation annotation to ensure that a given {@link Currency} is a valid enum value.
 * </p>
 */
public class CurrencyValidator implements ConstraintValidator<ValidCurrency, Currency> {

    /**
     * Validates if the provided {@link Currency} value is valid.
     * <p>
     * This method checks if the value is not {@code null} and is a valid {@link Currency} enum constant.
     * </p>
     *
     * @param value The {@link Currency} value to be validated.
     * @param context The {@link ConstraintValidatorContext} in which the validation is being executed.
     * @return {@code true} if the value is a valid currency, otherwise {@code false}.
     */
    @Override
    public boolean isValid(Currency value, ConstraintValidatorContext context) {
        return value != null && Arrays.asList(Currency.values()).contains(value);
    }
}
