import { useEffect, useState } from 'react';
import { Card, Stack, Text, Title, Loader, useMantineTheme } from '@mantine/core';
import { API_HOST } from '@/config/config';

interface Wallet {
    id: string;
    name: string;
}

interface Transaction {
    id: string;
    senderWallet?: Wallet | null;
    receiverWallet?: Wallet | null;
    accountNumber?: string | null;
    bankCode?: string | null;
    amount: number;
    currency: string;
    transactionType: 'DEPOSIT' | 'WITHDRAWAL' | 'TRANSFER';
}

interface TransactionHistoryProps {
    currentWalletId: string;
}

export default function TransactionHistory({ currentWalletId }: TransactionHistoryProps) {
    const theme = useMantineTheme();
    const [transactions, setTransactions] = useState<Transaction[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        if (!currentWalletId) return;

        const fetchTransactions = async () => {
            setLoading(true);

            try {
                const response = await fetch(`${API_HOST}/wallet/getHistory?walletId=${currentWalletId}`);

                if (!response.ok) {
                    throw new Error('Failed to fetch transactions');
                }

                const data = await response.json();
                setTransactions(data);
            } catch {
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

    if (!transactions.length) {
        return <Text>No transactions yet.</Text>;
    }

    return (
        <Stack mt="xl" spacing="md">
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
                                    <strong>Sender Wallet:</strong> {transaction.senderWallet?.name}
                                </Text>
                                <Text>
                                    <strong>Bank Code:</strong> {transaction.bankCode}
                                </Text>
                                <Text>
                                    <strong>Account Number:</strong> {transaction.accountNumber}
                                </Text>
                            </>
                        )}

                        {transaction.transactionType === 'DEPOSIT' && (
                            <>
                                <Text>
                                    <strong>Receiver Wallet:</strong> {transaction.receiverWallet?.name}
                                </Text>
                                <Text>
                                    <strong>Bank Code:</strong> {transaction.bankCode}
                                </Text>
                                <Text>
                                    <strong>Account Number:</strong> {transaction.accountNumber}
                                </Text>
                            </>
                        )}
                    </Card>
                );
            })}
        </Stack>
    );
}
