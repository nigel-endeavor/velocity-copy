import { createAction, props } from '@ngrx/store';
import { LookupValue } from '../../../models/lookup-value.model';
import { ComparableDateRange } from '../../../interfaces/date-range.interface';
import { SubjectInterface } from '../../../models/subject.model';
import { PaginatedResult } from '../../../models/paginated-result.model';
import { Company } from '../../../models/company.model';
import { MultiEditFailureInterface } from '../../../interfaces/multiEditFailure.interface';
import { LevelOfEffort } from 'src/app/models/level-of-effort.model';
import { ServiceWorklistFilterKeys, ServiceWorklistOptionsKeys } from '../data/services-worklist.consts';
import { ServiceView } from 'src/app/models/service-view.model';
import { CommonColumn } from 'src/app/interfaces/columns.interface';

export const updateFilters = createAction(
  '[ServiceInventoryWorklist] update filters',
  props<{ key: string, value: string | number | string[] | boolean | ComparableDateRange }>()
);

export const updateSort = createAction(
  '[ServiceInventoryWorklist] update sort',
  props<{ sort: {dir: string, col: string } }>()
);

export const toggleView = createAction(
  '[ServiceInventoryWorklist] Toggle View'
);

export const clearFilters = createAction(
  '[ServiceInventoryWorklist] clear filters',
);

export const pageDestroyed = createAction(
  '[ServiceInventoryWorklist] page Destroyed',
);

export const loadProvisionersFailure = createAction(
  '[ServiceInventoryWorklist] Load Provisioners Failure',
  props<any>()
);

export const loadProvisionersSuccess = createAction(
  '[ServiceInventoryWorklist] Load Provisioners Success',
  props<{subjects: SubjectInterface[]}>()
);

export const loadLevelOfEffort = createAction(
  '[ServiceInventoryWorklist] Load Level Of Effort',
);

export const loadLevelOfEffortFailure = createAction(
  '[ServiceInventoryWorklist] Load Level Of Effort Failure',
  props<any>()
);

export const loadLevelOfEffortSuccess = createAction(
  '[ServiceInventoryWorklist] Load Level Of Effort Success',
  props<{levelOfEffort: LevelOfEffort[]}>()
);

export const loadLookupValuesByKey = createAction(
  '[ServiceInventoryWorklist] Load Lookup Values by key',
  props<{key: string, lookupKey: string }>()
);

export const loadLookupValuesByKeyFailure = createAction(
  '[ServiceInventoryWorklist] Load Lookup Values Failure',
  props<any>()
);
export const loadLookupValuesByKeySuccess = createAction(
  '[ServiceInventoryWorklist] Load Lookup Values Success',
  props<{values: LookupValue[], key: string}>()
);
export const loadMetaData = createAction(
  '[ServiceInventoryWorklist] Load Meta Data',
);
export const loadMetaDataSuccess = createAction(
  '[ServiceInventoryWorklist] Load Meta Data Success',
  props<any>()
);
export const toggleColumn = createAction(
  '[ServiceInventoryWorklist] Toggle Column',
  props<{columnName: string}>()
);

export const toggleDaterangeColumn = createAction(
  '[ServiceInventoryWorklist] Toggle DateRange Column',
  props<{columnName: string}>()
);

export const updateService = createAction(
  '[ServiceInventoryWorklist] Service update',
  props<{ service: ServiceView }>()
);

export const updateServiceFailure = createAction(
  '[ServiceInventoryWorklist] Service Update Failure',
  props<any>()
);

export const setUpdatedItem = createAction(
  '[ServiceInventoryWorklist] Updated Item',
  props<{ updatedItem: ServiceView }>()
);

export const addSelectedItems = createAction(
  '[ServiceInventoryWorklist] Update Add Selected Items',
  props<{ value: ServiceView[] }>()
);

export const removeUnselectedItems = createAction(
  '[ServiceInventoryWorklist] Update Remove Selected Items',
  props<{ value: ServiceView[] }>()
);

export const sendMultieEdit = createAction(
  '[ServiceInventoryWorklist] Send Multie Edit',
  props<{ formResult: Record<string, string | number>, milestoneCodes: string[] }>()
);

export const sendMultieEditSuccess = createAction(
  '[ServiceInventoryWorklist] Send Multie Edit Success',
);

export const sendMultieEditFailure = createAction(
  '[ServiceInventoryWorklist] Send Multie Edit Failure',
  props<{ cantUpdate: MultiEditFailureInterface[] }>()
);

export const loadDropdownContent = createAction(
  '[ServiceInventoryWorklist] Load DropdownContent',
  props<{ filterKey: ServiceWorklistFilterKeys, optionKey: ServiceWorklistOptionsKeys, orderId?: number | null }>()
);

export const updateParams = createAction(
  '[ServiceInventoryWorklist] Load DropdownContent params',
  props<{ key: string, value: string | number, filterKey: ServiceWorklistFilterKeys, optionKey: ServiceWorklistOptionsKeys, orderId?: number | null }>()
);

export const loadDropdownContentSuccess = createAction(
  '[ServiceInventoryWorklist] Load DropdownContent Success',
  props<{ customers: PaginatedResult<Company | LookupValue>, filterKey: ServiceWorklistFilterKeys, optionKey: ServiceWorklistOptionsKeys }>()
);

export const loadDropdownContentFailure = createAction(
  '[ServiceInventoryWorklist] Load DropdownContent Failure',
  props<any>()
);

export const reorderColumns = createAction(
  '[ServiceWorklist] Reorder Columns',
  props<{ columns: CommonColumn[] }>()
);

 export const loadServiceTypes = createAction(
  '[ServiceInventoryWorklist] Load Service Types',
);

export const loadServiceTypesSuccess = createAction(
  '[ServiceInventoryWorklist] Load Service Types Success',
  props<{ serviceTypes: string[] }>()
);

export const loadServiceTypesFailure = createAction(
  '[ServiceInventoryWorklist] Load Service Types Failure',
  props<any>()
);