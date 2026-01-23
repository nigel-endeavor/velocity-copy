import { createAction, props } from '@ngrx/store';
import { ComparableDateRange } from '../../../interfaces/date-range.interface';
import { ServiceSurcharge } from '../../../models/service-surcharge-model';
import { SurchargeType } from '../../../models/surcharge-type.model';

export const updateFilters = createAction(
  '[SurchargeDialog] update filters',
  props<{ key: string, value: string | number | string[] | boolean | ComparableDateRange }>()
);

export const updateSort = createAction(
  '[SurchargeDialog] update sort',
  props<{ sort: {dir: string, col: string } }>()
);

export const toggleView = createAction(
  '[SurchargeDialog] Toggle View'
);

export const clearFilters = createAction(
  '[SurchargeDialog] clear filters',
);

export const pageDestroyed = createAction(
  '[SurchargeDialog] page destroyed',
);

export const createSurcharge = createAction(
  '[SurchargeDialog] Create Surcharge',
  props<{ id: number }>()
);

export const setSelectedSurcharge = createAction(
  '[SurchargeDialog] Set Selected Surcharge',
  props<{ surcharge: ServiceSurcharge | undefined }>()
);

export const saveSurcharge = createAction(
  '[SurchargeDialog] Save a surcharge',
  props<{ surcharge: ServiceSurcharge }>()
);

export const saveSurchargeSuccess = createAction(
  '[SurchargeDialog] Save a surcharge Success',
  props<{ surcharge: ServiceSurcharge }>()
);

export const saveSurchargeFailure = createAction(
  '[SurchargeDialog] Save a surcharge Failure',
  props<{ errorMessage: string }>()
);

export const deleteSurcharge = createAction(
  '[SurchargeDialog] Delete a surcharge',
  props<{ surcharge: ServiceSurcharge }>()
);

export const deleteSurchargeSuccess = createAction(
  '[SurchargeDialog] Delete a surcharge Success',
  props<{ surcharge: ServiceSurcharge }>()
);

export const deleteSurchargeFailure = createAction(
  '[SurchargeDialog] Delete a surcharge Failure',
  props<{ errorMessage: string }>()
);

export const updateSurcharge = createAction(
  '[SurchargeDialog] Update a surcharge',
  props<{ key: string, value: Date | SurchargeType }>()
);

export const loadSurchargeTypes = createAction(
  '[SurchargeDialog] Get surcharge types',
  props<{ companyId: number }>()
);

export const loadSurchargeTypesSuccess = createAction(
  '[SurchargeDialog] Get surcharge types Success',
  props<{ surchargeTypes: SurchargeType[] }>()
);

export const loadSurchargeTypesFailure = createAction(
  '[SurchargeDialog] Get surcharge types Failure',
  props<{ errorMessage: string }>()
);