package com.clientwallet.dto.wallet;

import com.clientwallet.model.Currency;
import com.clientwallet.validation.ValidCurrency;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * WalletTransferRequest is a DTO (Data Transfer Object) that represents the request body
 * for transferring funds between two wallets. It contains the necessary details required to
 * perform a transfer operation, including the sender and receiver wallet IDs, currency,
 * and the amount to be transferred.
 * <p>
 * This class ensures that the input data for the transfer request is validated before being processed.
 * It includes various validation annotations to ensure proper input formats, such as ensuring the
 * sender and receiver wallet IDs are not null, the currency is valid, and the amount is positive.
 * </p>
 */
@Data
public class WalletTransferRequest {

    /**
     * The unique identifier of the sender's wallet from which the funds will be transferred.
     * <p>
     * This field is required and cannot be null.
     * </p>
     */
    @NotNull(message = "Sender Wallet ID cannot be null.")
    private UUID senderWalletId;

    /**
     * The unique identifier of the receiver's wallet to which the funds will be transferred.
     * <p>
     * This field is required and cannot be null.
     * </p>
     */
    @NotNull(message = "Receiver Wallet ID cannot be null.")
    private UUID receiverWalletId;

    /**
     * The currency of the transfer.
     * <p>
     * This field is required and cannot be null. The currency is validated using the custom
     * {@link ValidCurrency} annotation to ensure it is a valid currency type.
     * </p>
     */
    @NotNull(message = "Currency cannot be null.")
    @ValidCurrency
    private Currency currency;

    /**
     * The amount to be transferred between the sender's and receiver's wallets.
     * <p>
     * This field is required and must be a positive value.
     * </p>
     */
    @NotNull(message = "Amount cannot be null.")
    @Positive(message = "Amount must be positive.")
    private BigDecimal amount;
}
