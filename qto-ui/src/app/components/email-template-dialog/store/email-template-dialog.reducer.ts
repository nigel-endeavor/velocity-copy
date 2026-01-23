import { createReducer, on } from '@ngrx/store';
import { EmailTemplate } from 'src/app/models/email-template.model';
import * as actions from './email-template-dialog.actions';

export const emailTemplateDialogFeatureKey = 'emailTemplateDialogState';


export interface EmailTemplateDialogState {
  templateList: EmailTemplate[];
  selectedTemplate: EmailTemplate | undefined;
  previewTemplateSubject: string | undefined;
  previewTemplateBody: string | undefined;
}

export const initialState: EmailTemplateDialogState = {
  templateList: [],
  selectedTemplate: undefined,
  previewTemplateSubject: undefined,
  previewTemplateBody: undefined
}

export const emailTemplateDialogReducer = createReducer(
  initialState,
  on(actions.loadEmailTemplateListSuccess, (state, action) => {
    return {
      ...state,
      templateList: action.templateList
    }
  }),
  on(actions.saveTemplateSuccess, (state, action) => {
    return {
      ...state,
      selectedTemplate: action.template
    }
  }),
  on(actions.deleteTemplateSuccess, (state) => {
    return {
      ...state,
      selectedTemplate: undefined
    }
  }),
  on(actions.setSelectedTemplate, (state, action) => {
    return {
      ...state,
      selectedTemplate: action.template
    }
  }),
  on(actions.clearState, (state, action) => {
    return {
      ...initialState
    }
  }),
  on(actions.updateTemplateState, (state, action) => {
    return {
      ...state,
      selectedTemplate: action.template
    }
  })
);
