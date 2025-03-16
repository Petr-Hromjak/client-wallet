package com.clientwallet.exception.wallet;

import java.math.BigDecimal;

public class WalletTransferNotEnoughFundsException extends RuntimeException {
  public WalletTransferNotEnoughFundsException(BigDecimal requested, BigDecimal walletAmount) {
    super("Cant transfer " + requested + " from wallet with only " + walletAmount + ".");
  }
}