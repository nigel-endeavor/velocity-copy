/**
 * API Index
 *
 * Central export for all API endpoints
 */

export * from './baseApi';
export * from './ordersApi';
export * from './servicesApi';
export * from './locationsApi';

// Re-export hooks
export {
  useGetOrderQuery,
  useSearchOrdersQuery,
  useCreateOrderMutation,
  useUpdateOrderMutation,
  useSaveOrderMutation,
  useDeleteOrderMutation,
  useGetOrderCountByStatusQuery,
} from './ordersApi';

export {
  useGetServiceQuery,
  useSearchServicesQuery,
  useGetServicesByLocationQuery,
  useGetServicesByOrderQuery,
  useCreateServiceMutation,
  useUpdateServiceMutation,
  useSaveServiceMutation,
  useDeleteServiceMutation,
  useBulkUpdateServicesMutation,
} from './servicesApi';

export {
  useGetLocationQuery,
  useSearchLocationsQuery,
  useGetLocationsByOrderQuery,
  useCreateLocationMutation,
  useUpdateLocationMutation,
  useSaveLocationMutation,
  useDeleteLocationMutation,
} from './locationsApi';
