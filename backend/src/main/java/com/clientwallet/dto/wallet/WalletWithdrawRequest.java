package com.clientwallet.dto.wallet;

import com.clientwallet.model.Currency;
import com.clientwallet.validation.ValidCurrency;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class WalletWithdrawRequest {

    @NotNull(message = "Wallet ID cannot be null.")
    private UUID walletId;

    @NotNull(message = "Currency cannot be null.")
    @ValidCurrency
    private Currency currency;

    @NotBlank(message = "Bank account number cannot be empty")
    @Pattern(regexp = "\\d+", message = "Bank account number must contain only numbers")
    @Size(min = 10, max = 18, message = "Bank account number must be between 10 and 18 digits")
    private String accountNumber;

    @NotBlank(message = "Bank code cannot be empty")
    @Pattern(regexp = "\\d+", message = "Bank code must contain only numbers")
    @Size(min = 4, max = 6, message = "Bank code must be between 4 and 6 digits")
    private String bankCode;

    @NotNull(message = "Amount cannot be null.")
    @Positive(message = "Amount must be positive.")
    private BigDecimal amount;
}