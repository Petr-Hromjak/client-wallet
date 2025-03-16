package com.clientwallet.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.UUID;

/**
 * Custom annotation used to validate {@link UUID} wallet IDs.
 * <p>
 * This annotation is used in combination with the {@link WalletIdValidator} to ensure that a field
 * or parameter representing a wallet ID is valid. It can be applied to fields or method parameters
 * that are expected to contain a wallet ID, typically a {@link UUID}.
 * </p>
 *
 * @see WalletIdValidator
 */
@Constraint(validatedBy = WalletIdValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidWalletId {

    /**
     * The default error message that will be shown when the validation fails.
     *
     * @return The default error message.
     */
    String message() default "Invalid wallet ID";

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
