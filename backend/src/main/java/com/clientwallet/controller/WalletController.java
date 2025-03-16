package com.clientwallet.controller;

import com.clientwallet.dto.wallet.WalletCreateRequest;
import com.clientwallet.dto.wallet.WalletDepositRequest;
import com.clientwallet.dto.wallet.WalletTransferRequest;
import com.clientwallet.dto.wallet.WalletWithdrawRequest;
import com.clientwallet.exception.wallet.*;
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

/**
 * WalletController is a REST controller that provides API endpoints for wallet-related operations.
 * These operations include creating a wallet, retrieving wallet details, depositing funds, withdrawing funds,
 * transferring funds between wallets, and fetching transaction history.
 */
@CrossOrigin(origins = "${cors.allowedOrigins}")
@RestController
@RequestMapping("/wallet")
@Validated
public class WalletController {

  @Autowired
  private WalletService walletService;

  /**
   * Creates a new wallet based on the provided details.
   * <p>
   * This method accepts a request body containing the wallet name and currency, performs validation,
   * and creates a new wallet in the system. It returns the created wallet in the response.
   * </p>
   *
   * @param request The request containing wallet creation details such as name and currency.
   * @return ResponseEntity containing the created wallet object.
   * @throws WalletCreateWalletWithNameAlreadyExistsException if a wallet with the same name already exists.
   */
  @PostMapping("/create")
  public ResponseEntity<Wallet> create(@Valid @RequestBody WalletCreateRequest request) {
    return ResponseEntity.ok(walletService.create(request));
  }

  /**
   * Retrieves the details of a wallet by its unique wallet ID.
   * <p>
   * This method accepts a wallet ID as a request parameter, fetches the wallet from the system,
   * and returns the wallet's details. If the wallet with the specified ID is not found,
   * an exception is thrown.
   * </p>
   *
   * @param walletId The unique identifier of the wallet to be fetched.
   * @return ResponseEntity containing the wallet details.
   * @throws WalletNotFoundException if the wallet with the specified ID does not exist.
   */
  @GetMapping("/get")
  public ResponseEntity<Wallet> get(@ValidWalletId @RequestParam UUID walletId) {
    return ResponseEntity.ok(walletService.get(walletId));
  }

  /**
   * Retrieves a list of all wallets.
   *
   * @return ResponseEntity containing a list of all wallets.
   */
  @GetMapping("/list")
  public ResponseEntity<List<Wallet>> listWallets() {
    return ResponseEntity.ok(walletService.list());
  }

  /**
   * Deposits funds into a wallet.
   * <p>
   * This method accepts a request containing deposit details, including the wallet ID, amount, currency, etc.,
   * performs the deposit, and returns a transaction object representing the deposit.
   * </p>
   *
   * @param request The request containing deposit details including wallet ID, amount, currency, etc.
   * @return ResponseEntity containing the created transaction for the deposit.
   * @throws WalletNotFoundException if the wallet with the specified ID does not exist.
   * @throws WalletDepositFailedException if the deposit fails due to any reason.
   * @throws WalletDepositCurrencyMissMatchException if there is a currency mismatch between the wallet and the deposit.
   */
  @PostMapping("/deposit")
  public ResponseEntity<Transaction> deposit(@Valid @RequestBody WalletDepositRequest request) {
    return ResponseEntity.ok(walletService.deposit(request));
  }

  /**
   * Withdraws funds from a wallet.
   * <p>
   * This method accepts a request containing withdrawal details, including the wallet ID, amount, currency, etc.,
   * performs the withdrawal, and returns a transaction object representing the withdrawal.
   * </p>
   *
   * @param request The request containing withdrawal details including wallet ID, amount, currency, etc.
   * @return ResponseEntity containing the created transaction for the withdrawal.
   * @throws WalletNotFoundException if the wallet with the specified ID does not exist.
   * @throws WalletWithdrawFailedException if the withdrawal fails due to any reason.
   * @throws WalletWithdrawCurrencyMissMatchException if there is a currency mismatch between the wallet and the withdrawal.
   * @throws WalletWithdrawNotEnoughFundsException if the wallet does not have sufficient balance for the withdrawal.
   */
  @PostMapping("/withdraw")
  public ResponseEntity<Transaction> withdraw(@Valid @RequestBody WalletWithdrawRequest request) {
    return ResponseEntity.ok(walletService.withdraw(request));
  }

  /**
   * Transfers funds from one wallet to another.
   * <p>
   * This method accepts a request containing transfer details, including the sender and receiver wallet IDs,
   * amount, currency, etc. It performs the transfer and returns a transaction object representing the transfer.
   * </p>
   *
   * @param request The request containing transfer details including sender and receiver wallet IDs, amount, currency, etc.
   * @return ResponseEntity containing the created transaction for the transfer.
   * @throws WalletNotFoundException if either of the wallets with the specified IDs do not exist.
   * @throws WalletTransferFailedException if the transfer fails due to any reason.
   * @throws WalletTransferCurrencyMissMatchException if there is a currency mismatch between the wallets and the transfer.
   * @throws WalletTransferNotEnoughFundsException if the sender wallet does not have enough balance to complete the transfer.
   */
  @PostMapping("/transfer")
  public ResponseEntity<Transaction> transfer(@Valid @RequestBody WalletTransferRequest request) {
    return ResponseEntity.ok(walletService.transfer(request));
  }

  /**
   * Retrieves the transaction history of a wallet.
   * <p>
   * This method accepts a wallet ID as a request parameter and returns a list of all transactions associated with that wallet.
   * </p>
   *
   * @param walletId The unique identifier of the wallet whose transaction history is to be fetched.
   * @return ResponseEntity containing a list of transactions associated with the wallet.
   */
  @GetMapping("/getHistory")
  public ResponseEntity<List<Transaction>> getWalletHistory(@ValidWalletId @RequestParam UUID walletId) {
    return ResponseEntity.ok(walletService.getTransactionHistory(walletId));
  }
}
