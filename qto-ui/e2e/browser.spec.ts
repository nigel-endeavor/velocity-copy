/**
 * Browser-level E2E tests using Playwright with MSAL auth mocked.
 *
 * Tests verify that after both bug fixes:
 *  1. No error dialog appears on the home page (previously: 500 from getCurrentTenant() NPE)
 *  2. No error dialog appears when navigating to /expenses  (previously: 404 for /public/about.json)
 *  3. No error dialog appears when navigating to /reports   (previously: 404 for /public/about.json)
 *
 * The MSAL authenticate flow is intercepted entirely at the network level, so
 * no real Azure AD credentials are needed.
 */
import { test, expect, Page } from '@playwright/test';
import { setupMsalMock } from './helpers/msal-mock';

// ─── Config (must match src/environments/environment.ts) ──────────────────────
const CLIENT_ID = '77dd2c9c-5d15-46ad-98d9-039c62d8ef9a';
const TENANT_ID = '119de762-6e78-4af0-a159-76b9a12af1a4';
const TEST_USER  = 'testuser@vertek.com';
const ROLES      = ['qto-admin', 'tenant-admin'];

const BASE = 'http://localhost:4200';

// ─── Selector helpers ─────────────────────────────────────────────────────────

/** Returns true if an error-dialog element is visible on the page. */
async function hasErrorDialog(page: Page): Promise<{ found: boolean; text: string }> {
  // The app renders <dialog open> for errors, containing an HTTP status code
  const dialogs = await page.locator('dialog[open]').all();
  for (const d of dialogs) {
    const text = await d.textContent();
    if (text && /4\d\d|5\d\d|Cannot GET/.test(text)) return { found: true, text: text.slice(0, 300) };
  }
  return { found: false, text: '' };
}

/** Wait until the Angular app stops showing a loading indicator. */
async function waitForAppReady(page: Page): Promise<void> {
  // Wait for either the navigation bar or an MSAL redirect to complete
  await page.waitForLoadState('networkidle', { timeout: 20_000 }).catch(() => {/* ignore timeout */});
}

// ─── Smoke test (no auth) ─────────────────────────────────────────────────────

test.describe('App smoke tests (no auth required)', () => {
  test('Angular app index.html responds with 200', async ({ page }) => {
    const res = await page.goto(BASE, { waitUntil: 'commit' });
    expect(res?.status()).toBe(200);
  });

  test('/public/about.json is accessible from the browser origin', async ({ page }) => {
    const res = await page.goto(`${BASE}/public/about.json`, { waitUntil: 'commit' });
    expect(res?.status()).toBe(200);
    const body = await page.evaluate(() => document.body.innerText);
    const json = JSON.parse(body);
    expect(json).toHaveProperty('version');
  });
});

// ─── Authenticated tests (MSAL mocked) ───────────────────────────────────────

test.describe('Authenticated routes — no error dialogs', () => {
  test.beforeEach(async ({ page }) => {
    await setupMsalMock(page, {
      clientId: CLIENT_ID,
      tenantId: TENANT_ID,
      username: TEST_USER,
      roles:    ROLES,
    });

    // Mock the WebSocket connection to prevent the notification service from
    // throwing errors during tests (WildFly WebSocket is not needed here).
    await page.addInitScript(() => {
      const OrigWS = window.WebSocket;
      (window as any).WebSocket = class extends OrigWS {
        constructor(url: string, protocols?: any) {
          super(url, protocols);
          setTimeout(() => {
            // immediately close so the notification service doesn't hang
            this.dispatchEvent(new CloseEvent('close'));
          }, 100);
        }
      };
    });
  });

  async function loginAndNavigate(page: Page, path: string): Promise<void> {
    // Navigate to path; MSAL will redirect to Azure AD, our mock intercepts it
    // and returns a fake auth code, then MSAL exchanges it for tokens.
    await page.goto(`${BASE}${path}`, { waitUntil: 'commit' });
    // Allow MSAL to finish the redirect flow + Angular to render
    await waitForAppReady(page);
    // Wait a bit more for any async API calls to settle
    await page.waitForTimeout(3000);
  }

  test('No error dialog on home page (#/)', async ({ page }) => {
    await loginAndNavigate(page, '/');
    const { found, text } = await hasErrorDialog(page);
    expect(found, `Error dialog should NOT appear on home page. Dialog said: ${text}`).toBe(false);
  });

  test('No error dialog on /expenses (fix for Issue #2)', async ({ page }) => {
    await loginAndNavigate(page, '/#/expenses');
    const { found, text } = await hasErrorDialog(page);
    expect(found, `Error dialog should NOT appear on /expenses. Dialog said: ${text}`).toBe(false);
  });

  test('No error dialog on /reports (fix for Issue #3)', async ({ page }) => {
    await loginAndNavigate(page, '/#/reports');
    const { found, text } = await hasErrorDialog(page);
    expect(found, `Error dialog should NOT appear on /reports. Dialog said: ${text}`).toBe(false);
  });

  test('GET /companyConfigProperties does not cause a 500 error dialog', async ({ page }) => {
    // Listen for any console errors that indicate a 500
    const errors: string[] = [];
    page.on('response', res => {
      if (res.url().includes('companyConfigProperties') && res.status() >= 500) {
        errors.push(`${res.status()} ${res.url()}`);
      }
    });

    await loginAndNavigate(page, '/');

    expect(errors.length, `Unexpected 5xx from companyConfigProperties: ${errors.join(', ')}`).toBe(0);
  });
});
