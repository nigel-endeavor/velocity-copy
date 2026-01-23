import { Injectable } from '@angular/core';

import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, switchMap, withLatestFrom } from 'rxjs/operators';

import * as actions from './email-template-dialog.actions'
import { EmailTemplateService } from 'src/app/services/emial-template.service';
import { of } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { PaginatedResult } from 'src/app/models/paginated-result.model';
import { EmailTemplate } from 'src/app/models/email-template.model';
import { Store } from '@ngrx/store';

@Injectable()
export class EmailTempolateDialogEffects {

  constructor(
    private actions$: Actions,
    private emailTemplateService: EmailTemplateService,
    private store: Store
  ) {}

  public loadEmaiLTemplateList = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadEmailTemplateList
    ),
    switchMap((action) => {
      return this.emailTemplateService.retrieveByTypeAndEntityId(action.level, action.id).pipe(
        map((templateList: PaginatedResult<EmailTemplate>) => actions.loadEmailTemplateListSuccess({ templateList: templateList.collection })),
        catchError((error: HttpErrorResponse) => {
          return of(actions.loadEmailTemplateListFailure({ errorMessage: error.message }));
        })
      );
    })
  ))

  public saveEmailTemplate = createEffect(() => this.actions$.pipe(
    ofType(
      actions.saveTemplate
    ),
    switchMap((action) => {
      return this.emailTemplateService.save(action.template).pipe(
        switchMap((template: EmailTemplate) => [actions.saveTemplateSuccess({ template }), actions.loadEmailTemplateList({ level: action.level, id: action.id })]),
        catchError((error: HttpErrorResponse) => {
          return of(actions.saveTemplateFailure({ errorMessage: error.message }));
        })
      );
    })
  ))

  public deleteEmailTemplate = createEffect(() => this.actions$.pipe(
    ofType(
      actions.deleteTemplate
    ),
    switchMap((action) => {
      return this.emailTemplateService.delete(action.template).pipe(
        switchMap((template: EmailTemplate) => [actions.deleteTemplateSuccess(), actions.loadEmailTemplateList({ level: action.level, id: action.id })]),
        catchError((error: HttpErrorResponse) => {
          return of(actions.saveTemplateFailure({ errorMessage: error.message }));
        })
      );
    })
  ))
}
