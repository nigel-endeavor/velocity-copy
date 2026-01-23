import { createAction, props } from '@ngrx/store';
import { LookupValue } from '../../../../models/lookup-value.model';
import { ComparableDateRange } from '../../../../interfaces/date-range.interface';
import { PaginatedResult } from '../../../../models/paginated-result.model';
import { Company } from '../../../../models/company.model';
import { CompanyView } from '../../../../models/company-view.model';
import { MultiEditFailureInterface } from '../../../../interfaces/multiEditFailure.interface';
import { ServiceEquipmentFilterKeys, ServiceEquipmentOptionsKeys } from '../data/service-equipment.consts';
import { CommonColumn } from '../../../../interfaces/columns.interface';
import { CustomersMeta } from './service-equipment.reducer';
import { SubjectInterface } from 'src/app/models/subject.model';
import { ServiceEquipment } from '../../../../models/service-equipment-model';

const actionKey = '[ServiceEquipment]';

export const updateFilters = createAction(
  `${actionKey} update filters`,
  props<{ key: string, value: string | number | string[] | boolean | ComparableDateRange }>()
);

export const updateSort = createAction(
  `${actionKey} update sort`,
  props<{ sort: {dir: string, col: string } }>()
);

export const toggleView = createAction(
  `${actionKey} Toggle View`
);

export const clearFilters = createAction(
  `${actionKey} clear filters`,
);

export const pageDestroyed = createAction(
  `${actionKey} page Destroyed`,
);

export const loadLookupValuesByKey = createAction(
  `${actionKey} Load Lookup Values by key`,
  props<{key: string, lookupKey: string }>()
);

export const loadLookupValuesByKeyFailure = createAction(
  `${actionKey} Load Lookup Values Failure`,
  props<any>()
);

export const loadLookupValuesByKeySuccess = createAction(
  `${actionKey} Load Lookup Values Success`,
  props<{values: LookupValue[], key: string}>()
);

export const loadMetaData = createAction(
  `${actionKey} Load Meta Data`,
);

export const loadMetaDataFailure = createAction(
  `${actionKey} Load Meta Data Failure`,
  props<any>()
);

export const loadMetaDataSuccess = createAction(
  `${actionKey} Load Meta Data Success`,
  props<CustomersMeta>()
);

export const toggleColumn = createAction(
  `${actionKey} Toggle Column`,
  props<{columnName: string}>()
);

export const toggleDaterangeColumn = createAction(
  `${actionKey} Toggle DateRange Column`,
  props<{columnName: string}>()
);

export const setUpdatedItem = createAction(
  `${actionKey} Updated Item`,
  props<{ updatedItem: CompanyView }>()
);

export const addSelectedItems = createAction(
  `${actionKey} Update Add Selected Items`,
  props<{ value: CompanyView[] }>()
);

export const removeUnselectedItems = createAction(
  `${actionKey} Update Remove Selected Items`,
  props<{ value: CompanyView[] }>()
);

export const sendMultieEdit = createAction(
  `${actionKey} Send Multie Edit`,
  props<{ formResult: Record<string, string | number>, milestoneCodes: string[] }>()
);

export const sendMultieEditSuccess = createAction(
  `${actionKey} Send Multie Edit Success`,
);

export const sendMultieEditFailure = createAction(
  `${actionKey} Send Multie Edit Failure`,
  props<{ cantUpdate: MultiEditFailureInterface[] }>()
);

export const loadDropdownContent = createAction(
  `${actionKey} Load DropdownContent`,
  props<{ filterKey: ServiceEquipmentFilterKeys, optionKey: ServiceEquipmentOptionsKeys, orderId?: number | null }>()
);

export const updateParams = createAction(
  `${actionKey} Load DropdownContent params`,
  props<{ key: string, value: string | number, filterKey: ServiceEquipmentFilterKeys, optionKey: ServiceEquipmentOptionsKeys, orderId?: number | null }>()
);

export const loadDropdownContentSuccess = createAction(
  `${actionKey} Load DropdownContent Success`,
  props<{ options: PaginatedResult<Company | LookupValue>, filterKey: ServiceEquipmentFilterKeys, optionKey: ServiceEquipmentOptionsKeys }>()
);

export const loadDropdownContentFailure = createAction(
  `${actionKey} Load DropdownContent Failure`,
  props<any>()
);

export const reorderColumns = createAction(
  `${actionKey} Reorder Columns`,
  props<{ columns: CommonColumn[] }>()
);

export const loadAssignablesFailure = createAction(
  `${actionKey} Load Assignables Failure`,
  props<any>()
);

export const loadAssignablesSuccess = createAction(
  `${actionKey} Load Assignables Success`,
  props<{subjects: SubjectInterface[]}>()
);

export const setSelectedServiceEquipment = createAction(
  `${actionKey} Set Selected Service Equipment`,
  props<{ serviceEquipment: ServiceEquipment | null }>()
);

export const saveServiceEquipment = createAction(
  `${actionKey} Save Service Equipment`,
  props<{ serviceEquipment: ServiceEquipment }>()
);

export const deleteServiceEquipment = createAction(
  `${actionKey} Delete Service Equipment`,
  props<{ serviceEquipment: ServiceEquipment }>()
);

export const saveServiceEquipmentSuccess = createAction(
  `${actionKey} Save Service Equipment Success`,
  props<{ serviceEquipment: ServiceEquipment | null }>()
);
