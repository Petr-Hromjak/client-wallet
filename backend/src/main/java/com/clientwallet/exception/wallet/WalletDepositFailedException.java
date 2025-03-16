package com.clientwallet.exception.wallet;

import java.util.UUID;

/**
 * Exception thrown when a deposit operation to a wallet fails.
 * <p>
 * This exception is thrown when a deposit to a wallet fails for any reason. It extends
 * {@link RuntimeException} and provides a custom message indicating that the deposit
 * to the wallet with a specific ID has failed. The exception also includes the original
 * exception that caused the failure.
 * </p>
 */
public class WalletDepositFailedException extends RuntimeException {

  /**
   * Constructs a new exception with a message indicating the failure of the deposit operation.
   *
   * @param id The unique identifier of the wallet to which the deposit was attempted.
   * @param e  The original exception that caused the deposit failure.
   */
  public WalletDepositFailedException(UUID id, Exception e) {
    super("Deposit to wallet with id: " + id + " failed.", e);
  }
}
