import { useState } from 'react';
import { Card, Stack, NumberInput, Select, Button, Group, TextInput, Alert } from '@mantine/core';
import { useWallets } from '@/context/WalletContext';
import { API_HOST } from '@/config/config';

/** Supported transaction types */
type TransactionType = 'DEPOSIT' | 'WITHDRAWAL' | 'TRANSFER' | '';

/** Supported currencies */
type Currency = 'EUR' | 'CZK';

/** Structure of the transaction form data */
interface TransactionFormData {
    transactionType: TransactionType;
    senderWalletId?: string;
    receiverWalletId?: string;
    bankCode: string;
    accountNumber: string;
    amount: number | '';
    currency: Currency;
}

/**
 * TransactionForm Component
 *
 * A form for creating deposit, withdrawal, and transfer transactions.
 *
 * @returns {JSX.Element} The transaction form UI
 */
export default function TransactionForm(): JSX.Element {
    const { wallets } = useWallets();

    const [formData, setFormData] = useState<TransactionFormData>({
        transactionType: '',
        senderWalletId: undefined,
        receiverWalletId: undefined,
        bankCode: '',
        accountNumber: '',
        amount: '',
        currency: 'EUR',
    });

    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);
    const [successMessage, setSuccessMessage] = useState<string | null>(null);

    /**
     * Updates form field values.
     *
     * @param {keyof TransactionFormData} field - The field to update
     * @param {TransactionFormData[keyof TransactionFormData]} value - The new value
     */
    const handleInputChange = <K extends keyof TransactionFormData>(field: K, value: TransactionFormData[K]) => {
        setFormData((prev) => ({ ...prev, [field]: value }));
    };

    /**
     * Resets form fields when transaction type changes.
     *
     * @param {TransactionType} value - The selected transaction type
     */
    const handleTransactionTypeChange = (value: TransactionType) => {
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

    /**
     * Sends a transaction request to the API.
     */
    const handleSendTransaction = async (): Promise<void> => {
        const { transactionType, senderWalletId, receiverWalletId, bankCode, accountNumber, amount, currency } = formData;

        if (!transactionType || amount === '' || amount <= 0) {
            setError('Please fill in all required fields with valid values.');
            return;
        }

        let apiUrl = '';
        let requestBody: Record<string, unknown> = {};

        if (transactionType === 'DEPOSIT') {
            if (!receiverWalletId) {
                setError('Deposit requires a receiver wallet.');
                return;
            }
            apiUrl = `${API_HOST}/wallet/deposit`;
            requestBody = { walletId: receiverWalletId, currency, accountNumber, bankCode, amount };
        } else if (transactionType === 'WITHDRAWAL') {
            if (!senderWalletId) {
                setError('Withdrawal requires a sender wallet.');
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
            handleTransactionTypeChange(transactionType);
        } catch (err: unknown) {
            setError(err instanceof Error ? err.message : 'An unexpected error occurred.');
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
                    onChange={(val) => handleTransactionTypeChange(val as TransactionType)}
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
                    onChange={(val) => handleInputChange('currency', val as Currency)}
                    required
                />

                {(formData.transactionType === 'WITHDRAWAL' || formData.transactionType === 'TRANSFER') && (
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
                )}

                {(formData.transactionType === 'DEPOSIT' || formData.transactionType === 'TRANSFER') && (
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
                )}

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
