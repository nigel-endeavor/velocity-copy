import { Injectable } from '@angular/core';

import { Actions, concatLatestFrom, createEffect, ofType } from '@ngrx/effects';
import { catchError, mergeMap, switchMap, withLatestFrom } from 'rxjs/operators';
import { select, Store } from '@ngrx/store';

import * as actions from './service-worklist.actions';
import { ServiceWorklistState } from './service-worklist.reducer';
import { filter, map, of } from 'rxjs';
import { LookupValueService } from '../../../services/lookup-value.service';
import { LookupValue } from '../../../models/lookup-value.model';
import { ServiceViewService } from '../../../services/service-view.service';
import { SubjectInterface } from '../../../models/subject.model';
import { SubjectService } from '../../../services/subject.service';
import { CompanySearchCriteria } from '../../../models/company-search-criteria';
import { PaginatedResult } from '../../../models/paginated-result.model';
import { Company } from '../../../models/company.model';
import { CompanyService } from '../../../services/company.service';
import { ServiceView } from '../../../models/service-view.model';
import { serviceTableActions } from '../configs/table.config';
import { MultiEditService } from '../../../services/multiEdit.service';
import { getSelectedItemsIds, getParamsByKey } from './service-worklist.selectors';
import { clearStore } from '../../multi-edit/store/multie-edit.actions';
import { LevelOfEffort } from '../../../models/level-of-effort.model';
import { LevelOfEffortService } from '../../../services/level-of-effort.service';

@Injectable()
export class ServiceWorklistEffects {

  constructor(
    private actions$: Actions,
    private store: Store<ServiceWorklistState>,
    private lookupValueService: LookupValueService,
    private subjectService: SubjectService,
    private companyService: CompanyService,
    private serviceWorklistService: ServiceViewService,
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

  public onLoadProvisioners$ = createEffect(() => this.actions$.pipe(
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

  public onLoadQaManagers$ = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadDropdownContent,
      actions.updateParams
    ),
    concatLatestFrom((action) => [
      this.store.pipe(select(getParamsByKey(action.filterKey))),
    ]),
    filter(([action, _]) => action.optionKey === 'qaManagers'),
    switchMap(([action, filters]) => {
      return this.subjectService.getSubjects(action.orderId, filters).pipe(
        map((subjects: SubjectInterface[]) => actions.loadQaManagersSuccess({ subjects }))
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
            });
          } else {
            payload.milestones.push({
              code: key,
              date: null
            });
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
      return this.multieditService.sendMultiEdit(payload).pipe(
        switchMap((res) => {
          this.multieditService.onMultiEdit.emit();
          return [actions.sendMultieEditSuccess()]
        }))
    })
  ));

      // listens for the loadServiceTypes action
      public loadServiceTypes$ = createEffect(() =>
        this.actions$.pipe(
          ofType(actions.loadServiceTypes),
          switchMap(() => 
            this.serviceWorklistService.getServiceTypes().pipe(  // Calls the getServiceTypes() method
              map(serviceTypes => actions.loadServiceTypesSuccess({ serviceTypes })),  // Dispatch success action
              catchError(error => of(actions.loadServiceTypesFailure({ error })))  // Dispatch failure action if error occurs
            )
          )
        )
      );
}
