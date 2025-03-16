package com.clientwallet.repository;

import com.clientwallet.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * TransactionRepository is a Spring Data JPA repository interface for managing {@link Transaction} entities.
 * <p>
 * This repository provides methods to perform CRUD operations and custom queries related to transactions in the database.
 * It extends the {@link JpaRepository} interface, inheriting a set of standard JPA repository methods.
 * </p>
 */
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

  /**
   * Finds a list of transactions by the sender or receiver wallet ID.
   * <p>
   * This method returns all transactions where either the sender wallet or the receiver wallet matches the specified
   * wallet ID. It is useful for querying the transaction history of a specific wallet.
   * </p>
   *
   * @param senderWalletId The ID of the sender's wallet.
   * @param receiverWalletId The ID of the receiver's wallet.
   * @return A list of {@link Transaction} objects where either the sender or receiver wallet matches one of the provided IDs.
   */
  List<Transaction> findBySenderWalletIdOrReceiverWalletId(UUID senderWalletId, UUID receiverWalletId);
}
