import { createAction, props } from '@ngrx/store';
import { LookupValue } from '../../../models/lookup-value.model';
import { ComparableDateRange } from '../../../interfaces/date-range.interface';
import { SubjectInterface } from '../../../models/subject.model';
import { PaginatedResult } from '../../../models/paginated-result.model';
import { Company } from '../../../models/company.model';
import { ServiceView } from '../../../models/service-view.model';
import { MultiEditFailureInterface } from '../../../interfaces/multiEditFailure.interface';
import { LevelOfEffort } from 'src/app/models/level-of-effort.model';
import { ServiceWorklistFilterKeys, ServiceWorklistOptionsKeys } from '../data/services-worklist.consts';
import { CommonColumn } from '../../../interfaces/columns.interface';

export const updateFilters = createAction(
  '[ServiceWorklist] update filters',
  props<{ key: string, value: string | number | string[] | boolean | ComparableDateRange }>()
);

export const updateSort = createAction(
  '[ServiceWorklist] update sort',
  props<{ sort: {dir: string, col: string } }>()
);

export const toggleView = createAction(
  '[ServiceWorklist] Toggle View'
);

export const clearFilters = createAction(
  '[ServiceWorklist] clear filters',
);

export const pageDestroyed = createAction(
  '[ServiceWorklist] page Destroyed',
);

export const loadProvisionersFailure = createAction(
  '[ServiceWorklist] Load Provisioners Failure',
  props<any>()
);

export const loadProvisionersSuccess = createAction(
  '[ServiceWorklist] Load Provisioners Success',
  props<{subjects: SubjectInterface[]}>()
);

export const loadQaManagersSuccess = createAction(
  '[ServiceWorklist] Load QA Managers Success',
  props<{subjects: SubjectInterface[]}>()
);

export const loadLevelOfEffort = createAction(
  '[ServiceWorklist] Load Level Of Effort',
);

export const loadLevelOfEffortFailure = createAction(
  '[ServiceWorklist] Load Level Of Effort Failure',
  props<any>()
);

export const loadLevelOfEffortSuccess = createAction(
  '[ServiceWorklist] Load Level Of Effort Success',
  props<{levelOfEffort: LevelOfEffort[]}>()
);

export const loadLookupValuesByKey = createAction(
  '[ServiceWorklist] Load Lookup Values by key',
  props<{key: string, lookupKey: string }>()
);

export const loadLookupValuesByKeyFailure = createAction(
  '[ServiceWorklist] Load Lookup Values Failure',
  props<any>()
);
export const loadLookupValuesByKeySuccess = createAction(
  '[ServiceWorklist] Load Lookup Values Success',
  props<{values: LookupValue[], key: string}>()
);
export const loadMetaData = createAction(
  '[ServiceWorklist] Load Meta Data',
);
export const loadMetaDataFailure = createAction(
  '[ServiceWorklist] Load Meta Data Failure',
  props<any>()
);
export const toggleColumn = createAction(
  '[ServiceWorklist] Toggle Column',
  props<{columnName: string}>()
);

export const toggleDaterangeColumn = createAction(
  '[ServiceWorklist] Toggle DateRange Column',
  props<{columnName: string}>()
);

export const updateService = createAction(
  '[ServiceWorklist] Service update',
  props<{ service: ServiceView }>()
);

export const updateServiceFailure = createAction(
  '[ServiceWorklist] Service Update Failure',
  props<any>()
);

export const setUpdatedItem = createAction(
  '[ServiceWorklist] Updated Item',
  props<{ updatedItem: ServiceView }>()
);

export const addSelectedItems = createAction(
  '[ServiceWorklist] Update Add Selected Items',
  props<{ value: ServiceView[] }>()
);

export const removeUnselectedItems = createAction(
  '[ServiceWorklist] Update Remove Selected Items',
  props<{ value: ServiceView[] }>()
);

export const sendMultieEdit = createAction(
  '[ServiceWorklist] Send Multie Edit',
  props<{ formResult: Record<string, string | number>, milestoneCodes: string[] }>()
);

export const sendMultieEditSuccess = createAction(
  '[ServiceWorklist] Send Multie Edit Success',
);

export const sendMultieEditFailure = createAction(
  '[ServiceWorklist] Send Multie Edit Failure',
  props<{ cantUpdate: MultiEditFailureInterface[] }>()
);

export const loadDropdownContent = createAction(
  '[ServiceWorklist] Load DropdownContent',
  props<{ filterKey: ServiceWorklistFilterKeys, optionKey: ServiceWorklistOptionsKeys, orderId?: number | null }>()
);

export const updateParams = createAction(
  '[ServiceWorklist] Load DropdownContent params',
  props<{ key: string, value: string | number, filterKey: ServiceWorklistFilterKeys, optionKey: ServiceWorklistOptionsKeys, orderId?: number | null }>()
);

export const loadDropdownContentSuccess = createAction(
  '[ServiceWorklist] Load DropdownContent Success',
  props<{ customers: PaginatedResult<Company | LookupValue>, filterKey: ServiceWorklistFilterKeys, optionKey: ServiceWorklistOptionsKeys }>()
);

export const loadDropdownContentFailure = createAction(
  '[ServiceWorklist] Load DropdownContent Failure',
  props<any>()
);

export const reorderColumns = createAction(
  '[ServiceWorklist] Reorder Columns',
  props<{ columns: CommonColumn[] }>()
);


export const setFilterBuilder = createAction(
  '[ServiceWorklist] Set Filter Builder',
  props<{ fbName: string }>()
);

export const loadServiceTypes = createAction(
  '[ServiceWorklist] Load Service Types',
);

export const loadServiceTypesSuccess = createAction(
  '[ServiceWorklist] Load Service Types Success',
  props<{ serviceTypes: string[] }>()
);

export const loadServiceTypesFailure = createAction(
  '[ServiceWorklist] Load Service Types Failure',
  props<any>()
);
