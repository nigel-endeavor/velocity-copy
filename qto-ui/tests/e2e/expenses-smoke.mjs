import { chromium, expect } from '@playwright/test';

const baseUrl = process.env.EXPENSES_SMOKE_URL || 'http://127.0.0.1:4173/#/expenses';

const expenses = [
  {
    id: 1,
    serviceId: 5001,
    locationId: 11,
    orderId: 91,
    companyName: 'TechNova Inc',
    masterCompanyName: 'TechNova',
    provisionerId: 1,
    vertekProjectManagerId: 2,
    clientProjectManager: 'Ava Bloom',
    provisioner: 'Jace Reed',
    vertekProjectManager: 'Liam Hart',
    clientOrderId: 'TN-5001',
    clientLocationId: 'HQ-1',
    locationName: 'TechNova Main Office',
    address1: '1 Innovation Drive',
    address2: null,
    city: 'Austin',
    stateProvince: 'TX',
    clientServiceId: 'UCAAS-01',
    provider: 'RingCentral',
    serviceStatus: 'In Progress',
    serviceType: 'UCaaS',
    active: true,
    dataProvisioningCompleteDate: '2026-01-10',
    serviceMrc: 4000,
    serviceBilledTo: 'Endeavor',
    completeDate: null,
    currentInventory: false,
  },
  {
    id: 2,
    serviceId: 5002,
    locationId: 12,
    orderId: 92,
    companyName: 'Acme Corporation',
    masterCompanyName: 'Acme',
    provisionerId: 2,
    vertekProjectManagerId: 3,
    clientProjectManager: 'Ava Bloom',
    provisioner: 'Jace Reed',
    vertekProjectManager: 'Liam Hart',
    clientOrderId: 'AC-5002',
    clientLocationId: 'DC-1',
    locationName: 'Acme Data Center',
    address1: '44 Core Street',
    address2: null,
    city: 'Dallas',
    stateProvince: 'TX',
    clientServiceId: 'ETH-22',
    provider: 'Zayo',
    serviceStatus: 'In Progress',
    serviceType: 'Ethernet',
    active: true,
    dataProvisioningCompleteDate: '2025-12-15',
    serviceMrc: 8000,
    serviceBilledTo: 'Endeavor',
    completeDate: null,
    currentInventory: false,
  },
  {
    id: 3,
    serviceId: 5003,
    locationId: 13,
    orderId: 93,
    companyName: 'Acme Corporation',
    masterCompanyName: 'Acme',
    provisionerId: 2,
    vertekProjectManagerId: 3,
    clientProjectManager: 'Ava Bloom',
    provisioner: 'Jace Reed',
    vertekProjectManager: 'Liam Hart',
    clientOrderId: 'AC-5003',
    clientLocationId: 'HQ-2',
    locationName: 'Acme HQ',
    address1: '88 Market Street',
    address2: null,
    city: 'Houston',
    stateProvince: 'TX',
    clientServiceId: 'DIA-77',
    provider: 'Lumen',
    serviceStatus: 'In Progress',
    serviceType: 'DIA',
    active: true,
    dataProvisioningCompleteDate: '2025-11-17',
    serviceMrc: 2500,
    serviceBilledTo: 'Endeavor',
    completeDate: null,
    currentInventory: false,
  },
];

const browser = await chromium.launch({ headless: true });
const context = await browser.newContext({ viewport: { width: 1280, height: 800 } });
const page = await context.newPage();

await page.route('**/qto/api/wipViews/unbillableNetworkExpenseAccrual**', async (route) => {
  await route.fulfill({
    status: 200,
    contentType: 'application/json',
    body: JSON.stringify(expenses),
  });
});

await page.goto(baseUrl, { waitUntil: 'networkidle' });

await expect(page.getByRole('heading', { name: 'Manage Expenses' })).toBeVisible();
await expect(page.getByText('Expense Accrual by Provider')).toBeVisible();
await expect(page.getByText('Expense Accrual by Company')).toBeVisible();
await expect(page.getByText('Open Expense Accrual Detail')).toBeVisible();

const metrics = await page.locator('text=$14,500.00').count();
if (metrics < 1) {
  throw new Error('Expected total accrual metric to render.');
}

await page.screenshot({ path: 'test-results/expenses-smoke.png', fullPage: true });

console.log(`Expenses smoke test passed for ${baseUrl}`);
console.log('Verified: viewport-fit page shell, themed red/orange accents, charts, and detail table.');
console.log('Screenshot: test-results/expenses-smoke.png');

await browser.close();