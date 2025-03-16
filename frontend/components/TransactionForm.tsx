import { useState } from 'react';
import { Card, Stack, NumberInput, Select, Button, Group, TextInput, Alert } from '@mantine/core';
import { useWallets } from '@/context/WalletContext';
import { API_HOST } from '@/config/config';

export default function TransactionForm() {
    const { wallets } = useWallets();

    const [formData, setFormData] = useState({
        transactionType: '' as 'DEPOSIT' | 'WITHDRAWAL' | 'TRANSFER' | '',
        senderWalletId: undefined as string | undefined,
        receiverWalletId: undefined as string | undefined,
        bankCode: '',
        accountNumber: '',
        amount: '' as number | '',
        currency: 'EUR',
    });

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [successMessage, setSuccessMessage] = useState<string | null>(null);

    const handleInputChange = (field: keyof typeof formData, value: any) => {
        setFormData((prev) => ({ ...prev, [field]: value }));
    };

    const handleTransactionTypeChange = (value: 'DEPOSIT' | 'WITHDRAWAL' | 'TRANSFER' | '') => {
        setFormData({
            transactionType: value,
            senderWalletId: undefined,
            receiverWalletId: undefined,
            bankCode: '',
            accountNumber: '',
            amount: '',
            currency: 'EUR',
        });
    };

    const handleSendTransaction = async () => {
        const { transactionType, senderWalletId, receiverWalletId, bankCode, accountNumber, amount, currency } = formData;

        if (!transactionType || amount === '') {
            setError('Please fill in all required fields.');
            return;
        }

        if (amount <= 0) {
            setError('Amount must be greater than 0.');
            return;
        }

        let apiUrl = '';
        let requestBody: any = {};

        if (transactionType === 'DEPOSIT') {
            if (!receiverWalletId) {
                setError('Deposit requires a receiver wallet.');
                return;
            }
            if (!/^\d{4,6}$/.test(bankCode)) {
                setError('Bank Code must be 4-6 digits.');
                return;
            }
            if (!/^\d{10,18}$/.test(accountNumber)) {
                setError('Account Number must be 10-18 digits.');
                return;
            }

            apiUrl = `${API_HOST}/wallet/deposit`;
            requestBody = { walletId: receiverWalletId, currency, accountNumber, bankCode, amount };
        } else if (transactionType === 'WITHDRAWAL') {
            if (!senderWalletId) {
                setError('Withdrawal requires a sender wallet.');
                return;
            }
            if (!/^\d{4,6}$/.test(bankCode)) {
                setError('Bank Code must be 4-6 digits.');
                return;
            }
            if (!/^\d{10,18}$/.test(accountNumber)) {
                setError('Account Number must be 10-18 digits.');
                return;
            }

            apiUrl = `${API_HOST}/wallet/withdraw`;
            requestBody = { walletId: senderWalletId, currency, accountNumber, bankCode, amount };
        } else if (transactionType === 'TRANSFER') {
            if (!senderWalletId || !receiverWalletId) {
                setError('Transfer requires both sender and receiver wallets.');
                return;
            }
            apiUrl = `${API_HOST}/wallet/transfer`;
            requestBody = { senderWalletId, receiverWalletId, currency, amount };
        }

        setLoading(true);
        setError(null);
        setSuccessMessage(null);

        try {
            const response = await fetch(apiUrl, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(requestBody),
            });

            const responseData = await response.json();

            if (!response.ok) {
                throw new Error(responseData?.error || 'Unknown error occurred.');
            }

            setSuccessMessage(`Transaction successful: ${transactionType}`);

            setFormData((prev) => ({
                transactionType: prev.transactionType,
                senderWalletId: undefined,
                receiverWalletId: undefined,
                bankCode: '',
                accountNumber: '',
                amount: '',
                currency: 'EUR',
            }));

        } catch (error: any) {
            setError(error.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <Card shadow="sm" padding="lg">
            <Stack gap="md">
                {error && <Alert color="red">{error}</Alert>}
                {successMessage && <Alert color="green">{successMessage}</Alert>}

                <Select
                    label="Transaction Type"
                    placeholder="Select type"
                    data={[
                        { value: 'DEPOSIT', label: 'Deposit' },
                        { value: 'WITHDRAWAL', label: 'Withdrawal' },
                        { value: 'TRANSFER', label: 'Transfer' },
                    ]}
                    value={formData.transactionType}
                    onChange={(val) => handleTransactionTypeChange(val as 'DEPOSIT' | 'WITHDRAWAL' | 'TRANSFER')}
                    required
                />

                <Select
                    label="Currency"
                    placeholder="Select currency"
                    data={[
                        { value: 'EUR', label: 'Euro (EUR)' },
                        { value: 'CZK', label: 'Czech Crown (CZK)' },
                    ]}
                    value={formData.currency}
                    onChange={(val) => handleInputChange('currency', val)}
                    required
                />

                {formData.transactionType === 'WITHDRAWAL' || formData.transactionType === 'TRANSFER' ? (
                    <Select
                        label="Sender Wallet"
                        placeholder="Select sender wallet"
                        data={wallets.map((wallet) => ({
                            value: wallet.id,
                            label: `${wallet.name} (${wallet.currency})`,
                        }))}
                        value={formData.senderWalletId}
                        onChange={(val) => handleInputChange('senderWalletId', val || undefined)}
                        required
                    />
                ) : null}

                {formData.transactionType === 'DEPOSIT' || formData.transactionType === 'TRANSFER' ? (
                    <Select
                        label="Receiver Wallet"
                        placeholder="Select receiver wallet"
                        data={wallets.map((wallet) => ({
                            value: wallet.id,
                            label: `${wallet.name} (${wallet.currency})`,
                        }))}
                        value={formData.receiverWalletId}
                        onChange={(val) => handleInputChange('receiverWalletId', val || undefined)}
                        required
                    />
                ) : null}

                {formData.transactionType !== 'TRANSFER' && (
                    <>
                        <TextInput
                            label="Bank Code"
                            placeholder="Enter bank code"
                            value={formData.bankCode}
                            onChange={(e) => handleInputChange('bankCode', e.target.value)}
                            required
                        />
                        <TextInput
                            label="Account Number"
                            placeholder="Enter account number"
                            value={formData.accountNumber}
                            onChange={(e) => handleInputChange('accountNumber', e.target.value)}
                            required
                        />
                    </>
                )}

                <NumberInput
                    label="Amount"
                    placeholder="Enter amount"
                    value={formData.amount}
                    onChange={(val) => handleInputChange('amount', val || 0)}
                    min={0.01}
                    required
                />

                <Group justify="flex-end">
                    <Button onClick={handleSendTransaction} variant="outline" loading={loading}>
                        Send Transaction
                    </Button>
                </Group>
            </Stack>
        </Card>
    );
}
