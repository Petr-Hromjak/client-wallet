package com.clientwallet.exception.wallet;

import com.clientwallet.model.Currency;

public class WalletDepositCurrencyMissMatchException extends RuntimeException {
  public WalletDepositCurrencyMissMatchException(Currency requestCurrency, Currency walletCurrency) {
    super("Cant deposit " + requestCurrency + " to wallet with " + walletCurrency + " currency.");
  }
}