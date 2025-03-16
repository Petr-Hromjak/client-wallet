import { ActionIcon, useMantineColorScheme } from '@mantine/core';
import { Sun, Moon } from 'lucide-react';

/**
 * A toggle button for switching between light and dark color schemes.
 * Uses Mantine's `useMantineColorScheme` hook to change the theme.
 *
 * @returns {JSX.Element} The color scheme toggle component.
 */
export function ColorSchemeToggle(): JSX.Element {
    const { colorScheme, setColorScheme } = useMantineColorScheme();
    const isDark: boolean = colorScheme === 'dark';

    return (
        <ActionIcon
            onClick={() => setColorScheme(isDark ? 'light' : 'dark')}
            variant="default"
            size="lg"
            radius="xl"
            aria-label="Toggle color scheme"
        >
            {isDark ? <Sun size={20} /> : <Moon size={20} />}
        </ActionIcon>
    );
}
