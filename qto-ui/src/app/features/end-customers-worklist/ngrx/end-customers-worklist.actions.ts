import { createAction, props } from '@ngrx/store';
import { LookupValue } from '../../../models/lookup-value.model';
import { ComparableDateRange } from '../../../interfaces/date-range.interface';
import { PaginatedResult } from '../../../models/paginated-result.model';
import { Company } from '../../../models/company.model';
import { CompanyView } from '../../../models/company-view.model';
import { MultiEditFailureInterface } from '../../../interfaces/multiEditFailure.interface';
import { EndCustomersWorklistFilterKeys, EndCustomersWorklistOptionsKeys } from '../data/end-customers-worklist.consts';
import { CommonColumn } from '../../../interfaces/columns.interface';
import { SubjectInterface } from 'src/app/models/subject.model';
import { CustomersMeta } from "./end-customers-worklist.reducer";

export const updateFilters = createAction(
  '[EndCustomersWorklist] update filters',
  props<{ key: string, value: string | number | string[] | boolean | ComparableDateRange }>()
);

export const updateSort = createAction(
  '[EndCustomersWorklist] update sort',
  props<{ sort: {dir: string, col: string } }>()
);

export const toggleView = createAction(
  '[EndCustomersWorklist] Toggle View'
);

export const clearFilters = createAction(
  '[EndCustomersWorklist] clear filters',
);

export const pageDestroyed = createAction(
  '[EndCustomersWorklist] page Destroyed',
);

export const loadLookupValuesByKey = createAction(
  '[EndCustomersWorklist] Load Lookup Values by key',
  props<{key: string, lookupKey: string }>()
);

export const loadLookupValuesByKeyFailure = createAction(
  '[EndCustomersWorklist] Load Lookup Values Failure',
  props<any>()
);

export const loadLookupValuesByKeySuccess = createAction(
  '[EndCustomersWorklist] Load Lookup Values Success',
  props<{values: LookupValue[], key: string}>()
);

export const loadMetaData = createAction(
  '[EndCustomersWorklist] Load Meta Data',
);

export const loadMetaDataFailure = createAction(
  '[EndCustomersWorklist] Load Meta Data Failure',
  props<any>()
);

export const loadMetaDataSuccess = createAction(
  '[EndCustomersWorklist] Load Meta Data Success',
  props<CustomersMeta>()
);

export const toggleColumn = createAction(
  '[EndCustomersWorklist] Toggle Column',
  props<{columnName: string}>()
);

export const toggleDaterangeColumn = createAction(
  '[EndCustomersWorklist] Toggle DateRange Column',
  props<{columnName: string}>()
);

export const updateDispute = createAction(
  '[EndCustomersWorklist] Dispute update',
  props<{ dispute: CompanyView }>()
);

export const updateDisputeFailure = createAction(
  '[EndCustomersWorklist] Dispute Update Failure',
  props<any>()
);

export const setUpdatedItem = createAction(
  '[EndCustomersWorklist] Updated Item',
  props<{ updatedItem: CompanyView }>()
);

export const addSelectedItems = createAction(
  '[EndCustomersWorklist] Update Add Selected Items',
  props<{ value: CompanyView[] }>()
);

export const removeUnselectedItems = createAction(
  '[EndCustomersWorklist] Update Remove Selected Items',
  props<{ value: CompanyView[] }>()
);

export const sendMultieEdit = createAction(
  '[EndCustomersWorklist] Send Multie Edit',
  props<{ formResult: Record<string, string | number>, milestoneCodes: string[] }>()
);

export const sendMultieEditSuccess = createAction(
  '[EndCustomersWorklist] Send Multie Edit Success',
);

export const sendMultieEditFailure = createAction(
  '[EndCustomersWorklist] Send Multie Edit Failure',
  props<{ cantUpdate: MultiEditFailureInterface[] }>()
);

export const loadDropdownContent = createAction(
  '[EndCustomersWorklist] Load DropdownContent',
  props<{ filterKey: EndCustomersWorklistFilterKeys, optionKey: EndCustomersWorklistOptionsKeys, orderId?: number | null }>()
);

export const updateParams = createAction(
  '[EndCustomersWorklist] Load DropdownContent params',
  props<{ key: string, value: string | number, filterKey: EndCustomersWorklistFilterKeys, optionKey: EndCustomersWorklistOptionsKeys, orderId?: number | null }>()
);

export const loadDropdownContentSuccess = createAction(
  '[EndCustomersWorklist] Load DropdownContent Success',
  props<{ options: PaginatedResult<Company | LookupValue>, filterKey: EndCustomersWorklistFilterKeys, optionKey: EndCustomersWorklistOptionsKeys }>()
);

export const loadDropdownContentFailure = createAction(
  '[EndCustomersWorklist] Load DropdownContent Failure',
  props<any>()
);

export const reorderColumns = createAction(
  '[EndCustomersWorklist] Reorder Columns',
  props<{ columns: CommonColumn[] }>()
);
