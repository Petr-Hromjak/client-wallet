import { Container, Title } from '@mantine/core';
import WalletList from '@/components/WalletList';
import CreateWalletForm from '@/components/CreateWalletForm';
import { WalletProvider } from '@/context/WalletContext';

/**
 * WalletPage Component
 *
 * This page allows users to create new wallets and view the list of existing wallets.
 * It wraps `CreateWalletForm` and `WalletList` inside `WalletProvider` to ensure wallet data is accessible.
 *
 * @returns {JSX.Element} The wallet page UI
 */
export default function WalletPage(){
    return (
        <WalletProvider>
            <Container size="sm" py="xl">
                <Title order={2} mb="lg">
                    Create Wallet
                </Title>
                <CreateWalletForm />
            </Container>
            <Title order={2} mb="lg">
                Wallet List
            </Title>
            <WalletList />
        </WalletProvider>
    );
}
