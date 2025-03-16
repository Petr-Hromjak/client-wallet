import { useState } from 'react';
import { Button, Card, Stack, Select, TextInput, Alert } from '@mantine/core';
import { API_HOST } from '@/config/config';
import { useWallets } from '@/context/WalletContext';

/** Supported currency types */
type Currency = 'CZK' | 'EUR';

/**
 * Component for creating a new wallet.
 * Allows users to input a wallet name and select a currency.
 */
export default function CreateWalletForm() {
    const { reloadWallets } = useWallets();
    const [walletName, setWalletName] = useState<string>('');
    const [currency, setCurrency] = useState<Currency>('EUR');
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);
    const [success, setSuccess] = useState<string | null>(null);

    /**
     * Handles the creation of a new wallet by sending a request to the API.
     * Performs input validation and handles response messages.
     */
    const handleCreateWallet = async (): Promise<void> => {
        if (!walletName.trim()) {
            setError('Wallet name is required.');
            return;
        }

        setLoading(true);
        setError(null);
        setSuccess(null);

        try {
            const response = await fetch(`${API_HOST}/wallet/create`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ name: walletName.trim(), currency }),
            });

            const responseData = await response.json();

            if (!response.ok) {
                throw new Error(responseData?.error || 'Unknown error occurred.');
            }

            setSuccess('Wallet created successfully!');
            setWalletName('');
            reloadWallets();
        } catch (err: unknown) {
            if (err instanceof Error) {
                setError(err.message);
            } else {
                setError('An unexpected error occurred.');
            }
        } finally {
            setLoading(false);
        }
    };

    return (
        <Card shadow="sm" padding="lg">
            <Stack gap="md">
                {error && <Alert color="red">{error}</Alert>}
                {success && <Alert color="green">{success}</Alert>}

                <TextInput
                    label="Wallet Name"
                    placeholder="Enter wallet name"
                    value={walletName}
                    onChange={(e) => setWalletName(e.target.value)}
                    required
                />

                <Select
                    label="Currency"
                    placeholder="Select currency"
                    data={[
                        { value: 'EUR', label: 'Euro (EUR)' },
                        { value: 'CZK', label: 'Czech Crown (CZK)' },
                    ]}
                    value={currency}
                    onChange={(val) => setCurrency(val as Currency)}
                    required
                />

                <Button onClick={handleCreateWallet} loading={loading}>
                    Create Wallet
                </Button>
            </Stack>
        </Card>
    );
}
