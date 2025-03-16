package com.clientwallet.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Transaction represents a financial transaction between two wallets in the system.
 * It contains details about the transaction such as sender and receiver wallets,
 * the amount involved, currency, transaction type, and status.
 * <p>
 * The transaction entity is persisted in the database with all relevant transaction details.
 * It includes fields such as sender and receiver wallet references, account number,
 * bank code, transaction type (e.g., deposit, withdrawal, transfer), status, and timestamps.
 * </p>
 */
@Data
@Entity
@Table(name = "transaction")
public class Transaction {

  /**
   * The unique identifier for the transaction.
   * <p>
   * This field is auto-generated and serves as the primary key in the database.
   * </p>
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  /**
   * The wallet from which the transaction is initiated (sender wallet).
   * <p>
   * This field represents the sender's wallet and is a reference to the
   * {@link Wallet} entity. A wallet can be involved in multiple transactions.
   * </p>
   */
  @ManyToOne
  @JoinColumn(name = "sender_wallet_id")
  @OnDelete(action = OnDeleteAction.SET_NULL)
  private Wallet senderWallet;

  /**
   * The wallet to which the transaction is directed (receiver wallet).
   * <p>
   * This field represents the receiver's wallet and is a reference to the
   * {@link Wallet} entity. A wallet can receive multiple transactions.
   * </p>
   */
  @ManyToOne
  @JoinColumn(name = "receiver_wallet_id")
  @OnDelete(action = OnDeleteAction.SET_NULL)
  private Wallet receiverWallet;

  /**
   * The currency used in the transaction.
   * <p>
   * This field is an enumeration value of type {@link Currency}, representing
   * the type of currency used in the transaction (e.g., CZK, EUR).
   * </p>
   */
  @Enumerated(EnumType.STRING)
  private Currency currency;

  /**
   * The account number from which the transaction is initiated.
   * <p>
   * This field represents the bank account number involved in the transaction
   * (if applicable). It is used for deposits and withdrawals.
   * </p>
   */
  private String accountNumber;

  /**
   * The bank code associated with the account.
   * <p>
   * This field represents the bank code associated with the account number
   * for deposit and withdrawal operations.
   * </p>
   */
  private String bankCode;

  /**
   * The amount involved in the transaction.
   * <p>
   * This field represents the monetary value being transferred, deposited, or withdrawn.
   * It is stored as a {@link BigDecimal} for precision.
   * </p>
   */
  private BigDecimal amount;

  /**
   * The type of transaction.
   * <p>
   * This field represents the type of the transaction, whether it is a deposit, withdrawal,
   * or transfer. It is an enumeration of type {@link TransactionType}.
   * </p>
   */
  @Enumerated(EnumType.STRING)
  private TransactionType transactionType;

  /**
   * The current status of the transaction.
   * <p>
   * This field represents the status of the transaction (e.g., pending, completed, failed).
   * It is an enumeration of type {@link TransactionStatus}.
   * </p>
   */
  @Enumerated(EnumType.STRING)
  private TransactionStatus transactionStatus;

  /**
   * The timestamp when the transaction was created.
   * <p>
   * This field is automatically set during the pre-persist lifecycle method,
   * marking the creation time of the transaction.
   * </p>
   */
  private LocalDateTime createdAt;

  /**
   * The timestamp when the transaction was last updated.
   * <p>
   * This field is automatically updated when the transaction is modified.
   * </p>
   */
  private LocalDateTime updatedAt;

  /**
   * Pre-persist lifecycle method to set the creation and update timestamps before
   * the entity is persisted to the database.
   * <p>
   * This method is automatically invoked by JPA before the entity is saved to
   * the database to set the {@link #createdAt} and {@link #updatedAt} fields.
   * </p>
   */
  @PrePersist
  public void prePersist() {
    LocalDateTime now = LocalDateTime.now();
    this.createdAt = now;
    this.updatedAt = now;
  }

  /**
   * Pre-update lifecycle method to update the update timestamp whenever the entity
   * is updated.
   * <p>
   * This method is automatically invoked by JPA before the entity is updated in the
   * database to set the {@link #updatedAt} field to the current time.
   * </p>
   */
  @PreUpdate
  public void preUpdate() {
    this.updatedAt = LocalDateTime.now();
  }
}
