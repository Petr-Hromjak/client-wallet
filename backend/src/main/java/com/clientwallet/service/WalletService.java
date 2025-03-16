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

@Service
public class WalletService {

  @Autowired
  private WalletRepository walletRepository;
  @Autowired
  private TransactionRepository transactionRepository;

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

  public Wallet get(UUID walletId) {
    return walletRepository.findById(walletId).orElseThrow(() -> new WalletNotFoundException(walletId));
  }

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

  public List<Transaction> getTransactionHistory(UUID walletId) {
    return transactionRepository.findBySenderWalletIdOrReceiverWalletId(walletId, walletId);
  }
}
