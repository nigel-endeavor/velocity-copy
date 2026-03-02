/**
 * Base API Configuration
 *
 * RTK Query base API with:
 * - Authentication header injection
 * - Request/response interceptors
 * - Error handling
 * - Demo mode support
 */

import { createApi, fetchBaseQuery, BaseQueryFn, FetchArgs, FetchBaseQueryError } from '@reduxjs/toolkit/query/react';
import { getApiUrl } from '@/config/environment';
import type { RootState } from '@/store';

/**
 * Base Query with Authentication
 */
const baseQuery = fetchBaseQuery({
  baseUrl: getApiUrl(),
  prepareHeaders: async (headers, { getState }) => {
    const state = getState() as RootState;

    // Add demo mode headers
    if (state.demoMode.enabled) {
      headers.set('X-Demo-Mode', 'true');
    }

    // Add auth header when using JWT/session auth
    // headers.set('Authorization', `Bearer ${token}`);

    headers.set('Content-Type', 'application/json');
    return headers;
  },
  credentials: 'include', // Include cookies for session-based auth
});

/**
 * Base Query with Error Handling
 */
const baseQueryWithInterceptor: BaseQueryFn<
  string | FetchArgs,
  unknown,
  FetchBaseQueryError
> = async (args, api, extraOptions) => {
  const state = api.getState() as RootState;

  // Demo mode network delay
  if (state.demoMode.enabled && state.demoMode.networkDelay > 0) {
    await new Promise((resolve) => setTimeout(resolve, state.demoMode.networkDelay));
  }

  let result = await baseQuery(args, api, extraOptions);

  // Handle 401 Unauthorized - redirect to login
  if (result.error && result.error.status === 401) {
    console.error('Unauthorized - redirecting to login');
    // window.location.href = '/login';
  }

  // Handle 403 Forbidden
  if (result.error && result.error.status === 403) {
    console.error('Forbidden - insufficient permissions');
  }

  // Handle 500 Internal Server Error
  if (result.error && result.error.status === 500) {
    console.error('Internal Server Error');
  }

  return result;
};

/**
 * Base API
 *
 * All feature APIs will be injected into this base API using injectEndpoints.
 * This enables code splitting and keeps the base bundle small.
 */
export const baseApi = createApi({
  reducerPath: 'api',
  baseQuery: baseQueryWithInterceptor,
  tagTypes: [
    'Order',
    'Service',
    'Location',
    'Invoice',
    'Dispute',
    'Customer',
    'User',
    'Configuration',
  ],
  endpoints: () => ({}), // Endpoints will be injected by feature modules
});

/**
 * Export hooks (will be extended by feature modules)
 */
export const {} = baseApi;

export default baseApi;
