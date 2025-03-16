import { useState } from 'react';
import { Button, Card, Stack, Select, TextInput, Alert } from '@mantine/core';
import { API_HOST } from '@/config/config';
import { useWallets } from '@/context/WalletContext';

export default function CreateWalletForm() {
    const { reloadWallets } = useWallets();
    const [walletName, setWalletName] = useState('');
    const [currency, setCurrency] = useState<'CZK' | 'EUR'>('EUR'); // Restrict to valid currencies
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [success, setSuccess] = useState<string | null>(null);

    const handleCreateWallet = async () => {
        if (!walletName) {
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
                body: JSON.stringify({ name: walletName, currency }),
            });

            const responseData = await response.json();

            if (!response.ok) {
                throw new Error(responseData?.error || 'Unknown error occurred.');
            }

            setSuccess('Wallet created successfully!');
            setWalletName('');
            reloadWallets();
        } catch (err: any) {
            setError(err.message);
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
                    onChange={(val) => setCurrency(val as 'CZK' | 'EUR')}
                    required
                />

                <Button onClick={handleCreateWallet} loading={loading}>
                    Create Wallet
                </Button>
            </Stack>
        </Card>
    );
}
