package com.clientwallet.exception.wallet;

import java.util.UUID;

/**
 * Exception thrown when a transfer attempt is made where the sender and receiver wallets are the same.
 * <p>
 * This exception is thrown when a transfer request specifies the same wallet as both the sender and the receiver,
 * which is an invalid operation. The exception provides the wallet ID that was used for both the sender and receiver.
 * </p>
 */
public class WalletTransferSameWalletException extends RuntimeException {

  /**
   * Constructs a new exception with a message indicating that the sender and receiver wallets cannot be the same.
   *
   * @param id The unique identifier of the wallet that was used as both the sender and the receiver.
   */
  public WalletTransferSameWalletException(UUID id) {
    super("Sender Wallet can be the same as Receiver Wallet. Id: " + id + ".");
  }
}
