package com.clientwallet.model;

/**
 * TransactionType represents the different types of transactions that can be performed in the system.
 * It helps categorize the transaction based on the operation being performed.
 * <p>
 *  The types of transactions are:
 *  <ul>
 *   <li>{@link #DEPOSIT}: Represents a deposit transaction, where funds are added to a wallet.</li>
 *   <li>{@link #WITHDRAWAL}: Represents a withdrawal transaction, where funds are removed from a wallet.</li>
 *   <li>{@link #TRANSFER}: Represents a transfer transaction, where funds are moved between two wallets.</li>
 *  </ul>
 */
public enum TransactionType {

  /**
   * A deposit transaction where funds are added to a wallet.
   */
  DEPOSIT,

  /**
   * A withdrawal transaction where funds are removed from a wallet.
   */
  WITHDRAWAL,

  /**
   * A transfer transaction where funds are moved from one wallet to another.
   */
  TRANSFER
}
