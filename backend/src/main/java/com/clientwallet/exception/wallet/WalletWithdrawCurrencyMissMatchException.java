package com.clientwallet.exception.wallet;

import com.clientwallet.model.Currency;

public class WalletWithdrawCurrencyMissMatchException extends RuntimeException {
  public WalletWithdrawCurrencyMissMatchException(Currency requestCurrency, Currency walletCurrency) {
    super("Cant withdraw " + requestCurrency + " from wallet with " + walletCurrency + " currency.");
  }
}