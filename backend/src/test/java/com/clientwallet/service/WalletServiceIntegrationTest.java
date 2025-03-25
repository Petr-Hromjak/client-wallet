package com.clientwallet.service;

import com.clientwallet.dto.wallet.WalletCreateRequest;
import com.clientwallet.dto.wallet.WalletDepositRequest;
import com.clientwallet.dto.wallet.WalletWithdrawRequest;
import com.clientwallet.dto.wallet.WalletTransferRequest;
import com.clientwallet.model.Currency;
import com.clientwallet.model.Transaction;
import com.clientwallet.model.TransactionType;
import com.clientwallet.model.Wallet;
import com.clientwallet.repository.TransactionRepository;
import com.clientwallet.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class WalletServiceIntegrationTest {

    @Autowired
    private WalletService walletService;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private Wallet wallet1;
    private Wallet wallet2;

    @BeforeEach
    void setUp() {
        // Set up two wallets for testing
        wallet1 = new Wallet();
        wallet1.setName("Wallet1");
        wallet1.setCurrency(Currency.EUR);
        wallet1.setBalance(BigDecimal.ZERO);

        wallet2 = new Wallet();
        wallet2.setName("Wallet2");
        wallet2.setCurrency(Currency.EUR);
        wallet2.setBalance(BigDecimal.ZERO);

        walletRepository.save(wallet1);
        walletRepository.save(wallet2);
    }

    @Test
    void testCreateWallet() {
        // Create a new wallet using WalletService
        WalletCreateRequest request = new WalletCreateRequest();
        request.setName("Wallet3");
        request.setCurrency(Currency.EUR);

        Wallet wallet = walletService.create(request);

        // Assert that the wallet is created correctly
        assertNotNull(wallet);
        assertEquals("Wallet3", wallet.getName());
        assertEquals(Currency.EUR, wallet.getCurrency());
        assertEquals(BigDecimal.ZERO, wallet.getBalance());

        // Check that the wallet is persisted in the database
        Wallet savedWallet = walletRepository.findById(wallet.getId()).orElseThrow();
        assertEquals(wallet, savedWallet);
    }

    @Test
    void testDepositMoney() {
        // Prepare deposit request
        WalletDepositRequest request = new WalletDepositRequest();
        request.setWalletId(wallet1.getId());
        request.setAmount(BigDecimal.valueOf(100));
        request.setCurrency(Currency.EUR);
        request.setBankCode("BANK123");
        request.setAccountNumber("1234567890");

        // Call deposit method
        Transaction transaction = walletService.deposit(request);

        // Assert the transaction is completed and wallet balance is updated
        assertNotNull(transaction);
        assertEquals(TransactionType.DEPOSIT, transaction.getTransactionType());
        assertEquals(wallet1.getId(), transaction.getReceiverWallet().getId());
        assertEquals(BigDecimal.valueOf(100), wallet1.getBalance());

        // Check that the transaction is saved in the repository
        Transaction savedTransaction = transactionRepository.findById(transaction.getId()).orElseThrow();
        assertEquals(transaction, savedTransaction);
    }

    @Test
    void testWithdrawMoney() {
        // Prepare deposit first to ensure wallet has enough balance
        WalletDepositRequest depositRequest = new WalletDepositRequest();
        depositRequest.setWalletId(wallet1.getId());
        depositRequest.setAmount(BigDecimal.valueOf(100));
        depositRequest.setCurrency(Currency.EUR);
        depositRequest.setBankCode("BANK123");
        depositRequest.setAccountNumber("1234567890");

        walletService.deposit(depositRequest);

        // Prepare withdrawal request
        WalletWithdrawRequest withdrawRequest = new WalletWithdrawRequest();
        withdrawRequest.setWalletId(wallet1.getId());
        withdrawRequest.setAmount(BigDecimal.valueOf(50));
        withdrawRequest.setCurrency(Currency.EUR);
        withdrawRequest.setBankCode("BANK123");
        withdrawRequest.setAccountNumber("1234567890");

        // Call withdraw method
        Transaction transaction = walletService.withdraw(withdrawRequest);

        // Assert the transaction is completed and wallet balance is updated
        assertNotNull(transaction);
        assertEquals(TransactionType.WITHDRAWAL, transaction.getTransactionType());
        assertEquals(wallet1.getId(), transaction.getSenderWallet().getId());
        assertEquals(BigDecimal.valueOf(50), wallet1.getBalance());

        // Check that the transaction is saved in the repository
        Transaction savedTransaction = transactionRepository.findById(transaction.getId()).orElseThrow();
        assertEquals(transaction, savedTransaction);
    }

    @Test
    void testTransferMoney() {
        // Prepare deposit requests for both wallets to ensure they have enough balance
        WalletDepositRequest depositRequest1 = new WalletDepositRequest();
        depositRequest1.setWalletId(wallet1.getId());
        depositRequest1.setAmount(BigDecimal.valueOf(100));
        depositRequest1.setCurrency(Currency.EUR);
        depositRequest1.setBankCode("1234");
        depositRequest1.setAccountNumber("1234567890");
        walletService.deposit(depositRequest1);

        WalletDepositRequest depositRequest2 = new WalletDepositRequest();
        depositRequest2.setWalletId(wallet2.getId());
        depositRequest2.setAmount(BigDecimal.valueOf(50));
        depositRequest2.setCurrency(Currency.EUR);
        depositRequest2.setBankCode("1234");
        depositRequest2.setAccountNumber("1234567890");
        walletService.deposit(depositRequest2);

        // Prepare transfer request
        WalletTransferRequest transferRequest = new WalletTransferRequest();
        transferRequest.setSenderWalletId(wallet1.getId());
        transferRequest.setReceiverWalletId(wallet2.getId());
        transferRequest.setAmount(BigDecimal.valueOf(50));
        transferRequest.setCurrency(Currency.EUR);

        // Call transfer method
        Transaction transaction = walletService.transfer(transferRequest);

        // Assert the transaction is completed, and the wallets have updated balances
        assertNotNull(transaction);
        assertEquals(TransactionType.TRANSFER, transaction.getTransactionType());
        assertEquals(wallet1.getId(), transaction.getSenderWallet().getId());
        assertEquals(wallet2.getId(), transaction.getReceiverWallet().getId());
        assertEquals(BigDecimal.valueOf(50), wallet1.getBalance());
        assertEquals(BigDecimal.valueOf(100), wallet2.getBalance());

        // Check that the transaction is saved in the repository
        Transaction savedTransaction = transactionRepository.findById(transaction.getId()).orElseThrow();
        assertEquals(transaction, savedTransaction);
    }

    @Test
    void testGetTransactionHistory() {
        // Create and save some transactions (deposit, withdraw, and transfer)
        WalletDepositRequest depositRequest = new WalletDepositRequest();
        depositRequest.setWalletId(wallet1.getId());
        depositRequest.setAmount(BigDecimal.valueOf(100));
        depositRequest.setCurrency(Currency.EUR);
        depositRequest.setBankCode("1234");
        depositRequest.setAccountNumber("1234567890");
        walletService.deposit(depositRequest);

        WalletWithdrawRequest withdrawRequest = new WalletWithdrawRequest();
        withdrawRequest.setWalletId(wallet1.getId());
        withdrawRequest.setAmount(BigDecimal.valueOf(50));
        withdrawRequest.setCurrency(Currency.EUR);
        withdrawRequest.setBankCode("1234");
        withdrawRequest.setAccountNumber("1234567890");
        walletService.withdraw(withdrawRequest);

        WalletTransferRequest transferRequest = new WalletTransferRequest();
        transferRequest.setSenderWalletId(wallet1.getId());
        transferRequest.setReceiverWalletId(wallet2.getId());
        transferRequest.setAmount(BigDecimal.valueOf(30));
        transferRequest.setCurrency(Currency.EUR);
        walletService.transfer(transferRequest);

        // Fetch transaction history for wallet1
        List<Transaction> transactions = walletService.getTransactionHistory(wallet1.getId());

        // Assert that the transactions were successfully retrieved
        assertNotNull(transactions);
        assertEquals(3, transactions.size()); // deposit, withdrawal, and transfer
    }
}
