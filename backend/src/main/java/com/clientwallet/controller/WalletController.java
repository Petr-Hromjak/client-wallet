package com.clientwallet.controller;

import com.clientwallet.dto.wallet.WalletCreateRequest;
import com.clientwallet.dto.wallet.WalletDepositRequest;
import com.clientwallet.dto.wallet.WalletTransferRequest;
import com.clientwallet.dto.wallet.WalletWithdrawRequest;
import com.clientwallet.model.Transaction;
import com.clientwallet.model.Wallet;
import com.clientwallet.service.WalletService;
import com.clientwallet.validation.ValidWalletId;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/wallet")
@Validated
public class WalletController {

  @Autowired
  private WalletService walletService;

  @PostMapping("/create")
  public ResponseEntity<Wallet> create(@Valid @RequestBody WalletCreateRequest request) {
    return ResponseEntity.ok(walletService.create(request));
  }

  @GetMapping("/get")
  public ResponseEntity<Wallet> get(@ValidWalletId @RequestParam UUID walletId) {
    return ResponseEntity.ok(walletService.get(walletId));
  }

  @PostMapping("/deposit")
  public ResponseEntity<Transaction> deposit(@Valid @RequestBody WalletDepositRequest request) {
    return ResponseEntity.ok(walletService.deposit(request));
  }

  @PostMapping("/withdraw")
  public ResponseEntity<Transaction> withdraw(@Valid @RequestBody WalletWithdrawRequest request) {
    return ResponseEntity.ok(walletService.withdraw(request));
  }

  @PostMapping("/transfer")
  public ResponseEntity<Transaction> transfer(@Valid @RequestBody WalletTransferRequest request) {
    return ResponseEntity.ok(walletService.transfer(request));
  }

  @GetMapping("/getHistory")
  public ResponseEntity<List<Transaction>> getWalletHistory(@ValidWalletId @RequestParam UUID walletId) {
    return ResponseEntity.ok(walletService.getTransactionHistory(walletId));
  }
}
