package com.clientwallet.validation;

import com.clientwallet.model.Currency;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation used to validate {@link Currency} values.
 * <p>
 * This annotation is used in combination with the {@link CurrencyValidator} to ensure that a field
 * or parameter is a valid {@link Currency} enum value. It can be applied to fields and parameters
 * and will trigger the validation process when validated.
 * </p>
 *
 * @see CurrencyValidator
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CurrencyValidator.class)
public @interface ValidCurrency {

    /**
     * The default error message that will be shown when the validation fails.
     *
     * @return The default error message.
     */
    String message() default "Invalid currency type";

    /**
     * Groups can be used to apply different validation rules to different validation groups.
     *
     * @return The groups that this constraint belongs to.
     */
    Class<?>[] groups() default {};

    /**
     * The payload allows additional data to be carried with the annotation.
     *
     * @return The payload associated with the annotation.
     */
    Class<? extends Payload>[] payload() default {};
}
