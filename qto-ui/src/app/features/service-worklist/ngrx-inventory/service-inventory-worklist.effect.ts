import { Injectable } from '@angular/core';

import { Actions, concatLatestFrom, createEffect, ofType } from '@ngrx/effects';
import { catchError, filter, map, mergeMap, switchMap, withLatestFrom } from 'rxjs/operators';
import { select, Store } from '@ngrx/store';

import * as actions from './service-inventory-worklist.actions';
import { of } from 'rxjs';
import { CompanySearchCriteria } from 'src/app/models/company-search-criteria';
import { Company } from 'src/app/models/company.model';
import { LevelOfEffort } from 'src/app/models/level-of-effort.model';
import { LookupValue } from 'src/app/models/lookup-value.model';
import { PaginatedResult } from 'src/app/models/paginated-result.model';
import { ServiceView } from 'src/app/models/service-view.model';
import { SubjectInterface } from 'src/app/models/subject.model';
import { CompanyService } from 'src/app/services/company.service';
import { LevelOfEffortService } from 'src/app/services/level-of-effort.service';
import { LookupValueService } from 'src/app/services/lookup-value.service';
import { MultiEditService } from 'src/app/services/multiEdit.service';
import { ServiceViewService } from 'src/app/services/service-view.service';
import { SubjectService } from 'src/app/services/subject.service';
import { clearStore } from '../../multi-edit/store/multie-edit.actions';
import { serviceTableActions } from '../configs/table.config';
import { getFilters, getParamsByKey, getSelectedItemsIds } from '../ngrx-inventory/service-inventory-worklist.selectors';
import { ServiceInventoryMeta, ServiceInventoryWorklistState } from './service-inventory-worklist.reducer';
import { ServiceInventoryViewService } from 'src/app/services/service-inventory-view.service';


@Injectable()
export class ServiceInventoryWorklistEffects {

  constructor(
    private actions$: Actions,
    private store: Store<ServiceInventoryWorklistState>,
    private lookupValueService: LookupValueService,
    private subjectService: SubjectService,
    private companyService: CompanyService,
    private serviceWorklistService: ServiceViewService,
    private serviceInventoryViewService: ServiceInventoryViewService,
    private multieditService: MultiEditService,
    private levelOfEffortService: LevelOfEffortService
  ) {}

  public onUpdateLocation$ = createEffect(() => this.actions$.pipe(
      ofType(actions.updateService),
      switchMap((action) => {
        return this.serviceWorklistService.save(action.service).pipe(
          switchMap((service: ServiceView) => [
            serviceTableActions.loadTableData(),
            actions.setUpdatedItem({ updatedItem: service })
          ]),
          catchError(error => of(actions.updateServiceFailure(error)))
        )
      })
    )
  );

  public onLoadSubjects$ = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadDropdownContent,
      actions.updateParams
    ),
    concatLatestFrom((action) => [
      this.store.pipe(select(getParamsByKey(action.filterKey))),
    ]),
    filter(([action, _]) => action.optionKey === 'provisioners'),
    switchMap(([action, filters]) => {
      return this.subjectService.getSubjects(action.orderId, filters).pipe(
        map((subjects: SubjectInterface[]) => actions.loadProvisionersSuccess({ subjects })),
        catchError(error => of(actions.loadProvisionersFailure(error)))
      )
    })
  ));

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
        map((customers: PaginatedResult<LookupValue>) => actions.loadDropdownContentSuccess({
          customers, filterKey: action.filterKey, optionKey: action.optionKey
        })),
        catchError(error => of(actions.loadLookupValuesByKeyFailure(error)))
      )
    })
  ));

  public onLoadLevelOfEffort$ = createEffect(() => this.actions$.pipe(
    ofType(actions.loadLevelOfEffort),
    switchMap((action) => {
      return this.levelOfEffortService.findByCompanyId(null).pipe(
        map((levelOfEffort: LevelOfEffort[]) => actions.loadLevelOfEffortSuccess({ levelOfEffort })),
        catchError(error => of(actions.loadLevelOfEffortFailure(error)))
      )
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
    filter(([action, params]) => action.optionKey === 'masterCustomers' ||  action.optionKey === 'customers'),
    mergeMap(([action, params]) => {
        let companyCriteria = new CompanySearchCriteria();
        companyCriteria.type = params.type || '';
        companyCriteria.name = params.name;
        companyCriteria.limit = params.limit;
        companyCriteria.offset = params.offset;
        return this.companyService.search(companyCriteria).pipe(
          map((customers: PaginatedResult<Company>) => actions.loadDropdownContentSuccess({ customers, filterKey: action.filterKey, optionKey: action.optionKey })),
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
          date: number
        }[]
      } = {
        ids: selectedIds,
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
      return this.multieditService.sendMultiEdit(payload).pipe(
        switchMap((res) => {
          this.multieditService.onMultiEdit.emit();
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
      return this.serviceInventoryViewService.getServiceInventoryWorklistMeta(searchCriteria).pipe(
        map((meta: ServiceInventoryMeta) => {
          return actions.loadMetaDataSuccess(meta)
        })
      )
    })
    )
  );

    // listens for the loadServiceTypes action
    public loadServiceTypes$ = createEffect(() =>
      this.actions$.pipe(
        ofType(actions.loadServiceTypes),  // Listens for the loadServiceTypes action
        switchMap(() => 
          this.serviceInventoryViewService.getServiceTypes().pipe(  // Calls the getServiceTypes() method
            map(serviceTypes => actions.loadServiceTypesSuccess({ serviceTypes })),  // Dispatch success action
            catchError(error => of(actions.loadServiceTypesFailure({ error })))  // Dispatch failure action if error occurs
          )
        )
      )
    );
}
