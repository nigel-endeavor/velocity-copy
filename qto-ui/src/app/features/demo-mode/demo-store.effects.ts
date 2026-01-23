import { Injectable } from '@angular/core';

import { Actions, concatLatestFrom, createEffect, ofType } from '@ngrx/effects';
import { tap } from 'rxjs/operators';
import { select, Store } from '@ngrx/store';

import * as actions from './demo-store.actions';
import * as selectors from './demo-store.selectors';
import { DemoStoreState, demoStoreFeatureKey } from './demo-store.reducer';
import { LocalStoreService } from '../local-store/local-store.service';

@Injectable()
export class DemoStoreEffects {

  constructor(
    private actions$: Actions,
    private store: Store<DemoStoreState>,
    private localStorageService: LocalStoreService,
  ) {}

  public onChangeDemoStoreState$ = createEffect(() => this.actions$.pipe(
    ofType(
      actions.toggleDemoMode,
      actions.toggleSlowdown,
      actions.slowdownSecondsChanged
    ),
    // @ts-ignore
    concatLatestFrom(() => this.store.pipe(select(selectors.selectDemoStoreState))),
    tap(([_, state]: [null, DemoStoreState]) => {
      this.localStorageService.setItem(demoStoreFeatureKey, JSON.stringify(state));
    })
  ), { dispatch: false });
}
