import { createAction, props } from "@ngrx/store";
import { ComparableDateRange } from "src/app/interfaces/date-range.interface";


export const updateFilters = createAction(
  '[ImportActivityWorklist] update filters',
  props<{ key: string, value: string | number | string[] | boolean | ComparableDateRange }>()
);

export const updateSort = createAction(
  '[ImportActivityWorklist] update sort',
  props<{ sort: {dir: string, col: string } }>()
);

export const clearFilters = createAction(
  '[ImportActivityWorklist] clear filters',
);

export const pageDestroyed = createAction(
  '[ImportActivityWorklist] page Destroyed',
);

export const toggleColumn = createAction(
  '[ImportActivityWorklist] Toggle Column',
  props<{columnName: string}>()
);

export const loadSubjects = createAction(
  '[ImportActivityWorklist] Load Subjects',
);

export const loadSubjectsSuccess = createAction(
  '[ImportActivityWorklist] Load Subjects Success',
  props<{ subjects: any[] }>()
);