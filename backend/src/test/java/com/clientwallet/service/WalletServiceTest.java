package com.clientwallet.service;

import com.clientwallet.dto.wallet.WalletCreateRequest;
import com.clientwallet.dto.wallet.WalletDepositRequest;
import com.clientwallet.dto.wallet.WalletTransferRequest;
import com.clientwallet.dto.wallet.WalletWithdrawRequest;
import com.clientwallet.exception.wallet.*;
import com.clientwallet.model.*;
import com.clientwallet.repository.TransactionRepository;
import com.clientwallet.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private WalletService walletService;

    // Test for create method
    @Test
    void testCreateWalletSuccessfully() {
        WalletCreateRequest request = new WalletCreateRequest();
        request.setName("MyWallet");
        request.setCurrency(Currency.EUR);

        when(walletRepository.existsByName("MyWallet")).thenReturn(false);
        ArgumentCaptor<Wallet> walletCaptor = ArgumentCaptor.forClass(Wallet.class);
        when(walletRepository.save(walletCaptor.capture())).thenAnswer(invocation -> walletCaptor.getValue());

        Wallet wallet = walletService.create(request);

        assertNotNull(wallet);
        assertEquals("MyWallet", wallet.getName());
        assertEquals(Currency.EUR, wallet.getCurrency());
        verify(walletRepository, times(1)).save(any(Wallet.class));
    }

    @Test
    void testCreateWalletWithNameAlreadyExists() {
        WalletCreateRequest request = new WalletCreateRequest();
        request.setName("MyWallet");
        request.setCurrency(Currency.EUR);

        when(walletRepository.existsByName("MyWallet")).thenReturn(true);

        assertThrows(WalletCreateWalletWithNameAlreadyExistsException.class, () -> walletService.create(request));
    }

    // Test for get method
    @Test
    void testGetWalletSuccessfully() {
        UUID walletId = UUID.randomUUID();
        Wallet wallet = new Wallet();
        wallet.setId(walletId);
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setCurrency(Currency.EUR);

        when(walletRepository.findById(walletId)).thenReturn(java.util.Optional.of(wallet));

        Wallet fetchedWallet = walletService.get(walletId);

        assertNotNull(fetchedWallet);
        assertEquals(walletId, fetchedWallet.getId());
        verify(walletRepository, times(1)).findById(walletId);
    }

    @Test
    void testGetWalletNotFound() {
        UUID walletId = UUID.randomUUID();

        when(walletRepository.findById(walletId)).thenReturn(java.util.Optional.empty());

        assertThrows(WalletNotFoundException.class, () -> walletService.get(walletId));
    }

    // Test for deposit method
    @Test
    void testDepositSuccessfully() {
        WalletDepositRequest request = new WalletDepositRequest();
        request.setWalletId(UUID.randomUUID());
        request.setCurrency(Currency.EUR);
        request.setAmount(BigDecimal.TEN);
        request.setBankCode("123");
        request.setAccountNumber("456");

        Wallet wallet = new Wallet();
        wallet.setId(request.getWalletId());
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setCurrency(Currency.EUR);

        when(walletRepository.findById(request.getWalletId())).thenReturn(java.util.Optional.of(wallet));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        Transaction transaction = walletService.deposit(request);

        assertNotNull(transaction);
        assertEquals(TransactionStatus.COMPLETED, transaction.getTransactionStatus());
        verify(walletRepository, times(1)).save(any(Wallet.class));
        verify(transactionRepository, times(2)).save(any(Transaction.class));
    }

    @Test
    void testDepositCurrencyMismatch() {
        WalletDepositRequest request = new WalletDepositRequest();
        request.setWalletId(UUID.randomUUID());
        request.setCurrency(Currency.CZK);
        request.setAmount(BigDecimal.TEN);
        request.setBankCode("123");
        request.setAccountNumber("456");

        Wallet wallet = new Wallet();
        wallet.setId(request.getWalletId());
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setCurrency(Currency.EUR);

        when(walletRepository.findById(request.getWalletId())).thenReturn(java.util.Optional.of(wallet));

        assertThrows(WalletDepositCurrencyMissMatchException.class, () -> walletService.deposit(request));
    }

    // Test for withdraw method
    @Test
    void testWithdrawSuccessfully() {
        WalletWithdrawRequest request = new WalletWithdrawRequest();
        request.setWalletId(UUID.randomUUID());
        request.setCurrency(Currency.EUR);
        request.setAmount(BigDecimal.TEN);
        request.setBankCode("123");
        request.setAccountNumber("456");

        Wallet wallet = new Wallet();
        wallet.setId(request.getWalletId());
        wallet.setBalance(BigDecimal.valueOf(100));
        wallet.setCurrency(Currency.EUR);

        when(walletRepository.findById(request.getWalletId())).thenReturn(java.util.Optional.of(wallet));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        Transaction transaction = walletService.withdraw(request);

        assertNotNull(transaction);
        assertEquals(TransactionStatus.COMPLETED, transaction.getTransactionStatus());
        verify(walletRepository, times(1)).save(any(Wallet.class));
        verify(transactionRepository, times(2)).save(any(Transaction.class));
    }

    @Test
    void testWithdrawNotEnoughFunds() {
        WalletWithdrawRequest request = new WalletWithdrawRequest();
        request.setWalletId(UUID.randomUUID());
        request.setCurrency(Currency.EUR);
        request.setAmount(BigDecimal.valueOf(200));
        request.setBankCode("123");
        request.setAccountNumber("456");

        Wallet wallet = new Wallet();
        wallet.setId(request.getWalletId());
        wallet.setBalance(BigDecimal.valueOf(100));
        wallet.setCurrency(Currency.EUR);

        when(walletRepository.findById(request.getWalletId())).thenReturn(java.util.Optional.of(wallet));

        assertThrows(WalletWithdrawNotEnoughFundsException.class, () -> walletService.withdraw(request));
    }

    // Test for transfer method
    @Test
    void testTransferSuccessfully() {
        WalletTransferRequest request = new WalletTransferRequest();
        request.setSenderWalletId(UUID.randomUUID());
        request.setReceiverWalletId(UUID.randomUUID());
        request.setCurrency(Currency.EUR);
        request.setAmount(BigDecimal.TEN);

        Wallet senderWallet = new Wallet();
        senderWallet.setId(request.getSenderWalletId());
        senderWallet.setBalance(BigDecimal.valueOf(100));
        senderWallet.setCurrency(Currency.EUR);

        Wallet receiverWallet = new Wallet();
        receiverWallet.setId(request.getReceiverWalletId());
        receiverWallet.setBalance(BigDecimal.valueOf(50));
        receiverWallet.setCurrency(Currency.EUR);

        when(walletRepository.findById(request.getSenderWalletId())).thenReturn(java.util.Optional.of(senderWallet));
        when(walletRepository.findById(request.getReceiverWalletId())).thenReturn(java.util.Optional.of(receiverWallet));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        Transaction transaction = walletService.transfer(request);

        assertNotNull(transaction);
        assertEquals(TransactionStatus.COMPLETED, transaction.getTransactionStatus());
        verify(walletRepository, times(2)).save(any(Wallet.class));
        verify(transactionRepository, times(2)).save(any(Transaction.class));
    }

    @Test
    void testTransferNotEnoughFunds() {
        WalletTransferRequest request = new WalletTransferRequest();
        request.setSenderWalletId(UUID.randomUUID());
        request.setReceiverWalletId(UUID.randomUUID());
        request.setCurrency(Currency.EUR);
        request.setAmount(BigDecimal.valueOf(200));

        Wallet senderWallet = new Wallet();
        senderWallet.setId(request.getSenderWalletId());
        senderWallet.setBalance(BigDecimal.valueOf(100));
        senderWallet.setCurrency(Currency.EUR);

        Wallet receiverWallet = new Wallet();
        receiverWallet.setId(request.getReceiverWalletId());
        receiverWallet.setBalance(BigDecimal.valueOf(50));
        receiverWallet.setCurrency(Currency.EUR);

        when(walletRepository.findById(request.getSenderWalletId())).thenReturn(java.util.Optional.of(senderWallet));
        when(walletRepository.findById(request.getReceiverWalletId())).thenReturn(java.util.Optional.of(receiverWallet));

        assertThrows(WalletTransferNotEnoughFundsException.class, () -> walletService.transfer(request));
    }

    @Test
    void testTransferCurrencyMismatch() {
        WalletTransferRequest request = new WalletTransferRequest();
        request.setSenderWalletId(UUID.randomUUID());
        request.setReceiverWalletId(UUID.randomUUID());
        request.setCurrency(Currency.CZK);
        request.setAmount(BigDecimal.TEN);

        Wallet senderWallet = new Wallet();
        senderWallet.setId(request.getSenderWalletId());
        senderWallet.setBalance(BigDecimal.valueOf(100));
        senderWallet.setCurrency(Currency.CZK);

        Wallet receiverWallet = new Wallet();
        receiverWallet.setId(request.getReceiverWalletId());
        receiverWallet.setBalance(BigDecimal.valueOf(50));
        receiverWallet.setCurrency(Currency.EUR);  // Currency mismatch

        when(walletRepository.findById(request.getSenderWalletId())).thenReturn(java.util.Optional.of(senderWallet));
        when(walletRepository.findById(request.getReceiverWalletId())).thenReturn(java.util.Optional.of(receiverWallet));

        assertThrows(WalletTransferCurrencyMissMatchException.class, () -> walletService.transfer(request));
    }

    // Test for getTransactionHistory method
    @Test
    void testGetTransactionHistory() {
        UUID walletId = UUID.randomUUID();
        List<Transaction> transactionList = List.of(new Transaction(), new Transaction());

        when(transactionRepository.findBySenderWalletIdOrReceiverWalletId(walletId, walletId)).thenReturn(transactionList);

        List<Transaction> transactions = walletService.getTransactionHistory(walletId);

        assertNotNull(transactions);
        assertEquals(2, transactions.size());
        verify(transactionRepository, times(1)).findBySenderWalletIdOrReceiverWalletId(walletId, walletId);
    }
}
