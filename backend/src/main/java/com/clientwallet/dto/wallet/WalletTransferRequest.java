package com.clientwallet.dto.wallet;

import com.clientwallet.model.Currency;
import com.clientwallet.validation.ValidCurrency;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class WalletTransferRequest {

    @NotNull(message = "Sender Wallet ID cannot be null.")
    private UUID senderWalletId;

    @NotNull(message = "Receiver Wallet ID cannot be null.")
    private UUID receiverWalletId;

    @NotNull(message = "Currency cannot be null.")
    @ValidCurrency
    private Currency currency;

    @NotNull(message = "Amount cannot be null.")
    @Positive(message = "Amount must be positive.")
    private BigDecimal amount;
}