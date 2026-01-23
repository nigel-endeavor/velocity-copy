import { Injectable } from '@angular/core';

import { Actions, concatLatestFrom, createEffect, ofType } from '@ngrx/effects';
import { catchError, filter, mergeMap, switchMap } from 'rxjs/operators';
import { select, Store } from '@ngrx/store';

import * as actions from './service-equipment.actions';
import { ServiceEquipmentState } from './service-equipment.reducer';
import { map, of} from 'rxjs';
import { getFilters } from './service-equipment.selectors';
import { SubjectService } from '../../../../services/subject.service';
import { SubjectInterface } from '../../../../models/subject.model';
import { ServiceEquipment } from '../../../../models/service-equipment-model';
import { ServiceEquipmentService } from '../../../../services/service-equipment.service';
import { equipmentTableActions } from '../configs/table.config';
import { LookupValueService } from '../../../../services/lookup-value.service';

@Injectable()
export class ServiceEquipmentEffects {

  constructor(
    private actions$: Actions,
    private store: Store<ServiceEquipmentState>,
    private customerWorklistService: ServiceEquipmentService,
    private serviceEquipmentService: ServiceEquipmentService,
    private subjectService: SubjectService,
    private lookupValueService: LookupValueService
  ) {}

  public loadLookupValues$ = createEffect(() => this.actions$.pipe(
    ofType(actions.loadLookupValuesByKey),
    mergeMap(action => {
      return this.lookupValueService.find(action.lookupKey).pipe(
        map(values => actions.loadLookupValuesByKeySuccess({ key: action.key, values })),
        catchError(error => of(actions.loadLookupValuesByKeyFailure({ error })))
      )
    })
  ));

  saveServiceEquipment$ = createEffect(() => this.actions$.pipe(
    ofType(actions.saveServiceEquipment),
    switchMap((action) => {
      return this.serviceEquipmentService.save(action.serviceEquipment).pipe(
        map((serviceEquipment: ServiceEquipment) => actions.saveServiceEquipmentSuccess({ serviceEquipment }))
      )
    })
  ));

  deleteServiceEquipment$ = createEffect(() => this.actions$.pipe(
    ofType(actions.deleteServiceEquipment),
    switchMap((action) => {
      return this.serviceEquipmentService.delete(action.serviceEquipment).pipe(
        map((serviceEquipment: ServiceEquipment) => actions.saveServiceEquipmentSuccess({ serviceEquipment: null }))
      )
    })
  ));

  loadServiceEquipment$ = createEffect(() => this.actions$.pipe(
    ofType(actions.saveServiceEquipmentSuccess),
    switchMap(() => [
      equipmentTableActions.loadTableData()
    ])
  ));
}
