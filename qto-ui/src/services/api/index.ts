/**
 * API Index
 *
 * Central export for all API endpoints
 */

export * from './baseApi';
export * from './ordersApi';
export * from './servicesApi';
export * from './locationsApi';
export * from './quotesApi';
export * from './configurationApi';
export * from './locationInventoryViewsApi';
export * from './serviceInventoryViewsApi';
export * from './companyViewsApi';
export * from './companiesApi';
export * from './contactsApi';
export * from './invoicesApi';
export * from './invoiceChargesApi';
export * from './disputesApi';
export * from './disputeViewsApi';
export * from './wipViewsApi';

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

export {
  useGetWipServicesQuery,
  useGetWipServiceJeopsQuery,
  useGetWipLocationJeopsQuery,
  useGetMonthlySpendQuery,
  useGetIncrementalNetworkSpendQuery,
  useGetUnbillableNetworkExpenseAccrualQuery,
} from './wipViewsApi';
