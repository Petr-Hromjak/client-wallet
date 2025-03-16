package com.clientwallet.exception.wallet;

import java.math.BigDecimal;

/**
 * Exception thrown when an attempt to withdraw an amount from a wallet exceeds the available funds.
 * <p>
 * This exception is thrown when a withdrawal operation is requested for an amount that exceeds
 * the current balance in the wallet. It provides the requested withdrawal amount and the available
 * wallet balance for reference.
 * </p>
 */
public class WalletWithdrawNotEnoughFundsException extends RuntimeException {

  /**
   * Constructs a new exception with a message indicating that the requested withdrawal exceeds
   * the available balance in the wallet.
   *
   * @param requested The amount the user tried to withdraw.
   * @param walletAmount The current balance of the wallet.
   */
  public WalletWithdrawNotEnoughFundsException(BigDecimal requested, BigDecimal walletAmount) {
    super("Can't withdraw " + requested + " from wallet with only " + walletAmount + ".");
  }
}
