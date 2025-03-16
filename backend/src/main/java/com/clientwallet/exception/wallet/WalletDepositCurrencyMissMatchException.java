package com.clientwallet.exception.wallet;

import com.clientwallet.model.Currency;

/**
 * Exception thrown when there is a currency mismatch during a wallet deposit operation.
 * <p>
 * This exception is thrown when the currency of the deposit request does not match the
 * currency of the wallet into which the deposit is being made. It extends {@link RuntimeException}
 * and provides a custom message that includes the requested and wallet currencies.
 * </p>
 */
public class WalletDepositCurrencyMissMatchException extends RuntimeException {

  /**
   * Constructs a new exception with a message indicating a currency mismatch during deposit.
   *
   * @param requestCurrency The currency provided in the deposit request.
   * @param walletCurrency  The currency of the wallet where the deposit is being made.
   */
  public WalletDepositCurrencyMissMatchException(Currency requestCurrency, Currency walletCurrency) {
    super("Can't deposit " + requestCurrency + " to wallet with " + walletCurrency + " currency.");
  }
}
