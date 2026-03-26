/**
 * Invoices API
 *
 * RTK Query endpoints for invoice operations.
 * Uses the /api/invoices endpoint.
 */

import { baseApi } from './baseApi';

export interface Invoice {
  id: number;
  clientName: string;
  invoiceNumber: string;
  invoiceStatus: string;
  totalCharges: number;
  invoiceStart: string | null;
  invoiceEnd: string | null;
  generatedBy: string;
  generatedDate: string | null;
  tenantId: number;
  version: number;
}

export interface InvoicePaginatedResult {
  collection: Invoice[];
  offset: number;
  limit: number;
  total: number;
}

export const invoicesApi = baseApi.injectEndpoints({
  endpoints: (builder) => ({
    searchInvoices: builder.query<InvoicePaginatedResult, { offset?: number; limit?: number; search?: string }>({
      query: ({ offset = 0, limit = 25, search } = {}) => {
        let url = `/invoices?offset=${offset}&limit=${limit}`;
        if (search) url += `&search=${encodeURIComponent(search)}`;
        return url;
      },
      providesTags: (result) =>
        result
          ? [
              ...result.collection.map(({ id }) => ({ type: 'Invoice' as const, id })),
              { type: 'Invoice', id: 'LIST' },
            ]
          : [{ type: 'Invoice', id: 'LIST' }],
    }),

    getInvoice: builder.query<Invoice, number>({
      query: (id) => `/invoices/${id}`,
      providesTags: (_result, _error, id) => [{ type: 'Invoice', id }],
    }),

    createInvoice: builder.mutation<Invoice, { client: string; body: Partial<Invoice> }>({
      query: ({ client, body }) => ({ url: `/invoices/${encodeURIComponent(client)}`, method: 'POST', body }),
      invalidatesTags: [{ type: 'Invoice', id: 'LIST' }],
    }),

    updateInvoice: builder.mutation<Invoice, { id: number; body: Partial<Invoice> }>({
      query: ({ id, body }) => ({ url: `/invoices/${id}`, method: 'PUT', body }),
      invalidatesTags: (_result, _error, { id }) => [{ type: 'Invoice', id }],
    }),

    finalizeInvoice: builder.mutation<Invoice, number>({
      query: (id) => ({ url: `/invoices/${id}/finalize`, method: 'PUT' }),
      invalidatesTags: (_result, _error, id) => [{ type: 'Invoice', id }],
    }),
  }),
});

export const {
  useSearchInvoicesQuery,
  useGetInvoiceQuery,
  useCreateInvoiceMutation,
  useUpdateInvoiceMutation,
  useFinalizeInvoiceMutation,
} = invoicesApi;
