package com.clientwallet.exception.wallet;

import java.util.UUID;

/**
 * Exception thrown when a withdrawal attempt fails from a wallet.
 * <p>
 * This exception is thrown when a withdrawal operation cannot be completed for some reason.
 * The exception provides the wallet's ID and the underlying exception that caused the failure.
 * </p>
 */
public class WalletWithdrawFailedException extends RuntimeException {

  /**
   * Constructs a new exception with a message indicating that the withdrawal from the specified wallet failed.
   *
   * @param id The unique identifier of the wallet from which the withdrawal was attempted.
   * @param e The underlying exception that caused the withdrawal failure.
   */
  public WalletWithdrawFailedException(UUID id, Exception e) {
    super("Withdraw from wallet with id: " + id + " failed.", e);
  }
}
