import { useEffect, useState } from 'react';
import { Card, Stack, Text, Title, Loader, useMantineTheme, Alert } from '@mantine/core';
import { API_HOST } from '@/config/config';

/** Supported currency types */
type Currency = 'EUR' | 'CZK';

/** Supported transaction types */
type TransactionType = 'DEPOSIT' | 'WITHDRAWAL' | 'TRANSFER';

/** Wallet interface */
interface Wallet {
    id: string;
    name: string;
}

/** Transaction interface */
interface Transaction {
    id: string;
    senderWallet?: Partial<Wallet> | null;
    receiverWallet?: Partial<Wallet> | null;
    accountNumber?: string | null;
    bankCode?: string | null;
    amount: number;
    currency: Currency;
    transactionType: TransactionType;
}

/** Props for TransactionHistory component */
interface TransactionHistoryProps {
    /** ID of the currently selected wallet */
    currentWalletId: string;
}

/**
 * TransactionHistory Component
 *
 * Displays a history of transactions for a given wallet.
 *
 * @param {TransactionHistoryProps} props - The component props
 * @returns {JSX.Element} Transaction history list
 */
export default function TransactionHistory({ currentWalletId }: TransactionHistoryProps) {
    const theme = useMantineTheme();
    const [transactions, setTransactions] = useState<Transaction[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    /**
     * Fetches transaction history for the given wallet ID.
     */
    useEffect(() => {
        if (!currentWalletId) return;

        const fetchTransactions = async () => {
            setLoading(true);
            setError(null);

            try {
                const response = await fetch(`${API_HOST}/wallet/getHistory?walletId=${currentWalletId}`);

                if (!response.ok) {
                    throw new Error('Failed to fetch transactions');
                }

                const data: Transaction[] = await response.json();
                setTransactions(data);
            } catch (err: unknown) {
                setError(err instanceof Error ? err.message : 'An unexpected error occurred.');
                setTransactions([]);
            } finally {
                setLoading(false);
            }
        };

        fetchTransactions();
    }, [currentWalletId]);

    if (loading) {
        return <Loader />;
    }

    if (error) {
        return <Alert color="red">{error}</Alert>;
    }

    if (!transactions.length) {
        return <Text>No transactions yet.</Text>;
    }

    return (
        <Stack mt="xl" gap="md">
            <Title order={3}>Transaction History</Title>

            {transactions.map((transaction) => {
                const isSender = transaction.senderWallet?.id === currentWalletId;
                const isReceiver = transaction.receiverWallet?.id === currentWalletId;

                let amountColor = theme.colors.gray[9];
                let formattedAmount = `${transaction.amount.toFixed(2)} ${transaction.currency}`;

                if (isSender) {
                    amountColor = theme.colors.red[6];
                    formattedAmount = `- ${formattedAmount}`;
                } else if (isReceiver) {
                    amountColor = theme.colors.green[6];
                }

                return (
                    <Card key={transaction.id} shadow="sm" padding="lg">
                        <Text>
                            <strong>Type:</strong> {transaction.transactionType}
                        </Text>
                        <Text color={amountColor}>
                            <strong>Amount:</strong> {formattedAmount}
                        </Text>

                        {transaction.transactionType === 'TRANSFER' && (
                            <>
                                <Text>
                                    <strong>Sender Wallet:</strong> {transaction.senderWallet?.name || 'Unknown'}
                                </Text>
                                <Text>
                                    <strong>Receiver Wallet:</strong> {transaction.receiverWallet?.name || 'Unknown'}
                                </Text>
                            </>
                        )}

                        {transaction.transactionType === 'WITHDRAWAL' && (
                            <>
                                <Text>
                                    <strong>Sender Wallet:</strong> {transaction.senderWallet?.name || 'Unknown'}
                                </Text>
                                <Text>
                                    <strong>Bank Code:</strong> {transaction.bankCode || 'N/A'}
                                </Text>
                                <Text>
                                    <strong>Account Number:</strong> {transaction.accountNumber || 'N/A'}
                                </Text>
                            </>
                        )}

                        {transaction.transactionType === 'DEPOSIT' && (
                            <>
                                <Text>
                                    <strong>Receiver Wallet:</strong> {transaction.receiverWallet?.name || 'Unknown'}
                                </Text>
                                <Text>
                                    <strong>Bank Code:</strong> {transaction.bankCode || 'N/A'}
                                </Text>
                                <Text>
                                    <strong>Account Number:</strong> {transaction.accountNumber || 'N/A'}
                                </Text>
                            </>
                        )}
                    </Card>
                );
            })}
        </Stack>
    );
}
