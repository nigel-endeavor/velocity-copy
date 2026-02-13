/**
 * Services API
 *
 * RTK Query endpoints for service CRUD operations
 */

import { baseApi } from './baseApi';
import { Service } from '@/shared/types/models';
import { PaginatedResult, BaseSearchCriteria } from '@/shared/types/common';

/**
 * Service Search Criteria
 */
export interface ServiceSearchCriteria extends BaseSearchCriteria {
  orderId?: number;
  locationId?: number;
  companyId?: number;
  clientServiceId?: string;
  type?: string;
  status?: string;
  provider?: string;
  circuitId?: string;
}

/**
 * Services API Endpoints
 */
export const servicesApi = baseApi.injectEndpoints({
  endpoints: (builder) => ({
    /**
     * Get service by ID
     */
    getService: builder.query<Service, number>({
      query: (id) => `/services/${id}`,
      providesTags: (result, error, id) => [{ type: 'Service', id }],
    }),

    /**
     * Search services
     */
    searchServices: builder.query<PaginatedResult<Service>, ServiceSearchCriteria>({
      query: (criteria) => ({
        url: '/services/search',
        method: 'POST',
        body: criteria,
      }),
      providesTags: (result) =>
        result
          ? [
              ...result.content.map(({ id }) => ({ type: 'Service' as const, id })),
              { type: 'Service', id: 'LIST' },
            ]
          : [{ type: 'Service', id: 'LIST' }],
    }),

    /**
     * Get services by location ID
     */
    getServicesByLocation: builder.query<Service[], number>({
      query: (locationId) => `/services/by-location/${locationId}`,
      providesTags: (result, error, locationId) => [
        { type: 'Service', id: 'LIST' },
        { type: 'Location', id: locationId },
      ],
    }),

    /**
     * Get services by order ID
     */
    getServicesByOrder: builder.query<Service[], number>({
      query: (orderId) => `/services/by-order/${orderId}`,
      providesTags: (result, error, orderId) => [
        { type: 'Service', id: 'LIST' },
        { type: 'Order', id: orderId },
      ],
    }),

    /**
     * Create new service
     */
    createService: builder.mutation<Service, Partial<Service>>({
      query: (service) => ({
        url: '/services',
        method: 'POST',
        body: service,
      }),
      invalidatesTags: (result, error, service) => [
        { type: 'Service', id: 'LIST' },
        { type: 'Location', id: service.locationId },
        { type: 'Order', id: service.orderId },
      ],
    }),

    /**
     * Update existing service
     */
    updateService: builder.mutation<Service, { id: number; service: Partial<Service> }>({
      query: ({ id, service }) => ({
        url: `/services/${id}`,
        method: 'PUT',
        body: service,
      }),
      invalidatesTags: (result, error, { id, service }) => [
        { type: 'Service', id },
        { type: 'Service', id: 'LIST' },
        { type: 'Location', id: service.locationId },
        { type: 'Order', id: service.orderId },
      ],
    }),

    /**
     * Save service (create or update)
     */
    saveService: builder.mutation<Service, Service>({
      query: (service) => ({
        url: service.id ? `/services/${service.id}` : '/services',
        method: service.id ? 'PUT' : 'POST',
        body: service,
      }),
      invalidatesTags: (result, error, service) => [
        { type: 'Service', id: service.id },
        { type: 'Service', id: 'LIST' },
        { type: 'Location', id: service.locationId },
        { type: 'Order', id: service.orderId },
      ],
    }),

    /**
     * Delete service
     */
    deleteService: builder.mutation<void, number>({
      query: (id) => ({
        url: `/services/${id}`,
        method: 'DELETE',
      }),
      invalidatesTags: (result, error, id) => [
        { type: 'Service', id },
        { type: 'Service', id: 'LIST' },
      ],
    }),

    /**
     * Bulk update services
     */
    bulkUpdateServices: builder.mutation<Service[], { ids: number[]; updates: Partial<Service> }>({
      query: ({ ids, updates }) => ({
        url: '/services/bulk-update',
        method: 'POST',
        body: { ids, updates },
      }),
      invalidatesTags: [{ type: 'Service', id: 'LIST' }],
    }),
  }),
});

/**
 * Export hooks for use in components
 */
export const {
  useGetServiceQuery,
  useSearchServicesQuery,
  useGetServicesByLocationQuery,
  useGetServicesByOrderQuery,
  useCreateServiceMutation,
  useUpdateServiceMutation,
  useSaveServiceMutation,
  useDeleteServiceMutation,
  useBulkUpdateServicesMutation,
} = servicesApi;

export default servicesApi;
