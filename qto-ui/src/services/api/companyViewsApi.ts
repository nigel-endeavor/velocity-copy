/**
 * Company Views API
 *
 * RTK Query endpoints for company view operations.
 * Uses the /api/companyViews endpoint backed by the v_company database view.
 */

import { baseApi } from './baseApi';

export interface CompanyView {
  id: number;
  name: string;
  type: string;
  active: boolean;
  uuid: string;
  clientId: string;
  legacyId: number;
  billingContactName: string;
  billingContactEmail: string;
  billingContactPhone: string;
  tenantName: string;
  inventoryLocationCount: number;
  inventoryMrc: number;
  inventoryMrr: number;
  inventoryNrr: number;
  taskGroupId: number;
  status: string;
  lastCompletedTask: string;
  nextTask: string;
  nextTaskAssignedTo: string;
  remainingTasks: number;
  progressPercentage: number;
  accountManager: string;
  masterCustomerId: number;
  tenantId: number;
  version: number;
}

export interface CompanyViewPaginatedResult {
  collection: CompanyView[];
  offset: number;
  limit: number;
  total: number;
}

export const companyViewsApi = baseApi.injectEndpoints({
  endpoints: (builder) => ({
    listCompanyViews: builder.query<CompanyViewPaginatedResult, { offset?: number; limit?: number; type?: string }>({
      query: ({ offset = 0, limit = 25, type } = {}) => {
        let url = `/companyViews?offset=${offset}&limit=${limit}`;
        if (type) url += `&type=${type}`;
        return url;
      },
      providesTags: (result) =>
        result
          ? [
              ...result.collection.map(({ id }) => ({ type: 'CompanyView' as const, id })),
              { type: 'CompanyView', id: 'LIST' },
            ]
          : [{ type: 'CompanyView', id: 'LIST' }],
    }),

    getCompanyViewMeta: builder.query<Record<string, any>, void>({
      query: () => '/companyViews/meta',
    }),
  }),
});

export const {
  useListCompanyViewsQuery,
  useGetCompanyViewMetaQuery,
} = companyViewsApi;
