package com.clientwallet.exception.wallet;

import java.util.UUID;

public class WalletTransferSameWalletException extends RuntimeException {
  public WalletTransferSameWalletException(UUID id) {
    super("Sender Wallet can be same as Receiver Wallet. Id: " + id + ".");
  }
}