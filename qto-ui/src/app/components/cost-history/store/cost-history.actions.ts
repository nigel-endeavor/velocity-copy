import { createAction, props } from "@ngrx/store";
import { CostHistoryMeta } from "src/app/models/cost-history.model";

export const updateFilters = createAction(
  '[CostHistory] update filters',
  props<{ key: string, value: string | number | string[] | boolean }>()
);

export const updateSort = createAction(
  '[CostHistory] update sort',
  props<{ sort: {dir: string, col: string } }>()
);

export const clearFilters = createAction(
  '[CostHistory] clear filters',
);

export const pageDestroyed = createAction(
  '[CostHistory] page destroyed',
);

export const loadMetaData = createAction(
  '[CostHistory] Load Meta Data',
);

export const loadMetaDataSuccess = createAction(
  '[CostHistory] Load Meta Data Success',
  props<CostHistoryMeta>()
);