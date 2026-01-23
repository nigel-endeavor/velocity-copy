import { createAction, props } from '@ngrx/store';
import { SelectedItems } from './table-deprecated.reducer';
import { SubjectInterface } from '../../../models/subject.model';
import { LookupValue } from '../../../models/lookup-value.model';
import { MultiEditFailureInterface } from '../../../interfaces/multiEditFailure.interface';

export const addSelectedItems = createAction(
  '[TableDeprecated] Set Selected items',
  props<{ selectedItems: SelectedItems[] }>()
);

export const addSelectedItem = createAction(
  '[TableDeprecated] Set selected item',
  props<{ selectedItem: SelectedItems }>()
);

export const removeSelectedItems = createAction(
  '[TableDeprecated] Remove Selected',
  props<{ unSelectedItems: SelectedItems[] }>()
);

export const clearStore = createAction(
  '[TableDeprecated] Clear Store',
);

export const loadProvisioners = createAction(
  '[TableDeprecated] Load Provisioners',
);

export const loadProvisionersFailure = createAction(
  '[TableDeprecated] Load Provisioners Failure',
  props<any>()
);

export const loadProvisionersSuccess = createAction(
  '[TableDeprecated] Load Provisioners Success',
  props<{subjects: SubjectInterface[]}>()
);


export const loadLookupValuesByKey = createAction(
  '[TableDeprecated] Load Lookup Values by key',
  props<{key: string, lookupKey: string}>()
);

export const loadLookupValuesByKeyFailure = createAction(
  '[TableDeprecated] Load Lookup Values Failure',
  props<any>()
);

export const loadLookupValuesByKeySuccess = createAction(
  '[TableDeprecated] Load Lookup Values Success',
  props<{values: LookupValue[], key: string}>()
);

export const sendMultieEdit = createAction(
  '[TableDeprecated] Send Multie Edit',
  props<{formResult: Record<string, string | number>, milestoneCodes: string[]}>()
);

export const sendMultieEditSuccess = createAction(
  '[TableDeprecated] Send Multie Edit Success',
);

export const sendMultieEditFailure = createAction(
  '[TableDeprecated] Send Multie Edit Failure',
  props<{cantUpdate: MultiEditFailureInterface[] }>()

);
