import { mkdirSync } from 'node:fs';
import { chromium, expect } from '@playwright/test';

const appBaseUrl = process.env.FEATURE_PAGES_SMOKE_URL || 'http://127.0.0.1:4173/#';
const screenshotDir = 'test-results/feature-pages';

mkdirSync(screenshotDir, { recursive: true });

const serviceViews = [
  {
    id: 1,
    locationId: 4001,
    orderId: 9101,
    clientLocationId: 'AUS-HQ',
    address: '100 Congress Ave, Austin, TX',
    address1: '100 Congress Ave',
    address2: null,
    city: 'Austin',
    stateProvince: 'TX',
    postalCode: '78701',
    status: 'In Progress',
    subStatus: 'Awaiting FOC',
    provisioner: 'Jordan Blake',
    projectManager: 'Nina Vega',
    qaManager: 'Kai Monroe',
    provider: 'Endeavor Fiber',
    clientServiceId: 'SVC-4001',
    mrc: 1450,
    nrc: 550,
    mrr: 0,
    nrr: 0,
    progressPercentage: 72,
    projectName: 'Austin Refresh',
    recordSource: 'velocity',
    customerRequestedInstall: null,
    siteSurveyDue: null,
    siteSurveySubmit: null,
    serviceBilledTo: 'Arck University',
    providerOrderSubmitted: null,
    networkProviderFoc: null,
    dataProvisioningComplete: null,
    created: '2026-02-01',
    qaCheckOpen: null,
    firstVendorInvoice: null,
    returnedToOrderGroup: null,
    returnedToSales: null,
    billingReviewComplete: null,
    accessCircuitFoc: null,
    onHold: null,
    followUpDate: null,
    greatestMilestoneName: 'Install booked',
    greatestMilestoneDate: '2026-02-14',
    clientLocationType: 'Campus',
    clientLocationInfo: 'HQ',
    companyName: 'Arck University',
    companyId: 11,
    parentCompanyName: 'Arck Group',
    speed: '1 Gbps',
    type: 'DIA',
    openJeop: '',
    latestNote: 'Provider confirmed construction complete.',
    showJeopIcon: false,
    showNoteIcon: true,
    openJeopResponsibilities: '',
    vertekProjectManager: 'Nina Vega',
    orderType: 'New',
    active: true,
    statusAge: 8,
    lconPhone: '555-0111',
    levelOfEffort: 'Standard',
    linked: false,
    bundled: false,
    linkedBundledParent: false,
    linkedBundledParentId: 0,
    showOpenDisconnectIcon: false,
    showOpenMacIcon: false,
  },
  {
    id: 2,
    locationId: 4002,
    orderId: 9102,
    clientLocationId: 'DAL-EDGE',
    address: '8 Loop Plaza, Dallas, TX',
    address1: '8 Loop Plaza',
    address2: null,
    city: 'Dallas',
    stateProvince: 'TX',
    postalCode: '75201',
    status: 'Complete',
    subStatus: 'Closed',
    provisioner: 'Tara Ross',
    projectManager: 'Kai Monroe',
    qaManager: 'Ella Hudson',
    provider: 'BlueWire Telecom',
    clientServiceId: 'SVC-4002',
    mrc: 980,
    nrc: 120,
    mrr: 0,
    nrr: 0,
    progressPercentage: 100,
    projectName: 'Dallas Upgrade',
    recordSource: 'velocity',
    customerRequestedInstall: null,
    siteSurveyDue: null,
    siteSurveySubmit: null,
    serviceBilledTo: 'Arck University',
    providerOrderSubmitted: null,
    networkProviderFoc: null,
    dataProvisioningComplete: '2026-01-10',
    created: '2026-01-01',
    qaCheckOpen: null,
    firstVendorInvoice: null,
    returnedToOrderGroup: null,
    returnedToSales: null,
    billingReviewComplete: null,
    accessCircuitFoc: null,
    onHold: null,
    followUpDate: null,
    greatestMilestoneName: 'Billing clean',
    greatestMilestoneDate: '2026-01-21',
    clientLocationType: 'Branch',
    clientLocationInfo: 'Edge',
    companyName: 'Arck University',
    companyId: 11,
    parentCompanyName: 'Arck Group',
    speed: '500 Mbps',
    type: 'VoIP',
    openJeop: '',
    latestNote: 'Closed with QA approval.',
    showJeopIcon: false,
    showNoteIcon: true,
    openJeopResponsibilities: '',
    vertekProjectManager: 'Kai Monroe',
    orderType: 'MAC',
    active: true,
    statusAge: 0,
    lconPhone: '555-0112',
    levelOfEffort: 'High',
    linked: false,
    bundled: false,
    linkedBundledParent: false,
    linkedBundledParentId: 0,
    showOpenDisconnectIcon: false,
    showOpenMacIcon: false,
  },
];

const orderViews = {
  collection: [
    {
      id: 501,
      clientOrderId: 'AR-501',
      companyName: 'Arck University',
      status: 'In Progress',
      vertekClient: 'Higher Education',
      locationCount: 8,
      mrc: 14500,
      nrc: 2200,
    },
    {
      id: 502,
      clientOrderId: 'AR-502',
      companyName: 'Nova Health',
      status: 'New Order',
      vertekClient: 'Healthcare',
      locationCount: 3,
      mrc: 6200,
      nrc: 980,
    },
  ],
  offset: 0,
  limit: 25,
  total: 2,
};

const quotes = {
  collection: [
    {
      id: 77,
      vendorQuoteId: 'CB-98001',
      accountName: 'Arck University',
      userEmail: 'ops@arck.edu',
      userName: 'Ella Hudson',
      orderId: 501,
      quoteNumber: 'Q-2026-001',
      handledTime: '2026-03-01T12:00:00Z',
      quoteProvider: 'Endeavor Fiber',
    },
    {
      id: 78,
      vendorQuoteId: 'CB-98002',
      accountName: 'Nova Health',
      userEmail: 'ops@novahealth.com',
      userName: 'Tara Ross',
      orderId: null,
      quoteNumber: 'Q-2026-002',
      handledTime: null,
      quoteProvider: 'BlueWire Telecom',
    },
  ],
  offset: 0,
  limit: 25,
  total: 2,
};

const companies = {
  master: {
    collection: [
      {
        id: 11,
        name: 'Arck Group',
        type: 'MASTER_CUSTOMER',
        active: true,
        uuid: 'master-11',
        clientId: 'MC-11',
        legacyId: 11,
        billingContactName: 'Sam Carter',
        billingContactEmail: 'billing@arckgroup.com',
        billingContactPhone: '555-0140',
        tenantName: 'Arck',
        inventoryLocationCount: 25,
        inventoryMrc: 24800,
        inventoryMrr: 0,
        inventoryNrr: 0,
        taskGroupId: 1,
        status: 'active',
        lastCompletedTask: 'Contract signed',
        nextTask: 'Provider acceptance',
        nextTaskAssignedTo: 'Jordan Blake',
        remainingTasks: 5,
        progressPercentage: 81,
        accountManager: 'Ava Bloom',
        masterCustomerId: 11,
        tenantId: 1,
        version: 1,
      },
    ],
    offset: 0,
    limit: 25,
    total: 1,
  },
  end: {
    collection: [
      {
        id: 21,
        name: 'Arck University',
        type: 'END_CUSTOMER',
        active: true,
        uuid: 'end-21',
        clientId: 'EC-21',
        legacyId: 21,
        billingContactName: 'Mia Torres',
        billingContactEmail: 'finance@arck.edu',
        billingContactPhone: '555-0141',
        tenantName: 'Arck',
        inventoryLocationCount: 8,
        inventoryMrc: 14500,
        inventoryMrr: 0,
        inventoryNrr: 0,
        taskGroupId: 1,
        status: 'onboarding',
        lastCompletedTask: 'Design review',
        nextTask: 'Install coordination',
        nextTaskAssignedTo: 'Nina Vega',
        remainingTasks: 3,
        progressPercentage: 67,
        accountManager: 'Ava Bloom',
        masterCustomerId: 11,
        tenantId: 1,
        version: 1,
      },
    ],
    offset: 0,
    limit: 25,
    total: 1,
  },
};

const locationInventory = {
  collection: [
    {
      id: 801,
      orderId: 501,
      companyName: 'Arck University',
      companyId: 21,
      endCustomerClientId: 'EC-21',
      parentCompanyName: 'Arck Group',
      masterCustomerId: 11,
      parentCompanyClientId: 'MC-11',
      provisioner: 'Jordan Blake',
      clientProjectManager: 'Ella Hudson',
      vertekProjectManager: 'Nina Vega',
      clientOrderId: 'AR-501',
      clientLocationId: 'AUS-HQ',
      clientLocationInfo: 'HQ',
      clientLocationType: 'Campus',
      locationName: 'Austin HQ',
      locationStatus: 'Active',
      countServices: 3,
      countActiveServices: 3,
      countInactiveServices: 0,
      services: 'DIA, Voice',
      progressPercentage: 86,
      address: '100 Congress Ave, Austin, TX',
      address1: '100 Congress Ave',
      address2: null,
      city: 'Austin',
      stateProvince: 'TX',
      postalCode: '78701',
      tenantId: 1,
      version: 1,
      active: true,
      countServicesComplete: 2,
      countServicesCancelled: 0,
      countServicesChangeInAssignment: 0,
      activeCompleteMrc: 14500,
      activeCompleteNrc: 2200,
      activeCompleteMrr: 0,
      activeCompleteNrr: 0,
      annualRecurringCost: 174000,
      countOpenDisputes: 0,
      openDisputeMrc: 0,
      openDisputeNrc: 0,
      macdCount: 0,
      activeInactive: 'Active',
      inventoryAddedDate: '2026-02-10',
      subOrderTypes: 'New',
      showOpenDisconnectIcon: false,
      showOpenMacIcon: false,
      showOpenDisputeIcon: false,
      showLinkedIcon: false,
      showBundledIcon: false,
    },
  ],
  offset: 0,
  limit: 25,
  total: 1,
};

const serviceInventory = {
  collection: [
    {
      id: 901,
      locationId: 801,
      orderId: 501,
      orderType: 'New',
      clientLocationId: 'AUS-HQ',
      clientLocationInfo: 'HQ',
      clientLocationType: 'Campus',
      address: '100 Congress Ave, Austin, TX',
      address1: '100 Congress Ave',
      address2: null,
      city: 'Austin',
      stateProvince: 'TX',
      postalCode: '78701',
      status: 'In Progress',
      provisioner: 'Jordan Blake',
      projectManager: 'Nina Vega',
      provider: 'Endeavor Fiber',
      summaryBill: 'No',
      providerCircuitId: 'EF-2001',
      clientServiceId: 'SVC-4001',
      companyName: 'Arck University',
      companyId: 21,
      endCustomerClientId: 'EC-21',
      parentCompanyName: 'Arck Group',
      masterCustomerId: 11,
      parentCompanyClientId: 'MC-11',
      speed: '1 Gbps',
      type: 'DIA',
      serviceBilledTo: 'Arck University',
      mrc: 1450,
      nrc: 550,
      mrr: 0,
      nrr: 0,
      annualRecurringCost: 17400,
      contractSignedDate: '2026-01-12',
      contractTerm: '36 months',
      circuitTermEndDate: '2029-01-12',
      inventoryAddedDate: '2026-02-10',
      accountNumber: 'BAN-1001',
      active: true,
      billable: true,
      hasIcb: false,
      activeInactive: 'Active',
      countOpenDisputes: 0,
      openDisputeMrc: 0,
      openDisputeNrc: 0,
      disputeTypes: '',
      isMacd: 0,
      subOrderType: 'New',
      linked: false,
      bundled: false,
      childIds: '',
      childOrderTypes: '',
      childSubOrderTypes: '',
      showOpenDisconnectIcon: false,
      showOpenMacIcon: false,
      showOpenDisputeIcon: false,
      tenantId: 1,
      version: 1,
    },
  ],
  offset: 0,
  limit: 25,
  total: 1,
};

const invoices = {
  collection: [
    {
      id: 3001,
      clientName: 'Arck University',
      invoiceNumber: 'INV-3001',
      invoiceStatus: 'Finalized',
      totalCharges: 19450,
      invoiceStart: '2026-02-01',
      invoiceEnd: '2026-02-28',
      generatedBy: 'Ava Bloom',
      generatedDate: '2026-03-01',
      tenantId: 1,
      version: 1,
    },
  ],
  offset: 0,
  limit: 25,
  total: 1,
};

const dashboardWip = [
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

function getWipResponse(url) {
  if (url.includes('unbillableNetworkExpenseAccrual')) {
    return expenses;
  }

  if (url.includes('wipServiceJeops')) {
    return [dashboardWip[0]];
  }

  if (url.includes('wipLocationJeops')) {
    return [dashboardWip[2]];
  }

  return dashboardWip;
}

const routes = [
  { hash: '/', heading: 'QTO Application', file: 'home.png' },
  { hash: '/services', heading: 'Service Worklist', file: 'services.png' },
  { hash: '/orders', heading: 'Orders', file: 'orders.png' },
  { hash: '/quotes', heading: 'Quote Management', file: 'quotes.png' },
  { hash: '/inventory', heading: 'Network Inventory', file: 'inventory.png' },
  { hash: '/customers', heading: 'Customer Management', file: 'customers.png' },
  { hash: '/invoicing', heading: 'Invoicing', file: 'invoicing.png' },
  { hash: '/expenses', heading: 'Manage Expenses', file: 'expenses.png' },
  { hash: '/dashboard', heading: 'Service delivery dashboard', file: 'dashboard.png' },
];

const browser = await chromium.launch({ headless: true });
const context = await browser.newContext({ viewport: { width: 1600, height: 1024 } });

await context.route('**/*', async (route) => {
  const url = route.request().url();

  if (url.includes('/serviceViews?')) {
    await route.fulfill({ status: 200, contentType: 'application/json', body: JSON.stringify({ collection: serviceViews, offset: 0, limit: 25, total: serviceViews.length }) });
    return;
  }

  if (url.includes('/orderViews?')) {
    await route.fulfill({ status: 200, contentType: 'application/json', body: JSON.stringify(orderViews) });
    return;
  }

  if (url.includes('/quotes?')) {
    await route.fulfill({ status: 200, contentType: 'application/json', body: JSON.stringify(quotes) });
    return;
  }

  if (url.includes('/companyViews?')) {
    const body = url.includes('END_CUSTOMER') ? companies.end : companies.master;
    await route.fulfill({ status: 200, contentType: 'application/json', body: JSON.stringify(body) });
    return;
  }

  if (url.includes('/locationInventoryViews?')) {
    await route.fulfill({ status: 200, contentType: 'application/json', body: JSON.stringify(locationInventory) });
    return;
  }

  if (url.includes('/serviceInventoryViews?')) {
    await route.fulfill({ status: 200, contentType: 'application/json', body: JSON.stringify(serviceInventory) });
    return;
  }

  if (url.includes('/invoices?')) {
    await route.fulfill({ status: 200, contentType: 'application/json', body: JSON.stringify(invoices) });
    return;
  }

  if (url.includes('/wipViews/')) {
    await route.fulfill({ status: 200, contentType: 'application/json', body: JSON.stringify(getWipResponse(url)) });
    return;
  }

  if (url.includes('/services?page=0&size=1')) {
    await route.fulfill({ status: 200, contentType: 'application/json', body: JSON.stringify({ content: serviceViews.slice(0, 1) }) });
    return;
  }

  await route.continue();
});

for (const target of routes) {
  const page = await context.newPage();
  await page.goto(`${appBaseUrl}${target.hash}`, { waitUntil: 'networkidle' });
  await expect(page.getByRole('heading', { name: new RegExp(target.heading, 'i') })).toBeVisible();
  await page.screenshot({ path: `${screenshotDir}/${target.file}` });
  await page.close();
}

console.log(`Feature page smoke passed for ${routes.length} routes.`);
console.log(`Screenshots saved to ${screenshotDir}`);

await browser.close();