package com.clientwallet.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Wallet represents a user's wallet that stores funds and keeps track of transactions.
 * It contains the wallet's name, currency type, balance, and timestamps for creation and updates.
 * <p>
 * This class is mapped to the "wallet" table in the database and supports entity lifecycle hooks
 * to automatically set the creation and update timestamps.
 * </p>
 */
@Data
@Entity
@Table(name = "wallet")
public class Wallet {

  /**
   * The unique identifier for the wallet.
   * <p>
   * This is a UUID generated automatically for each wallet and serves as the primary key in the database.
   * </p>
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  /**
   * The name of the wallet.
   * <p>
   * This field represents the name of the wallet, such as "Personal Wallet" or "Savings".
   * </p>
   */
  private String name;

  /**
   * The currency associated with the wallet.
   * <p>
   * This field represents the type of currency the wallet uses (e.g., USD, EUR).
   * It is stored as an enum value of {@link Currency}.
   * </p>
   */
  @Enumerated(EnumType.STRING)
  private Currency currency;

  /**
   * The balance of the wallet.
   * <p>
   * This field represents the current balance in the wallet. It can hold a positive or negative value
   * depending on deposits and withdrawals.
   * </p>
   */
  private BigDecimal balance;

  /**
   * The timestamp when the wallet was created.
   * <p>
   * This field is automatically set when the wallet is created and cannot be modified directly.
   * </p>
   */
  private LocalDateTime createdAt;

  /**
   * The timestamp when the wallet was last updated.
   * <p>
   * This field is automatically updated whenever the wallet is modified.
   * </p>
   */
  private LocalDateTime updatedAt;

  /**
   * Pre-persist lifecycle hook to automatically set the creation and update timestamps
   * when the wallet is created.
   * <p>
   * This method is automatically invoked before the wallet is persisted to the database, ensuring that the
   * {@link #createdAt} and {@link #updatedAt} fields are populated.
   * </p>
   */
  @PrePersist
  public void prePersist() {
    LocalDateTime now = LocalDateTime.now();
    this.createdAt = now;
    this.updatedAt = now;
  }

  /**
   * Pre-update lifecycle hook to automatically update the {@link #updatedAt} timestamp
   * whenever the wallet is updated.
   * <p>
   * This method is automatically invoked before an update to the wallet is persisted to the database.
   * </p>
   */
  @PreUpdate
  public void preUpdate() {
    this.updatedAt = LocalDateTime.now();
  }
}
