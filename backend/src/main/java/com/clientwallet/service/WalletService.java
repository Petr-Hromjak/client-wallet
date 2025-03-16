package com.clientwallet.service;

import com.clientwallet.dto.wallet.WalletCreateRequest;
import com.clientwallet.dto.wallet.WalletDepositRequest;
import com.clientwallet.dto.wallet.WalletTransferRequest;
import com.clientwallet.dto.wallet.WalletWithdrawRequest;
import com.clientwallet.exception.wallet.*;
import com.clientwallet.model.*;
import com.clientwallet.repository.TransactionRepository;
import com.clientwallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * WalletService is a service class that provides various wallet-related operations.
 * It interacts with the {@link WalletRepository} and {@link TransactionRepository} to perform operations
 * such as creating a wallet, depositing, withdrawing, transferring funds, and fetching transaction history.
 * <p>
 * This service ensures that the operations are executed with the appropriate business logic and validations.
 * All state-changing operations (deposit, withdraw, transfer) are handled within transactions to ensure consistency.
 * </p>
 */
@Service
public class WalletService {

  @Autowired
  private WalletRepository walletRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  /**
   * Creates a new wallet.
   * <p>
   * This method checks if a wallet with the same name already exists. If not, it creates and saves a new wallet.
   * </p>
   *
   * @param request The request object containing the wallet creation data.
   * @return The created {@link Wallet} object.
   * @throws WalletCreateWalletWithNameAlreadyExistsException If a wallet with the same name already exists.
   */
  public Wallet create(WalletCreateRequest request) {
    if (walletRepository.existsByName(request.getName())) {
      throw new WalletCreateWalletWithNameAlreadyExistsException(request.getName());
    }

    Wallet wallet = new Wallet();
    wallet.setName(request.getName());
    wallet.setCurrency(request.getCurrency());
    wallet.setBalance(BigDecimal.ZERO);

    return walletRepository.save(wallet);
  }

  /**
   * Retrieves a wallet by its unique ID.
   * <p>
   * If the wallet with the given ID does not exist, a {@link WalletNotFoundException} is thrown.
   * </p>
   *
   * @param walletId The ID of the wallet to be fetched.
   * @return The {@link Wallet} object if found.
   * @throws WalletNotFoundException If no wallet is found with the provided ID.
   */
  public Wallet get(UUID walletId) {
    return walletRepository.findById(walletId).orElseThrow(() -> new WalletNotFoundException(walletId));
  }

  /**
   * Deposits money into the specified wallet.
   * <p>
   * The deposit operation checks that the currency of the request matches the wallet's currency.
   * A {@link WalletDepositCurrencyMissMatchException} is thrown if the currencies do not match.
   * If the deposit is successful, the transaction is saved with the status {@link TransactionStatus#COMPLETED}.
   * In case of failure, the transaction status is set to {@link TransactionStatus#FAILED}.
   * </p>
   *
   * @param request The request object containing the deposit data.
   * @return The {@link Transaction} object representing the deposit.
   * @throws WalletNotFoundException If the wallet does not exist.
   * @throws WalletDepositCurrencyMissMatchException If the currencies do not match.
   * @throws WalletDepositFailedException If the deposit fails.
   */
  @Transactional
  public Transaction deposit(WalletDepositRequest request) {
    Wallet wallet = walletRepository.findById(request.getWalletId()).orElseThrow(() -> new WalletNotFoundException(request.getWalletId()));

    if(wallet.getCurrency() != request.getCurrency()){
      throw new WalletDepositCurrencyMissMatchException(request.getCurrency(), wallet.getCurrency());
    }

    Transaction transaction = new Transaction();
    transaction.setReceiverWallet(wallet);
    transaction.setAmount(request.getAmount());
    transaction.setCurrency(request.getCurrency());
    transaction.setBankCode(request.getBankCode());
    transaction.setAccountNumber(request.getAccountNumber());
    transaction.setTransactionType(TransactionType.DEPOSIT);
    transaction.setTransactionStatus(TransactionStatus.PENDING);
    transactionRepository.save(transaction);

    try {
      wallet.setBalance(wallet.getBalance().add(request.getAmount()));
      walletRepository.save(wallet);

      transaction.setTransactionStatus(TransactionStatus.COMPLETED);
      transactionRepository.save(transaction);
    } catch (Exception e) {
      transaction.setTransactionStatus(TransactionStatus.FAILED);
      transactionRepository.save(transaction);
      throw new WalletDepositFailedException(request.getWalletId(), e);
    }
    return transaction;
  }

  /**
   * Withdraws money from the specified wallet.
   * <p>
   * The withdrawal operation checks that the wallet's currency matches the requested currency.
   * It also checks that the wallet has enough balance to complete the withdrawal.
   * If the withdrawal fails, the transaction status is set to {@link TransactionStatus#FAILED}.
   * </p>
   *
   * @param request The request object containing the withdrawal data.
   * @return The {@link Transaction} object representing the withdrawal.
   * @throws WalletNotFoundException If the wallet does not exist.
   * @throws WalletWithdrawCurrencyMissMatchException If the currencies do not match.
   * @throws WalletWithdrawNotEnoughFundsException If the wallet has insufficient funds.
   * @throws WalletWithdrawFailedException If the withdrawal fails.
   */
  @Transactional
  public Transaction withdraw(WalletWithdrawRequest request) {
    Wallet wallet = walletRepository.findById(request.getWalletId()).orElseThrow(() -> new WalletNotFoundException(request.getWalletId()));

    if(wallet.getCurrency() != request.getCurrency()){
      throw new WalletWithdrawCurrencyMissMatchException(request.getCurrency(), wallet.getCurrency());
    }

    Transaction transaction = new Transaction();
    transaction.setSenderWallet(wallet);
    transaction.setAmount(request.getAmount());
    transaction.setCurrency(request.getCurrency());
    transaction.setBankCode(request.getBankCode());
    transaction.setAccountNumber(request.getAccountNumber());
    transaction.setTransactionType(TransactionType.WITHDRAWAL);
    transaction.setTransactionStatus(TransactionStatus.PENDING);
    transactionRepository.save(transaction);

    if(request.getAmount().compareTo(wallet.getBalance()) > 0){
      throw new WalletWithdrawNotEnoughFundsException(request.getAmount(), wallet.getBalance());
    }

    try {
      wallet.setBalance(wallet.getBalance().subtract(request.getAmount()));
      walletRepository.save(wallet);

      transaction.setTransactionStatus(TransactionStatus.COMPLETED);
      transactionRepository.save(transaction);
    } catch (Exception e) {
      transaction.setTransactionStatus(TransactionStatus.FAILED);
      transactionRepository.save(transaction);
      throw new WalletWithdrawFailedException(request.getWalletId(), e);
    }
    return transaction;
  }

  /**
   * Transfers money between two wallets.
   * <p>
   * This method checks that the sender and receiver wallets are different, and that both wallets have the same currency as the request.
   * The transfer operation also ensures that the sender wallet has sufficient funds for the transfer.
   * If the transfer fails, the transaction status is set to {@link TransactionStatus#FAILED}.
   * </p>
   *
   * @param request The request object containing the transfer data.
   * @return The {@link Transaction} object representing the transfer.
   * @throws WalletTransferSameWalletException If the sender and receiver wallets are the same.
   * @throws WalletNotFoundException If either the sender or receiver wallet does not exist.
   * @throws WalletTransferCurrencyMissMatchException If the currencies do not match.
   * @throws WalletTransferNotEnoughFundsException If the sender wallet has insufficient funds.
   * @throws WalletTransferFailedException If the transfer fails.
   */
  @Transactional
  public Transaction transfer(WalletTransferRequest request) {
    if(request.getReceiverWalletId().equals(request.getSenderWalletId())){
      throw new WalletTransferSameWalletException(request.getReceiverWalletId());
    }

    Wallet receiverWallet = walletRepository.findById(request.getReceiverWalletId()).orElseThrow(() -> new WalletNotFoundException(request.getReceiverWalletId()));
    Wallet senderWallet = walletRepository.findById(request.getSenderWalletId()).orElseThrow(() -> new WalletNotFoundException(request.getSenderWalletId()));

    if(receiverWallet.getCurrency() != request.getCurrency()){
      throw new WalletTransferCurrencyMissMatchException(request.getCurrency(), receiverWallet.getCurrency());
    }

    if(senderWallet.getCurrency() != request.getCurrency()){
      throw new WalletTransferCurrencyMissMatchException(request.getCurrency(), senderWallet.getCurrency());
    }

    Transaction transaction = new Transaction();
    transaction.setReceiverWallet(receiverWallet);
    transaction.setSenderWallet(senderWallet);
    transaction.setAmount(request.getAmount());
    transaction.setCurrency(request.getCurrency());
    transaction.setTransactionType(TransactionType.TRANSFER);
    transaction.setTransactionStatus(TransactionStatus.PENDING);
    transactionRepository.save(transaction);

    if(request.getAmount().compareTo(senderWallet.getBalance()) > 0){
      throw new WalletTransferNotEnoughFundsException(request.getAmount(), senderWallet.getBalance());
    }

    try {
      receiverWallet.setBalance(receiverWallet.getBalance().add(request.getAmount()));
      senderWallet.setBalance(senderWallet.getBalance().subtract(request.getAmount()));

      walletRepository.save(receiverWallet);
      walletRepository.save(senderWallet);

      transaction.setTransactionStatus(TransactionStatus.COMPLETED);
      transactionRepository.save(transaction);
    } catch (Exception e) {
      transaction.setTransactionStatus(TransactionStatus.FAILED);
      transactionRepository.save(transaction);
      throw new WalletTransferFailedException(request.getSenderWalletId(), request.getReceiverWalletId(), e);
    }
    return transaction;
  }

  /**
   * Retrieves the transaction history for a given wallet.
   *
   * @param walletId The ID of the wallet for which transaction history is being requested.
   * @return A list of {@link Transaction} objects associated with the specified wallet.
   */
  public List<Transaction> getTransactionHistory(UUID walletId) {
    return transactionRepository.findBySenderWalletIdOrReceiverWalletId(walletId, walletId);
  }
}
