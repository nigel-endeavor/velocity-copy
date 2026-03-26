/**
 * Service Views API
 *
 * RTK Query endpoints for service view operations.
 * Uses the /api/serviceViews endpoint backed by the v_manage_services database view.
 */

import { baseApi } from './baseApi';

/**
 * ServiceView matches the backend ServiceView entity (v_manage_services view)
 */
export interface ServiceView {
  id: number;
  locationId: number;
  orderId: number;
  clientLocationId: string;
  address: string;
  address1: string;
  address2: string;
  city: string;
  stateProvince: string;
  postalCode: string;
  status: string;
  subStatus: string;
  provisioner: string;
  projectManager: string;
  qaManager: string;
  provider: string;
  clientServiceId: string;
  mrc: number;
  nrc: number;
  mrr: number;
  nrr: number;
  progressPercentage: number;
  projectName: string;
  recordSource: string;
  customerRequestedInstall: string | null;
  siteSurveyDue: string | null;
  siteSurveySubmit: string | null;
  serviceBilledTo: string;
  providerOrderSubmitted: string | null;
  networkProviderFoc: string | null;
  dataProvisioningComplete: string | null;
  created: string | null;
  qaCheckOpen: string | null;
  firstVendorInvoice: string | null;
  returnedToOrderGroup: string | null;
  returnedToSales: string | null;
  billingReviewComplete: string | null;
  accessCircuitFoc: string | null;
  onHold: string | null;
  followUpDate: string | null;
  greatestMilestoneName: string;
  greatestMilestoneDate: string | null;
  clientLocationType: string;
  clientLocationInfo: string;
  companyName: string;
  companyId: number;
  parentCompanyName: string;
  speed: string;
  type: string;
  openJeop: string;
  latestNote: string;
  showJeopIcon: boolean;
  showNoteIcon: boolean;
  openJeopResponsibilities: string;
  vertekProjectManager: string;
  orderType: string;
  active: boolean;
  statusAge: number;
  lconPhone: string;
  levelOfEffort: string;
  linked: boolean;
  bundled: boolean;
  linkedBundledParent: boolean;
  linkedBundledParentId: number;
  showOpenDisconnectIcon: boolean;
  showOpenMacIcon: boolean;
}

/**
 * Backend PaginatedResult format
 */
export interface ServiceViewPaginatedResult {
  collection: ServiceView[];
  offset: number;
  limit: number;
  total: number;
}

/**
 * Service Views API Endpoints
 */
export const serviceViewsApi = baseApi.injectEndpoints({
  endpoints: (builder) => ({
    /**
     * List service views with pagination
     */
    listServiceViews: builder.query<ServiceViewPaginatedResult, { offset?: number; limit?: number }>({
      query: ({ offset = 0, limit = 25 } = {}) => `/serviceViews?offset=${offset}&limit=${limit}`,
      providesTags: (result) =>
        result
          ? [
              ...result.collection.map(({ id }) => ({ type: 'ServiceView' as const, id })),
              { type: 'ServiceView', id: 'LIST' },
            ]
          : [{ type: 'ServiceView', id: 'LIST' }],
    }),

    /**
     * Get service view by ID (uses the list endpoint with search)
     */
    getServiceView: builder.query<ServiceView, number>({
      query: (id) => `/serviceViews?serviceId=${id}&offset=0&limit=1`,
      transformResponse: (response: ServiceViewPaginatedResult) =>
        response.collection[0],
      providesTags: (_result, _error, id) => [{ type: 'ServiceView', id }],
    }),
  }),
});

export const {
  useListServiceViewsQuery,
  useGetServiceViewQuery,
} = serviceViewsApi;
