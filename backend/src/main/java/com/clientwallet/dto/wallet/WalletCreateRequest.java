package com.clientwallet.dto.wallet;

import com.clientwallet.model.Currency;
import com.clientwallet.validation.ValidCurrency;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class WalletCreateRequest {

    @NotBlank(message = "Wallet name cannot be empty.")
    @Size(min = 3, max = 50, message = "Wallet name must be between 3 and 50 characters.")
    private String name;

    @NotNull(message = "Currency cannot be null.")
    @ValidCurrency
    private Currency currency;
}