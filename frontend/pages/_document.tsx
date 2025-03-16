import { Head, Html, Main, NextScript } from 'next/document';
import { ColorSchemeScript, mantineHtmlProps } from '@mantine/core';

/**
 * Custom Document Component
 *
 * This is the root document structure for the Next.js app.
 * It applies global HTML attributes and integrates Mantine's color scheme handling.
 *
 * @returns {JSX.Element} The document structure
 */
export default function Document(): JSX.Element {
    return (
        <Html lang="en" {...mantineHtmlProps}>
            <Head>
                {/* Ensures Mantine's color scheme is preserved between page loads */}
                <ColorSchemeScript />
            </Head>
            <body>
            <Main />
            <NextScript />
            </body>
        </Html>
    );
}
