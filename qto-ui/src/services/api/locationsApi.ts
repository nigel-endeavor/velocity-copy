/**
 * Locations API
 *
 * RTK Query endpoints for location CRUD operations
 */

import { baseApi } from './baseApi';
import { Location } from '@/shared/types/models';
import { PaginatedResult, BaseSearchCriteria } from '@/shared/types/common';

/**
 * Location Search Criteria
 */
export interface LocationSearchCriteria extends BaseSearchCriteria {
  orderId?: number;
  companyId?: number;
  clientLocationId?: string;
  status?: string;
  city?: string;
  state?: string;
}

/**
 * Locations API Endpoints
 */
export const locationsApi = baseApi.injectEndpoints({
  endpoints: (builder) => ({
    /**
     * Get location by ID
     */
    getLocation: builder.query<Location, number>({
      query: (id) => `/locations/${id}`,
      providesTags: (result, error, id) => [{ type: 'Location', id }],
    }),

    /**
     * Search locations
     */
    searchLocations: builder.query<PaginatedResult<Location>, LocationSearchCriteria>({
      query: (criteria) => ({
        url: '/locations/search',
        method: 'POST',
        body: criteria,
      }),
      transformResponse: (response: { collection: Location[]; offset: number; limit: number; total: number }): PaginatedResult<Location> => ({
        content: response.collection,
        totalElements: response.total,
        totalPages: Math.ceil(response.total / (response.limit || 25)),
        pageNumber: Math.floor(response.offset / (response.limit || 25)),
        pageSize: response.limit,
        first: response.offset === 0,
        last: response.offset + response.limit >= response.total,
        empty: response.collection.length === 0,
      }),
      providesTags: (result) =>
        result
          ? [
              ...result.content.map(({ id }) => ({ type: 'Location' as const, id })),
              { type: 'Location', id: 'LIST' },
            ]
          : [{ type: 'Location', id: 'LIST' }],
    }),

    /**
     * Get locations by order ID
     */
    getLocationsByOrder: builder.query<Location[], number>({
      query: (orderId) => `/locations/by-order/${orderId}`,
      providesTags: (result, error, orderId) => [
        { type: 'Location', id: 'LIST' },
        { type: 'Order', id: orderId },
      ],
    }),

    /**
     * Create new location
     */
    createLocation: builder.mutation<Location, Partial<Location>>({
      query: (location) => ({
        url: '/locations',
        method: 'POST',
        body: location,
      }),
      invalidatesTags: (result, error, location) => [
        { type: 'Location', id: 'LIST' },
        { type: 'Order', id: location.orderId },
      ],
    }),

    /**
     * Update existing location
     */
    updateLocation: builder.mutation<Location, { id: number; location: Partial<Location> }>({
      query: ({ id, location }) => ({
        url: `/locations/${id}`,
        method: 'PUT',
        body: location,
      }),
      invalidatesTags: (result, error, { id, location }) => [
        { type: 'Location', id },
        { type: 'Location', id: 'LIST' },
        { type: 'Order', id: location.orderId },
      ],
    }),

    /**
     * Save location (create or update)
     */
    saveLocation: builder.mutation<Location, Location>({
      query: (location) => ({
        url: location.id ? `/locations/${location.id}` : '/locations',
        method: location.id ? 'PUT' : 'POST',
        body: location,
      }),
      invalidatesTags: (result, error, location) => [
        { type: 'Location', id: location.id },
        { type: 'Location', id: 'LIST' },
        { type: 'Order', id: location.orderId },
      ],
    }),

    /**
     * Delete location
     */
    deleteLocation: builder.mutation<void, number>({
      query: (id) => ({
        url: `/locations/${id}`,
        method: 'DELETE',
      }),
      invalidatesTags: (result, error, id) => [
        { type: 'Location', id },
        { type: 'Location', id: 'LIST' },
      ],
    }),
  }),
});

/**
 * Export hooks for use in components
 */
export const {
  useGetLocationQuery,
  useSearchLocationsQuery,
  useGetLocationsByOrderQuery,
  useCreateLocationMutation,
  useUpdateLocationMutation,
  useSaveLocationMutation,
  useDeleteLocationMutation,
} = locationsApi;

export default locationsApi;
