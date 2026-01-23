import { Injectable } from '@angular/core';

import { Actions, concatLatestFrom, createEffect, ofType } from '@ngrx/effects';
import { catchError, mergeMap, switchMap, withLatestFrom } from 'rxjs/operators';
import { select, Store } from '@ngrx/store';

import * as actions from './dispute-worklist.actions';
import { DisputeMeta, DisputeWorklistState } from './dispute-worklist.reducer';
import { filter, map, of } from 'rxjs';
import { LookupValueService } from '../../../services/lookup-value.service';
import { LookupValue } from '../../../models/lookup-value.model';
import { DisputeViewService } from '../../../services/dispute-view.service';
import { CompanySearchCriteria } from '../../../models/company-search-criteria';
import { PaginatedResult } from '../../../models/paginated-result.model';
import { Company } from '../../../models/company.model';
import { CompanyService } from '../../../services/company.service';
import { DisputeView } from '../../../models/dispute-view.model';
import { disputeTableActions, disputeTableSelectors } from '../configs/table.config';
import { DisputeMultiEditService } from '../../../services/disputeMultiEdit.service';
import { getSelectedItemsIds, getParamsByKey, getFilters } from './dispute-worklist.selectors';
import { clearStore } from '../../multi-edit/store/multie-edit.actions';
import { SubjectInterface } from 'src/app/models/subject.model';
import { SubjectService } from 'src/app/services/subject.service';

@Injectable()
export class DisputeWorklistEffects {

  constructor(
    private actions$: Actions,
    private store: Store<DisputeWorklistState>,
    private lookupValueService: LookupValueService,
    private companyService: CompanyService,
    private disputeWorklistService: DisputeViewService,
    private disputeMultieditService: DisputeMultiEditService,
    private subjectService: SubjectService
  ) { }

  public onUpdateLocation$ = createEffect(() => this.actions$.pipe(
    ofType(actions.updateDispute),
    switchMap((action) => {
      return this.disputeWorklistService.save(action.dispute).pipe(
        switchMap((dispute: DisputeView) => [
          disputeTableActions.loadTableData(),
          actions.setUpdatedItem({ updatedItem: dispute })
        ]),
        catchError(error => of(actions.updateDisputeFailure(error)))
      )
    })
  )
  );

  public onLoadProviders$ = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadDropdownContent,
      actions.updateParams
    ),
    concatLatestFrom((action) => [
      this.store.pipe(select(getParamsByKey(action.filterKey))),
    ]),
    filter(([action, _]) => action.optionKey === 'providers'),
    switchMap(([action, filters]) => {
      return this.lookupValueService.findFull('PROVIDER', undefined, filters).pipe(
        map((options: PaginatedResult<LookupValue>) => actions.loadDropdownContentSuccess({
          options, filterKey: action.filterKey, optionKey: action.optionKey
        })),
        catchError(error => of(actions.loadLookupValuesByKeyFailure(error)))
      )
    })
  ));

  public onLoadDisputeTypes$ = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadDropdownContent,
      actions.updateParams
    ),
    concatLatestFrom((action) => [
      this.store.pipe(select(getParamsByKey(action.filterKey))),
    ]),
    filter(([action, _]) => action.optionKey === 'disputeTypes'),
    switchMap(([action, filters]) => {
      return this.lookupValueService.findFull('DISPUTE_TYPE', undefined, filters).pipe(
        map((options: PaginatedResult<LookupValue>) => actions.loadDropdownContentSuccess({
          options, filterKey: action.filterKey, optionKey: action.optionKey
        })),
        catchError(error => of(actions.loadLookupValuesByKeyFailure(error)))
      )
    })
  ));

  public onLoadDisputeAssignments$ = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadDropdownContent,
      actions.updateParams
    ),
    concatLatestFrom((action) => [
      this.store.pipe(select(getParamsByKey(action.filterKey))),
    ]),
    filter(([action, _]) => action.optionKey === 'disputeAssignments'),
    switchMap(([action, filters]) => {
      return this.subjectService.getSubjects(action.orderId, filters, true).pipe(
        map((disputeAssignments: SubjectInterface[]) =>
          actions.loadDisputeAssignmentsSuccess({ disputeAssignments })),
        catchError(error => of(actions.loadDropdownContentFailure(error)))
      );
    })
  ));


  public onloadCustomersContent$ = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadDropdownContent,
      actions.updateParams
    ),
    concatLatestFrom((action) => [
      this.store.pipe(select(getParamsByKey(action.filterKey))),
    ]),
    filter(([action, params]) => action.optionKey === 'masterCustomers' || action.optionKey === 'customers'),
    mergeMap(([action, params]) => {
      let companyCriteria = new CompanySearchCriteria();
      companyCriteria.type = params.type || '';
      companyCriteria.name = params.name;
      companyCriteria.limit = params.limit;
      companyCriteria.offset = params.offset;
      return this.companyService.search(companyCriteria).pipe(
        map((customers: PaginatedResult<Company>) => actions.loadDropdownContentSuccess({ options: customers, filterKey: action.filterKey, optionKey: action.optionKey })),
        catchError(error => of(actions.loadDropdownContentFailure(error)))
      )
    })
  )
  );

  public onLoadLookupValue$ = createEffect(() => this.actions$.pipe(
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

  public onMultiEditSuccess = createEffect(() => this.actions$.pipe(
    ofType(
      actions.sendMultieEditSuccess
    ),
    // @ts-ignore
    mergeMap(() => {
      return [
        clearStore()
      ];
    })
  ));

  public onSendMuliedit$ = createEffect(() => this.actions$.pipe(
    ofType(actions.sendMultieEdit),
    withLatestFrom(this.store.pipe(select(getSelectedItemsIds))),
    mergeMap(([action, selectedIds]) => {
      const payload: {
        ids: number[],
        fieldValues: Record<string, string | number>,
        milestones: {
          code: string,
          date: number | null
        }[]
      } = {
        ids: selectedIds,
        fieldValues: {},
        milestones: []
      }
      Object.keys(action.formResult).forEach(key => {
        if (action.milestoneCodes.includes(key)) {
          if (action.formResult[key]) {
            const date = new Date(new Date(action.formResult[key]).setHours(0, 0, 0, 0));
            payload.milestones.push({
              code: key,
              date: date.getTime()
            })
          } else {
            payload.milestones.push({
              code: key,
              date: null
            })
          }
        } else if (!action.milestoneCodes.includes(key)) {
          if (key.includes('Date')) {
            payload.fieldValues[key] = new Date(new Date(action.formResult[key]).setHours(0, 0, 0, 0)).getTime();
            // @ts-ignore
          } else if (action.formResult[key]?.indexOf && (action.formResult[key].indexOf('}') > 0)) {
            // @ts-ignore
            const item = JSON.parse(action.formResult[key])
            payload.fieldValues[key] = item.value || item.id;
          } else {
            payload.fieldValues[key] = action.formResult[key];
          }
        }
      });
      return this.disputeMultieditService.sendMultiEdit(payload).pipe(
        switchMap((res) => {
          this.disputeMultieditService.onMultiEdit.emit();
          return [actions.sendMultieEditSuccess()]
        }))
    })
  ));

  public onLoadMetaData$ = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadMetaData,
      actions.updateFilters,
      actions.clearFilters
    ),
    concatLatestFrom(() => [
      this.store.pipe(select(getFilters)),
    ]),
    switchMap(([_, searchCriteria]) => {
      return this.disputeWorklistService.getDisputeWorklistMeta(searchCriteria).pipe(
        map((meta: DisputeMeta) => {
          return actions.loadMetaDataSuccess(meta)
        }),
        catchError(error => of(actions.loadMetaDataFailure(error)))
      )
    })
  )
  );

  // listens for the loadServiceTypes action
  public loadServiceTypes$ = createEffect(() =>
    this.actions$.pipe(
      ofType(actions.loadServiceTypes),
      switchMap(() =>
        this.disputeWorklistService.getServiceTypes().pipe(  // Calls the getServiceTypes() method
          map(serviceTypes => actions.loadServiceTypesSuccess({ serviceTypes })),  // Dispatch success action
          catchError(error => of(actions.loadServiceTypesFailure({ error })))  // Dispatch failure action if error occurs
        )
      )
    )
  );
}
