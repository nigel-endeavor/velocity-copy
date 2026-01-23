import { Injectable } from '@angular/core';

import { Actions, concatLatestFrom, createEffect, ofType } from '@ngrx/effects';
import {catchError, mergeMap, switchMap, withLatestFrom} from 'rxjs/operators';
import {select, Store} from '@ngrx/store';

import * as actions from './location-inventory-worklist.actions';
import { LocationInventoryMeta, LocationInventoryWorklistState } from './location-inventory-worklist.reducer';
import { SubjectService } from '../../../services/subject.service';
import { map, of} from 'rxjs';
import { SubjectInterface } from '../../../models/subject.model';
import { LookupValueService } from '../../../services/lookup-value.service';
import { LookupValue } from '../../../models/lookup-value.model';
import { CompanyService } from '../../../services/company.service';
import { CompanySearchCriteria } from '../../../models/company-search-criteria';
import { Company } from '../../../models/company.model';
import { PaginatedResult } from '../../../models/paginated-result.model';
import { LocationViewService } from '../../../services/location-view.service';
import { LocationView } from '../../../models/location-view.model';
import { locationTableActions } from '../configs/ordering-table.config';
import {getCustomersParams, getFilters, getProvisionersParams, getCompanyNameParams} from "./location-inventory-worklist.selectors";
import { LocationInventoryViewService } from 'src/app/services/location-inventory-view.service';

@Injectable()
export class LocationInventoryWorklistEffects {

  constructor(
    private actions$: Actions,
    private store: Store<LocationInventoryWorklistState>,
    private lookupValueService: LookupValueService,
    private subjectService: SubjectService,
    private companyService: CompanyService,
    private locationViewService: LocationViewService,
    private locationInventoryViewService: LocationInventoryViewService,
  ) {}

  public onUpdateLocation$ = createEffect(() => this.actions$.pipe(
    ofType(actions.updateLocation),
    switchMap((action) => {
      return this.locationViewService.save(action.location).pipe(
        switchMap((location: LocationView) => [
          locationTableActions.loadTableData(),
          actions.setUpdatedItem({ updatedItem: location })
        ]),
        catchError(error => of(actions.updateLocationFailure(error)))
      )
      })
    )
  );

  public onLoadSubjects$ = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadProvisioners,
      actions.updateProvisionersParams
    ),
    withLatestFrom(this.store.pipe(select(getProvisionersParams))),
    switchMap(([action, filters]) => {
      return this.subjectService.getSubjects(action.orderId || null, filters).pipe(
        map((subjects: SubjectInterface[]) => actions.loadProvisionersSuccess({ subjects })),
        catchError(error => of(actions.loadProvisionersFailure(error)))
      )
      })
    )
  );

  public onLoadCompanyName$ = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadCompanyName,
      actions.updateCompanyNameParams
    ),
    withLatestFrom(this.store.pipe(select(getCompanyNameParams))),
    switchMap(([_, filters]) => {
      let companyCriteria = new CompanySearchCriteria();
      companyCriteria.type = filters.type || '';
      companyCriteria.name = filters.name;
      companyCriteria.limit = filters.limit;
      companyCriteria.offset = filters.offset;
      return this.companyService.search(companyCriteria).pipe(
        map((companyName: PaginatedResult<Company>) => actions.loadCompanyNameSuccess({ companyName })),
        catchError(error => of(actions.loadCompanyNameFailure(error)))
      )
      })
    )
  );

  public onLoadCustomers$ = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadCustomers,
      actions.updateParams
    ),
    withLatestFrom(this.store.pipe(select(getCustomersParams))),
    switchMap(([_, filters]) => {
      let companyCriteria = new CompanySearchCriteria();
      companyCriteria.type = filters.type || '';
      companyCriteria.name = filters.name;
      companyCriteria.limit = filters.limit;
      companyCriteria.offset = filters.offset;
      return this.companyService.search(companyCriteria).pipe(
        map((customers: PaginatedResult<Company>) => actions.loadCustomersSuccess({ customers })),
        catchError(error => of(actions.loadCustomersFailure(error)))
      )
      })
    )
  );

  public onLoadLookupValue$ = createEffect(() => this.actions$.pipe(
    ofType(actions.loadLookupValuesByKey),
    mergeMap((action) => {
      return this.lookupValueService.find(action.lookupKey, action.companyId).pipe(
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
      return this.locationInventoryViewService.getLocationInventoryWorklistMeta(searchCriteria).pipe(
        map((meta: LocationInventoryMeta) => {
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
          this.locationInventoryViewService.getServiceTypes().pipe(  // Calls the getServiceTypes() method
            map(serviceTypes => actions.loadServiceTypesSuccess({ serviceTypes })),  // Dispatch success action
            catchError(error => of(actions.loadServiceTypesFailure({ error })))  // Dispatch failure action if error occurs
          )
        )
      )
    );

}
