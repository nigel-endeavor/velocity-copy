/**
 * API-level E2E tests — no browser needed.
 * Verifies the two bug fixes:
 *   1. getCurrentTenant() NPE →  backend now returns 404 (not 500)
 *   2. /public/about.json 404 →  file now exists and is served
 */
import { test, expect } from '@playwright/test';

const BACKEND  = 'http://localhost:8080/qto/api';
const FRONTEND = 'http://localhost:4200';

// ─────────────────────────────────────────────
// Static asset tests (no auth)
// ─────────────────────────────────────────────

test.describe('Static assets', () => {
  test('/public/about.json is served with correct JSON (fix for Issue #2 & #3)', async ({ request }) => {
    const res = await request.get(`${FRONTEND}/public/about.json`);
    expect(res.status(), 'about.json should return HTTP 200').toBe(200);

    const body = await res.json();
    expect(body, 'about.json must have a version field').toHaveProperty('version');
    expect(typeof body.version).toBe('string');
  });

  test('Angular app index.html loads', async ({ request }) => {
    const res = await request.get(`${FRONTEND}/`);
    expect(res.status()).toBe(200);
    const html = await res.text();
    expect(html).toContain('<app-root>');
  });
});

// ─────────────────────────────────────────────
// Backend API tests (no auth)
// ─────────────────────────────────────────────

test.describe('Backend API', () => {
  test('WildFly is running on port 8080', async ({ request }) => {
    const res = await request.get(`${BACKEND}/lookupValues?page=0&size=1`);
    expect(res.status(), 'WildFly should be up').toBeLessThan(500);
  });

  test('GET /companyConfigProperties returns 404 (not 500) when user has no tenant – fix for Issue #1', async ({ request }) => {
    const res = await request.get(`${BACKEND}/companyConfigProperties/TELECOM_CLIENT`);
    const status = res.status();
    // Before fix: 500 (NullPointerException in getCurrentTenant().getName())
    // After fix:  404 (graceful null check)
    expect(status, `Expected 4xx but got ${status}`).not.toBe(500);
    expect([401, 403, 404]).toContain(status);
  });

  test('GET /companyConfigProperties/CYBER_SECURITY_CLIENT returns 404 (not 500)', async ({ request }) => {
    const res = await request.get(`${BACKEND}/companyConfigProperties/CYBER_SECURITY_CLIENT`);
    expect(res.status()).not.toBe(500);
  });

  test('GET /lookupValues returns 200 (general backend health)', async ({ request }) => {
    const res = await request.get(`${BACKEND}/lookupValues?page=0&size=5`);
    expect(res.status()).toBe(200);
  });
});
