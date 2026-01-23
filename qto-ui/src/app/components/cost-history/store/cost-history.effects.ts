import { Injectable } from "@angular/core";
import { Actions, concatLatestFrom, createEffect, ofType } from "@ngrx/effects";
import { Store, select } from "@ngrx/store";
import { CostHistoryState } from "./cost-history.reducer";
import { CostHistoryService } from "src/app/services/cost-history.service";
import * as actions from './cost-history.actions';
import * as selectors from './cost-history.selectors';
import { map, switchMap } from "rxjs";
import { CostHistoryMeta } from "src/app/models/cost-history.model";

@Injectable()
export class CostHistoryEffects {

  constructor(
    private actions$: Actions,
    private store: Store<CostHistoryState>,
    private costHistoryService: CostHistoryService
  ) {}

  public onLoadMetaData$ = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadMetaData
    ),
    concatLatestFrom(() => [
      this.store.pipe(select(selectors.getFilters))
    ]),
    switchMap(([_, searchCriteria]) => {
      return this.costHistoryService.getCostHistoryMeta(searchCriteria).pipe(
        map((meta: CostHistoryMeta) => {
          return actions.loadMetaDataSuccess(meta);
        })
      )
    })
  ));

}