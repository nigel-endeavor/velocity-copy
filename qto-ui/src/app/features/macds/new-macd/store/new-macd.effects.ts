import { Injectable } from '@angular/core';

import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, switchMap, withLatestFrom } from 'rxjs/operators';
import { Store, select } from '@ngrx/store';
import * as actions from './new-macd.actions';

import { NewMacdState } from './new-macd.reducer';
import { LookupValueService } from '../../../../services/lookup-value.service';
import { MacdService } from '../../../../services/macd.service';
import { selectCreateLinkedBundled, selectMacdNote } from './new-macd.selectors';
import { of } from 'rxjs';
import { AbstractFileAttachmentService } from 'src/app/services/abstract-file-attachment.service';
import { BaseSearchCriteria } from 'src/app/models/base-search-criteria.model';

@Injectable()
export class NewMacdEffects {

  constructor(
    private actions$: Actions,
    private store: Store<NewMacdState>,
    private lookupValueService: LookupValueService,
    private macdService: MacdService,
    private abstractFileAttachmentService: AbstractFileAttachmentService
  ) { }

  public loadOrderTypes = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadOrderTypes
    ),
    switchMap((action) => {
      return this.lookupValueService.find('ORDER_TYPE', action.companyId).pipe(
        switchMap((orderTypes: any) => [actions.loadOrderTypesSuccess({ orderTypes })]),
      );
    })
  ));

  public loadServiceTypes = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadServiceTypes
    ),
    switchMap((action) => {
      return this.lookupValueService.find('TENANT_SERVICE_TYPES', action.companyId).pipe(
        switchMap((serviceTypes: any) => [actions.loadServiceTypesSuccess({ serviceTypes })]),
      );
    })
  ));

  public loadSubOrderTypes = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadSubOrderTypes
    ),
    switchMap((action) => {
      return this.lookupValueService.find('SUB_ORDER_TYPE', action.companyId).pipe(
        switchMap((subOrderTypes: any) => [actions.loadSubOrderTypesSuccess({ subOrderTypes })]),
      );
    })
  ));

  public loadDisconnectReasons = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadSubOrderTypes
    ),
    switchMap((action) => {
      return this.lookupValueService.find('DISCONNECT_REASON', action.companyId).pipe(
        switchMap((disconnectReasons: any) => [actions.loadDisconnectReasonsSuccess({ disconnectReasons })]),
      );
    })
  ));

  public loadProjectNames = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadProjectNames
    ),
    switchMap((action) => {
      return this.lookupValueService.find('PROJECT_NAME', action.companyId).pipe(
        switchMap((projectNames: any) => [actions.loadProjectNamesSuccess({ projectNames })]),
      );
    })
  ));

  public saveMacd = createEffect(() => this.actions$.pipe(
    ofType(actions.saveMacd),
    withLatestFrom(this.store.pipe(select(selectMacdNote)), this.store.pipe(select(selectCreateLinkedBundled))),
    switchMap(([action, macdNote, createLinkedBundled]) => {
      // Convert macds to a structure the backend expects
      const macdMap = action.macds.map((macd) => ({
        orderType: macd.orderType.value,
        subOrderType: macd.subOrderType ? macd.subOrderType.value : null,
        createDisconnectUponCompletion: macd.createDisconnectUponCompletion,
        disconnectReason: macd.disconnectReason ? macd.disconnectReason.value : null,
        serviceType: macd.serviceType ? macd.serviceType.value : null,
        projectName: macd.projectName ? macd.projectName.value : null
      }));

      if (action.fileAttachment) {

        return this.abstractFileAttachmentService.upload([action.fileAttachment], new BaseSearchCriteria()).pipe(
          switchMap((attachmentResponse: any) => {

            if (!action.isMultiMacd) {
              return this.macdService.createMacd({
                serviceId: action.serviceIds[0],
                macds: macdMap,
                macdNote: macdNote,
                createLinkedBundled: createLinkedBundled,
                fileAttachments: JSON.parse(attachmentResponse)
              }).pipe(
                map((result: any) => actions.saveMacdSuccess({ result })),

              );
            } else {
              return this.macdService.submitMultiMacd({
                ids: action.serviceIds,
                macds: macdMap,
                macdNote: macdNote,
                fileAttachments: JSON.parse(attachmentResponse)
              }).pipe(
                map(() => {
                  this.macdService.onMacd.emit();
                  return actions.submitMacdsSuccess({ result: 'accepted' });
                }),

              );
            }
          }),

        );
      } else {

        if (!action.isMultiMacd) {
          return this.macdService.createMacd({
            serviceId: action.serviceIds[0],
            macds: macdMap,
            macdNote: macdNote,
            createLinkedBundled: createLinkedBundled
          }).pipe(
            map((result: any) => actions.saveMacdSuccess({ result })),
          );
        } else {
          return this.macdService.submitMultiMacd({
            ids: action.serviceIds,
            macds: macdMap,
            macdNote: macdNote
          }).pipe(
            map(() => {
              this.macdService.onMacd.emit();
              return actions.submitMacdsSuccess({ result: 'accepted' });
            }),
          );
        }
      }
    })
  ));

}
