import { createAction, props } from "@ngrx/store";
import { LookupValue } from "../../../models/lookup-value.model";
import { MultiEditFailureInterface } from "../../../interfaces/multiEditFailure.interface";
import { SubjectInterface } from "../../../models/subject.model";
import { PaginatedResult } from "../../../models/paginated-result.model";
import { Company } from "../../../models/company.model";
import { CommonColumn } from "../../../interfaces/columns.interface";
import { ServiceCyberWorklistFilterKeys, ServiceCyberWorklistOptionsKeys } from "../data/service-cyber-worklist.const";
import { ServiceCyberView } from "../../../models/service-cyber-view.model";
import { ComparableDateRange } from "../../../interfaces/date-range.interface";

export const updateFilters = createAction(
  '[ServiceCyberWorklist] update filters' ,
  props<{ key: string, value: string | number | string[] | boolean | ComparableDateRange }>()
)

export const updateSort = createAction(
  '[ServiceCyberWorklist] update sort',
  props<{ sort: {dir: string, col: string } }>()
);

export const toggleView = createAction(
  '[ServiceCyberWorklist] Toggle View'
);

export const clearFilters = createAction(
  '[ServiceCyberWorklist] clear filters',
);

export const pageDestroyed = createAction(
  '[ServiceCyberWorklist] page Destroyed',
);

export const loadLookupValuesByKey = createAction(
  '[ServiceCyberWorklist] Load Lookup Values by key',
  props<{key: string, lookupKey: string }>()
);

export const loadLookupValuesByKeyFailure = createAction(
  '[ServiceCyberWorklist] Load Lookup Values Failure',
  props<any>()
);

export const loadLookupValuesByKeySuccess = createAction(
  '[ServiceCyberWorklist] Load Lookup Values Success',
  props<{values: LookupValue[], key: string}>()
);

export const loadMetaData = createAction(
  '[ServiceCyberWorklist] Load Meta Data',
);

export const loadMetaDataFailure = createAction(
  '[ServiceCyberWorklist] Load Meta Data Failure',
  props<any>()
);

export const toggleColumn = createAction(
  '[ServiceCyberWorklist] Toggle Column',
  props<{columnName: string}>()
);

export const toggleDaterangeColumn = createAction(
  '[ServiceCyberWorklist] Toggle DateRange Column',
  props<{columnName: string}>()
);

export const updateServiceCyber = createAction(
  '[ServiceCyberWorklist] Service update',
  props<{ serviceCyberView: ServiceCyberView }>()
);

export const updateServiceCyberFailure = createAction(
  '[ServiceCyberWorklist] ServiceCyber Update Failure',
  props<any>()
);

export const setUpdatedItem = createAction(
  '[ServiceCyberWorklist] Updated Item',
  props<{ updatedItem: ServiceCyberView }>()
);

export const addSelectedItems = createAction(
  '[ServiceCyberWorklist] Update Add Selected Items',
  props<{ value: ServiceCyberView[] }>()
);

export const removeUnselectedItems = createAction(
  '[ServiceCyberWorklist] Update Remove Selected Items',
  props<{ value: ServiceCyberView[] }>()
);

export const sendMultieEdit = createAction(
  '[ServiceCyberWorklist] Send Multie Edit',
  props<{ formResult: Record<string, string | number>, milestoneCodes: string[] }>()
);

export const sendMultieEditSuccess = createAction(
  '[ServiceCyberWorklist] Send Multie Edit Success',
);

export const sendMultieEditFailure = createAction(
  '[ServiceCyberWorklist] Send Multie Edit Failure',
  props<{ cantUpdate: MultiEditFailureInterface[] }>()
);

export const loadProvisionersFailure = createAction(
  '[ServiceCyberWorklist] Load Provisioners Failure',
  props<any>()
);

export const loadProvisionersSuccess = createAction(
  '[ServiceCyberWorklist] Load Provisioners Success',
  props<{subjects: SubjectInterface[]}>()
);

export const loadi90ProjectManagersSuccess = createAction(
  '[ServiceCyberWorklist] Load i90 Project Managers Success',
  props<{subjects: SubjectInterface[]}>()
);

export const load90ProjectManagersFailure = createAction(
  '[ServiceCyberWorklist] Load i90 Project Managers Failure',
  props<any>()
);

export const loadDropdownContent = createAction(
  '[ServiceCyberWorklist] Load DropdownContent',
  props<{ filterKey: ServiceCyberWorklistFilterKeys, optionKey: ServiceCyberWorklistOptionsKeys, orderId?: number | null }>()
);

export const updateParams = createAction(
  '[ServiceCyberWorklist] Load DropdownContent params',
  props<{ key: string, value: string | number, filterKey: ServiceCyberWorklistFilterKeys, optionKey: ServiceCyberWorklistOptionsKeys, orderId?: number | null }>()
);

export const loadDropdownContentSuccess = createAction(
  '[ServiceCyberWorklist] Load DropdownContent Success',
  props<{ options: PaginatedResult<Company | LookupValue>, filterKey: ServiceCyberWorklistFilterKeys, optionKey: ServiceCyberWorklistOptionsKeys }>()
);

export const loadDropdownContentFailure = createAction(
  '[ServiceCyberWorklist] Load DropdownContent Failure',
  props<any>()
);

export const reorderColumns = createAction(
  '[ServiceCyberWorklist] Reorder Columns',
  props<{ columns: CommonColumn[] }>()
);
