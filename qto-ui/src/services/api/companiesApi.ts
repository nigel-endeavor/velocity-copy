/**
 * Companies API
 *
 * RTK Query endpoints for company CRUD operations.
 * Uses the /api/companies endpoint.
 */

import { baseApi } from './baseApi';

export interface Company {
  id: number;
  name: string;
  type: string;
  active: boolean;
  uuid: string;
  clientId: string;
  legacyId: number;
  inventoryLocationCount: number;
  inventoryMrc: number;
  inventoryMrr: number;
  inventoryNrr: number;
  accountNotes: string;
  duplicatedMasterCustomerDetails: boolean;
  accountManager: number;
  provisioner: number;
  i90ProjectManager: number;
  address1: string;
  address2: string;
  city: string;
  state: string;
  postalCode: string;
  country: string;
  taskGroupId: number;
  automateEmailAddresses: string;
  automatedEmailsEnabled: boolean;
  masterCustomerId: number;
  tenantId: number;
  version: number;
}

export interface CompanyTask {
  companyTaskId: number;
  companyId: number;
  value: string;
  assignedTo: string;
  completeDate: string | null;
}

export const companiesApi = baseApi.injectEndpoints({
  endpoints: (builder) => ({
    getCompany: builder.query<Company, number>({
      query: (id) => `/companies/${id}`,
      providesTags: (_result, _error, id) => [{ type: 'Company', id }],
    }),

    createCompany: builder.mutation<Company, Partial<Company>>({
      query: (body) => ({ url: '/companies', method: 'POST', body }),
      invalidatesTags: [{ type: 'Company', id: 'LIST' }, { type: 'CompanyView', id: 'LIST' }],
    }),

    updateCompany: builder.mutation<Company, { id: number; body: Partial<Company> }>({
      query: ({ id, body }) => ({ url: `/companies/${id}`, method: 'PUT', body }),
      invalidatesTags: (_result, _error, { id }) => [{ type: 'Company', id }, { type: 'CompanyView', id: 'LIST' }],
    }),

    deleteCompany: builder.mutation<void, number>({
      query: (id) => ({ url: `/companies/${id}`, method: 'DELETE' }),
      invalidatesTags: [{ type: 'Company', id: 'LIST' }, { type: 'CompanyView', id: 'LIST' }],
    }),

    getCompanyTasks: builder.query<CompanyTask[], number>({
      query: (companyId) => `/companies/${companyId}/tasks`,
      providesTags: (_result, _error, companyId) => [{ type: 'Company', id: companyId }],
    }),
  }),
});

export const {
  useGetCompanyQuery,
  useCreateCompanyMutation,
  useUpdateCompanyMutation,
  useDeleteCompanyMutation,
  useGetCompanyTasksQuery,
} = companiesApi;
