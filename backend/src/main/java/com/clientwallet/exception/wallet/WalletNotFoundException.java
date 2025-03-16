package com.clientwallet.exception.wallet;

import java.util.UUID;

/**
 * Exception thrown when a wallet with a specified ID is not found.
 * <p>
 * This exception is thrown when an operation attempts to access a wallet that does not exist
 * or cannot be found in the system based on the given wallet ID. It extends {@link RuntimeException}
 * and provides a custom error message indicating that the wallet with the specified ID was not found.
 * </p>
 */
public class WalletNotFoundException extends RuntimeException {

  /**
   * Constructs a new exception with a message indicating that the wallet with the specified ID was not found.
   *
   * @param id The unique identifier of the wallet that was not found.
   */
  public WalletNotFoundException(UUID id) {
    super("Wallet with id: " + id + " not found.");
  }
}
