import { createAction, props } from '@ngrx/store';

export const updateFormState = createAction(
  '[MultieEdit] Set Form State',
  props<{ formState: Record<string, string | number> }>()
);

export const clearStore = createAction(
  '[MultieEdit] Remove Form State',
);
