/**
 * WIP Views API
 *
 * RTK Query endpoints for dashboard WIP view operations.
 * Uses the /api/wipViews endpoint.
 */

import { baseApi } from './baseApi';

export interface WipServiceView {
  id: number;
  serviceId: number;
  locationId: number;
  orderId: number;
  companyName: string;
  masterCompanyName: string;
  provisionerId: number | null;
  vertekProjectManagerId: number | null;
  clientProjectManager: string | null;
  provisioner: string | null;
  vertekProjectManager: string | null;
  clientOrderId: string | null;
  clientLocationId: string | null;
  locationName: string | null;
  address1: string | null;
  address2: string | null;
  city: string | null;
  stateProvince: string | null;
  clientServiceId: string | null;
  provider: string | null;
  serviceStatus: string | null;
  serviceType: string | null;
  active: boolean;
  dataProvisioningCompleteDate: string | null;
  serviceMrc: number | null;
  serviceBilledTo: string | null;
  completeDate: string | null;
  currentInventory: boolean;
}

export interface DashboardSearchParams {
  tenantNames?: string[];
  masterCompanyNames?: string[];
  companyNames?: string[];
  serviceTypes?: string[];
  providers?: string[];
  serviceBilledTos?: string[];
  allStatuses?: boolean;
}

function buildWipQueryString(params?: DashboardSearchParams): string {
  if (!params) return '';
  const parts: string[] = [];
  if (params.tenantNames?.length) params.tenantNames.forEach(v => parts.push(`tenantNames=${encodeURIComponent(v)}`));
  if (params.masterCompanyNames?.length) params.masterCompanyNames.forEach(v => parts.push(`masterCompanyNames=${encodeURIComponent(v)}`));
  if (params.companyNames?.length) params.companyNames.forEach(v => parts.push(`companyNames=${encodeURIComponent(v)}`));
  if (params.serviceTypes?.length) params.serviceTypes.forEach(v => parts.push(`serviceTypes=${encodeURIComponent(v)}`));
  if (params.providers?.length) params.providers.forEach(v => parts.push(`providers=${encodeURIComponent(v)}`));
  if (params.serviceBilledTos?.length) params.serviceBilledTos.forEach(v => parts.push(`serviceBilledTos=${encodeURIComponent(v)}`));
  if (params.allStatuses) parts.push('allStatuses=true');
  return parts.length ? '?' + parts.join('&') : '';
}

export const wipViewsApi = baseApi.injectEndpoints({
  endpoints: (builder) => ({
    getWipServices: builder.query<WipServiceView[], DashboardSearchParams | void>({
      query: (params) => `/wipViews/wipServices${buildWipQueryString(params || undefined)}`,
    }),

    getWipServiceJeops: builder.query<WipServiceView[], DashboardSearchParams | void>({
      query: (params) => `/wipViews/wipServiceJeops${buildWipQueryString(params || undefined)}`,
    }),

    getWipLocationJeops: builder.query<WipServiceView[], DashboardSearchParams | void>({
      query: (params) => `/wipViews/wipLocationJeops${buildWipQueryString(params || undefined)}`,
    }),

    getMonthlySpend: builder.query<WipServiceView[], DashboardSearchParams | void>({
      query: (params) => `/wipViews/monthlySpend${buildWipQueryString(params || undefined)}`,
    }),

    getIncrementalNetworkSpend: builder.query<WipServiceView[], DashboardSearchParams | void>({
      query: (params) => `/wipViews/incrementalNetworkSpend${buildWipQueryString(params || undefined)}`,
    }),

    getUnbillableNetworkExpenseAccrual: builder.query<WipServiceView[], DashboardSearchParams | void>({
      query: (params) => `/wipViews/unbillableNetworkExpenseAccrual${buildWipQueryString(params || undefined)}`,
    }),
  }),
});

export const {
  useGetWipServicesQuery,
  useGetWipServiceJeopsQuery,
  useGetWipLocationJeopsQuery,
  useGetMonthlySpendQuery,
  useGetIncrementalNetworkSpendQuery,
  useGetUnbillableNetworkExpenseAccrualQuery,
} = wipViewsApi;
