import { createContext, useContext, useEffect, useState, ReactNode, useCallback } from 'react';
import { API_HOST } from '@/config/config';

export interface Wallet {
    id: string;
    name: string;
    currency: string;
    balance: number;
}

interface WalletContextType {
    wallets: Wallet[];
    reloadWallets: () => void;
    loading: boolean;
    error: string | null;
}

const WalletContext = createContext<WalletContextType | undefined>(undefined);

export const WalletProvider = ({ children }: { children: ReactNode }) => {
    const [wallets, setWallets] = useState<Wallet[]>([]); // Store wallet data
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);

    // Function to fetch wallet list
    const fetchWallets = useCallback(async (showLoading = true) => {
        if (showLoading) setLoading(true);
        setError(null);

        try {
            const response = await fetch(`${API_HOST}/wallet/list`);
            if (!response.ok) {
                throw new Error(`Failed to fetch wallets: ${response.statusText}`);
            }
            const data = await response.json();
            setWallets(data); // Update wallets with new data
        } catch (err: any) {
            setError(err.message || 'Failed to load wallets');
        } finally {
            if (showLoading) setLoading(false);
        }
    }, []);

    const reloadWallets = () => {
        fetchWallets(false);
    };

    useEffect(() => {
        fetchWallets();
        const interval = setInterval(() => fetchWallets(false), 10000); // Refresh every 10 seconds
        return () => clearInterval(interval); // Cleanup on unmount
    }, [fetchWallets]);

    return (
        <WalletContext.Provider value={{ wallets, reloadWallets, loading, error }}>
            {children}
        </WalletContext.Provider>
    );
};

export const useWallets = () => {
    const context = useContext(WalletContext);
    if (!context) {
        throw new Error('useWallets must be used within a WalletProvider');
    }
    return context;
};
