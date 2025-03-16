package com.clientwallet.exception;

import com.clientwallet.exception.wallet.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * GlobalExceptionHandler provides centralized exception handling for the application,
 * capturing specific exceptions and returning structured error responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation exceptions for invalid request parameters or constraints violations.
     *
     * @param ex the MethodArgumentNotValidException thrown when validation fails
     * @return ResponseEntity containing a map of validation errors with field names as keys and error messages as values
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles exceptions when a requested wallet is not found.
     *
     * @param ex the WalletNotFoundException thrown
     * @return ResponseEntity with an error message and HTTP status NOT_FOUND
     */
    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleWalletNotFoundException(WalletNotFoundException ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles exceptions when attempting to create a wallet with a name that already exists.
     *
     * @param ex the WalletCreateWalletWithNameAlreadyExistsException thrown
     * @return ResponseEntity with an error message and HTTP status CONFLICT
     */
    @ExceptionHandler(WalletCreateWalletWithNameAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleWalletCreationException(WalletCreateWalletWithNameAlreadyExistsException ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Handles exceptions when depositing funds with a currency mismatch.
     *
     * @param ex the WalletDepositCurrencyMissMatchException thrown
     * @return ResponseEntity with an error message and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(WalletDepositCurrencyMissMatchException.class)
    public ResponseEntity<Map<String, String>> handleDepositCurrencyMismatch(WalletDepositCurrencyMissMatchException ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles exceptions when a deposit operation fails.
     *
     * @param ex the WalletDepositFailedException thrown
     * @return ResponseEntity with an error message and HTTP status INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(WalletDepositFailedException.class)
    public ResponseEntity<Map<String, String>> handleDepositFailed(WalletDepositFailedException ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles exceptions when transferring funds with a currency mismatch.
     *
     * @param ex the WalletTransferCurrencyMissMatchException thrown
     * @return ResponseEntity with an error message and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(WalletTransferCurrencyMissMatchException.class)
    public ResponseEntity<Map<String, String>> handleTransferCurrencyMismatch(WalletTransferCurrencyMissMatchException ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles exceptions when a transfer operation fails.
     *
     * @param ex the WalletTransferFailedException thrown
     * @return ResponseEntity with an error message and HTTP status INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(WalletTransferFailedException.class)
    public ResponseEntity<Map<String, String>> handleTransferFailed(WalletTransferFailedException ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles exceptions when there are insufficient funds for a transfer.
     *
     * @param ex the WalletTransferNotEnoughFundsException thrown
     * @return ResponseEntity with an error message and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(WalletTransferNotEnoughFundsException.class)
    public ResponseEntity<Map<String, String>> handleTransferNotEnoughFunds(WalletTransferNotEnoughFundsException ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles exceptions when attempting to transfer funds to the same wallet.
     *
     * @param ex the WalletTransferSameWalletException thrown
     * @return ResponseEntity with an error message and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(WalletTransferSameWalletException.class)
    public ResponseEntity<Map<String, String>> handleTransferSameWallet(WalletTransferSameWalletException ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles exceptions when withdrawing funds with a currency mismatch.
     *
     * @param ex the WalletWithdrawCurrencyMissMatchException thrown
     * @return ResponseEntity with an error message and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(WalletWithdrawCurrencyMissMatchException.class)
    public ResponseEntity<Map<String, String>> handleWithdrawCurrencyMismatch(WalletWithdrawCurrencyMissMatchException ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles exceptions when a withdrawal operation fails.
     *
     * @param ex the WalletWithdrawFailedException thrown
     * @return ResponseEntity with an error message and HTTP status INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(WalletWithdrawFailedException.class)
    public ResponseEntity<Map<String, String>> handleWithdrawFailed(WalletWithdrawFailedException ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles exceptions when there are insufficient funds for a withdrawal.
     *
     * @param ex the WalletWithdrawNotEnoughFundsException thrown
     * @return ResponseEntity with an error message and HTTP status BAD_REQUEST
     */
    @ExceptionHandler(WalletWithdrawNotEnoughFundsException.class)
    public ResponseEntity<Map<String, String>> handleWithdrawNotEnoughFunds(WalletWithdrawNotEnoughFundsException ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Creates a structured error response with a given message and HTTP status.
     *
     * @param message the error message to be included in the response
     * @param status the HTTP status associated with the error
     * @return ResponseEntity containing the error message and HTTP status
     */
    private ResponseEntity<Map<String, String>> createErrorResponse(String message, HttpStatus status) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", message);
        return new ResponseEntity<>(errorResponse, status);
    }
}
