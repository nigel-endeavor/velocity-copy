/**
 * Contacts API
 *
 * RTK Query endpoints for contact operations.
 * Uses the /api/contacts endpoint.
 */

import { baseApi } from './baseApi';

export interface Contact {
  id: number;
  legacyId: number;
  companyId: number;
  firstName: string;
  lastName: string;
  active: boolean;
  notes: string;
  type: string;
  role: string;
  phone: string;
  email: string;
  lastUpdateBy: string;
  lastUpdateDate: string | null;
  parentContactId: number;
  masterCustomerId: number;
  tenantId: number;
  version: number;
}

export const contactsApi = baseApi.injectEndpoints({
  endpoints: (builder) => ({
    getContactsByCompany: builder.query<Contact[], { companyId: number; type?: string }>({
      query: ({ companyId, type }) => {
        let url = `/contacts?companyId=${companyId}`;
        if (type) url += `&type=${type}`;
        return url;
      },
      providesTags: (result) =>
        result
          ? [
              ...result.map(({ id }) => ({ type: 'Contact' as const, id })),
              { type: 'Contact', id: 'LIST' },
            ]
          : [{ type: 'Contact', id: 'LIST' }],
    }),

    createContact: builder.mutation<Contact, Partial<Contact>>({
      query: (body) => ({ url: '/contacts', method: 'POST', body }),
      invalidatesTags: [{ type: 'Contact', id: 'LIST' }],
    }),

    updateContact: builder.mutation<Contact, { id: number; body: Partial<Contact> }>({
      query: ({ id, body }) => ({ url: `/contacts/${id}`, method: 'PUT', body }),
      invalidatesTags: (_result, _error, { id }) => [{ type: 'Contact', id }],
    }),
  }),
});

export const {
  useGetContactsByCompanyQuery,
  useCreateContactMutation,
  useUpdateContactMutation,
} = contactsApi;
