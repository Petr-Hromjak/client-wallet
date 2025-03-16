import { Card, Stack, Text, Title, Loader, Notification } from '@mantine/core';
import { useWallets } from '@/context/WalletContext';
import { useRouter } from 'next/router';

/** Supported currency types */
type Currency = 'EUR' | 'CZK';

/** Wallet interface */
interface Wallet {
    id: string;
    name: string;
    balance: number;
    currency: Currency;
}

/**
 * WalletList Component
 *
 * Displays a list of available wallets. Allows users to click a wallet to navigate to its details.
 *
 * @returns {JSX.Element} The wallet list UI.
 */
export default function WalletList(): JSX.Element {
    const { wallets, loading, error } = useWallets();
    const router = useRouter();

    return (
        <Stack gap="md">
            <Title order={3}>Wallets</Title>

            {loading && wallets.length === 0 && <Loader size="sm" />}
            {error && <Notification color="red">{error}</Notification>}

            {wallets.length > 0 ? (
                wallets.map((wallet: Wallet) => (
                    <Card
                        key={wallet.id}
                        shadow="sm"
                        padding="lg"
                        onClick={() => router.push(`/wallet/${wallet.id}`)}
                        style={{
                            cursor: 'pointer',
                            transition: 'background-color 0.2s ease-in-out',
                        }}
                        sx={(theme) => ({
                            '&:hover': { backgroundColor: theme.colors.gray[1] },
                        })}
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
