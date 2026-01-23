import { Injectable } from '@angular/core';

import { Actions, concatLatestFrom, createEffect, ofType } from '@ngrx/effects';
import {catchError, filter, mergeMap, switchMap} from 'rxjs/operators';
import { select, Store } from '@ngrx/store';

import * as actions from './dashboards.actions';
import { DashboardsState } from './dashboards.reducer';
import { CompanySearchCriteria } from '../../../models/company-search-criteria';
import {forkJoin, map, of} from 'rxjs';
import { PaginatedResult } from '../../../models/paginated-result.model';
import { Company } from '../../../models/company.model';
import { getFilters, getParamsByKey, getTabFiltersByKey } from './dashboards.selectors';
import { CompanyService } from '../../../services/company.service';
import { WipService } from '../../../services/wip.service';
import { WipServiceView } from '../../../models/wip-service-view.model';
import { WipServiceJeopView } from '../../../models/wip-service-jeop-view.model';
import { ProvidersService } from '../../../services/providers.service';
import { ProviderIntervalsView } from '../../../models/provider-intervals-view.model';
import { ActivationAttemptViewService } from '../../../services/activation-attempt-view.service';
import { arrangeDataInMonthlyOrder } from '../utils/arrangeDataInMonthlyOrder';
import { getIntervalAverages } from '../utils/intervaAverages';
import { groupByDate } from '../utils/groupBytDate';
import { ActivationAttemptView } from '../../../models/activation-attempt-view-model';
import { WipLocationJeopView } from '../../../models/wip-location-jeop-view.model';
import { ServiceSnapshotService } from 'src/app/services/service-snapshot.service';
import { LookupValue } from '../../../models/lookup-value.model';
import { LookupValueService } from '../../../services/lookup-value.service';

@Injectable()
export class DashboardsEffects {

  constructor(
    private actions$: Actions,
    private store: Store<DashboardsState>,
    private companyService: CompanyService,
    private wipService: WipService,
    private providersService: ProvidersService,
    private activationAttemptViewService: ActivationAttemptViewService,
    private lookupValueService: LookupValueService,
    private serviceSnapshotService: ServiceSnapshotService
  ) {}

  public onLoadCustomers$ = createEffect(() => this.actions$.pipe(
      ofType(
        actions.loadCustomers,
        actions.updateCustomerParams
      ),
      concatLatestFrom((action) => [
        this.store.pipe(select(getParamsByKey(action.filterKey))),
        this.store.pipe(select(getFilters))
      ]),
      mergeMap(([action, params, filters]) => {
        let companyCriteria = new CompanySearchCriteria();
        companyCriteria.type = params.type || '';
        companyCriteria.name = params.name;
        companyCriteria.limit = params.limit;
        companyCriteria.offset = params.offset;
        if (filters.selectedMasterCompanies && params.type === 'End Customers') {
          companyCriteria.masterCustomers = filters.selectedMasterCompanies?.split(',') || [];
        }
        if (params.tenants) {
          companyCriteria.tenants = params.tenants?.split(',') || [];
        }
        return this.companyService.search(companyCriteria).pipe(
          map((customers: PaginatedResult<Company>) => actions.loadCustomersSuccess({ customers, filterKey: action.filterKey, optionKey: action.optionKey, clear: action.clear })),
          catchError(error => of(actions.loadCustomersFailure(error)))
        )
      })
    )
  );

  public onLoadWipServices$ = createEffect(() => this.actions$.pipe(
      ofType(
        actions.loadWipServices,
        actions.updateFilters
      ),
      concatLatestFrom((action) => [
        this.store.pipe(select(getFilters))
      ]),
      mergeMap(([_, filters]) => {
        return this.wipService.getWipServices({
          tenantNames: filters.selectedTenants?.split(', ') || [],
          masterCompanyNames: filters.selectedMasterCompanies?.split(', ') || [],
          companyNames: filters.selectedEndCompanies?.split(', ') || [],
          serviceTypes: filters.selectedServiceTypes?.split(', ') || [],
          providers: filters.selectedProviders?.split(', ') || [],
          serviceBilledTos: filters.selectedServiceBilledTos?.split(', ') || []
      }).pipe(
          map((res: WipServiceView[]) => actions.loadWipServicesSuccess({ wipServiceViews: res })),
          catchError(error => of(actions.loadWipServicessFailure(error)))
        )
      })
    )
  );

  public onLoadAllWipServices$ = createEffect(() => this.actions$.pipe(
      ofType(
        actions.loadAllWipServices,
        actions.updateFilters
      ),
      concatLatestFrom((action) => [
        this.store.pipe(select(getFilters)),
        this.store.pipe(select(getTabFiltersByKey('wip')))
      ]),
      mergeMap(([_, filters, tabFilters, ]) => {
        return this.wipService.getWipServices({
            tenantNames: filters.selectedTenants?.split(',') || [],
            masterCompanyNames: filters.selectedMasterCompanies?.split(',') || [],
            companyNames: filters.selectedEndCompanies?.split(',') || [],
            serviceTypes: filters.selectedServiceTypes?.split(',') || [],
            providers: filters.selectedProviders?.split(',') || [],
            serviceBilledTos: filters.selectedServiceBilledTos?.split(',') || []
          },
          true
        ).pipe(
          map((res: WipServiceView[]) => actions.loadAllWipServicesSuccess({ wipAllServiceViews: res })),
          catchError(error => of(actions.loadAllWipServicessFailure(error)))
        )
      })
    )
  );

  public onLoadWipServicesJeops$ = createEffect(() => this.actions$.pipe(
      ofType(
        actions.loadWipServiceJeops,
        actions.updateFilters
      ),
      concatLatestFrom((action) => [
        this.store.pipe(select(getFilters)),
        this.store.pipe(select(getTabFiltersByKey('wip')))
      ]),
      mergeMap(([_, filters, tabFilters, ]) => {
        return this.wipService.getWipServiceJeops({
          tenantNames: filters.selectedTenants?.split(',') || [],
          masterCompanyNames: filters.selectedMasterCompanies?.split(',') || [],
          companyNames: filters.selectedEndCompanies?.split(',') || [],
          serviceTypes: filters.selectedServiceTypes?.split(',') || [],
          providers: filters.selectedProviders?.split(',') || [],
          serviceBilledTos: filters.selectedServiceBilledTos?.split(',') || []
        }).pipe(
          map((res: WipServiceJeopView[]) => actions.loadWipServiceJeopsSuccess({ wipServiceJeopViews: res })),
          catchError(error => of(actions.loadWipServiceJeopsFailure(error)))
        )
      })
    )
  );

  public onLoadWipLocationsJeops$ = createEffect(() => this.actions$.pipe(
      ofType(
        actions.loadWipLocationJeops,
        actions.updateFilters
      ),
      concatLatestFrom((action) => [
        this.store.pipe(select(getFilters)),
        this.store.pipe(select(getTabFiltersByKey('wip')))
      ]),
      mergeMap(([_, filters, tabFilters, ]) => {
        return this.wipService.getWipLocationJeops({
          tenantNames: filters.selectedTenants?.split(',') || [],
          masterCompanyNames: filters.selectedMasterCompanies?.split(',') || [],
          companyNames: filters.selectedEndCompanies?.split(',') || [],
          serviceTypes: filters.selectedServiceTypes?.split(',') || [],
          providers: filters.selectedProviders?.split(',') || [],
          serviceBilledTos: filters.selectedServiceBilledTos?.split(',') || []
        }).pipe(
          map((res: WipLocationJeopView[]) => actions.loadWipLocationJeopsSuccess({ wipLocationJeopViews: res })),
          catchError(error => of(actions.loadWipLocationJeopsFailure(error)))
        )
      })
    )
  );

  public loadProviderInstallIntervals$ = createEffect(() => this.actions$.pipe(
      ofType(
        actions.loadProviderInstallIntervals,
        actions.updateFilters
      ),
      concatLatestFrom((action) => [
        this.store.pipe(select(getFilters)),
        this.store.pipe(select(getTabFiltersByKey('providers')))
      ]),
      mergeMap(([action, filters, tabFilters, ] : [any, any, any]) => {
        let numOfMonths = tabFilters[action.chart]?.allTime ? 0 : 6;

        return this.providersService.getProviderIntervals({
            tenantNames: filters.selectedTenants?.split(',') || [],
            masterCompanyNames: filters.selectedMasterCompanies?.split(',') || [],
            companyNames: filters.selectedEndCompanies?.split(',') || [],
            serviceTypes: filters.selectedServiceTypes?.split(',') || [],
            providers: filters.selectedProviders?.split(',') || [],
            serviceBilledTos: filters.selectedServiceBilledTos?.split(',') || []
          },
          'PROVIDER_ORDER_SUBMITTED_TO_DATA_PROVISIONING_COMPLETE',
          numOfMonths
        ).pipe(
          map((res: ProviderIntervalsView[]) => actions.loadProviderInstallIntervalsSuccess({ provInstallIntervals: res })),
          catchError(error => of(actions.loadProviderInstallIntervalsFailure(error)))
        )
      })
    )
  );

  public loadProviderSurveyIntervals$ = createEffect(() => this.actions$.pipe(
      ofType(
        actions.loadProviderSurveyIntervals,
        actions.updateFilters
      ),
      concatLatestFrom((action) => [
        this.store.pipe(select(getFilters)),
        this.store.pipe(select(getTabFiltersByKey('providers')))
      ]),
      mergeMap(([action, filters, tabFilters, ] : [any, any, any]) => {
        let numOfMonths = tabFilters[action.chart]?.allTime ? 0 : 6;

        return this.providersService.getProviderIntervals({
            tenantNames: filters.selectedTenants?.split(',') || [],
            masterCompanyNames: filters.selectedMasterCompanies?.split(',') || [],
            companyNames: filters.selectedEndCompanies?.split(',') || [],
            serviceTypes: filters.selectedServiceTypes?.split(',') || [],
            providers: filters.selectedProviders?.split(',') || [],
            serviceBilledTos: filters.selectedServiceBilledTos?.split(',') || []
          },
          'SITE_SURVEY_SUBMIT_TO_SITE_SURVEY_COMPLETE',
          numOfMonths
        ).pipe(
          map((res: ProviderIntervalsView[]) => actions.loadProviderSurveyIntervalsSuccess({ provSurveyIntervals: res })),
          catchError(error => of(actions.loadProviderSurveyIntervalsFailure(error)))
        )
      })
    )
  );

  public loadProviderReliance$ = createEffect(() => this.actions$.pipe(
      ofType(
        actions.loadProviderReliance,
        actions.updateFilters
      ),
      concatLatestFrom((action) => [
        this.store.pipe(select(getFilters)),
        this.store.pipe(select(getTabFiltersByKey('providers')))
      ]),
      mergeMap(([action, filters, tabFilters, ] : [any, any, any]) => {
        let numOfMonths = tabFilters[action.chart]?.allTime ? 0 : 6;
        return this.providersService.getProviderReliance({
            tenantNames: filters.selectedTenants?.split(',') || [],
            masterCompanyNames: filters.selectedMasterCompanies?.split(',') || [],
            companyNames: filters.selectedEndCompanies?.split(',') || [],
            serviceTypes: filters.selectedServiceTypes?.split(',') || [],
            providers: filters.selectedProviders?.split(',') || [],
            serviceBilledTos: filters.selectedServiceBilledTos?.split(',') || []
          },
          numOfMonths
        ).pipe(
          map((res: WipServiceView[]) => actions.loadProviderRelianceSuccess({ providerReliance: res })),
          catchError(error => of(actions.loadProviderRelianceFailure(error)))
        )
      })
    )
  );

  public onLoadMonthlySpend$ = createEffect(() => this.actions$.pipe(
      ofType(
        actions.loadMonthlySpend,
        actions.updateFilters
      ),
      concatLatestFrom((action) => [
        this.store.pipe(select(getFilters))
      ]),
      mergeMap(([_, filters]) => {
        return this.wipService.getMonthlySpend({
          tenantNames: filters.selectedTenants?.split(',') || [],
          masterCompanyNames: filters.selectedMasterCompanies?.split(',') || [],
          companyNames: filters.selectedEndCompanies?.split(',') || [],
          serviceTypes: filters.selectedServiceTypes?.split(',') || [],
          providers: filters.selectedProviders?.split(',') || [],
          serviceBilledTos: filters.selectedServiceBilledTos?.split(',') || []
        }).pipe(
          map((res: WipServiceView[]) => actions.loadMonthlySpendSuccess({ wipServiceViews: res })),
          catchError(error => of(actions.loadMonthlySpendFailure(error)))
        )
      })
    )
  );

  public loadUnbillableNetworkExpenseAccrual$ = createEffect(() => this.actions$.pipe(
      ofType(
        actions.loadUnbillableNetworkExpenseAccrual,
        actions.updateFilters
      ),
      concatLatestFrom((action) => [
        this.store.pipe(select(getFilters))
      ]),
      mergeMap(([_, filters]) => {
        return this.wipService.getUnbillableNetworkExpenseAccrual({
          tenantNames: filters.selectedTenants?.split(',') || [],
          masterCompanyNames: filters.selectedMasterCompanies?.split(',') || [],
          companyNames: filters.selectedEndCompanies?.split(',') || [],
          serviceTypes: filters.selectedServiceTypes?.split(',') || [],
          providers: filters.selectedProviders?.split(',') || [],
          serviceBilledTos: filters.selectedServiceBilledTos?.split(',') || []
        }).pipe(
          map((res: WipServiceView[]) => actions.loadUnbillableNetworkExpenseAccrualSuccess({ wipServiceViews: res })),
          catchError(error => of(actions.loadUnbillableNetworkExpenseAccrualFailure(error)))
        )
      })
    )
  );

  public loadIncrementalNetworkSpend$ = createEffect(() => this.actions$.pipe(
      ofType(
        actions.loadIncrementalNetworkSpend,
        actions.updateFilters
      ),
      concatLatestFrom((action) => [
        this.store.pipe(select(getFilters)),
        this.store.pipe(select(getTabFiltersByKey('financials')))
      ]),
      mergeMap(([_, filters, tabFilters]: [any, any, any]) => {
        return this.wipService.getIncrementalNetworkSpend({
          tenantNames: filters.selectedTenants?.split(',') || [],
          masterCompanyNames: filters.selectedMasterCompanies?.split(',') || [],
          companyNames: filters.selectedEndCompanies?.split(',') || [],
          serviceTypes: filters.selectedServiceTypes?.split(',') || [],
          providers: filters.selectedProviders?.split(',') || [],
          serviceBilledTos: filters.selectedServiceBilledTos?.split(',') || []
        }).pipe(
          map((res: WipServiceView[]) => actions.loadIncrementalNetworkSpendSuccess({ wipServiceViews: res })),
          catchError(error => of(actions.loadIncrementalNetworkSpendFailure(error)))
        )
      })
    )
  );

  public loadServiceIntervals$ = createEffect(() => this.actions$.pipe(
      ofType(
        actions.loadServiceIntervals,
        actions.updateFilters
      ),
      concatLatestFrom((action) => [
        this.store.pipe(select(getFilters))
      ]),
      mergeMap(([_, filters]) => {
        return this.activationAttemptViewService.getServiceIntervals({
            tenantNames: filters.selectedTenants?.split(',') || [],
            masterCompanyNames: filters.selectedMasterCompanies?.split(',') || [],
            companyNames: filters.selectedEndCompanies?.split(',') || [],
            serviceTypes: filters.selectedServiceTypes?.split(',') || [],
            providers: filters.selectedProviders?.split(',') || [],
            serviceBilledTos: filters.selectedServiceBilledTos?.split(',') || []
          },
          6
        ).pipe(
          map((res: ActivationAttemptView[]) => actions.loadServiceIntervalsSuccess({ activationAttempt: res })),
          catchError(error => of(actions.loadServiceIntervalsFailure(error)))
        )
      })
    )
  );

  public loadProviderAverages$ = createEffect(() => this.actions$.pipe(
    ofType(
        actions.loadProviderAverages,
        actions.updateFilters

      ),
      concatLatestFrom((action) => [
        this.store.pipe(select(getFilters))
      ]),
      mergeMap(([action, filters]) => {
        return forkJoin([this.providersService.getProviderIntervals({
            tenantNames: filters.selectedTenants?.split(',') || [],
            masterCompanyNames: filters.selectedMasterCompanies?.split(',') || [],
            companyNames: filters.selectedEndCompanies?.split(',') || [],
            serviceTypes: filters.selectedServiceTypes?.split(',') || [],
            providers: filters.selectedProviders?.split(',') || [],
            serviceBilledTos: filters.selectedServiceBilledTos?.split(',') || []
          },
          'RECEIVED_TO_COMPLETE',
          6
        ), this.providersService.getProviderIntervals({
            tenantNames: filters.selectedTenants?.split(',') || [],
            masterCompanyNames: filters.selectedMasterCompanies?.split(',') || [],
            companyNames: filters.selectedEndCompanies?.split(',') || [],
            serviceTypes: filters.selectedServiceTypes?.split(',') || [],
            providers: filters.selectedProviders?.split(',') || [],
            serviceBilledTos: filters.selectedServiceBilledTos?.split(',') || []
          },
          'RECEIVED_TO_DATA_PROVISIONING_COMPLETE',
          6
        )]).pipe(
          switchMap(([toComplete, receivedData]) => {

            let last6Months = [];
            for (let i = 0; i < 6; i++) {
              let date = new Date();
              date.setDate(1);
              date.setMonth(date.getMonth() - i);
              let label = `${date.toLocaleString('default', { month: 'long' })}`;
              last6Months.unshift(label);
            }
            const rtcEndDateGroup = groupByDate('endDate', toComplete);
            const cdiAverages = getIntervalAverages(rtcEndDateGroup);
            const cdiArranged = arrangeDataInMonthlyOrder(cdiAverages, last6Months);
            let dpcEndDateGroup = groupByDate('endDate', receivedData);
            const npiAverages = getIntervalAverages(dpcEndDateGroup);
            let npiArranged = arrangeDataInMonthlyOrder(npiAverages, last6Months);
            return of(actions.loadProviderAveragesSuccess({
              cdiArranged,
              npiArranged
            }));
          }),
          catchError(error => of(actions.loadProviderAveragesFailure(error)))
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
      console.log('loadProviders$', action.type, action.filterKey, action.optionKey, filters);
      return this.lookupValueService.findFull('PROVIDER', undefined, filters).pipe(
        map((options: PaginatedResult<LookupValue>) => actions.loadDropdownContentSuccess({
          options, filterKey: action.filterKey, optionKey: action.optionKey
        })),
        catchError(error => of(actions.loadLookupValuesByKeyFailure(error)))
      )
    })
  ));

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

  public loadInventoryValuation$ = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadInventoryValuation,
      actions.updateFilters
    ),
    concatLatestFrom((action) => [
      this.store.pipe(select(getFilters)),
      this.store.pipe(select(getTabFiltersByKey('inventory')))
    ]),
    mergeMap(([_, filters, tabFilters]) => {
      return this.serviceSnapshotService.getInventoryValuation(
        {
          tenantNames: filters.selectedTenants?.split(',') || [],
          masterCompanyNames: filters.selectedMasterCompanies?.split(',') || [],
          companyNames: filters.selectedEndCompanies?.split(',') || [],
          serviceTypes: filters.selectedServiceTypes?.split(',') || [],
          providers: filters.selectedProviders?.split(',') || [],
          serviceBilledTos: filters.selectedServiceBilledTos?.split(',') || []
        },
        //@ts-ignore
        tabFilters['inventoryValuation'].numOfMonths
      ).pipe(
        map((res: any) => actions.loadInventoryValuationSuccess({ data: res })),
        catchError(error => of(actions.loadInventoryValuationFailure(error)))
      )
    })
  ));

  public loadInventoryCounts$ = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadInventoryCounts,
      actions.updateFilters
    ),
    concatLatestFrom((action) => [
      this.store.pipe(select(getFilters)),
      this.store.pipe(select(getTabFiltersByKey('inventory')))
    ]),
    mergeMap(([_, filters, tabFilters]) => {
      return this.serviceSnapshotService.getInventoryCounts(
        {
          tenantNames: filters.selectedTenants?.split(',') || [],
          masterCompanyNames: filters.selectedMasterCompanies?.split(',') || [],
          companyNames: filters.selectedEndCompanies?.split(',') || [],
          serviceTypes: filters.selectedServiceTypes?.split(',') || [],
          providers: filters.selectedProviders?.split(',') || [],
          serviceBilledTos: filters.selectedServiceBilledTos?.split(',') || []
        },
        //@ts-ignore
        tabFilters['inventoryCounts'].numOfMonths
      ).pipe(
        map((res: any) => actions.loadInventoryCountsSuccess({ data: res })),
        catchError(error => of(actions.loadInventoryCountsFailure(error)))
      )
    })
  ));

  public loadNewInventory$ = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadNewInventory,
      actions.updateFilters
    ),
    concatLatestFrom((action) => [
      this.store.pipe(select(getFilters)),
      this.store.pipe(select(getTabFiltersByKey('inventory'))
      )
    ]),
    mergeMap(([_, filters, tabFilters]) => {
      return this.serviceSnapshotService.getNewInventory(
        {
          tenantNames: filters.selectedTenants?.split(',') || [],
          masterCompanyNames: filters.selectedMasterCompanies?.split(',') || [],
          companyNames: filters.selectedEndCompanies?.split(',') || [],
          serviceTypes: filters.selectedServiceTypes?.split(',') || [],
          providers: filters.selectedProviders?.split(',') || [],
          serviceBilledTos: filters.selectedServiceBilledTos?.split(',') || []
        },
        //@ts-ignore
        tabFilters['newInventory'].numOfMonths
      ).pipe(
        map((res: any) => actions.loadNewInventorySuccess({ data: res })),
        catchError(error => of(actions.loadNewInventoryFailure(error)))
      )
    })
  ));

  public loadServiceTypes$ = createEffect(() =>
    this.actions$.pipe(
      ofType(actions.loadServiceTypes),
      switchMap(() =>
        this.serviceSnapshotService.getServiceTypes().pipe(
          map(serviceTypes => actions.loadServiceTypesSuccess({ serviceTypes })),
          catchError(error => of(actions.loadServiceTypesFailure({ error })))
        )
      )
    )
  );
 
}
