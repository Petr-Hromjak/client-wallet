import { useEffect, useState } from 'react';
import { useRouter } from 'next/router';
import { Container, Title, Text, Card, Loader } from '@mantine/core';
import TransactionHistory from '@/components/TransactionHistory';
import { API_HOST } from '@/config/config';

interface Wallet {
    id: string;
    name: string;
    currency: string;
    balance: number;
}

export default function WalletDetailPage() {
    const router = useRouter();
    const { id } = router.query; // Get wallet ID from URL

    const [wallet, setWallet] = useState<Wallet | null>(null);
    const [loading, setLoading] = useState(true);

    // Fetch wallet details from the backend
    useEffect(() => {
        if (!id || typeof id !== 'string') return;

        const fetchWalletDetails = async () => {
            setLoading(true);

            try {
                const response = await fetch(`${API_HOST}/wallet/get?walletId=${id}`);

                if (!response.ok) {
                    throw new Error('Failed to fetch wallet details');
                }

                const data = await response.json();
                setWallet(data);
            } catch {
                setWallet(null); // Ensure error leads to "Wallet Not Found"
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
            <TransactionHistory transactions={[]} currentWalletId={wallet.id} />
        </>
    );
}
