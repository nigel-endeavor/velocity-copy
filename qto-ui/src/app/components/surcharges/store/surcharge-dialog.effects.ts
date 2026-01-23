import { Injectable } from '@angular/core';

import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, switchMap } from 'rxjs/operators';
import { Store } from '@ngrx/store';
import * as actions from './surcharge-dialog.actions';
import { of } from 'rxjs';

import { SurchargeDialogState } from './surcharge-dialog.reducer';
import { ServiceSurchargeService } from '../../../services/service-surcharge.service';
import { ServiceSurcharge } from '../../../models/service-surcharge-model';
import { HttpErrorResponse } from '@angular/common/http';
import { surchargeTableActions } from '../configs/table.config';
import { SurchargeTypeService } from '../../../services/surcharge-type.service';
import { SurchargeType } from '../../../models/surcharge-type.model';
import { PaginatedResult } from '../../../models/paginated-result.model';

@Injectable()
export class SurchargeDialogEffects {

  constructor(
    private actions$: Actions,
    private store: Store<SurchargeDialogState>,
    private serviceSurchargeService: ServiceSurchargeService,
    private surchargeTypeService: SurchargeTypeService
  ) {}

  public saveSurcharge = createEffect(() => this.actions$.pipe(
    ofType(
      actions.saveSurcharge
    ),
    switchMap((action) => {
      return this.serviceSurchargeService.save(action.surcharge).pipe(
        switchMap((surcharge: ServiceSurcharge) => [actions.saveSurchargeSuccess({ surcharge }), surchargeTableActions.loadTableData()]),
        catchError((error: HttpErrorResponse) => {
          return of(actions.saveSurchargeFailure({ errorMessage: error.message }));
        })
      );
    })
  ))

  public deleteSurcharge = createEffect(() => this.actions$.pipe(
    ofType(
      actions.deleteSurcharge
    ),
    switchMap((action) => {
      return this.serviceSurchargeService.delete(action.surcharge).pipe(
        switchMap((surcharge: ServiceSurcharge) => [actions.deleteSurchargeSuccess({ surcharge }), surchargeTableActions.loadTableData()]),
        catchError((error: HttpErrorResponse) => {
          return of(actions.saveSurchargeFailure({ errorMessage: error.message }));
        })
      );
    })
  ))

  public loadSurchargeTypes = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadSurchargeTypes
    ),
    switchMap((action) => {
      return this.surchargeTypeService.retrieveByCompanyId(action.companyId).pipe(
        map((surchargeTypes: PaginatedResult<SurchargeType>) => actions.loadSurchargeTypesSuccess({ surchargeTypes: surchargeTypes.collection })),
        catchError((error: HttpErrorResponse) => {
          return of(actions.loadSurchargeTypesFailure({ errorMessage: error.message }));
        })
      );
    })
  ))
}
