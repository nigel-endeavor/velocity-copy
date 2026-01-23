import { createFeatureSelector, createSelector } from '@ngrx/store';

import * as reducer from './email-template-dialog.reducer';

export const selectEmailTemplateDialogState
  = createFeatureSelector<reducer.EmailTemplateDialogState>(reducer.emailTemplateDialogFeatureKey);

export const getTemplateList = createSelector(selectEmailTemplateDialogState,
  (state: reducer.EmailTemplateDialogState) => {
  return state.templateList;
  }
);

export const getSelectedTemplate = createSelector(selectEmailTemplateDialogState,
  (state: reducer.EmailTemplateDialogState) => {
    return state.selectedTemplate;
  }
);