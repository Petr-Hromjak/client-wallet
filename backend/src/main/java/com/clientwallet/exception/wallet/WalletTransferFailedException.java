package com.clientwallet.exception.wallet;

import java.util.UUID;

/**
 * Exception thrown when a wallet transfer operation fails.
 * <p>
 * This exception is thrown when an attempt to transfer money from one wallet to another fails.
 * It extends {@link RuntimeException} and provides a custom error message indicating the sender
 * and receiver wallet IDs involved in the failed transfer operation.
 * </p>
 */
public class WalletTransferFailedException extends RuntimeException {

  /**
   * Constructs a new exception with a message indicating that the transfer operation between
   * the specified sender and receiver wallets has failed.
   *
   * @param senderId   The unique identifier of the sender wallet.
   * @param receiverId The unique identifier of the receiver wallet.
   * @param e          The underlying exception that caused the transfer to fail.
   */
  public WalletTransferFailedException(UUID senderId, UUID receiverId, Exception e) {
    super("Transfer from wallet with id: " + senderId + " to wallet with id: " + receiverId + " failed.", e);
  }
}
