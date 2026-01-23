import { createAction, props } from '@ngrx/store';

export const updateFilters = createAction(
  '[RelocateRecord] update filters',
  props<{ key: string, value: string | number | string[] | boolean }>()
);

export const updateSort = createAction(
  '[RelocateRecord] update sort',
  props<{ sort: {dir: string, col: string } }>()
);

export const clearFilters = createAction(
  '[RelocateRecord] clear filters',
);

export const pageDestroyed = createAction(
  '[RelocateRecord] page destroyed',
);