package com.clientwallet.exception.wallet;

import java.util.UUID;

public class WalletWithdrawFailedException extends RuntimeException {
  public WalletWithdrawFailedException(UUID id, Exception e) {
    super("Withdraw from wallet with id: " + id + " failed.", e);
  }
}