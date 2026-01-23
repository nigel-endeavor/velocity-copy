import { createAction, props } from '@ngrx/store';
import { LookupValue } from '../../../models/lookup-value.model';
import { ComparableDateRange } from '../../../interfaces/date-range.interface';
import { PaginatedResult } from '../../../models/paginated-result.model';
import { Company } from '../../../models/company.model';
import { DisputeView } from '../../../models/dispute-view.model';
import { MultiEditFailureInterface } from '../../../interfaces/multiEditFailure.interface';
import { DisputeWorklistFilterKeys, DisputeWorklistOptionsKeys } from '../data/dispute-worklist.consts';
import { CommonColumn } from '../../../interfaces/columns.interface';
import { DisputeMeta } from './dispute-worklist.reducer';
import { Subject } from 'rxjs';
import { SubjectInterface } from 'src/app/models/subject.model';

export const updateFilters = createAction(
  '[DisputeWorklist] update filters',
  props<{ key: string, value: string | number | string[] | boolean | ComparableDateRange }>()
);

export const updateSort = createAction(
  '[DisputeWorklist] update sort',
  props<{ sort: {dir: string, col: string } }>()
);

export const toggleView = createAction(
  '[DisputeWorklist] Toggle View'
);

export const clearFilters = createAction(
  '[DisputeWorklist] clear filters',
);

export const pageDestroyed = createAction(
  '[DisputeWorklist] page Destroyed',
);

export const loadLookupValuesByKey = createAction(
  '[DisputeWorklist] Load Lookup Values by key',
  props<{key: string, lookupKey: string }>()
);

export const loadLookupValuesByKeyFailure = createAction(
  '[DisputeWorklist] Load Lookup Values Failure',
  props<any>()
);

export const loadLookupValuesByKeySuccess = createAction(
  '[DisputeWorklist] Load Lookup Values Success',
  props<{values: LookupValue[], key: string}>()
);

export const loadMetaData = createAction(
  '[DisputeWorklist] Load Meta Data',
);

export const loadMetaDataFailure = createAction(
  '[DisputeWorklist] Load Meta Data Failure',
  props<any>()
);

export const loadMetaDataSuccess = createAction(
  '[DisputeWorklist] Load Meta Data Success',
  props<DisputeMeta>()
);

export const toggleColumn = createAction(
  '[DisputeWorklist] Toggle Column',
  props<{columnName: string}>()
);

export const toggleDaterangeColumn = createAction(
  '[DisputeWorklist] Toggle DateRange Column',
  props<{columnName: string}>()
);

export const updateDispute = createAction(
  '[DisputeWorklist] Dispute update',
  props<{ dispute: DisputeView }>()
);

export const updateDisputeFailure = createAction(
  '[DisputeWorklist] Dispute Update Failure',
  props<any>()
);

export const setUpdatedItem = createAction(
  '[DisputeWorklist] Updated Item',
  props<{ updatedItem: DisputeView }>()
);

export const addSelectedItems = createAction(
  '[DisputeWorklist] Update Add Selected Items',
  props<{ value: DisputeView[] }>()
);

export const removeUnselectedItems = createAction(
  '[DisputeWorklist] Update Remove Selected Items',
  props<{ value: DisputeView[] }>()
);

export const sendMultieEdit = createAction(
  '[DisputeWorklist] Send Multie Edit',
  props<{ formResult: Record<string, string | number>, milestoneCodes: string[] }>()
);

export const sendMultieEditSuccess = createAction(
  '[DisputeWorklist] Send Multie Edit Success',
);

export const sendMultieEditFailure = createAction(
  '[DisputeWorklist] Send Multie Edit Failure',
  props<{ cantUpdate: MultiEditFailureInterface[] }>()
);

export const loadDropdownContent = createAction(
  '[DisputeWorklist] Load DropdownContent',
  props<{ filterKey: DisputeWorklistFilterKeys, optionKey: DisputeWorklistOptionsKeys, orderId?: number | null }>()
);

export const updateParams = createAction(
  '[DisputeWorklist] Load DropdownContent params',
  props<{ key: string, value: string | number, filterKey: DisputeWorklistFilterKeys, optionKey: DisputeWorklistOptionsKeys, orderId?: number | null }>()
);

export const loadDropdownContentSuccess = createAction(
  '[DisputeWorklist] Load DropdownContent Success',
  props<{ options: PaginatedResult<Company | LookupValue>, filterKey: DisputeWorklistFilterKeys, optionKey: DisputeWorklistOptionsKeys }>()
);

export const loadDropdownContentFailure = createAction(
  '[DisputeWorklist] Load DropdownContent Failure',
  props<any>()
);

export const reorderColumns = createAction(
  '[DisputeWorklist] Reorder Columns',
  props<{ columns: CommonColumn[] }>()
);

export const loadDisputeAssignments = createAction(
  '[Disputes] Load Dispute Assignments',
);

export const loadDisputeAssignmentsSuccess = createAction(
  '[DisputeWorklist] Load Dispute Assignments Success',
  props<{  disputeAssignments: SubjectInterface[] }>()
);

export const loadServiceTypes = createAction(
  '[DisputeWorklist] Load Service Types',
);

export const loadServiceTypesSuccess = createAction(
  '[DisputeWorklist] Load Service Types Success',
  props<{ serviceTypes: string[] }>()
);

export const loadServiceTypesFailure = createAction(
  '[DisputeWorklist] Load Service Types Failure',
  props<any>()
);

