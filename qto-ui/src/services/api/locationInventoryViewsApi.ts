/**
 * Location Inventory Views API
 *
 * RTK Query endpoints for location inventory view operations.
 * Uses the /api/locationInventoryViews endpoint backed by the v_manage_location_inventory database view.
 */

import { baseApi } from './baseApi';

export interface LocationInventoryView {
  id: number;
  orderId: number;
  companyName: string;
  companyId: number;
  endCustomerClientId: string;
  parentCompanyName: string;
  masterCustomerId: number;
  parentCompanyClientId: string;
  provisioner: string;
  clientProjectManager: string;
  vertekProjectManager: string;
  clientOrderId: string;
  clientLocationId: string;
  clientLocationInfo: string;
  clientLocationType: string;
  locationName: string;
  locationStatus: string;
  countServices: number;
  countActiveServices: number;
  countInactiveServices: number;
  services: string;
  progressPercentage: number;
  address: string;
  address1: string;
  address2: string;
  city: string;
  stateProvince: string;
  postalCode: string;
  tenantId: number;
  version: number;
  active: boolean;
  countServicesComplete: number;
  countServicesCancelled: number;
  countServicesChangeInAssignment: number;
  activeCompleteMrc: number;
  activeCompleteNrc: number;
  activeCompleteMrr: number;
  activeCompleteNrr: number;
  annualRecurringCost: number;
  countOpenDisputes: number;
  openDisputeMrc: number;
  openDisputeNrc: number;
  macdCount: number;
  activeInactive: string;
  inventoryAddedDate: string | null;
  subOrderTypes: string;
  showOpenDisconnectIcon: boolean;
  showOpenMacIcon: boolean;
  showOpenDisputeIcon: boolean;
  showLinkedIcon: boolean;
  showBundledIcon: boolean;
}

export interface LocationInventoryViewPaginatedResult {
  collection: LocationInventoryView[];
  offset: number;
  limit: number;
  total: number;
}

export const locationInventoryViewsApi = baseApi.injectEndpoints({
  endpoints: (builder) => ({
    listLocationInventoryViews: builder.query<LocationInventoryViewPaginatedResult, { offset?: number; limit?: number }>({
      query: ({ offset = 0, limit = 25 } = {}) => `/locationInventoryViews?offset=${offset}&limit=${limit}`,
      providesTags: (result) =>
        result
          ? [
              ...result.collection.map(({ id }) => ({ type: 'LocationInventoryView' as const, id })),
              { type: 'LocationInventoryView', id: 'LIST' },
            ]
          : [{ type: 'LocationInventoryView', id: 'LIST' }],
    }),

    getLocationInventoryViewMeta: builder.query<Record<string, any>, void>({
      query: () => '/locationInventoryViews/meta',
    }),
  }),
});

export const {
  useListLocationInventoryViewsQuery,
  useGetLocationInventoryViewMetaQuery,
} = locationInventoryViewsApi;
