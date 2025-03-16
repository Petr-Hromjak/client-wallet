package com.clientwallet.dto.wallet;

import com.clientwallet.model.Currency;
import com.clientwallet.validation.ValidCurrency;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * WalletCreateRequest is a DTO (Data Transfer Object) used to represent the request body
 * for creating a new wallet. It contains the required information for creating a wallet,
 * such as the wallet's name and currency.
 * <p>
 * This class is used as an input to the API endpoint that handles the creation of wallets.
 * It ensures that the input data meets the specified validation constraints.
 * </p>
 */
@Data
public class WalletCreateRequest {

    /**
     * The name of the wallet.
     * <p>
     * This field is required and cannot be empty. It must contain between 3 and 50 characters.
     * </p>
     */
    @NotBlank(message = "Wallet name cannot be empty.")
    @Size(min = 3, max = 50, message = "Wallet name must be between 3 and 50 characters.")
    private String name;

    /**
     * The currency associated with the wallet.
     * <p>
     * This field is required and cannot be null. The currency must be validated using a custom validator,
     * {@link ValidCurrency}, to ensure that it is a valid currency type.
     * </p>
     */
    @NotNull(message = "Currency cannot be null.")
    @ValidCurrency
    private Currency currency;
}
