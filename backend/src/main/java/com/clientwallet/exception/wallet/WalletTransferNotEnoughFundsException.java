package com.clientwallet.exception.wallet;

import java.math.BigDecimal;

/**
 * Exception thrown when a wallet transfer fails due to insufficient funds in the sender's wallet.
 * <p>
 * This exception is thrown when a transfer attempt requests an amount greater than the available balance
 * in the sender's wallet. It provides details about the requested transfer amount and the current wallet balance.
 * </p>
 */
public class WalletTransferNotEnoughFundsException extends RuntimeException {

  /**
   * Constructs a new exception with a message indicating that the transfer failed due to insufficient funds.
   *
   * @param requested    The amount requested for the transfer.
   * @param walletAmount The current balance in the wallet.
   */
  public WalletTransferNotEnoughFundsException(BigDecimal requested, BigDecimal walletAmount) {
    super("Can't transfer " + requested + " from wallet with only " + walletAmount + ".");
  }
}
