import { createAction, props } from '@ngrx/store';
import { ComparableDateRange } from '../../../../interfaces/date-range.interface';

export const updateFilters = createAction(
  '[InvoiceCharges] update filters',
  props<{ key: string, value: string | number | string[] | boolean | ComparableDateRange }>()
);

export const updateSort = createAction(
  '[InvoiceCharges] update sort',
  props<{ sort: {dir: string, col: string } }>()
);

export const toggleView = createAction(
  '[InvoiceCharges] Toggle View'
);

export const clearFilters = createAction(
  '[InvoiceCharges] clear filters',
);

export const pageDestroyed = createAction(
  '[InvoiceCharges] page destroyed',
); 