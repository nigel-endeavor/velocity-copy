/**
 * BREE - Elysia BFF Server
 *
 * Proxies requests to Spring Boot backend with typed routes for Eden Treaty.
 * Runs on Bun at port 3000.
 */

import { Elysia } from 'elysia';
import { cors } from '@elysiajs/cors';

const BACKEND_URL = process.env.BACKEND_URL || 'http://localhost:8081';
const API_BASE = `${BACKEND_URL}/api`;
const PORT = parseInt(process.env.BFF_PORT || '3000', 10);

/**
 * Proxy a request to the Spring Boot backend
 */
async function proxy(
  path: string,
  init?: RequestInit
): Promise<Response> {
  const url = `${API_BASE}${path}`;
  const headers = new Headers(init?.headers);
  headers.set('Content-Type', headers.get('Content-Type') || 'application/json');
  return fetch(url, { ...init, headers });
}

const app = new Elysia()
  .use(
    cors({
      origin: ['http://localhost:4200', 'http://127.0.0.1:4200'],
      credentials: true,
      allowedHeaders: ['Content-Type', 'Authorization', 'X-Demo-Mode'],
      methods: ['GET', 'POST', 'PUT', 'PATCH', 'DELETE', 'OPTIONS'],
    })
  )
  // Status endpoints
  .get('/status', async () => {
    const res = await proxy('/status');
    return res.json();
  })
  .get('/status/ping', async () => {
    const res = await proxy('/status/ping');
    return res.json();
  })
  // Orders
  .get('/orders/:id', async ({ params }) => {
    const res = await proxy(`/orders/${params.id}`);
    return res.json();
  })
  .post('/orders/search', async ({ body }) => {
    const res = await proxy('/orders/search', {
      method: 'POST',
      body: JSON.stringify(body),
    });
    return res.json();
  })
  .post('/orders', async ({ body }) => {
    const res = await proxy('/orders', {
      method: 'POST',
      body: JSON.stringify(body),
    });
    return res.json();
  })
  .put('/orders/:id', async ({ params, body }) => {
    const res = await proxy(`/orders/${params.id}`, {
      method: 'PUT',
      body: JSON.stringify(body),
    });
    return res.json();
  })
  .delete('/orders/:id', async ({ params }) => {
    const res = await proxy(`/orders/${params.id}`, { method: 'DELETE' });
    return res;
  })
  .get('/orders/count-by-status', async () => {
    const res = await proxy('/orders/count-by-status');
    return res.json();
  })
  // Services
  .get('/services/:id', async ({ params }) => {
    const res = await proxy(`/services/${params.id}`);
    return res.json();
  })
  .post('/services/search', async ({ body }) => {
    const res = await proxy('/services/search', {
      method: 'POST',
      body: JSON.stringify(body),
    });
    return res.json();
  })
  .get('/orders/:id/services', async ({ params }) => {
    const res = await proxy(`/orders/${params.id}/services`);
    return res.json();
  })
  .get('/locations/:id/services', async ({ params }) => {
    const res = await proxy(`/locations/${params.id}/services`);
    return res.json();
  })
  .post('/services', async ({ body }) => {
    const res = await proxy('/services', {
      method: 'POST',
      body: JSON.stringify(body),
    });
    return res.json();
  })
  .put('/services/:id', async ({ params, body }) => {
    const res = await proxy(`/services/${params.id}`, {
      method: 'PUT',
      body: JSON.stringify(body),
    });
    return res.json();
  })
  .delete('/services/:id', async ({ params }) => {
    const res = await proxy(`/services/${params.id}`, { method: 'DELETE' });
    return res;
  })
  // Locations
  .get('/locations/:id', async ({ params }) => {
    const res = await proxy(`/locations/${params.id}`);
    return res.json();
  })
  .post('/locations/search', async ({ body }) => {
    const res = await proxy('/locations/search', {
      method: 'POST',
      body: JSON.stringify(body),
    });
    return res.json();
  })
  .get('/orders/:id/locations', async ({ params }) => {
    const res = await proxy(`/orders/${params.id}/locations`);
    return res.json();
  })
  .post('/locations', async ({ body }) => {
    const res = await proxy('/locations', {
      method: 'POST',
      body: JSON.stringify(body),
    });
    return res.json();
  })
  .put('/locations/:id', async ({ params, body }) => {
    const res = await proxy(`/locations/${params.id}`, {
      method: 'PUT',
      body: JSON.stringify(body),
    });
    return res.json();
  })
  .delete('/locations/:id', async ({ params }) => {
    const res = await proxy(`/locations/${params.id}`, { method: 'DELETE' });
    return res;
  })
  // Generic proxy for unmapped routes (forward to Spring Boot)
  .all('/*', async ({ request }) => {
    const url = new URL(request.url);
    const path = url.pathname;
    const targetUrl = `${API_BASE}${path}${url.search}`;
    const init: RequestInit = {
      method: request.method,
      headers: request.headers,
    };
    if (request.method !== 'GET' && request.method !== 'HEAD' && request.body) {
      init.body = request.body;
    }
    return fetch(targetUrl, init);
  })
  .listen(PORT);

export type App = typeof app;

console.log(`🦊 BREE Elysia BFF running at http://localhost:${PORT}`);
console.log(`   Proxying to ${API_BASE}`);
