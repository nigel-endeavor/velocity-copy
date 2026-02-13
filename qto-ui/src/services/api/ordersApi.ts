/**
 * Orders API
 *
 * RTK Query endpoints for order CRUD operations
 */

import { baseApi } from './baseApi';
import { Order } from '@/shared/types/models';
import { PaginatedResult, BaseSearchCriteria } from '@/shared/types/common';

/**
 * Order Search Criteria
 */
export interface OrderSearchCriteria extends BaseSearchCriteria {
  companyId?: number;
  companyName?: string;
  clientOrderId?: string;
  status?: string;
  provisioner?: string;
  startDate?: string;
  endDate?: string;
}

/**
 * Orders API Endpoints
 */
export const ordersApi = baseApi.injectEndpoints({
  endpoints: (builder) => ({
    /**
     * Get order by ID
     */
    getOrder: builder.query<Order, number>({
      query: (id) => `/orders/${id}`,
      providesTags: (result, error, id) => [{ type: 'Order', id }],
    }),

    /**
     * Search orders
     */
    searchOrders: builder.query<PaginatedResult<Order>, OrderSearchCriteria>({
      query: (criteria) => ({
        url: '/orders/search',
        method: 'POST',
        body: criteria,
      }),
      providesTags: (result) =>
        result
          ? [
              ...result.content.map(({ id }) => ({ type: 'Order' as const, id })),
              { type: 'Order', id: 'LIST' },
            ]
          : [{ type: 'Order', id: 'LIST' }],
    }),

    /**
     * Create new order
     */
    createOrder: builder.mutation<Order, Partial<Order>>({
      query: (order) => ({
        url: '/orders',
        method: 'POST',
        body: order,
      }),
      invalidatesTags: [{ type: 'Order', id: 'LIST' }],
    }),

    /**
     * Update existing order
     */
    updateOrder: builder.mutation<Order, { id: number; order: Partial<Order> }>({
      query: ({ id, order }) => ({
        url: `/orders/${id}`,
        method: 'PUT',
        body: order,
      }),
      invalidatesTags: (result, error, { id }) => [
        { type: 'Order', id },
        { type: 'Order', id: 'LIST' },
      ],
    }),

    /**
     * Save order (create or update)
     */
    saveOrder: builder.mutation<Order, Order>({
      query: (order) => ({
        url: order.id ? `/orders/${order.id}` : '/orders',
        method: order.id ? 'PUT' : 'POST',
        body: order,
      }),
      invalidatesTags: (result, error, order) => [
        { type: 'Order', id: order.id },
        { type: 'Order', id: 'LIST' },
      ],
    }),

    /**
     * Delete order
     */
    deleteOrder: builder.mutation<void, number>({
      query: (id) => ({
        url: `/orders/${id}`,
        method: 'DELETE',
      }),
      invalidatesTags: (result, error, id) => [
        { type: 'Order', id },
        { type: 'Order', id: 'LIST' },
      ],
    }),

    /**
     * Get order count by status
     */
    getOrderCountByStatus: builder.query<Record<string, number>, void>({
      query: () => '/orders/count-by-status',
      providesTags: [{ type: 'Order', id: 'STATS' }],
    }),
  }),
});

/**
 * Export hooks for use in components
 */
export const {
  useGetOrderQuery,
  useSearchOrdersQuery,
  useCreateOrderMutation,
  useUpdateOrderMutation,
  useSaveOrderMutation,
  useDeleteOrderMutation,
  useGetOrderCountByStatusQuery,
} = ordersApi;

export default ordersApi;
