package com.clientwallet.controller;

import com.clientwallet.dto.wallet.WalletCreateRequest;
import com.clientwallet.dto.wallet.WalletDepositRequest;
import com.clientwallet.dto.wallet.WalletWithdrawRequest;
import com.clientwallet.dto.wallet.WalletTransferRequest;
import com.clientwallet.model.Currency;
import com.clientwallet.model.Wallet;
import com.clientwallet.repository.WalletRepository;
import com.clientwallet.service.WalletService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WalletService walletService;

    @Autowired
    private WalletRepository walletRepository;

    private UUID walletId;

    @BeforeAll
    void setUp() {
        WalletCreateRequest walletCreateRequest = new WalletCreateRequest();
        walletCreateRequest.setName("Test Wallet");
        walletCreateRequest.setCurrency(Currency.EUR);
        Wallet wallet = walletService.create(walletCreateRequest);

        wallet.setBalance(BigDecimal.valueOf(100));
        walletRepository.save(wallet);
        walletId = wallet.getId();
    }
    @AfterAll
    void clear() {
        walletRepository.deleteById(walletId);
    }

    @Test
    void testCreateWallet() throws Exception {
        WalletCreateRequest request = new WalletCreateRequest();
        request.setName("New Wallet");
        request.setCurrency(Currency.EUR);

        mockMvc.perform(post("/wallet/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Wallet\", \"currency\":\"EUR\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Wallet"))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    void testDeposit() throws Exception {
        WalletDepositRequest depositRequest = new WalletDepositRequest();
        depositRequest.setWalletId(walletId);
        depositRequest.setAmount(BigDecimal.valueOf(100));
        depositRequest.setCurrency(Currency.EUR);
        depositRequest.setBankCode("1234");
        depositRequest.setAccountNumber("1234567890");

        mockMvc.perform(post("/wallet/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"walletId\":\"" + walletId + "\", \"amount\":100, \"currency\":\"EUR\", \"bankCode\":\"1234\", \"accountNumber\":\"1234567890\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionType").value("DEPOSIT"))
                .andExpect(jsonPath("$.transactionStatus").value("COMPLETED"));
    }

    @Test
    void testWithdraw() throws Exception {
        WalletWithdrawRequest withdrawRequest = new WalletWithdrawRequest();
        withdrawRequest.setWalletId(walletId);
        withdrawRequest.setAmount(BigDecimal.valueOf(50));
        withdrawRequest.setCurrency(Currency.EUR);
        withdrawRequest.setBankCode("1234");
        withdrawRequest.setAccountNumber("1234567890");

        mockMvc.perform(post("/wallet/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"walletId\":\"" + walletId + "\", \"amount\":50, \"currency\":\"EUR\", \"bankCode\":\"1234\", \"accountNumber\":\"1234567890\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionType").value("WITHDRAWAL"))
                .andExpect(jsonPath("$.transactionStatus").value("COMPLETED"));
    }

    @Test
    void testTransfer() throws Exception {
        WalletCreateRequest walletCreateRequest = new WalletCreateRequest();
        walletCreateRequest.setName("Test Wallet 2");
        walletCreateRequest.setCurrency(Currency.EUR);
        UUID walletId2 = walletService.create(walletCreateRequest).getId();

        WalletTransferRequest transferRequest = new WalletTransferRequest();
        transferRequest.setSenderWalletId(walletId);
        transferRequest.setReceiverWalletId(walletId2);
        transferRequest.setAmount(BigDecimal.valueOf(50));
        transferRequest.setCurrency(Currency.EUR);

        mockMvc.perform(post("/wallet/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"senderWalletId\":\"" + walletId + "\", \"receiverWalletId\":\"" + walletId2 + "\", \"amount\":50, \"currency\":\"EUR\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionType").value("TRANSFER"))
                .andExpect(jsonPath("$.transactionStatus").value("COMPLETED"));
    }

    @Test
    void testGetTransactionHistory() throws Exception {

        mockMvc.perform(get("/wallet/getHistory")
                        .param("walletId", walletId.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
