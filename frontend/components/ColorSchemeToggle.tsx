import { ActionIcon, useMantineColorScheme } from '@mantine/core';
import { Sun, Moon } from 'lucide-react';

export function ColorSchemeToggle() {
    const { colorScheme, setColorScheme } = useMantineColorScheme();
    const isDark = colorScheme === 'dark';

    return (
        <ActionIcon
            onClick={() => setColorScheme(isDark ? 'light' : 'dark')}
            variant="default"
            size="lg"
            radius="xl"
        >
            {isDark ? (<Sun size={20} /> as React.ReactNode) : (<Moon size={20} /> as React.ReactNode)}
        </ActionIcon>
    );
}