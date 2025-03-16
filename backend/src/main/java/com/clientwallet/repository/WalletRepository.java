package com.clientwallet.repository;

import com.clientwallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * WalletRepository is a Spring Data JPA repository interface for managing {@link Wallet} entities.
 * <p>
 * This repository provides methods to perform CRUD operations on wallets, as well as custom queries related to wallets in the database.
 * It extends the {@link JpaRepository} interface, inheriting a set of standard JPA repository methods.
 * </p>
 */
public interface WalletRepository extends JpaRepository<Wallet, UUID> {

  /**
   * Checks if a wallet with the given name already exists in the database.
   * <p>
   * This method returns a boolean indicating whether a wallet with the specified name exists. It is useful for checking
   * if a wallet with the same name already exists before creating a new wallet.
   * </p>
   *
   * @param name The name of the wallet to be checked.
   * @return {@code true} if a wallet with the specified name already exists, otherwise {@code false}.
   */
  boolean existsByName(String name);
}
