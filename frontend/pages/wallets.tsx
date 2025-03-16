import { Container, Title } from '@mantine/core';
import WalletList from '@/components/WalletList';
import CreateWalletForm from '@/components/CreateWalletForm';
import {WalletProvider} from "@/context/WalletContext";

export default function WalletPage() {
    return (
        <WalletProvider>
            <Container size="sm" py="xl">
                <Title order={2} mb="lg">
                    Create Wallet
                </Title>
                <CreateWalletForm />
            </Container>
            <WalletList />
        </WalletProvider>
    );
}
