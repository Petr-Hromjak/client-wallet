package com.clientwallet.exception.wallet;

import java.util.UUID;

public class WalletNotFoundException extends RuntimeException {
  public WalletNotFoundException(UUID id) {
    super("Wallet with id: " + id + " not found.");
  }
}