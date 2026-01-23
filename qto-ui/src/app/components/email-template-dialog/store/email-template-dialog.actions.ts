import { createAction, props } from '@ngrx/store';
import { EmailTemplate } from '../../../models/email-template.model';

export const loadEmailTemplateList = createAction(
  '[EmailTemplateDialog] Load Email Template List',
  props<{ id: number, level: string }>()
);

export const loadEmailTemplateListSuccess = createAction(
  '[EmailTemplateDialog] Load Email Template List Success',
  props<{ templateList: EmailTemplate[] }>()
);

export const loadEmailTemplateListFailure = createAction(
  '[EmailTemplateDialog] Load Email Template List Failure',
  props<{ errorMessage: string }>()
);

export const saveTemplate = createAction(
  '[EmailTemplateDialog] Save a Template',
  props<{ template: EmailTemplate, id: number, level: string }>()
);

export const saveTemplateSuccess = createAction(
  '[EmailTemplateDialog] Save a Template Success',
  props<{ template: EmailTemplate }>()
);

export const saveTemplateFailure = createAction(
  '[EmailTemplateDialog] Save a Template Failure',
  props<{ errorMessage: string }>()
);

export const deleteTemplate = createAction(
  '[EmailTemplateDialog] Delete a Template',
  props<{ template: EmailTemplate, id: number, level: string }>()
);

export const deleteTemplateSuccess = createAction(
  '[EmailTemplateDialog] Delete a Template Success'
);

export const deleteTemplateFailure = createAction(
  '[EmailTemplateDialog] Delete a Template Failure',
  props<{ errorMessage: string }>()
);

export const setSelectedTemplate = createAction(
  '[EmailTemplateDialogForm] Set Selected Template',
  props<{ template: EmailTemplate | undefined }>()
);

export const clearState = createAction(
  '[EmailTemplateDialogForm] Clear State'
);

export const updateTemplateState = createAction(
  '[EmailTemplateDialogForm] Update Template State',
  props<{ template: EmailTemplate }>()
);

export const updateTemplateStateSuccess = createAction(
  '[EmailTemplateDialogForm] Update Template State Success'
);

export const updateTemplateStateFailure = createAction(
  '[EmailTemplateDialogForm] Update Template State Failure',
  props<{ errorMessage: string }>()
);
