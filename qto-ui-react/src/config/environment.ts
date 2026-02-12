/**
 * Environment Configuration
 *
 * Centralized configuration for different environments (dev, test, prod).
 * Uses Vite environment variables with VITE_ prefix.
 */

export interface Environment {
  production: boolean;
  appUrl: string;
  wsUrl: string;
  publicUrl: string;
  baseHref: string;
}

const isDevelopment = import.meta.env.MODE === 'development';
const isProduction = import.meta.env.MODE === 'production';
const isTest = import.meta.env.MODE === 'test';

/**
 * Development Environment (BREE - BFF at 3000)
 */
const developmentEnv: Environment = {
  production: false,
  appUrl: import.meta.env.VITE_API_URL || 'http://localhost:3000',
  wsUrl: import.meta.env.VITE_WS_URL || 'ws://localhost:8080/qto',
  publicUrl: import.meta.env.VITE_PUBLIC_URL || 'http://localhost:8080/public',
  baseHref: '/qto-ops/',
};

/**
 * Test Environment (BREE)
 */
const testEnv: Environment = {
  production: false,
  appUrl: import.meta.env.VITE_API_URL || 'http://localhost:3000',
  wsUrl: import.meta.env.VITE_WS_URL || 'ws://localhost:8080/qto',
  publicUrl: import.meta.env.VITE_PUBLIC_URL || 'http://localhost:8080/public',
  baseHref: '/qto-ops/',
};

/**
 * Production Environment
 */
const productionEnv: Environment = {
  production: true,
  appUrl: import.meta.env.VITE_API_URL || '/qto/api',
  wsUrl: import.meta.env.VITE_WS_URL || `ws://${window.location.host}/qto`,
  publicUrl: import.meta.env.VITE_PUBLIC_URL || '/public',
  baseHref: '/qto-ops/',
};

/**
 * Current Environment
 * Auto-selected based on Vite mode
 */
export const environment: Environment = isProduction
  ? productionEnv
  : isTest
  ? testEnv
  : developmentEnv;

/**
 * Helper functions
 */
export function getApiUrl(): string {
  return environment.appUrl;
}

export function getWsUrl(): string {
  return environment.wsUrl;
}

export function getPublicUrl(): string {
  return environment.publicUrl;
}

export function getBaseHref(): string {
  return environment.baseHref;
}

export function isProductionMode(): boolean {
  return environment.production;
}
