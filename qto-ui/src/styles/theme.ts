/**
 * Theme Configuration
 *
 * Design-token constants for the application.
 * These values are available to any component that needs programmatic access
 * to the palette; most styling is handled by Tailwind utility classes.
 */

const colors = {
  primary: {
    main: '#1976d2',
    light: '#42a5f5',
    dark: '#1565c0',
    contrastText: '#fff',
  },
  secondary: {
    main: '#dc004e',
    light: '#ff4081',
    dark: '#c51162',
    contrastText: '#fff',
  },
  error: { main: '#f44336', light: '#e57373', dark: '#d32f2f' },
  warning: { main: '#ff9800', light: '#ffb74d', dark: '#f57c00' },
  info: { main: '#2196f3', light: '#64b5f6', dark: '#1976d2' },
  success: { main: '#4caf50', light: '#81c784', dark: '#388e3c' },
  grey: {
    50: '#fafafa',
    100: '#f5f5f5',
    200: '#eeeeee',
    300: '#e0e0e0',
    400: '#bdbdbd',
    500: '#9e9e9e',
    600: '#757575',
    700: '#616161',
    800: '#424242',
    900: '#212121',
  },
};

export const theme = {
  palette: {
    mode: 'light' as const,
    ...colors,
    background: { default: '#fafafa', paper: '#fff' },
    text: {
      primary: 'rgba(0, 0, 0, 0.87)',
      secondary: 'rgba(0, 0, 0, 0.6)',
      disabled: 'rgba(0, 0, 0, 0.38)',
    },
  },
  typography: {
    fontFamily: [
      'Roboto',
      '-apple-system',
      'BlinkMacSystemFont',
      '"Segoe UI"',
      '"Helvetica Neue"',
      'Arial',
      'sans-serif',
    ].join(','),
  },
  shape: { borderRadius: 4 },
  spacing: 8,
};

export const darkTheme = {
  ...theme,
  palette: {
    ...theme.palette,
    mode: 'dark' as const,
    background: { default: '#121212', paper: '#1e1e1e' },
    text: {
      primary: 'rgba(255, 255, 255, 0.87)',
      secondary: 'rgba(255, 255, 255, 0.6)',
      disabled: 'rgba(255, 255, 255, 0.38)',
    },
  },
};

export default theme;
