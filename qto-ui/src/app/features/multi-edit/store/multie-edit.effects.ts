import { Injectable } from '@angular/core';

import { Actions, concatLatestFrom, createEffect, ofType } from '@ngrx/effects';
import { tap } from 'rxjs/operators';
import { select, Store } from '@ngrx/store';

import * as actions from './multie-edit.actions';
import * as selectors from './multie-edit.selectors';
import { MultiEditState, multieEditFeatureKey } from './multie-edit.reducer';
import { LocalStoreService } from '../../local-store/local-store.service';
import {selectMultiEditState} from "./multie-edit.selectors";

@Injectable()
export class MultieEditEffects {

  constructor(
    private actions$: Actions,
    private store: Store<MultiEditState>,
    private localStorageService: LocalStoreService,
  ) {}

 public onMultieEditState = createEffect(() => this.actions$.pipe(
    ofType(
      actions.updateFormState,
      actions.clearStore,
    ),
    // @ts-ignore
    concatLatestFrom(() => this.store.pipe(select(selectors.selectMultiEditState))),
    tap(([_, state]: [null, MultiEditState]) => {
      this.localStorageService.setItem(multieEditFeatureKey, JSON.stringify(state));
    })
  ), { dispatch: false });
}
