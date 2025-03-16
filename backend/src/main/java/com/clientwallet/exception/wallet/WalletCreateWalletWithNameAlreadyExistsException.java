package com.clientwallet.exception.wallet;

/**
 * Exception thrown when attempting to create a wallet with a name that already exists.
 * <p>
 * This exception is used to indicate that a wallet creation operation failed because
 * the wallet name provided already exists in the system. It extends {@link RuntimeException}
 * and provides a custom message detailing the conflicting wallet name.
 * </p>
 */
public class WalletCreateWalletWithNameAlreadyExistsException extends RuntimeException {

  /**
   * Constructs a new exception with a message that indicates the wallet name already exists.
   *
   * @param name The name of the wallet that caused the exception to be thrown.
   */
  public WalletCreateWalletWithNameAlreadyExistsException(String name) {
    super("Can't create wallet with name: " + name + " because it already exists.");
  }
}
