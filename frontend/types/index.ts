export type Currency = 'EUR' | 'CZK';

export type TransactionType = 'DEPOSIT' | 'WITHDRAWAL' | 'TRANSFER';

export interface Wallet {
    id: string;
    name: string;
    currency: Currency;
    balance: number;
    createdAt: string;
    updatedAt: string;
}

export interface Transaction {
    id: string;
    senderWalletId?: string | null;
    receiverWalletId?: string | null;
    accountNumber?: string | null;
    bankCode?: string | null;
    amount: number;
    currency: Currency;
    transactionType: TransactionType;
}