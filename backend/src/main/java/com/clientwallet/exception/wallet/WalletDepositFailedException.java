package com.clientwallet.exception.wallet;

import java.util.UUID;

public class WalletDepositFailedException extends RuntimeException {
  public WalletDepositFailedException(UUID id, Exception e) {
    super("Deposit to wallet with id: " + id + " failed.", e);
  }
}