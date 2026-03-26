/**
 * Dispute Views API
 *
 * RTK Query endpoints for dispute view operations.
 * Uses the /api/disputeViews endpoint backed by the v_manage_disputes database view.
 */

import { baseApi } from './baseApi';

export interface DisputeView {
  id: number;
  serviceId: number;
  locationId: number;
  orderId: number;
  clientLocationId: string;
  parentCompanyName: string;
  masterCustomerId: number;
  parentCompanyClientId: string;
  companyName: string;
  companyId: number;
  endCustomerClientId: string;
  address: string;
  address1: string;
  address2: string;
  city: string;
  stateProvince: string;
  postalCode: string;
  serviceType: string;
  disputeStatus: string;
  disputeType: string;
  disputeAssignment: string;
  provider: string;
  clientServiceId: string;
  serviceBilledTo: string;
  amountDisputedMrc: number;
  amountDisputedNrc: number;
  providerCircuitId: string;
  summaryBill: string;
  openDate: string | null;
  disputeFollowUpDate: string | null;
  creditRecognized: string | null;
  billingReviewCompleteDate: string | null;
  disputeClosedDate: string | null;
  invoiceNum: string;
  vendorTrackingNum: string;
  realizedCredit: number;
  realizedMrcAdjustment: number;
  annualizedMrcSave: number;
  serviceMrc: number;
  serviceNrc: number;
  speed: string;
  hasIcb: boolean;
  serviceActive: boolean;
  latestNote: string;
  showDisputeFollowUpIcon: boolean;
  tenantId: number;
  version: number;
}

export interface DisputeViewPaginatedResult {
  collection: DisputeView[];
  offset: number;
  limit: number;
  total: number;
}

export const disputeViewsApi = baseApi.injectEndpoints({
  endpoints: (builder) => ({
    listDisputeViews: builder.query<DisputeViewPaginatedResult, { offset?: number; limit?: number }>({
      query: ({ offset = 0, limit = 25 } = {}) => `/disputeViews?offset=${offset}&limit=${limit}`,
      providesTags: (result) =>
        result
          ? [
              ...result.collection.map(({ id }) => ({ type: 'DisputeView' as const, id })),
              { type: 'DisputeView', id: 'LIST' },
            ]
          : [{ type: 'DisputeView', id: 'LIST' }],
    }),

    getDisputeViewMeta: builder.query<Record<string, any>, void>({
      query: () => '/disputeViews/meta',
    }),
  }),
});

export const {
  useListDisputeViewsQuery,
  useGetDisputeViewMetaQuery,
} = disputeViewsApi;
