/**
 * Service Inventory Views API
 *
 * RTK Query endpoints for service inventory view operations.
 * Uses the /api/serviceInventoryViews endpoint backed by the v_manage_service_inventory database view.
 */

import { baseApi } from './baseApi';

export interface ServiceInventoryView {
  id: number;
  locationId: number;
  orderId: number;
  orderType: string;
  clientLocationId: string;
  clientLocationInfo: string;
  clientLocationType: string;
  address: string;
  address1: string;
  address2: string;
  city: string;
  stateProvince: string;
  postalCode: string;
  status: string;
  provisioner: string;
  projectManager: string;
  provider: string;
  summaryBill: string;
  providerCircuitId: string;
  clientServiceId: string;
  companyName: string;
  companyId: number;
  endCustomerClientId: string;
  parentCompanyName: string;
  masterCustomerId: number;
  parentCompanyClientId: string;
  speed: string;
  type: string;
  serviceBilledTo: string;
  mrc: number;
  nrc: number;
  mrr: number;
  nrr: number;
  annualRecurringCost: number;
  contractSignedDate: string | null;
  contractTerm: string;
  circuitTermEndDate: string | null;
  inventoryAddedDate: string | null;
  accountNumber: string;
  active: boolean;
  billable: boolean;
  hasIcb: boolean;
  activeInactive: string;
  countOpenDisputes: number;
  openDisputeMrc: number;
  openDisputeNrc: number;
  disputeTypes: string;
  isMacd: number;
  subOrderType: string;
  linked: boolean;
  bundled: boolean;
  childIds: string;
  childOrderTypes: string;
  childSubOrderTypes: string;
  showOpenDisconnectIcon: boolean;
  showOpenMacIcon: boolean;
  showOpenDisputeIcon: boolean;
  tenantId: number;
  version: number;
}

export interface ServiceInventoryViewPaginatedResult {
  collection: ServiceInventoryView[];
  offset: number;
  limit: number;
  total: number;
}

export const serviceInventoryViewsApi = baseApi.injectEndpoints({
  endpoints: (builder) => ({
    listServiceInventoryViews: builder.query<ServiceInventoryViewPaginatedResult, { offset?: number; limit?: number }>({
      query: ({ offset = 0, limit = 25 } = {}) => `/serviceInventoryViews?offset=${offset}&limit=${limit}`,
      providesTags: (result) =>
        result
          ? [
              ...result.collection.map(({ id }) => ({ type: 'ServiceInventoryView' as const, id })),
              { type: 'ServiceInventoryView', id: 'LIST' },
            ]
          : [{ type: 'ServiceInventoryView', id: 'LIST' }],
    }),

    getServiceInventoryViewMeta: builder.query<Record<string, any>, void>({
      query: () => '/serviceInventoryViews/meta',
    }),
  }),
});

export const {
  useListServiceInventoryViewsQuery,
  useGetServiceInventoryViewMetaQuery,
} = serviceInventoryViewsApi;
