import { Container, Title } from '@mantine/core';
import TransactionForm from '@/components/TransactionForm';
import {WalletProvider} from "@/context/WalletContext";

export default function TransactionPage() {
    return (
        <>
            <Container size="sm" py="xl">
                <Title order={2} mb="lg">
                    Send Transaction
                </Title>
                <WalletProvider>
                    <TransactionForm/>
                </WalletProvider>
            </Container>
        </>
    );
}
