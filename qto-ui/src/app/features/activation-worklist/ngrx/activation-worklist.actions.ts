import { createAction, props } from '@ngrx/store';
import { LookupValue } from '../../../models/lookup-value.model';
import { ComparableDateRange } from '../../../interfaces/date-range.interface';
import { ActivationMetaData } from './activation-worklist.reducer';
import { CommonColumn } from '../../../interfaces/columns.interface';
import { PaginatedResult } from '../../../models/paginated-result.model';
import { Company } from '../../../models/company.model';
import { ActivationWorklistFilterKeys, ActivationWorklistOptionKeys } from '../data/activation-worklist.consts';

export const updateFilters = createAction(
  '[ActivationWorklist] update filters',
  props<{ key: string, value: string | number | string[] | boolean | ComparableDateRange }>()
);
export const updateSort = createAction(
  '[ActivationWorklist] update sort',
  props<{ sort: {dir: string, col: string } }>()
);

export const resetFilters = createAction(
  '[ActivationWorklist] reset filters',
);

export const pageDestroyed = createAction(
  '[ActivationWorklist] page Destroyed',
);

export const loadLookupValuesByKey = createAction(
  '[ActivationWorklist] Load Lookup Values by key',
  props<{key: string, lookupKey: string }>()
);

export const loadLookupValuesByKeyFailure = createAction(
  '[ActivationWorklist] Load Lookup Values Failure',
  props<any>()
);

export const loadLookupValuesByKeySuccess = createAction(
  '[ActivationWorklist] Load Lookup Values Success',
  props<{values: LookupValue[], key: string}>()
);

export const loadMetaData = createAction(
  '[ActivationWorklist] Load Meta Data',
);

export const loadMetaDataFailure = createAction(
  '[ActivationWorklist] Load Meta Data Failure',
  props<any>()
);

export const loadMetaDataSuccess = createAction(
  '[ActivationWorklist] Load Meta Data Success',
  props<ActivationMetaData>()
);

export const toggleColumn = createAction(
  '[ActivationWorklist] Toggle Column',
  props<{columnName: string}>()
);

export const reorderColumns = createAction(
  '[ActivationWorklist] Reorder Columns',
  props<{ columns: CommonColumn[] }>()
);

export const updateParams = createAction(
  '[ActivationWorklist] Load DropdownContent params',
  props<{ key: string, value: string | number, filterKey: ActivationWorklistFilterKeys, optionKey: ActivationWorklistOptionKeys, orderId?: number | null }>()
);

export const loadDropdownContent = createAction(
  '[ActivationWorklist] Load DropdownContent',
  props<{ filterKey: ActivationWorklistFilterKeys, optionKey: ActivationWorklistOptionKeys, orderId?: number | null }>()
);

export const loadDropdownContentSuccess = createAction(
  '[ActivationWorklist] Load DropdownContent Success',
  props<{ options: PaginatedResult<Company>, filterKey: ActivationWorklistFilterKeys, optionKey: ActivationWorklistOptionKeys }>()
);

export const loadDropdownContentFailure = createAction(
  '[ActivationWorklist] Load DropdownContent Failure',
  props<any>()
);
