import { createAction, props } from '@ngrx/store';
import { PaginatedResult } from '../../../models/paginated-result.model';
import { Company } from '../../../models/company.model';
import { DashboardFilterKeys, DashboardOptionsKeys, TabFilterKeys } from '../data/dashboards.consts';
import { WipServiceView } from '../../../models/wip-service-view.model';
import { WipServiceJeopView } from '../../../models/wip-service-jeop-view.model';
import { ProviderIntervalsView } from '../../../models/provider-intervals-view.model';
import {ActivationAttemptView} from "../../../models/activation-attempt-view-model";
import { WipLocationJeopView } from '../../../models/wip-location-jeop-view.model';
import { LookupValue } from '../../../models/lookup-value.model';
import { DashboardDataset } from 'src/app/models/dashboard-dataset.model';

export const updateFilters = createAction(
  '[Dashboards] update filters',
  props<{ key: string, value: string }>()
);

export const updateParams = createAction(
  '[Dashboards] Load DropdownContent params',
  props<{ key: string, value: string | number, filterKey: DashboardFilterKeys, optionKey: DashboardOptionsKeys }>()
);

export const clearFilters = createAction(
  '[Dashboards] clear filters'
);

export const updateTabFilters = createAction(
  '[Dashboards] update tab filters',
  props<{ tabKey: TabFilterKeys, key: string, value: string | boolean | Record<any, any> }>()
);

export const loadCustomers = createAction(
  '[Dashboards] Load Customers',
  props<{ filterKey: DashboardFilterKeys, optionKey: DashboardOptionsKeys, clear: boolean }>()
);

export const updateCustomerParams = createAction(
  '[Dashboards] Load Customers params',
  props<{ key: string, value: string | number, filterKey: DashboardFilterKeys, optionKey: DashboardOptionsKeys, clear: boolean }>()
);

export const loadCustomersSuccess = createAction(
  '[Dashboards] Load Customers Success',
  props<{ customers: PaginatedResult<Company>, filterKey: DashboardFilterKeys, optionKey: DashboardOptionsKeys, clear: boolean }>()
);

export const loadCustomersFailure = createAction(
  '[Dashboards] Load Customers Failure',
  props<any>()
);

export const loadWipServices = createAction(
  '[Dashboards] Load WipServices',
);

export const loadWipServicesSuccess = createAction(
  '[Dashboards] Load WipServices Success',
  props<{ wipServiceViews: WipServiceView[] }>()
);

export const loadWipServicessFailure = createAction(
  '[Dashboards] Load WipServices Failure',
  props<any>()
);


export const loadAllWipServices = createAction(
  '[Dashboards] Load All WipServices',
);

export const loadAllWipServicesSuccess = createAction(
  '[Dashboards] Load All WipServices Success',
  props<{ wipAllServiceViews: WipServiceView[] }>()
);

export const loadAllWipServicessFailure = createAction(
  '[Dashboards] Load All WipServices Failure',
  props<any>()
);

export const loadWipServiceJeops = createAction(
  '[Dashboards] Load Service Jeops',
);

export const loadWipServiceJeopsSuccess = createAction(
  '[Dashboards] Load All WipServices Jeops Success',
  props<{ wipServiceJeopViews: WipServiceJeopView[] }>()
);

export const loadWipServiceJeopsFailure = createAction(
  '[Dashboards] Load All WipServices Jeops Failure',
  props<any>()
);

export const loadWipLocationJeops = createAction(
  '[Dashboards] Load Location Jeops',
);

export const loadWipLocationJeopsSuccess = createAction(
  '[Dashboards] Load Location Jeops Success',
  props<{ wipLocationJeopViews: WipLocationJeopView[] }>()
);

export const loadWipLocationJeopsFailure = createAction(
  '[Dashboards] Load Locations Jeops Failure',
  props<any>()
);

export const loadProviderInstallIntervals = createAction(
  '[Dashboards] Load Provider Install Intervals Jeops'
);

export const loadProviderInstallIntervalsSuccess = createAction(
  '[Dashboards] Load Provider Install Intervals Success',
  props<{ provInstallIntervals: ProviderIntervalsView[] }>()
);

export const loadProviderInstallIntervalsFailure = createAction(
  '[Dashboards] Load Provider Install Intervals Failure',
  props<any>()
);

export const loadProviderSurveyIntervals = createAction(
  '[Dashboards] Load Provider Survey Intervals Jeops'
);

export const loadProviderSurveyIntervalsSuccess = createAction(
  '[Dashboards] Load Provider Survey Intervals Success',
  props<{ provSurveyIntervals: ProviderIntervalsView[] }>()
);

export const loadProviderSurveyIntervalsFailure = createAction(
  '[Dashboards] Load Provider Survey Intervals Failure',
  props<any>()
);

export const loadProviderReliance = createAction(
  '[Dashboards] Load Provider Reliance',
  props<{ chart: 'provider' | 'survey' | 'reliance' }>()
);

export const loadProviderRelianceSuccess = createAction(
  '[Dashboards] Load Provider Reliance Success',
  props<{ providerReliance: WipServiceView[] }>()
);

export const loadProviderRelianceFailure = createAction(
  '[Dashboards] Load Provider Reliance Failure',
  props<any>()
);

export const loadMonthlySpend = createAction(
  '[Dashboards] Load Monthly Spend',
);

export const loadMonthlySpendSuccess = createAction(
  '[Dashboards] Load Monthly Spend Success',
  props<{ wipServiceViews: WipServiceView[] }>()
);

export const loadMonthlySpendFailure = createAction(
  '[Dashboards] Load Monthly Spend Failure',
  props<any>()
);

export const loadUnbillableNetworkExpenseAccrual = createAction(
  '[Dashboards] Load Unbillable Network Expense',
);

export const loadUnbillableNetworkExpenseAccrualSuccess = createAction(
  '[Dashboards] Load Unbillable Network Success',
  props<{ wipServiceViews: WipServiceView[] }>()
);

export const loadUnbillableNetworkExpenseAccrualFailure = createAction(
  '[Dashboards] Load Unbillable Network Failure',
  props<any>()
);

export const loadIncrementalNetworkSpend = createAction(
  '[Dashboards] Load Incremental Network Expense',
);

export const loadIncrementalNetworkSpendSuccess = createAction(
  '[Dashboards] Load Incremental Network Success',
  props<{ wipServiceViews: WipServiceView[] }>()
);

export const loadIncrementalNetworkSpendFailure = createAction(
  '[Dashboards] Load Incremental Network Failure',
  props<any>()
);

export const loadServiceIntervals = createAction(
  '[Dashboards] Load Service Intervals Expense',
);

export const loadServiceIntervalsSuccess = createAction(
  '[Dashboards] Load Service Intervals Success',
  props<{ activationAttempt: ActivationAttemptView[] }>()
);

export const loadServiceIntervalsFailure = createAction(
  '[Dashboards] Load Service Intervals Failure',
  props<any>()
);


export const loadProviderAverages = createAction(
  '[Dashboards] Load Provider Averages Expense',
  props<{ monthArray: string[] }>()
);

export const loadProviderAveragesSuccess = createAction(
  '[Dashboards] Load Provider Averages Success',
  props<{ cdiArranged: (string[] | number[])[], npiArranged: (string[] | number[])[] }>()
);

export const loadProviderAveragesFailure = createAction(
  '[Dashboards] Load Provider Intervals Failure',
  props<any>()
);

export const loadInventoryValuation = createAction(
  '[Dashboards] Load Inventory Valuation',
  props<{ numOfMonths: number }>()
);

export const loadInventoryValuationSuccess = createAction(
  '[Dashboards] Load Inventory Valuation Success',
  props<{ data: DashboardDataset }>()
);

export const loadInventoryValuationFailure = createAction(
  '[Dashboards] Load Inventory Valuation Failure',
  props<any>()
);

export const loadInventoryCounts = createAction(
  '[Dashboards] Load Inventory Counts',
  props<{ numOfMonths: number }>()
);

export const loadInventoryCountsSuccess = createAction(
  '[Dashboards] Load Inventory Counts Success',
  props<{ data: DashboardDataset }>()
);

export const loadInventoryCountsFailure = createAction(
  '[Dashboards] Load Inventory Counts Failure',
  props<any>()
);

export const loadNewInventory = createAction(
  '[Dashboards] Load New Inventory',
  props<{ numOfMonths: number }>()
);

export const loadNewInventorySuccess = createAction(
  '[Dashboards] Load New Inventory Success',
  props<{ data: DashboardDataset }>()
);

export const loadNewInventoryFailure = createAction(
  '[Dashboards] Load New Inventory Failure',
  props<any>()
);

export const loadLookupValuesByKey = createAction(
  '[Dashboards] Load Lookup Values by key',
  props<{key: string, lookupKey: string }>()
);

export const loadLookupValuesByKeyFailure = createAction(
  '[Dashboards] Load Lookup Values Failure',
  props<any>()
);
export const loadLookupValuesByKeySuccess = createAction(
  '[Dashboards] Load Lookup Values Success',
  props<{values: LookupValue[], key: string}>()
);

export const loadDropdownContent = createAction(
  '[Dashboards] Load DropdownContent',
  props<{ filterKey: DashboardFilterKeys, optionKey: DashboardOptionsKeys }>()
);

export const loadDropdownContentSuccess = createAction(
  '[Dashboards] Load DropdownContent Success',
  props<{ options: PaginatedResult<Company | LookupValue>, filterKey: DashboardFilterKeys, optionKey: DashboardOptionsKeys }>()
);

export const loadDropdownContentFailure = createAction(
  '[Dashboards] Load DropdownContent Failure',
  props<any>()
);

export const loadServiceTypes = createAction(
  '[Dashboards] Load Service Types',
);

export const loadServiceTypesSuccess = createAction(
  '[Dashboards] Load Service Types Success',
  props<{ serviceTypes: string[] }>()
);

export const loadServiceTypesFailure = createAction(
  '[Dashboards] Load Service Types Failure',
  props<any>()
);

