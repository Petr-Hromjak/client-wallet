package com.clientwallet.exception.wallet;

import java.math.BigDecimal;

public class WalletWithdrawNotEnoughFundsException extends RuntimeException {
  public WalletWithdrawNotEnoughFundsException(BigDecimal requested, BigDecimal walletAmount) {
    super("Cant withdraw " + requested + " from wallet with only " + walletAmount + ".");
  }
}