import { baseApi } from './baseApi';
import type { LookupType, LookupValue } from '@/shared/types/models';

export interface BackendCollectionResult<T> {
  collection: T[];
  offset: number;
  limit: number;
  total: number;
}

export interface LookupTypeSearchParams {
  offset?: number;
  limit?: number;
}

export interface LookupValueSearchParams {
  typeCode: string;
  offset?: number;
  limit?: number;
  parentId?: number;
  companyId?: number;
  active?: boolean;
}

export const configurationApi = baseApi.injectEndpoints({
  endpoints: (builder) => ({
    searchLookupTypes: builder.query<BackendCollectionResult<LookupType>, LookupTypeSearchParams | void>({
      query: ({ offset = 0, limit = 500 } = {}) => `/lookupTypes?offset=${offset}&limit=${limit}`,
      providesTags: (result) =>
        result
          ? [
              ...result.collection.map(({ id }) => ({ type: 'Configuration' as const, id })),
              { type: 'Configuration', id: 'LOOKUP_TYPES' },
            ]
          : [{ type: 'Configuration', id: 'LOOKUP_TYPES' }],
    }),

    getLookupType: builder.query<LookupType, number>({
      query: (id) => `/lookupTypes/${id}`,
      providesTags: (_result, _error, id) => [{ type: 'Configuration', id }],
    }),

    searchLookupValues: builder.query<BackendCollectionResult<LookupValue>, LookupValueSearchParams>({
      query: ({ typeCode, offset = 0, limit = 10000, parentId, companyId, active }) => {
        const params = new URLSearchParams();
        params.set('typeCode', typeCode);
        params.set('offset', String(offset));
        params.set('limit', String(limit));
        if (parentId != null) {
          params.set('parentId', String(parentId));
        }
        if (companyId != null) {
          params.set('companyId', String(companyId));
        }
        if (active != null) {
          params.set('active', String(active));
        }
        return `/lookupValues?${params.toString()}`;
      },
      providesTags: [{ type: 'Configuration', id: 'LOOKUP_VALUES' }],
    }),

    setLookupTypeValues: builder.mutation<void, { id: number; body: LookupType }>({
      query: ({ id, body }) => ({
        url: `/lookupTypes/${id}`,
        method: 'PUT',
        body,
      }),
      invalidatesTags: (_result, _error, { id }) => [
        { type: 'Configuration', id },
        { type: 'Configuration', id: 'LOOKUP_TYPES' },
        { type: 'Configuration', id: 'LOOKUP_VALUES' },
      ],
    }),
  }),
});

export const {
  useSearchLookupTypesQuery,
  useGetLookupTypeQuery,
  useSearchLookupValuesQuery,
  useSetLookupTypeValuesMutation,
} = configurationApi;
