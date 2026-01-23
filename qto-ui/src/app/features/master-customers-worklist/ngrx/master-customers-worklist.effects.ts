import { Injectable } from '@angular/core';

import { Actions, concatLatestFrom, createEffect, ofType } from '@ngrx/effects';
import { catchError, filter, switchMap } from 'rxjs/operators';
import { select, Store } from '@ngrx/store';

import * as actions from './master-customers-worklist.actions';
import { CustomersMeta, MasterCustomersWorklistState } from './master-customers-worklist.reducer';
import { map, of} from 'rxjs';
import { getFilters, getParamsByKey } from './master-customers-worklist.selectors';

import { CompanyViewService } from '../../../services/company-view.service';
import { SubjectService } from '../../../services/subject.service';
import { SubjectInterface } from '../../../models/subject.model';
import { customerTableActions } from '../configs/table.config';

@Injectable()
export class MasterCustomersWorklistEffects {

  constructor(
    private actions$: Actions,
    private store: Store<MasterCustomersWorklistState>,
    private customerWorklistService: CompanyViewService,
    private subjectService: SubjectService
  ) {}

  public onLoadMetaData$ = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadMetaData,
      actions.updateFilters,
      actions.clearFilters,
      customerTableActions.loadTableData
    ),
    concatLatestFrom(() => [
      this.store.pipe(select(getFilters)),
    ]),
    switchMap(([_, searchCriteria]) => {
      return this.customerWorklistService.getCustomerWorklistMeta(searchCriteria).pipe(
        map((meta: CustomersMeta) => {
          return actions.loadMetaDataSuccess(meta)
        }),
        catchError(error => of(actions.loadMetaDataFailure(error)))
      )
    })
    )
  );

  public onLoadAssignables$ = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadDropdownContent,
      actions.updateParams
    ),
    concatLatestFrom((action) => [
      this.store.pipe(select(getParamsByKey(action.filterKey))),
    ]),
    filter(([action, _]) => action.optionKey === 'assignables'),
    switchMap(([action, filters]) => {
      return this.subjectService.getSubjects(action.orderId, filters).pipe(
        map((subjects: SubjectInterface[]) => actions.loadAssignablesSuccess({ subjects })),
        catchError(error => of(actions.loadAssignablesFailure(error)))
      )
    })
  ));
}
