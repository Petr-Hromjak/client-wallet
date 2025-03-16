import '@mantine/core/styles.css';
import type { AppProps } from 'next/app';
import { MantineProvider } from '@mantine/core';
import { theme } from '@/theme';
import Layout from '@/components/Layout';

/**
 * Custom App Component
 *
 * This is the root component that wraps all pages in the application.
 * It provides a global layout and theme using Mantine.
 *
 * @param {AppProps} props - The app component props
 * @param {React.ComponentType} props.Component - The active page component
 * @param {Record<string, unknown>} props.pageProps - Props specific to the active page
 * @returns {JSX.Element} The wrapped application
 */
export default function App({ Component, pageProps }: AppProps): JSX.Element {
    return (
        <MantineProvider theme={theme}>
            <Layout>
                <Component {...pageProps} />
            </Layout>
        </MantineProvider>
    );
}
