package com.clientwallet.validation;

import com.clientwallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.UUID;

public class WalletIdValidator implements ConstraintValidator<ValidWalletId, UUID> {

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public boolean isValid(UUID walletId, ConstraintValidatorContext context) {
        if (walletId == null) {
            return false;
        }
        
        // Check if the wallet exists in the database
        return walletRepository.existsById(walletId);
    }
}
