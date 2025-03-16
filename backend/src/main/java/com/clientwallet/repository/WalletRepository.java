package com.clientwallet.repository;

import com.clientwallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
  boolean existsByName(String name);
}
