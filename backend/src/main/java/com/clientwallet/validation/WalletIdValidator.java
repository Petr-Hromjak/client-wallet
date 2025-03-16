package com.clientwallet.validation;

import com.clientwallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.UUID;

/**
 * Validator class for validating wallet IDs.
 * <p>
 * This class implements the {@link ConstraintValidator} interface to provide custom validation logic
 * for wallet IDs, ensuring that the ID corresponds to an existing wallet in the system's database.
 * It is used in conjunction with the {@link ValidWalletId} annotation.
 * </p>
 * <p>
 * The validator checks whether the provided wallet ID is not null and whether the wallet with that
 * ID exists in the {@link WalletRepository}.
 * </p>
 *
 * @see ValidWalletId
 * @see WalletRepository
 */
public class WalletIdValidator implements ConstraintValidator<ValidWalletId, UUID> {

    @Autowired
    private WalletRepository walletRepository;

    /**
     * Validates if the given wallet ID is valid.
     * <p>
     * This method checks if the wallet ID is not null and if the wallet with the given ID exists
     * in the database. If the wallet does not exist, it will return false, meaning the validation fails.
     * </p>
     *
     * @param walletId The wallet ID to be validated.
     * @param context  The context in which the constraint is applied. This can be used for more complex error reporting.
     * @return True if the wallet ID exists in the database, otherwise false.
     */
    @Override
    public boolean isValid(UUID walletId, ConstraintValidatorContext context) {
        if (walletId == null) {
            return false; // Invalid if the wallet ID is null.
        }

        // Check if the wallet exists in the database using the WalletRepository.
        return walletRepository.existsById(walletId);
    }
}
