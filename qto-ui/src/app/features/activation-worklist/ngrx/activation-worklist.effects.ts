import { Injectable } from '@angular/core';

import { Actions, concatLatestFrom, createEffect, ofType } from '@ngrx/effects';
import { catchError, mergeMap, switchMap, withLatestFrom } from 'rxjs/operators';
import { select, Store } from '@ngrx/store';

import * as actions from './activation-worklist.actions';
import { ActivationWorklistState } from './activation-worklist.reducer';
import { filter, map, of} from 'rxjs';
import { LookupValueService } from '../../../services/lookup-value.service';
import { LookupValue } from '../../../models/lookup-value.model';
import { ActivationViewService } from '../../../services/activation-view.service';
import { ActivationWorklistMeta } from '../../../models/activation-view.model';
import { getFilters, getParamsByKey, getStatusesValues } from './activation-worklist.selectors';
import { CompanySearchCriteria } from '../../../models/company-search-criteria';
import { PaginatedResult } from '../../../models/paginated-result.model';
import { CompanyService } from '../../../services/company.service';
import { Company } from '../../../models/company.model';

@Injectable()
export class ActivationWorklistEffects {

  constructor(
    private actions$: Actions,
    private store: Store<ActivationWorklistState>,
    private lookupValueService: LookupValueService,
    private activationViewService: ActivationViewService,
    private companyService: CompanyService
  ) {}

  public onLoadMetaData$ = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadMetaData,
      actions.updateFilters,
      actions.loadLookupValuesByKeySuccess,
      actions.resetFilters
    ),
    concatLatestFrom(() => [
      this.store.pipe(select(getFilters)),
      this.store.pipe(select(getStatusesValues))
    ]),
    filter(([_, searchCriteria, statuses]) => !!statuses.length),
    switchMap(([_, searchCriteria, statuses]) => {
      return this.activationViewService.getActivationWorklistMeta(searchCriteria).pipe(
        map((meta: ActivationWorklistMeta) => {
          let statusCounts: { status: string; count: number; }[] = [];
          statuses.forEach(status => statusCounts.push({ status: status, count: 0 }));
          for (const status in meta.statusCounts) {
            let index = statusCounts.findIndex(sc => sc.status == status);
            if (index > -1) {
              statusCounts[index].count = meta.statusCounts[status];
            } else {
              statusCounts.push({ status: status, count: meta.statusCounts[status] });
            }
          }
          let zone = new Date().toLocaleTimeString('en-us', { timeZoneName: 'short' }).split(' ')[2];
          const header = searchCriteria.scheduledCheckInTime.isEmpty ? 'Date unscheduled' : 'Schedule Status for '
            + new Date( searchCriteria.scheduledCheckInTime?.dateRange?.startDate + ' ' + zone).toLocaleString('en-us', { dateStyle: 'short' })
            + ' - ' + new Date( searchCriteria.scheduledCheckInTime?.dateRange?.endDate + ' ' + zone).toLocaleString('en-us', { dateStyle: 'short' });
          return actions.loadMetaDataSuccess({
            statusCounts: statusCounts,
            ttuEquivTotal:  meta.ttuEquivalentTotal,
            statusCountHeader: header
          })
        }),
        catchError(error => of(actions.loadMetaDataFailure(error)))
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
          map((customers: PaginatedResult<Company>) => actions.loadDropdownContentSuccess({ options: customers, filterKey: action.filterKey, optionKey: action.optionKey })),
          catchError(error => of(actions.loadDropdownContentFailure(error)))
        )
      })
    )
  );
}
