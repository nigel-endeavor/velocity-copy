import { createAction, props } from '@ngrx/store';
import { SubjectInterface } from '../../../models/subject.model';
import { LookupValue } from '../../../models/lookup-value.model';
import { PaginatedResult } from '../../../models/paginated-result.model';
import { Company } from '../../../models/company.model';
import { LocationView } from '../../../models/location-view.model';
import { ComparableDateRange } from '../../../interfaces/date-range.interface';
import { LocationInventoryMeta } from './location-inventory-worklist.reducer';
import { CommonColumn } from 'src/app/interfaces/columns.interface';

export const updateFilters = createAction(
  '[LocationInventoryWorklist] update filters',
  props<{ key: string, value: string | number | string[] | boolean  | ComparableDateRange }>()
);

export const updateSort = createAction(
  '[LocationInventoryWorklist] update sort',
  props<{ sort: {dir: string, col: string } }>()
);

export const clearFilters = createAction(
  '[LocationInventoryWorklist] clear filters',
);

export const pageDestroyed = createAction(
  '[LocationInventoryWorklist] page Destroyed',
);

// Provisioners
export const loadProvisioners = createAction(
  '[LocationInventoryWorklist] Load Provisioners',
  props<{ orderId: number | null }>()
);

export const updateProvisionersParams = createAction(
  '[LocationInventoryWorklist] Load Provisioners params',
  props<{ key: string, value: string | number, orderId?: number | null }>()
);

export const loadProvisionersFailure = createAction(
  '[LocationInventoryWorklist] Load Provisioners Failure',
  props<any>()
);

export const loadProvisionersSuccess = createAction(
  '[LocationInventoryWorklist] Load Provisioners Success',
  props<{subjects: SubjectInterface[]}>()
);

export const loadLookupValuesByKey = createAction(
  '[LocationInventoryWorklist] Load Lookup Values by key',
  props<{key: string, lookupKey: string, companyId?: number}>()
);

export const loadLookupValuesByKeyFailure = createAction(
  '[LocationInventoryWorklist] Load Lookup Values Failure',
  props<any>()
);

export const loadLookupValuesByKeySuccess = createAction(
  '[LocationInventoryWorklist] Load Lookup Values Success',
  props<{values: LookupValue[], key: string}>()
);

export const loadCustomers = createAction(
  '[LocationInventoryWorklist] Load Customers',
);

export const updateParams = createAction(
  '[LocationInventoryWorklist] Load Customers params',
  props<{ key: string, value: string | number }>()
);

// company name
export const loadCompanyName = createAction(
  '[LocationInventoryWorklist] Load Company Name',
);

export const updateCompanyNameParams = createAction(
  '[LocationInventoryWorklist] Load Customers params',
  props<{ key: string, value: string | number}>()
);

export const loadCompanyNameSuccess = createAction(
  '[LocationInventoryWorklist] Load Company Name Success',
  props<{companyName: PaginatedResult<Company>}>()
);

export const loadCompanyNameFailure = createAction(
  '[LocationInventoryWorklist] Load Company Name Failure',
  props<any>()
);

export const loadCustomersSuccess = createAction(
  '[LocationInventoryWorklist] Load Customers Success',
  props<{customers: PaginatedResult<Company>}>()
);

export const loadCustomersFailure = createAction(
  '[LocationInventoryWorklist] Load Customers Failure',
  props<any>()
);

export const toggleView = createAction(
  '[LocationInventoryWorklist] Toggle View'
);

export const toggleColumn = createAction(
  '[LocationInventoryWorklist] Toggle Column',
  props<{columnName: string}>()
);

export const setUpdatedItem = createAction(
  '[LocationInventoryWorklist] Updated Item',
  props<{updatedItem: LocationView}>()
);

export const updateLocation = createAction(
  '[LocationInventoryWorklist] Location update',
  props<{ location: LocationView }>()
);

export const updateLocationFailure = createAction(
  '[LocationInventoryWorklist] Location Update Failure',
  props<any>()
);

export const loadMetaData = createAction(
  '[LocationInventoryWorklist] Load Meta Data',
);

export const loadMetaDataFailure = createAction(
  '[LocationInventoryWorklist] Load Meta Data Failure',
  props<any>()
);

export const loadMetaDataSuccess = createAction(
  '[LocationInventoryWorklist] Load Meta Data Success',
  props<LocationInventoryMeta>()
);

export const reorderColumns = createAction(
  '[LocationInventoryWorklist] Reorder Columns',
  props<{ columns: CommonColumn[] }>()
);

export const loadServiceTypes = createAction(
  '[LocationInventoryWorklist] Load Service Types',
);

export const loadServiceTypesSuccess = createAction(
  '[LocationInventoryWorklist] Load Service Types Success',
  props<{ serviceTypes: string[] }>()
);

export const loadServiceTypesFailure = createAction(
  '[LocationInventoryWorklist] Load Service Types Failure',
  props<any>()
);
