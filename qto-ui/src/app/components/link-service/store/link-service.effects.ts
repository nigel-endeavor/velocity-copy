import { Injectable } from "@angular/core";
import { Store } from "@ngrx/store";
import { Actions, concatLatestFrom, createEffect, ofType } from "@ngrx/effects";
import * as actions from "./link-service.actions";
import { switchMap, catchError, of, map } from "rxjs";
import { reloadOrder } from 'src/app/order-detail-page/ngrx/order-details.actions';

import { LinkServiceState } from "./link-service.reducer";
import { LinkServicesService } from "../../../services/link-services.service";
import { HttpErrorResponse } from "@angular/common/http";

@Injectable()
export class LinkServiceEffects {

  constructor(
    private actions$: Actions,
    private store: Store<LinkServiceState>,
    private linkService: LinkServicesService
  ) {}

  public saveLink = createEffect(() => this.actions$.pipe(
    ofType(
      actions.saveLink
    ),
    switchMap((action) => {
      return this.linkService.saveLink(action.incomingServiceId, action.selectedItems, action.linkType).pipe(
        switchMap((link: any) => [actions.saveLinkSuccess(), reloadOrder()]),
        catchError((error: HttpErrorResponse) => {
          return of(actions.saveLinkFailure({ errorMessage: error.message }));
        })
      );
    })
  ))

  public saveLinkInventory = createEffect(() => this.actions$.pipe(
    ofType(
      actions.saveLinkInventory
    ),
    switchMap((action) => {
      return this.linkService.saveLinkInventory(action.incomingServiceId, action.selectedItem, action.linkType).pipe(
        switchMap((link: any) => [actions.saveLinkInevntorySuccess(), reloadOrder()]),
        catchError((error: HttpErrorResponse) => {
          return of(actions.saveLinkInventoryFailure({ errorMessage: error.message }));
        })
      );
    })
  ))
}
