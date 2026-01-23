import { createAction, props } from "@ngrx/store";

export const updateFilters = createAction(
  '[LinkLocation] update filters',
  props<{ key: string, value: string | number | string[] | boolean }>()
);

export const updateSort = createAction(
  '[LinkLocation] update sort',
  props<{ sort: {dir: string, col: string } }>()
);

export const clearFilters = createAction(
  '[LinkLocation] clear filters',
);

export const pageDestroyed = createAction(
  '[LinkLocation] page destroyed',
);