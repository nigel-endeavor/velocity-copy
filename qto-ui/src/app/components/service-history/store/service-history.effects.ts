import { Injectable } from '@angular/core';
import { Actions } from '@ngrx/effects';
import { Store } from '@ngrx/store';
import { ServiceHistoryState } from './service-history.reducer';

@Injectable()
export class ServiceHistoryEffects {

  constructor(
    private actions$: Actions,
    private store: Store<ServiceHistoryState>,
  ) {}

}