/**
 * MSAL Mock Helper for Playwright E2E tests.
 *
 * How it works:
 *  1. Intercepts OIDC discovery → returns mock config pointing to mocked endpoints.
 *  2. Intercepts the authorize redirect → captures the nonce/state, redirects the
 *     browser back to the app with a fake auth code (hash fragment).
 *  3. Intercepts the token endpoint → returns RSA-signed fake JWTs (id_token and
 *     access_token) containing the captured nonce so MSAL's nonce validation passes.
 *  4. Intercepts the JWKS endpoint → returns the public key that corresponds to the
 *     private key used to sign the tokens.
 */

import { Page } from '@playwright/test';
import { generateKeyPairSync, createSign, createPublicKey } from 'crypto';

// ─── RSA key pair used for the lifetime of the test run ───────────────────────
const { privateKey, publicKey } = generateKeyPairSync('rsa', { modulusLength: 2048 });
const KEY_ID = 'playwright-test-key-1';

// ─── Helpers ──────────────────────────────────────────────────────────────────

function base64url(input: string | Buffer): string {
  const buf = typeof input === 'string' ? Buffer.from(input) : input;
  return buf.toString('base64').replace(/\+/g, '-').replace(/\//g, '_').replace(/=+$/, '');
}

export function signJwt(payload: object): string {
  const header = { alg: 'RS256', typ: 'JWT', kid: KEY_ID };
  const parts  = `${base64url(JSON.stringify(header))}.${base64url(JSON.stringify(payload))}`;
  const signer = createSign('RSA-SHA256');
  signer.update(parts);
  return `${parts}.${signer.sign(privateKey, 'base64url')}`;
}

function buildJwks() {
  const jwk = createPublicKey(publicKey).export({ format: 'jwk' }) as any;
  return {
    keys: [{ kty: 'RSA', use: 'sig', alg: 'RS256', kid: KEY_ID, n: jwk.n, e: jwk.e }],
  };
}

// ─── Main setup function ──────────────────────────────────────────────────────

export interface MsalMockOptions {
  clientId: string;
  tenantId: string;
  username: string;
  roles: string[];
}

export async function setupMsalMock(page: Page, opts: MsalMockOptions): Promise<void> {
  const { clientId, tenantId, username, roles } = opts;
  const oid = 'test-oid-00000001';
  let capturedNonce = 'test-nonce';

  // 1. OIDC discovery document
  await page.route(
    `**/login.microsoftonline.com/${tenantId}/v2.0/.well-known/openid-configuration`,
    async (route) => {
      await route.fulfill({
        status: 200,
        contentType: 'application/json',
        body: JSON.stringify({
          issuer: `https://login.microsoftonline.com/${tenantId}/v2.0`,
          authorization_endpoint: `https://login.microsoftonline.com/${tenantId}/oauth2/v2.0/authorize`,
          token_endpoint: `https://login.microsoftonline.com/${tenantId}/oauth2/v2.0/token`,
          jwks_uri: `https://login.microsoftonline.com/${tenantId}/discovery/v2.0/keys`,
          response_types_supported: ['code', 'token', 'id_token', 'code id_token'],
          subject_types_supported: ['pairwise'],
          id_token_signing_alg_values_supported: ['RS256'],
          token_endpoint_auth_methods_supported: ['client_secret_post', 'private_key_jwt'],
          claims_supported: ['sub', 'iss', 'aud', 'exp', 'iat', 'name', 'preferred_username', 'oid', 'tid', 'nonce'],
          end_session_endpoint: `https://login.microsoftonline.com/${tenantId}/oauth2/v2.0/logout`,
        }),
      });
    }
  );

  // 2. JWKS endpoint
  await page.route(
    `**/login.microsoftonline.com/${tenantId}/discovery/v2.0/keys`,
    async (route) => {
      await route.fulfill({
        status: 200,
        contentType: 'application/json',
        body: JSON.stringify(buildJwks()),
      });
    }
  );

  // 3. Authorize redirect: capture nonce/state, redirect back with auth code
  await page.route(
    '**/login.microsoftonline.com/**oauth2**authorize**',
    async (route) => {
      const url         = new URL(route.request().url());
      capturedNonce     = url.searchParams.get('nonce') || 'test-nonce';
      const state       = url.searchParams.get('state') || '';
      const redirectUri = url.searchParams.get('redirect_uri') || `http://localhost:4200/`;

      // MSAL uses response_mode=fragment by default for auth-code+PKCE
      const fragment = `code=PLAYWRIGHT_MOCK_CODE&state=${encodeURIComponent(state)}&session_state=mock-ss`;
      await route.fulfill({
        status: 302,
        headers: { Location: `${redirectUri}#${fragment}` },
      });
    }
  );

  // 4. Token endpoint: return signed fake tokens
  await page.route(
    '**/login.microsoftonline.com/**oauth2**token**',
    async (route) => {
      const now = Math.floor(Date.now() / 1000);

      const idToken = signJwt({
        aud: clientId,
        iss: `https://login.microsoftonline.com/${tenantId}/v2.0`,
        iat: now,
        exp: now + 3600,
        sub: oid,
        oid,
        tid: tenantId,
        nonce: capturedNonce,   // must match what MSAL sent in the authorize request
        name: 'Playwright Test User',
        preferred_username: username,
        email: username,
        roles,
      });

      const accessToken = signJwt({
        aud: `api://${clientId}`,
        iss: `https://login.microsoftonline.com/${tenantId}/v2.0`,
        iat: now,
        exp: now + 3600,
        sub: oid,
        oid,
        tid: tenantId,
        scp: 'qto',
        roles,
      });

      const clientInfo = base64url(JSON.stringify({ uid: oid, utid: tenantId }));

      await route.fulfill({
        status: 200,
        contentType: 'application/json',
        body: JSON.stringify({
          access_token: accessToken,
          id_token: idToken,
          token_type: 'Bearer',
          expires_in: 3600,
          ext_expires_in: 7200,
          scope: `api://${clientId}/qto openid profile email`,
          client_info: clientInfo,
        }),
      });
    }
  );
}
