import { Container, Title } from '@mantine/core';
import TransactionForm from '@/components/TransactionForm';
import { WalletProvider } from '@/context/WalletContext';

/**
 * TransactionPage Component
 *
 * This page allows users to send transactions. It wraps the `TransactionForm`
 * inside `WalletProvider` to ensure wallet data is accessible.
 *
 * @returns {JSX.Element} The transaction page UI
 */
export default function TransactionPage(): JSX.Element {
    return (
        <Container size="sm" py="xl">
            <Title order={2} mb="lg">
                Send Transaction
            </Title>
            <WalletProvider>
                <TransactionForm />
            </WalletProvider>
        </Container>
    );
}
