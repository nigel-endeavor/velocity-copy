import { createAction, props } from '@ngrx/store';
import { LookupValue } from '../../../../models/lookup-value.model';
import { Service } from '../../../../models/service.model';
import { FileAttachment } from 'src/app/models/file-attachment';


export const loadOrderTypes = createAction(
  '[New MACD] Load Order Types',
  props<{ companyId: number }>()
);

export const loadOrderTypesSuccess = createAction(
  '[New MACD] Load Order Types Success',
  props<{ orderTypes: LookupValue[] }>()
);
export const loadServiceTypes = createAction(
  '[New MACD] Load Service Types',
  props<{ companyId: number }>()
);

export const loadServiceTypesSuccess = createAction(
  '[New MACD] Load Service Types Success',
  props<{ serviceTypes: LookupValue[] }>()
);

export const loadSubOrderTypes = createAction(
  '[New MACD] Load Sub Order Types',
  props<{ companyId: number }>()
);

export const loadSubOrderTypesSuccess = createAction(
  '[New MACD] Load Sub Order Types Success',
  props<{ subOrderTypes: LookupValue[] }>()
);

export const loadDisconnectReasons = createAction(
  '[New MACD] Load Disconnect Reasons',
  props<{ companyId: number }>()
);

export const loadDisconnectReasonsSuccess = createAction(
  '[New MACD] Load Disconnect Reasons Success',
  props<{ disconnectReasons: LookupValue[] }>()
);

export const loadProjectNames = createAction(
  '[New MACD] Load Project Names',
  props<{ companyId: number }>()
);

export const loadProjectNamesSuccess = createAction(
  '[New MACD] Load Project Names Success',
  props<{ projectNames: LookupValue[] }>()
);

export const updateMacdSelection = createAction(
  '[New MACD] Update MACD Selection',
  props<{ selection: number, key: string, value: LookupValue | boolean | string }>()
);

export const addMacd = createAction(
  '[New MACD] Add MACD'
);

export const removeMacd = createAction(
  '[New MACD] Remove MACD',
  props<{ index: number }>()
);

export const pageDestroyed = createAction(
  '[New MACD] page destroyed',
);

export const saveMacd = createAction(
  '[New MACD] Save MACD',
  props<{ serviceIds: number[], macds: any[], isMultiMacd: boolean, fileAttachment: FileAttachment }>()
);

export const saveMacdSuccess = createAction(
  '[Macd] Save Macd Success',
  props<{ result: Service }>()
);

export const submitMacdsSuccess = createAction(
  '[Macd] Submit Macds Success',
  props<{ result: any }>()
);

export const updateMacdNote = createAction(
  '[MacdNote] Update',
  props<{ macdNote: string }>()
);

export const updateCreateLinkedBundled = createAction(
  '[CreateLinkedBundled] Update',
  props<{ createLinkedBundled: boolean }>()
);
