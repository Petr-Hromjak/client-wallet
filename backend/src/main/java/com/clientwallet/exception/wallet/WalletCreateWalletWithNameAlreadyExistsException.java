package com.clientwallet.exception.wallet;

public class WalletCreateWalletWithNameAlreadyExistsException extends RuntimeException {
  public WalletCreateWalletWithNameAlreadyExistsException(String name) {
    super("Cant create wallet with name: " + name + " because it already exists.");
  }
}