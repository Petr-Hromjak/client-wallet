package com.clientwallet.exception.wallet;

import com.clientwallet.model.Currency;

/**
 * Exception thrown when there is a currency mismatch during a wallet transfer operation.
 * <p>
 * This exception is thrown when an attempt is made to transfer an amount with a certain currency
 * to a wallet that has a different currency. It extends {@link RuntimeException} and provides a custom
 * error message that indicates the mismatch between the request currency and the wallet's currency.
 * </p>
 */
public class WalletTransferCurrencyMissMatchException extends RuntimeException {

  /**
   * Constructs a new exception with a message indicating that the currencies of the transfer and the wallet do not match.
   *
   * @param requestCurrency The currency of the transfer request.
   * @param walletCurrency  The currency associated with the wallet.
   */
  public WalletTransferCurrencyMissMatchException(Currency requestCurrency, Currency walletCurrency) {
    super("Can't transfer " + requestCurrency + " with wallet with " + walletCurrency + " currency.");
  }
}
