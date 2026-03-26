/**
 * Invoice Charges API
 *
 * RTK Query endpoints for invoice charge operations.
 * Uses the /api/invoiceCharges endpoint.
 */

import { baseApi } from './baseApi';

export interface InvoiceCharge {
  id: number;
  invoiceId: number;
  locationId: number;
  chargeCredit: string;
  unitCost: number;
  previouslyBilled: number;
  invoicedAmount: number;
  itemDesc: string;
  chargeDesc: string;
  chargeType: string;
  chargeLevel: string;
  masterCustomerName: string;
  endCustomerName: string;
  billableEventMilestoneDescription: string;
  billableEventDate: string | null;
  milestoneInstanceId: number;
  tenantId: number;
  version: number;
}

export interface InvoiceChargePaginatedResult {
  collection: InvoiceCharge[];
  offset: number;
  limit: number;
  total: number;
}

export const invoiceChargesApi = baseApi.injectEndpoints({
  endpoints: (builder) => ({
    getInvoiceCharges: builder.query<InvoiceChargePaginatedResult, { invoiceId: number; offset?: number; limit?: number }>({
      query: ({ invoiceId, offset = 0, limit = 100 }) =>
        `/invoiceCharges?invoiceId=${invoiceId}&offset=${offset}&limit=${limit}`,
      providesTags: (result) =>
        result
          ? [
              ...result.collection.map(({ id }) => ({ type: 'InvoiceCharge' as const, id })),
              { type: 'InvoiceCharge', id: 'LIST' },
            ]
          : [{ type: 'InvoiceCharge', id: 'LIST' }],
    }),
  }),
});

export const {
  useGetInvoiceChargesQuery,
} = invoiceChargesApi;
