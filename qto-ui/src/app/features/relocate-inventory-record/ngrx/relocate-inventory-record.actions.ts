import { createAction, props } from '@ngrx/store';

export const updateFilters = createAction(
  '[RelocateInventoryRecord] update filters',
  props<{ key: string, value: string | number | string[] | boolean }>()
);

export const updateSort = createAction(
  '[RelocateInventoryRecord] update sort',
  props<{ sort: {dir: string, col: string } }>()
);

export const clearFilters = createAction(
  '[RelocateInventoryRecord] clear filters',
);

export const pageDestroyed = createAction(
  '[RelocateInventoryRecord] page destroyed',
);