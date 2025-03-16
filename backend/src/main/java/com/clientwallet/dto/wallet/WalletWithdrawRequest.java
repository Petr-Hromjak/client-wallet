package com.clientwallet.dto.wallet;

import com.clientwallet.model.Currency;
import com.clientwallet.validation.ValidCurrency;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * WalletWithdrawRequest is a DTO (Data Transfer Object) that represents the request body
 * for withdrawing money from a wallet. It contains the necessary details required to perform
 * a withdrawal operation, including the wallet ID, currency, bank account details, and the amount to be withdrawn.
 * <p>
 * This class ensures that the input data for a withdrawal request is validated before being processed.
 * It includes validation annotations to ensure proper input formats, such as checking that the
 * wallet ID is not null, the currency is valid, the account number is well-formed, and the amount is positive.
 * </p>
 */
@Data
public class WalletWithdrawRequest {

    /**
     * The unique identifier of the wallet from which the funds will be withdrawn.
     * <p>
     * This field is required and cannot be null.
     * </p>
     */
    @NotNull(message = "Wallet ID cannot be null.")
    private UUID walletId;

    /**
     * The currency of the withdrawal.
     * <p>
     * This field is required and cannot be null. The currency is validated using the custom
     * {@link ValidCurrency} annotation to ensure it is a valid currency type.
     * </p>
     */
    @NotNull(message = "Currency cannot be null.")
    @ValidCurrency
    private Currency currency;

    /**
     * The bank account number from which the withdrawal is being made.
     * <p>
     * This field is required and must not be empty. The account number should only contain
     * digits and be between 10 and 18 digits long.
     * </p>
     */
    @NotBlank(message = "Bank account number cannot be empty")
    @Pattern(regexp = "\\d+", message = "Bank account number must contain only numbers")
    @Size(min = 10, max = 18, message = "Bank account number must be between 10 and 18 digits")
    private String accountNumber;

    /**
     * The bank code associated with the withdrawal.
     * <p>
     * This field is required and must not be empty. The bank code should only contain
     * digits and be between 4 and 6 digits long.
     * </p>
     */
    @NotBlank(message = "Bank code cannot be empty")
    @Pattern(regexp = "\\d+", message = "Bank code must contain only numbers")
    @Size(min = 4, max = 6, message = "Bank code must be between 4 and 6 digits")
    private String bankCode;

    /**
     * The amount to be withdrawn from the wallet.
     * <p>
     * This field is required and must be a positive value.
     * </p>
     */
    @NotNull(message = "Amount cannot be null.")
    @Positive(message = "Amount must be positive.")
    private BigDecimal amount;
}
