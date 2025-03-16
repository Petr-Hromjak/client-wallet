package com.clientwallet.exception.wallet;

import com.clientwallet.model.Currency;

public class WalletTransferCurrencyMissMatchException extends RuntimeException {
  public WalletTransferCurrencyMissMatchException(Currency requestCurrency, Currency walletCurrency) {
    super("Cant transfer " + requestCurrency + " with wallet with " + walletCurrency + " currency.");
  }
}