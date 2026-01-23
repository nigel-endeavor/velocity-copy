import { createAction, props } from '@ngrx/store';
import { ComparableDateRange } from '../../../interfaces/date-range.interface';
import { Invoice } from '../../../models/invoice.model';
import { PaginatedResult } from '../../../models/paginated-result.model';
import { Company } from '../../../models/company.model';

export const updateFilters = createAction(
  '[Invoicing] update filters',
  props<{ key: string, value: string | number | string[] | boolean | ComparableDateRange }>()
);

export const updateSort = createAction(
  '[Invoicing] update sort',
  props<{ sort: {dir: string, col: string } }>()
);

export const toggleView = createAction(
  '[Invoicing] Toggle View'
);

export const clearFilters = createAction(
  '[Invoicing] clear filters',
);

export const pageDestroyed = createAction(
  '[Invoicing] page destroyed',
);

export const createInvoice = createAction(
  '[Invoicing] Create Invoice',
);

export const setSelectedInvoice = createAction(
  '[Invoicing] Set Selected Invoice',
  props<{ invoice: Invoice | undefined }>()
);

export const saveInvoice = createAction(
  '[Invoicing] Save an invoice',
  props<{ invoice: Invoice }>()
);

export const saveInvoiceSuccess = createAction(
  '[Invoicing] Save an invoice Success',
  props<{ invoice: Invoice }>()
);

export const saveInvoiceFailure = createAction(
  '[Invoicing] Save an invoice Failure',
  props<{ errorMessage: string }>()
);
export const finalizeInvoice = createAction(
  '[Invoicing] Finalize an invoice',
  props<{ invoice: Invoice }>()
);

export const finalizeInvoiceSuccess = createAction(
  '[Invoicing] Finalize an invoice Success',
  props<{ invoice: Invoice }>()
);

export const finalizeInvoiceFailure = createAction(
  '[Invoicing] Finalize an invoice Failure',
  props<{ errorMessage: string }>()
);

export const updateInvoice = createAction(
  '[Invoicing] Update an invoice',
  props<{ key: string, value: Date | boolean }>()
);

export const loadVertekClients = createAction(
  '[Invoicing] Load Vertek Clients',
);

export const loadVertekClientsSuccess = createAction(
  '[Invoicing] Load Vertek Clients Success',
  props<{vertekClients: PaginatedResult<Company>}>()
);

export const loadVertekClientsFailure = createAction(
  '[Invoicing] Load Vertek Clients Failure',
  props<any>()
);