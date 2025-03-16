import { AppShell, Burger, Group, NavLink } from '@mantine/core';
import { useDisclosure } from '@mantine/hooks';
import Link from 'next/link';
import { ColorSchemeToggle } from '@/components/ColorSchemeToggle';

const navLinks = [
    { label: 'Wallets', href: '/wallets' },
    { label: 'Transaction', href: '/transaction' }, // Fixed: Correct plural naming
];

export default function Layout({ children }: { children: React.ReactNode }) {
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
            {/* Header */}
            <AppShell.Header>
                <Group h="100%" px="md" justify="space-between">
                    <Group>
                        <Burger opened={mobileOpened} onClick={toggleMobile} hiddenFrom="sm" size="sm" />
                        <Burger opened={desktopOpened} onClick={toggleDesktop} visibleFrom="sm" size="sm" />
                        <span>Client Wallet App</span>
                    </Group>
                    <ColorSchemeToggle />
                </Group>
            </AppShell.Header>
            <AppShell.Navbar p="md">
                <span>Navigation</span>
                {navLinks.map((link) => (
                    <NavLink key={link.href} component={Link} href={link.href} label={link.label}/>
                ))}
            </AppShell.Navbar>
            <AppShell.Main>{children}</AppShell.Main>
        </AppShell>
    );
}
