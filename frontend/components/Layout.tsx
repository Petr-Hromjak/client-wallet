import { AppShell, Burger, Group, NavLink, Text } from '@mantine/core';
import { useDisclosure } from '@mantine/hooks';
import Link from 'next/link';
import { ColorSchemeToggle } from '@/components/ColorSchemeToggle';

/** Navigation links for the sidebar */
const navLinks: { label: string; href: string }[] = [
    { label: 'Wallets', href: '/wallets' },
    { label: 'Transactions', href: '/transactions' }, // Fixed plural naming
];

/**
 * Layout component for the application.
 * Provides a navigation sidebar, header, and main content area.
 *
 * @param {Object} props - Component props
 * @param {React.ReactNode} props.children - Child components to render in the main area
 */
export default function Layout({ children }: { children: React.ReactNode }) {
    // Handle mobile and desktop navbar toggling
    const [mobileOpened, { toggle: toggleMobile }] = useDisclosure();
    const [desktopOpened, { toggle: toggleDesktop }] = useDisclosure(true);

    return (
        <AppShell
            header={{ height: 60 }}
            navbar={{
                width: 300,
                breakpoint: 'sm',
                collapsed: { mobile: !mobileOpened, desktop: !desktopOpened },
            }}
            padding="md"
        >
            {/* Header Section */}
            <AppShell.Header>
                <Group h="100%" px="md" justify="space-between">
                    <Group>
                        <Burger opened={mobileOpened} onClick={toggleMobile} hiddenFrom="sm" size="sm" />
                        <Burger opened={desktopOpened} onClick={toggleDesktop} visibleFrom="sm" size="sm" />
                        <Text size="lg" fw={500}>Client Wallet App</Text>
                    </Group>
                    <ColorSchemeToggle />
                </Group>
            </AppShell.Header>

            {/* Sidebar Navigation */}
            <AppShell.Navbar p="md">
                <Text size="sm" fw={500} mb="sm">Navigation</Text>
                {navLinks.map((link) => (
                    <NavLink
                        key={link.href}
                        component={Link}
                        href={link.href}
                        label={link.label}
                        active={false} // Ensure proper highlighting logic if needed
                    />
                ))}
            </AppShell.Navbar>

            {/* Main Content */}
            <AppShell.Main>{children}</AppShell.Main>
        </AppShell>
    );
}
