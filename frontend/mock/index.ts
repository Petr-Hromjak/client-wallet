import { Wallet, Transaction } from '@/types';

// Mock Wallets
export const wallets: Wallet[] = [
    {
        id: '1',
        name: 'Main Wallet',
        currency: 'EUR',
        balance: 1000,
        createdAt: '2025-03-01T12:00:00Z',
        updatedAt: '2025-03-10T12:00:00Z',
    },
    {
        id: '2',
        name: 'Savings Wallet',
        currency: 'CZK',
        balance: 5000,
        createdAt: '2025-02-15T10:30:00Z',
        updatedAt: '2025-03-08T14:45:00Z',
    },
];

// Mock Transactions
export const transactions: Transaction[] = [
    {
        id: 't1',
        senderWalletId: '1',
        receiverWalletId: null,
        accountNumber: '123456789012345678',
        bankCode: '0100',
        amount: 50,
        currency: 'EUR',
        transactionType: 'WITHDRAWAL',
    },
    {
        id: 't2',
        senderWalletId: null,
        receiverWalletId: '1',
        accountNumber: null,
        bankCode: null,
        amount: 200,
        currency: 'EUR',
        transactionType: 'DEPOSIT',
    },
    {
        id: 't3',
        senderWalletId: '2',
        receiverWalletId: null,
        accountNumber: '987654321098765432',
        bankCode: '0300',
        amount: 100,
        currency: 'CZK',
        transactionType: 'WITHDRAWAL',
    },
];