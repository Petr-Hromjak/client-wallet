/**
 * Supported currency types.
 */
export type Currency = 'EUR' | 'CZK';

/**
 * Types of transactions that can occur.
 */
export type TransactionType = 'DEPOSIT' | 'WITHDRAWAL' | 'TRANSFER';

/**
 * Represents a wallet that holds a balance in a specific currency.
 */
export interface Wallet {
    /** Unique identifier for the wallet */
    id: string;

    /** Name of the wallet */
    name: string;

    /** Currency type of the wallet */
    currency: Currency;

    /** Current balance of the wallet */
    balance: number;

    /** Timestamp when the wallet was created (ISO string format) */
    createdAt: string;

    /** Timestamp when the wallet was last updated (ISO string format) */
    updatedAt: string;
}

/**
 * Represents a financial transaction between wallets.
 */
export interface Transaction {
    /** Unique identifier for the transaction */
    id: string;

    /** ID of the sender's wallet (if applicable) */
    senderWalletId?: string | null;

    /** ID of the receiver's wallet (if applicable) */
    receiverWalletId?: string | null;

    /** External account number for transfers (if applicable) */
    accountNumber?: string | null;

    /** Bank code associated with the transaction (if applicable) */
    bankCode?: string | null;

    /** Transaction amount */
    amount: number;

    /** Currency of the transaction */
    currency: Currency;

    /** Type of transaction (Deposit, Withdrawal, Transfer) */
    transactionType: TransactionType;
}
