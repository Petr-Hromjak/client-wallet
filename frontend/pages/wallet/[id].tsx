import { useEffect, useState } from 'react';
import { useRouter } from 'next/router';
import { Container, Title, Text, Card, Loader, Alert } from '@mantine/core';
import TransactionHistory from '@/components/TransactionHistory';
import { API_HOST } from '@/config/config';

/** Supported currency types */
type Currency = 'EUR' | 'CZK';

/** Wallet interface */
interface Wallet {
    id: string;
    name: string;
    currency: Currency;
    balance: number;
}

/**
 * WalletDetailPage Component
 *
 * Displays details of a selected wallet and its transaction history.
 *
 * @returns {JSX.Element} The wallet details page
 */
export default function WalletDetailPage(){
    const router = useRouter();
    const { id } = router.query; // Get wallet ID from URL

    const [wallet, setWallet] = useState<Wallet | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    /**
     * Fetches wallet details from the backend.
     */
    useEffect(() => {
        if (!id || typeof id !== 'string') return;

        const fetchWalletDetails = async () => {
            setLoading(true);
            setError(null);

            try {
                const response = await fetch(`${API_HOST}/wallet/get?walletId=${id}`);

                if (!response.ok) {
                    throw new Error('Failed to fetch wallet details');
                }

                const data: Wallet = await response.json();
                setWallet(data);
            } catch (err: unknown) {
                setError(err instanceof Error ? err.message : 'An unexpected error occurred.');
                setWallet(null);
            } finally {
                setLoading(false);
            }
        };

        fetchWalletDetails();
    }, [id]);

    if (loading) {
        return (
            <Container>
                <Loader />
            </Container>
        );
    }

    if (error) {
        return (
            <Container>
                <Alert color="red">{error}</Alert>
            </Container>
        );
    }

    if (!wallet) {
        return (
            <Container>
                <Text>Wallet not found.</Text>
            </Container>
        );
    }

    return (
        <>
            <Container size="sm" py="xl">
                <Card shadow="sm" padding="lg" mb="lg">
                    <Title order={2}>{wallet.name}</Title>
                    <Text>
                        <strong>Balance:</strong> {wallet.balance.toFixed(2)} {wallet.currency}
                    </Text>
                    <Text>
                        <strong>Currency:</strong> {wallet.currency}
                    </Text>
                </Card>
            </Container>
            <TransactionHistory currentWalletId={wallet.id} />
        </>
    );
}
