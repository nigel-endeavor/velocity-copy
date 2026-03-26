/**
 * Disputes API
 *
 * RTK Query endpoints for dispute CRUD operations.
 * Uses the /api/disputes endpoint.
 */

import { baseApi } from './baseApi';

export interface Dispute {
  id: number;
  serviceId: number;
  openDate: string | null;
  disputeStatus: string;
  disputeType: string;
  disputeAssignment: string;
  invoiceNum: string;
  amountDisputedMrc: number;
  amountDisputedNrc: number;
  vendorTrackingNum: string;
  disputeFollowUpDate: string | null;
  creditRecognized: string | null;
  billingReviewComplete: string | null;
  disputeClosedDate: string | null;
  realizedCredit: number;
  realizedMrcAdjustment: number;
  annualizedMrcSave: number;
  masterCustomerId: number;
  tenantId: number;
  version: number;
}

export interface DisputePaginatedResult {
  content: Dispute[];
  totalElements: number;
  pageNumber: number;
  pageSize: number;
}

interface BackendDisputeResult {
  collection: Dispute[];
  offset: number;
  limit: number;
  total: number;
}

export const disputesApi = baseApi.injectEndpoints({
  endpoints: (builder) => ({
    searchDisputes: builder.query<DisputePaginatedResult, Record<string, any>>({
      query: (params) => {
        const searchParams = new URLSearchParams();
        Object.entries(params).forEach(([key, value]) => {
          if (value !== undefined && value !== null && value !== '') {
            searchParams.set(key, String(value));
          }
        });
        return `/disputes?${searchParams.toString()}`;
      },
      transformResponse: (response: BackendDisputeResult): DisputePaginatedResult => ({
        content: response.collection,
        totalElements: response.total,
        pageNumber: Math.floor(response.offset / (response.limit || 25)),
        pageSize: response.limit,
      }),
      providesTags: (result) =>
        result
          ? [
              ...result.content.map(({ id }) => ({ type: 'Dispute' as const, id })),
              { type: 'Dispute', id: 'LIST' },
            ]
          : [{ type: 'Dispute', id: 'LIST' }],
    }),

    getDisputesByService: builder.query<Dispute[], number>({
      query: (serviceId) => `/disputes/service/${serviceId}`,
      providesTags: [{ type: 'Dispute', id: 'LIST' }],
    }),

    createDispute: builder.mutation<Dispute, Partial<Dispute>>({
      query: (body) => ({ url: '/disputes', method: 'POST', body }),
      invalidatesTags: [{ type: 'Dispute', id: 'LIST' }, { type: 'DisputeView', id: 'LIST' }],
    }),

    updateDispute: builder.mutation<Dispute, { id: number; body: Partial<Dispute> }>({
      query: ({ id, body }) => ({ url: `/disputes/${id}`, method: 'PUT', body }),
      invalidatesTags: (_result, _error, { id }) => [
        { type: 'Dispute', id },
        { type: 'DisputeView', id: 'LIST' },
      ],
    }),

    getDisputeMeta: builder.query<Record<string, any>, void>({
      query: () => '/disputes/meta',
    }),
  }),
});

export const {
  useSearchDisputesQuery,
  useGetDisputesByServiceQuery,
  useCreateDisputeMutation,
  useUpdateDisputeMutation,
  useGetDisputeMetaQuery,
} = disputesApi;
