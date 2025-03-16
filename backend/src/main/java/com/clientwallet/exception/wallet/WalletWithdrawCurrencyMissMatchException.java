package com.clientwallet.exception.wallet;

import com.clientwallet.model.Currency;

/**
 * Exception thrown when a withdrawal attempt is made with a currency mismatch between the requested and wallet currency.
 * <p>
 * This exception is thrown when a user attempts to withdraw a certain currency from a wallet that holds a different currency.
 * The exception provides details of the currencies involved: the requested currency and the wallet's currency.
 * </p>
 */
public class WalletWithdrawCurrencyMissMatchException extends RuntimeException {

  /**
   * Constructs a new exception with a message indicating that the requested currency does not match the wallet's currency.
   *
   * @param requestCurrency The currency that was requested for withdrawal.
   * @param walletCurrency The currency that is stored in the wallet.
   */
  public WalletWithdrawCurrencyMissMatchException(Currency requestCurrency, Currency walletCurrency) {
    super("Can't withdraw " + requestCurrency + " from wallet with " + walletCurrency + " currency.");
  }
}
