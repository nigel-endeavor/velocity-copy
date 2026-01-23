import { createAction, props } from '@ngrx/store';
import { LookupValue } from '../../../models/lookup-value.model';
import { ComparableDateRange } from '../../../interfaces/date-range.interface';
import { PaginatedResult } from '../../../models/paginated-result.model';
import { Company } from '../../../models/company.model';
import { DisconnectView } from '../../../models/disconnect-view.model';
import { MultiEditFailureInterface } from '../../../interfaces/multiEditFailure.interface';
import { DisconnectWorklistFilterKeys, DisconnectWorklistOptionsKeys } from '../data/disconnect-worklist.consts';
import { CommonColumn } from '../../../interfaces/columns.interface';
import { DisconnectMeta } from './disconnect-worklist.reducer';
import { SubjectInterface } from '../../../models/subject.model';

export const updateFilters = createAction(
  '[DisconnectWorklist] update filters',
  props<{ key: string, value: string | number | string[] | boolean | ComparableDateRange }>()
);

export const updateSort = createAction(
  '[DisconnectWorklist] update sort',
  props<{ sort: {dir: string, col: string } }>()
);

export const toggleView = createAction(
  '[DisconnectWorklist] Toggle View'
);

export const clearFilters = createAction(
  '[DisconnectWorklist] clear filters',
);

export const pageDestroyed = createAction(
  '[DisconnectWorklist] page Destroyed',
);

export const loadLookupValuesByKey = createAction(
  '[DisconnectWorklist] Load Lookup Values by key',
  props<{key: string, lookupKey: string }>()
);

export const loadLookupValuesByKeyFailure = createAction(
  '[DisconnectWorklist] Load Lookup Values Failure',
  props<any>()
);

export const loadLookupValuesByKeySuccess = createAction(
  '[DisconnectWorklist] Load Lookup Values Success',
  props<{values: LookupValue[], key: string}>()
);

export const loadMetaData = createAction(
  '[DisconnectWorklist] Load Meta Data',
);

export const loadMetaDataFailure = createAction(
  '[DisconnectWorklist] Load Meta Data Failure',
  props<any>()
);

export const loadMetaDataSuccess = createAction(
  '[DisconnectWorklist] Load Meta Data Success',
  props<DisconnectMeta>()
);

export const toggleColumn = createAction(
  '[DisconnectWorklist] Toggle Column',
  props<{columnName: string}>()
);

export const toggleDaterangeColumn = createAction(
  '[DisconnectWorklist] Toggle DateRange Column',
  props<{columnName: string}>()
);

export const updateDisconnect = createAction(
  '[DisconnectWorklist] Disconnect update',
  props<{ disconnect: DisconnectView }>()
);

export const updateDisconnectFailure = createAction(
  '[DisconnectWorklist] Disconnect Update Failure',
  props<any>()
);

export const setUpdatedItem = createAction(
  '[DisconnectWorklist] Updated Item',
  props<{ updatedItem: DisconnectView }>()
);

export const addSelectedItems = createAction(
  '[DisconnectWorklist] Update Add Selected Items',
  props<{ value: DisconnectView[] }>()
);

export const removeUnselectedItems = createAction(
  '[DisconnectWorklist] Update Remove Selected Items',
  props<{ value: DisconnectView[] }>()
);

export const sendMultieEdit = createAction(
  '[DisconnectWorklist] Send Multie Edit',
  props<{ formResult: Record<string, string | number>, milestoneCodes: string[] }>()
);

export const sendMultieEditSuccess = createAction(
  '[DisconnectWorklist] Send Multie Edit Success',
);

export const sendMultieEditFailure = createAction(
  '[DisconnectWorklist] Send Multie Edit Failure',
  props<{ cantUpdate: MultiEditFailureInterface[] }>()
);

export const loadProvisionersFailure = createAction(
  '[DisconnectWorklist] Load Provisioners Failure',
  props<any>()
);

export const loadProvisionersSuccess = createAction(
  '[DisconnectWorklist] Load Provisioners Success',
  props<{subjects: SubjectInterface[]}>()
);

export const loadDropdownContent = createAction(
  '[DisconnectWorklist] Load DropdownContent',
  props<{ filterKey: DisconnectWorklistFilterKeys, optionKey: DisconnectWorklistOptionsKeys, orderId?: number | null }>()
);

export const updateParams = createAction(
  '[DisconnectWorklist] Load DropdownContent params',
  props<{ key: string, value: string | number, filterKey: DisconnectWorklistFilterKeys, optionKey: DisconnectWorklistOptionsKeys, orderId?: number | null }>()
);

export const loadDropdownContentSuccess = createAction(
  '[DisconnectWorklist] Load DropdownContent Success',
  props<{ options: PaginatedResult<Company | LookupValue>, filterKey: DisconnectWorklistFilterKeys, optionKey: DisconnectWorklistOptionsKeys }>()
);

export const loadDropdownContentFailure = createAction(
  '[DisconnectWorklist] Load DropdownContent Failure',
  props<any>()
);

export const reorderColumns = createAction(
  '[DisconnectWorklist] Reorder Columns',
  props<{ columns: CommonColumn[] }>()
);

export const loadServiceTypes = createAction(
  '[DisconnectWorklist] Load Service Types',
);

export const loadServiceTypesSuccess = createAction(
  '[DisconnectWorklist] Load Service Types Success',
  props<{ serviceTypes: string[] }>()
);

export const loadServiceTypesFailure = createAction(
  '[DisconnectWorklist] Load Service Types Failure',
  props<any>()
);
