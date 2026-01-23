import { createAction, props } from "@ngrx/store";

export const updateFilters = createAction(
  '[ServiceHistory] update filters',
  props<{ key: string, value: string | number | string[] | boolean }>()
);

export const updateSort = createAction(
  '[ServiceHistory] update sort',
  props<{ sort: {dir: string, col: string } }>()
);

export const clearFilters = createAction(
  '[ServiceHistory] clear filters',
);

export const pageDestroyed = createAction(
  '[ServiceHistory] page destroyed',
);