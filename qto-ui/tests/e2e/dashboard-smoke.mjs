import { chromium, expect } from '@playwright/test';

const baseUrl = process.env.DASHBOARD_SMOKE_URL || 'http://127.0.0.1:4173/#/dashboard';

const sampleServices = [
  {
    id: 1,
    serviceId: 1001,
    locationId: 201,
    orderId: 301,
    companyName: 'Arck University',
    masterCompanyName: 'Arck Group',
    provisionerId: 1,
    vertekProjectManagerId: 2,
    clientProjectManager: 'Ella Hudson',
    provisioner: 'Jordan Blake',
    vertekProjectManager: 'Nina Vega',
    clientOrderId: 'AR-1001',
    clientLocationId: 'L-01',
    locationName: 'North Campus',
    address1: '101 Main Street',
    address2: null,
    city: 'Austin',
    stateProvince: 'TX',
    clientServiceId: 'SVC-1001',
    provider: 'Endeavor Fiber',
    serviceStatus: 'In Progress',
    serviceType: 'Internet',
    active: true,
    dataProvisioningCompleteDate: '2026-02-02',
    serviceMrc: 1450,
    serviceBilledTo: 'Arck University',
    completeDate: null,
    currentInventory: true,
  },
  {
    id: 2,
    serviceId: 1002,
    locationId: 202,
    orderId: 302,
    companyName: 'Arck University',
    masterCompanyName: 'Arck Group',
    provisionerId: 3,
    vertekProjectManagerId: 2,
    clientProjectManager: 'Ella Hudson',
    provisioner: 'Tara Ross',
    vertekProjectManager: 'Nina Vega',
    clientOrderId: 'AR-1002',
    clientLocationId: 'L-02',
    locationName: 'South Campus',
    address1: '120 Learning Ave',
    address2: null,
    city: 'Austin',
    stateProvince: 'TX',
    clientServiceId: 'SVC-1002',
    provider: 'BlueWire Telecom',
    serviceStatus: 'Complete',
    serviceType: 'VoIP',
    active: true,
    dataProvisioningCompleteDate: '2026-01-12',
    serviceMrc: 870,
    serviceBilledTo: 'Arck University',
    completeDate: '2026-01-22',
    currentInventory: true,
  },
  {
    id: 3,
    serviceId: 1003,
    locationId: 203,
    orderId: 303,
    companyName: 'Arck University',
    masterCompanyName: 'Arck Group',
    provisionerId: null,
    vertekProjectManagerId: 4,
    clientProjectManager: 'Ella Hudson',
    provisioner: null,
    vertekProjectManager: 'Kai Monroe',
    clientOrderId: 'AR-1003',
    clientLocationId: 'L-03',
    locationName: 'Remote Learning Hub',
    address1: '88 Cloud Way',
    address2: null,
    city: 'Dallas',
    stateProvince: 'TX',
    clientServiceId: 'SVC-1003',
    provider: 'Endeavor Fiber',
    serviceStatus: 'Pending',
    serviceType: 'Managed Router',
    active: true,
    dataProvisioningCompleteDate: null,
    serviceMrc: 540,
    serviceBilledTo: 'Arck University',
    completeDate: null,
    currentInventory: false,
  },
];

const serviceJeops = [sampleServices[0]];
const locationJeops = [sampleServices[2]];

function responseFor(url) {
  if (url.includes('/wipServiceJeops')) return serviceJeops;
  if (url.includes('/wipLocationJeops')) return locationJeops;
  return sampleServices;
}

const tabAssertions = [
  { name: 'WIP', heading: 'Project status breakdown' },
  { name: 'KPIs', heading: 'Network delivery interval' },
  { name: 'Providers', heading: 'Install interval by provider' },
  { name: 'Financials', heading: 'Total MRC by provider' },
  { name: 'Activations', heading: 'Activation status distribution' },
  { name: 'Inventory', heading: 'Inventory valuation by provider' },
];

const browser = await chromium.launch({ headless: true });
const context = await browser.newContext({ viewport: { width: 1600, height: 1200 } });
const page = await context.newPage();

await page.route('**/qto/api/wipViews/**', async (route) => {
  await route.fulfill({
    status: 200,
    contentType: 'application/json',
    body: JSON.stringify(responseFor(route.request().url())),
  });
});

await page.goto(baseUrl, { waitUntil: 'networkidle' });

await expect(page.getByRole('heading', { name: 'Service delivery dashboard' })).toBeVisible();
await expect(page.getByText('Operations profile')).toBeVisible();
await expect(page.getByRole('link', { name: /Endeavor\s+Velocity/i })).toBeVisible();
await expect(page.getByText('3 services loaded')).toBeVisible();

for (const tab of tabAssertions) {
  await page.getByRole('tab', { name: new RegExp(tab.name, 'i') }).click();
  await expect(page.getByText(new RegExp(tab.heading, 'i'))).toBeVisible();
}

await page.screenshot({ path: 'test-results/dashboard-smoke.png', fullPage: true });

console.log(`Dashboard smoke test passed for ${baseUrl}`);
console.log('Verified: branded shell, profile panel, WIP summary, and all six dashboard tabs.');
console.log('Screenshot: test-results/dashboard-smoke.png');

await browser.close();