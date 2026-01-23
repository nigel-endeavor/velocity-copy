import { createAction, props } from '@ngrx/store';
import { LookupValue } from '../../../models/lookup-value.model';
import { ComparableDateRange } from '../../../interfaces/date-range.interface';
import { PaginatedResult } from '../../../models/paginated-result.model';
import { Company } from '../../../models/company.model';
import { CompanyView } from '../../../models/company-view.model';
import { MultiEditFailureInterface } from '../../../interfaces/multiEditFailure.interface';
import { MasterCustomersWorklistFilterKeys, MasterCustomersWorklistOptionsKeys } from '../data/master-customers-worklist.consts';
import { CommonColumn } from '../../../interfaces/columns.interface';
import { CustomersMeta } from './master-customers-worklist.reducer';
import { SubjectInterface } from 'src/app/models/subject.model';

export const updateFilters = createAction(
  '[MasterCustomersWorklist] update filters',
  props<{ key: string, value: string | number | string[] | boolean | ComparableDateRange }>()
);

export const updateSort = createAction(
  '[MasterCustomersWorklist] update sort',
  props<{ sort: {dir: string, col: string } }>()
);

export const toggleView = createAction(
  '[MasterCustomersWorklist] Toggle View'
);

export const clearFilters = createAction(
  '[MasterCustomersWorklist] clear filters',
);

export const pageDestroyed = createAction(
  '[MasterCustomersWorklist] page Destroyed',
);

export const loadLookupValuesByKey = createAction(
  '[MasterCustomersWorklist] Load Lookup Values by key',
  props<{key: string, lookupKey: string }>()
);

export const loadLookupValuesByKeyFailure = createAction(
  '[MasterCustomersWorklist] Load Lookup Values Failure',
  props<any>()
);

export const loadLookupValuesByKeySuccess = createAction(
  '[MasterCustomersWorklist] Load Lookup Values Success',
  props<{values: LookupValue[], key: string}>()
);

export const loadMetaData = createAction(
  '[MasterCustomersWorklist] Load Meta Data',
);

export const loadMetaDataFailure = createAction(
  '[MasterCustomersWorklist] Load Meta Data Failure',
  props<any>()
);

export const loadMetaDataSuccess = createAction(
  '[MasterCustomersWorklist] Load Meta Data Success',
  props<CustomersMeta>()
);

export const toggleColumn = createAction(
  '[MasterCustomersWorklist] Toggle Column',
  props<{columnName: string}>()
);

export const toggleDaterangeColumn = createAction(
  '[MasterCustomersWorklist] Toggle DateRange Column',
  props<{columnName: string}>()
);

export const setUpdatedItem = createAction(
  '[MasterCustomersWorklist] Updated Item',
  props<{ updatedItem: CompanyView }>()
);

export const addSelectedItems = createAction(
  '[MasterCustomersWorklist] Update Add Selected Items',
  props<{ value: CompanyView[] }>()
);

export const removeUnselectedItems = createAction(
  '[MasterCustomersWorklist] Update Remove Selected Items',
  props<{ value: CompanyView[] }>()
);

export const sendMultieEdit = createAction(
  '[MasterCustomersWorklist] Send Multie Edit',
  props<{ formResult: Record<string, string | number>, milestoneCodes: string[] }>()
);

export const sendMultieEditSuccess = createAction(
  '[MasterCustomersWorklist] Send Multie Edit Success',
);

export const sendMultieEditFailure = createAction(
  '[MasterCustomersWorklist] Send Multie Edit Failure',
  props<{ cantUpdate: MultiEditFailureInterface[] }>()
);

export const loadDropdownContent = createAction(
  '[MasterCustomersWorklist] Load DropdownContent',
  props<{ filterKey: MasterCustomersWorklistFilterKeys, optionKey: MasterCustomersWorklistOptionsKeys, orderId?: number | null }>()
);

export const updateParams = createAction(
  '[MasterCustomersWorklist] Load DropdownContent params',
  props<{ key: string, value: string | number, filterKey: MasterCustomersWorklistFilterKeys, optionKey: MasterCustomersWorklistOptionsKeys, orderId?: number | null }>()
);

export const loadDropdownContentSuccess = createAction(
  '[MasterCustomersWorklist] Load DropdownContent Success',
  props<{ options: PaginatedResult<Company | LookupValue>, filterKey: MasterCustomersWorklistFilterKeys, optionKey: MasterCustomersWorklistOptionsKeys }>()
);

export const loadDropdownContentFailure = createAction(
  '[MasterCustomersWorklist] Load DropdownContent Failure',
  props<any>()
);

export const reorderColumns = createAction(
  '[MasterCustomersWorklist] Reorder Columns',
  props<{ columns: CommonColumn[] }>()
);

export const loadAssignablesFailure = createAction(
  '[MasterCustomersWorklist] Load Assignables Failure',
  props<any>()
);

export const loadAssignablesSuccess = createAction(
  '[MasterCustomersWorklist] Load Assignables Success',
  props<{subjects: SubjectInterface[]}>()
);
