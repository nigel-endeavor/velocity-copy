/**
 * Quotes API
 *
 * RTK Query endpoints for quote management
 */

import { baseApi } from './baseApi';
import type { BackendPaginatedResult } from './ordersApi';

export interface Quote {
  id: number;
  vendorQuoteId: string;
  accountId?: number;
  accountName?: string;
  userEmail?: string;
  userName?: string;
  orderId?: number;
  quoteNumber?: string;
  handledTime?: string;
  tenantId?: number;
  quoteProvider?: string;
  solutions?: QuoteSolution[];
}

export interface QuoteSolution {
  id: number;
  locationId?: string;
  solutionType?: string;
  provider?: string;
  mrc?: number;
  nrc?: number;
}

export const quotesApi = baseApi.injectEndpoints({
  endpoints: (builder) => ({
    listQuotes: builder.query<BackendPaginatedResult<Quote>, { offset?: number; limit?: number }>({
      query: ({ offset = 0, limit = 25 } = {}) => ({
        url: '/quotes',
        params: { offset, limit },
      }),
      providesTags: ['Quote'],
    }),

    getQuote: builder.query<Quote, number>({
      query: (id) => `/quotes/${id}`,
      providesTags: (_result, _error, id) => [{ type: 'Quote', id }],
    }),
  }),
});

export const { useListQuotesQuery, useGetQuoteQuery } = quotesApi;

export default quotesApi;
