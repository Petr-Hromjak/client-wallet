package com.clientwallet.model;

/**
 * TransactionStatus represents the different possible statuses of a transaction.
 * It helps track the state of a transaction as it moves through various stages of processing.
 * <p>
 * The statuses are:
 * <ul>
 *   <li>{@link #PENDING}: The transaction is in progress or awaiting some action.</li>
 *   <li>{@link #COMPLETED}: The transaction has been successfully completed.</li>
 *   <li>{@link #FAILED}: The transaction has failed due to some error or issue.</li>
 * </ul>
 */
public enum TransactionStatus {

  /**
   * The transaction is in progress or awaiting some action. It indicates that the transaction
   * has not been completed yet.
   */
  PENDING,

  /**
   * The transaction has been successfully completed. This status indicates that the transaction
   * was successfully processed.
   */
  COMPLETED,

  /**
   * The transaction has failed due to an error or some issue preventing its successful completion.
   * This status indicates that the transaction did not go through as expected.
   */
  FAILED
}
