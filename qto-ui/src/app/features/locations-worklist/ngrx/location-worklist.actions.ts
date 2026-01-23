import { createAction, props } from '@ngrx/store';
import { SubjectInterface } from '../../../models/subject.model';
import { LookupValue } from '../../../models/lookup-value.model';
import { PaginatedResult } from '../../../models/paginated-result.model';
import { Company } from '../../../models/company.model';
import { LocationView } from '../../../models/location-view.model';
import { ComparableDateRange } from '../../../interfaces/date-range.interface';
import { CommonColumn } from '../../../interfaces/columns.interface';

export const updateFilters = createAction(
  '[LocationWorklist] update filters',
  props<{ key: string, value: string | number | string[] | boolean  | ComparableDateRange }>()
);

export const updateSort = createAction(
  '[LocationWorklist] update sort',
  props<{ sort: {dir: string, col: string } }>()
);

export const clearFilters = createAction(
  '[LocationWorklist] clear filters',
);

export const pageDestroyed = createAction(
  '[LocationWorklist] page Destroyed',
);

export const loadProvisioners = createAction(
  '[LocationWorklist] Load Provisioners',
  props<{ orderId: number | null }>()
);

export const loadProvisionersFailure = createAction(
  '[LocationWorklist] Load Provisioners Failure',
  props<any>()
);

export const loadProvisionersSuccess = createAction(
  '[LocationWorklist] Load Provisioners Success',
  props<{subjects: SubjectInterface[]}>()
);

export const loadLookupValuesByKey = createAction(
  '[LocationWorklist] Load Lookup Values by key',
  props<{key: string, lookupKey: string, companyId?: number}>()
);

export const loadLookupValuesByKeyFailure = createAction(
  '[LocationWorklist] Load Lookup Values Failure',
  props<any>()
);

export const loadLookupValuesByKeySuccess = createAction(
  '[LocationWorklist] Load Lookup Values Success',
  props<{values: LookupValue[], key: string}>()
);

export const loadCustomers = createAction(
  '[LocationWorklist] Load Customers',
);

export const updateParams = createAction(
  '[LocationWorklist] Load Customers params',
  props<{ key: string, value: string | number }>()
);

export const updateEndCustomerParams = createAction(
  '[LocationWorklist] Load End Customers params',
  props<{ key: string, value: string | number }>()
);

export const updateProvisionersParams = createAction(
  '[LocationWorklist] Load Provisioners params',
  props<{ key: string, value: string | number, orderId?: number | null }>()
);

export const loadCustomersSuccess = createAction(
  '[LocationWorklist] Load Customers Success',
  props<{customers: PaginatedResult<Company>}>()
);

export const loadCustomersFailure = createAction(
  '[LocationWorklist] Load Customers Failure',
  props<any>()
);

export const loadEndCustomers = createAction(
  '[LocationWorklist] Load End Customers',
);

export const loadEndCustomersSuccess = createAction(
  '[LocationWorklist] Load End Customers Success',
  props<{endCustomers: PaginatedResult<Company>}>()
);

export const loadEndCustomersFailure = createAction(
  '[LocationWorklist] Load End Customers Failure',
  props<any>()
);

export const toggleView = createAction(
  '[LocationWorklist] Toggle View'
);

export const toggleColumn = createAction(
  '[LocationWorklist] Toggle Column',
  props<{ columnName: string }>()
);

export const reorderColumns = createAction(
  '[LocationWorklist] Reorder Columns',
  props<{ columns: CommonColumn[] }>()
);

export const setUpdatedItem = createAction(
  '[LocationWorklist] Updated Item',
  props<{ updatedItem: LocationView }>()
);

export const updateLocaion = createAction(
  '[LocationWorklist] Location update',
  props<{ location: LocationView }>()
);

export const updateLocaionFailure = createAction(
  '[LocationWorklist] Location Update Failure',
  props<any>()
);

export const loadServiceTypes = createAction(
  '[LocationWorklist] Load Service Types',
);

export const loadServiceTypesSuccess = createAction(
  '[LocationWorklist] Load Service Types Success',
  props<{ serviceTypes: string[] }>()
);

export const loadServiceTypesFailure = createAction(
  '[LocationWorklist] Load Service Types Failure',
  props<any>()
);
