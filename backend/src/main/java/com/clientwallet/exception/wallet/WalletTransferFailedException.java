package com.clientwallet.exception.wallet;

import java.util.UUID;

public class WalletTransferFailedException extends RuntimeException {
  public WalletTransferFailedException(UUID senderId, UUID receiverId, Exception e) {
    super("Transfer from wallet with id: " + senderId + "to wallet with id: " + receiverId +" failed.", e);
  }
}