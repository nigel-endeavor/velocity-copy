/**
 * Quick API smoke test
 * Run with: node test-api.mjs
 */
const BASE = 'http://localhost:8085/qto/api';

async function test(name, url) {
  try {
    const res = await fetch(url);
    const status = res.status;
    if (!res.ok) {
      console.log(`FAIL ${name}: HTTP ${status}`);
      return;
    }
    const json = await res.json();
    const summary = JSON.stringify(json).substring(0, 150);
    console.log(`OK   ${name}: HTTP ${status} - ${summary}...`);
  } catch (e) {
    console.log(`FAIL ${name}: ${e.message}`);
  }
}

async function run() {
  console.log('=== API Smoke Tests ===\n');
  await test('GET /api/orders?offset=0&limit=2', `${BASE}/orders?offset=0&limit=2`);
  await test('GET /api/orders/1',                `${BASE}/orders/1`);
  await test('GET /api/orders/2',                `${BASE}/orders/2`);
  await test('GET /api/quotes?offset=0&limit=5', `${BASE}/quotes?offset=0&limit=5`);
  await test('GET /api/quotes/1',                `${BASE}/quotes/1`);
  console.log('\n=== Done ===');
}

run();
