import { Card, Stack, Text, Title, Loader, Notification } from '@mantine/core';
import { useWallets } from '@/context/WalletContext';
import { useRouter } from 'next/router';

export default function WalletList() {
    const { wallets, loading, error } = useWallets();
    const router = useRouter();

    return (
        <Stack spacing="md">
            <Title order={3}>Wallets</Title>

            {loading && wallets.length === 0 && <Loader size="sm" />}
            {error && <Notification color="red">{error}</Notification>}

            {wallets.length > 0 ? (
                wallets.map((wallet) => (
                    <Card
                        key={wallet.id}
                        shadow="sm"
                        padding="lg"
                        onClick={() => router.push(`/wallet/${wallet.id}`)}
                        style={{ cursor: 'pointer', transition: '0.2s', ':hover': { backgroundColor: '#f5f5f5' } }}
                    >
                        <Text>
                            <strong>Name:</strong> {wallet.name}
                        </Text>
                        <Text>
                            <strong>Balance:</strong> {wallet.balance.toFixed(2)} {wallet.currency}
                        </Text>
                    </Card>
                ))
            ) : (
                !loading && <Text>No wallets found.</Text>
            )}
        </Stack>
    );
}
