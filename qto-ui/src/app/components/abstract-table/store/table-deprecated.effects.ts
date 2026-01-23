import { Injectable } from '@angular/core';

import { Actions, concatLatestFrom, createEffect, ofType } from '@ngrx/effects';
import { catchError, mergeMap, switchMap, tap, withLatestFrom } from 'rxjs/operators';
import { select, Store } from '@ngrx/store';

import * as actions from './table-deprecated.actions';
import * as selectors from './table-deprecated.selectors';
import { TableDeprecatedState, tableDeprecatedStoreFeatureKey } from './table-deprecated.reducer';
import { LocalStoreService } from '../../../features/local-store/local-store.service';
import { SubjectService } from '../../../services/subject.service';
import { map, of } from 'rxjs';
import { SubjectInterface } from '../../../models/subject.model';
import { LookupValueService } from '../../../services/lookup-value.service';
import { LookupValue } from '../../../models/lookup-value.model';
import { getSelectedIs } from './table-deprecated.selectors';
import { MultiEditService } from '../../../services/multiEdit.service';
import { MultiEditFailureInterface } from '../../../interfaces/multiEditFailure.interface';
import { clearStore } from '../../../features/multi-edit/store/multie-edit.actions';

@Injectable()
export class TableDeprecatedEffects {

  constructor(
    private actions$: Actions,
    private store: Store<TableDeprecatedState>,
    private localStorageService: LocalStoreService,
    private lookupValueService: LookupValueService,
    private subjectService: SubjectService,
    private multieditService: MultiEditService
  ) {}

  public onLoadSubjects = createEffect(() => this.actions$.pipe(
    ofType(actions.loadProvisioners),
    switchMap(() => {
      return this.subjectService.getSubjects().pipe(
        map((subjects: SubjectInterface[]) => actions.loadProvisionersSuccess({ subjects })),
        catchError(error => of(actions.loadProvisionersFailure(error)))
      )
      })
    )
  );

  public onLoadLookupValue = createEffect(() => this.actions$.pipe(
    ofType(actions.loadLookupValuesByKey),
    mergeMap((action) => {
      return this.lookupValueService.find(action.lookupKey).pipe(
        map(item => {
          return item
        }),
        map((result: LookupValue[]) => actions.loadLookupValuesByKeySuccess({
          key: action.key,
          values: result
        })),
        catchError(error => of(actions.loadLookupValuesByKeyFailure(error)))
      )
      })
    )
  );

  public onSendMuliedit = createEffect(() => this.actions$.pipe(
    ofType(actions.sendMultieEdit),
    withLatestFrom(this.store.pipe(select(getSelectedIs))),
    mergeMap(([action, selectedIds]) => {
      const payload: {
        serviceIds: number[],
        fieldValues: Record<string, string | number>,
        milestones: {
          code: string,
          date: number
        }[]
      } = {
        serviceIds: selectedIds,
        fieldValues: {},
        milestones: []
      }
      Object.keys(action.formResult).forEach(key => {
        if (action.milestoneCodes.includes(key) && action.formResult[key]) {
          const date = new Date(new Date(action.formResult[key]).setHours(0, 0, 0, 0));
          payload.milestones.push({
            code: key,
            date: date.getTime()
          })
        } else if (!action.milestoneCodes.includes(key)) {
          if (key.includes('Date')) {
            payload.fieldValues[key] = new Date(new Date(action.formResult[key]).setHours(0, 0, 0, 0)).getTime();
          } else {
            payload.fieldValues[key] = action.formResult[key];
          }
        }
      });
      return this.multieditService.sendMultiEdit(payload).pipe(
        map((res: { cantEdit: MultiEditFailureInterface[] }) => {
          if (res.cantEdit.length === 0) {
            return actions.sendMultieEditSuccess()
          }
          return actions.sendMultieEditFailure({cantUpdate: res.cantEdit})
        }))
    })
  ));

  public onMultiEditSuccess = createEffect(() => this.actions$.pipe(
    ofType(
      actions.sendMultieEditSuccess
    ),
    // @ts-ignore
    switchMap(() => {
      return [
        actions.clearStore(),
        clearStore()
      ]
    })
  ));

  public onChangeTableDeprecatedState = createEffect(() => this.actions$.pipe(
    ofType(
      actions.addSelectedItems,
      actions.addSelectedItem,
      actions.removeSelectedItems,
      actions.clearStore,
    ),
    // @ts-ignore
    concatLatestFrom(() => this.store.pipe(select(selectors.selectTableDeprecatedState))),
    tap(([_, state]: [null, TableDeprecatedState]) => {
      this.localStorageService.setItem(tableDeprecatedStoreFeatureKey, JSON.stringify(state));
    })
  ), { dispatch: false });
}
