import { createContext, useContext, useEffect, useState, ReactNode, useCallback } from 'react';
import { API_HOST } from '@/config/config';

/** Supported currency types */
type Currency = 'EUR' | 'CZK';

/** Wallet interface */
export interface Wallet {
    id: string;
    name: string;
    currency: Currency;
    balance: number;
}

/** Wallet context type definition */
interface WalletContextType {
    /** List of wallets */
    wallets: Wallet[];

    /** Reloads wallet data manually */
    reloadWallets: () => void;

    /** Indicates if data is being loaded */
    loading: boolean;

    /** Stores any errors encountered during fetching */
    error: string | null;
}

const WalletContext = createContext<WalletContextType | undefined>(undefined);

/**
 * WalletProvider Component
 *
 * Provides wallet-related data and functionality to child components.
 *
 * @param {Object} props - Component props
 * @param {ReactNode} props.children - Child components
 * @returns {JSX.Element} The WalletProvider component
 */
export const WalletProvider = ({ children }: { children: ReactNode }): JSX.Element => {
    const [wallets, setWallets] = useState<Wallet[]>([]);
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);

    /**
     * Fetches wallet data from the API.
     *
     * @param {boolean} showLoading - Determines if loading state should be shown
     */
    const fetchWallets = useCallback(async (showLoading = true) => {
        if (showLoading) setLoading(true);
        setError(null);

        try {
            const response = await fetch(`${API_HOST}/wallet/list`);
            if (!response.ok) {
                throw new Error(`Failed to fetch wallets: ${response.statusText}`);
            }

            const data: Wallet[] = await response.json();
            setWallets(data);
        } catch (err: unknown) {
            setError(err instanceof Error ? err.message : 'Failed to load wallets');
        } finally {
            if (showLoading) setLoading(false);
        }
    }, []);

    /**
     * Manually triggers a wallet data reload.
     */
    const reloadWallets = useCallback(() => {
        fetchWallets(false);
    }, [fetchWallets]);

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

/**
 * Custom hook to access wallet data and functions.
 *
 * @returns {WalletContextType} The wallet context
 * @throws Will throw an error if used outside a WalletProvider
 */
export const useWallets = (): WalletContextType => {
    const context = useContext(WalletContext);
    if (!context) {
        throw new Error('useWallets must be used within a WalletProvider');
    }
    return context;
};
