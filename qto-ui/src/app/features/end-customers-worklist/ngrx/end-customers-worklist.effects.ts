import { Injectable } from '@angular/core';

import { Actions, concatLatestFrom, createEffect, ofType } from '@ngrx/effects';
import { catchError, switchMap } from 'rxjs/operators';
import { select, Store } from '@ngrx/store';

import * as actions from './end-customers-worklist.actions';
import { CustomersMeta, EndCustomersWorklistState } from './end-customers-worklist.reducer';
import { map, of} from 'rxjs';
import { getFilters } from './end-customers-worklist.selectors';

import { CompanyViewService } from '../../../services/company-view.service';
import { customerTableActions } from "../configs/table.config";

@Injectable()
export class EndCustomersWorklistEffects {

  constructor(
    private actions$: Actions,
    private store: Store<EndCustomersWorklistState>,
    private customerWorklistService: CompanyViewService
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
}
