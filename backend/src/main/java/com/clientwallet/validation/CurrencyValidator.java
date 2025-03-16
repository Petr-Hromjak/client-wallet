package com.clientwallet.validation;

import com.clientwallet.model.Currency;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class CurrencyValidator implements ConstraintValidator<ValidCurrency, Currency> {

    @Override
    public boolean isValid(Currency value, ConstraintValidatorContext context) {
        return value != null && Arrays.asList(Currency.values()).contains(value);
    }
}
