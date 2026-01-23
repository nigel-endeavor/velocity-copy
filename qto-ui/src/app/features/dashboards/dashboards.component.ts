import { Component, OnInit } from '@angular/core';
import { CompanyService } from '../../services/company.service';
import { Company } from '../../models/company.model';
import { CompanySearchCriteria } from '../../models/company-search-criteria';
import { PaginatedResult } from '../../models/paginated-result.model';
import { ServiceType } from '../../models/constants/service-type';
import { Permissions, SecurityUtilService } from '../../services/security-util.service';
import { select, Store } from '@ngrx/store';
import { getAppliedFilters, getCustomersByKeyAsOptions, getFilters, getIsLoading, getParamsByKey, getProvidersValues, getServiceBilledTo, getServiceBilledToValues, getServiceTypes } from './ngrx/dashboards.selectors';
import { clearFilters, loadCustomers, loadDropdownContent, loadLookupValuesByKey, updateCustomerParams, updateFilters, updateParams, loadServiceTypes } from './ngrx/dashboards.actions';
import { DashboardFilterKeys, DashboardOptionsKeys } from './data/dashboards.consts';
import { Observable, filter, map, take } from 'rxjs';
import { LookupValueService } from 'src/app/services/lookup-value.service';

@Component({
  selector: 'app-dashboards',
  templateUrl: './dashboards.component.html',
  styleUrls: ['./dashboards.component.scss', '../worklist-styles.scss']
})
export class DashboardsComponent implements OnInit {

  public filters$= this.store.pipe(select(getFilters));
  public appliedFilters$: Observable<any[]>;
  public masterCustomerOptions$ = this.store.pipe(select(getCustomersByKeyAsOptions('masterCustomers')));
  public masterCustomerParams$ = this.store.pipe(select(getParamsByKey('masterCustomerSearchCriteria')));
  public customerOptions$ = this.store.pipe(select(getCustomersByKeyAsOptions('endCustomers')));
  public endCustomerParams$ = this.store.pipe(select(getParamsByKey('endCustomerSearchCriteria')));
  public providersParams$: Observable<any>;
  public searchCriteria$ = this.store.pipe(select(getFilters), filter(item => !!item));
  public isLoading$ = this.store.pipe(select(getIsLoading));

  public tenantOptions: string[];
  public serviceTypeOptions$: Observable<any>;
  public providerOptions$: Observable<any>;
  public serviceBilledToOptions$: Observable<any>;

  public currentDashboard: string = 'WIP';

  showAllDashboards: boolean  = false;
  telecomClient: boolean = false;

  constructor(
    private companyService: CompanyService,
    private securityUtils: SecurityUtilService,
    private store: Store,
    private lookupValueService: LookupValueService
  ) { }

  ngOnInit(): void {
    let tenantCriteria = new CompanySearchCriteria();
    tenantCriteria.type = 'Vertek Client';
    this.providersParams$ = this.store.pipe(select(getParamsByKey('providersSearchCriteria')));
    this.store.dispatch(loadDropdownContent({ filterKey: 'providersSearchCriteria', optionKey: 'providers'}));
    this.companyService.search(tenantCriteria).subscribe((result: PaginatedResult<Company>) => {
      this.tenantOptions = result.collection.map(c => c.name);
    });
    this.providerOptions$ = this.store.pipe(select(getProvidersValues));
    this.store.dispatch(loadServiceTypes());
    this.serviceTypeOptions$ = this.store.pipe(select(getServiceTypes));
    this.serviceBilledToOptions$ = this.store.pipe(select(getServiceBilledToValues));
    this.store.dispatch(loadLookupValuesByKey({
      key: 'serviceBilledTo',
      lookupKey: 'SERVICE_BILLED_TO'
    }));
    this.initMasterCustomerOptions();
    this.initCustomerOptions();
    this.showAllDashboards = this.securityUtils.userHasPermission(Permissions.ORDER_WRITE) || this.securityUtils.userHasPermission(Permissions.INVENTORY_WRITE);
    this.telecomClient = localStorage.getItem('TELECOM_CLIENT') === 'true';
    this.appliedFilters$ = this.store.pipe(select(getAppliedFilters));
  }

  ngOnDestroy(): void {
  }

  onTenantSelected(value: string): void {
    this.onDropdownSearch(value, 'masterCustomerSearchCriteria', 'tenants', 'masterCustomers')
    this.onDropdownSearch(value, 'endCustomerSearchCriteria', 'tenants', 'endCustomers')
    this.onFilterChanged(value, 'selectedTenants');
    this.onFilterChanged('', 'selectedMasterCompanies');
    this.onFilterChanged('', 'selectedEndCompanies');
    this.initMasterCustomerOptions();
    this.initCustomerOptions();
  }

  onMasterCustomerSelected(value: string): void {
    this.onDropdownSearch(value, 'endCustomerSearchCriteria', 'endCustomers', 'endCustomers')
    this.onFilterChanged(value, 'selectedMasterCompanies')
    this.onFilterChanged('', 'selectedEndCompanies');
    this.initCustomerOptions();
  }

  initCustomerOptions(): void {
    this.store.dispatch(loadCustomers({ filterKey: 'endCustomerSearchCriteria', optionKey: 'endCustomers', clear: true }));
  }

  initMasterCustomerOptions(): void {
    this.store.dispatch(loadCustomers({ filterKey: 'masterCustomerSearchCriteria', optionKey: 'masterCustomers', clear: true }));
  }

  onDropdownSearch(value: string | number, filterKey: DashboardFilterKeys, key: string, optionKey: DashboardOptionsKeys): void {
    this.store.dispatch(updateCustomerParams({ key, value, filterKey, optionKey, clear: false }));
  }

  onFilterChanged(value: string, key: string): void {
    this.store.dispatch(updateFilters({ key, value}));
  }

  setCurrentDashboard(dashboard: string): void {
    this.isLoading$.pipe(take(1)).subscribe(isLoading => {
      if (isLoading) {
        return;
      }
      this.currentDashboard = dashboard;
    });
  }

  clearFilters() {
    this.store.dispatch(clearFilters());
    this.onFilterChanged('', 'selectedTenants');
    this.initMasterCustomerOptions();
    this.initCustomerOptions();
  }

  onUpdateParams(value: string | number, filterKey: DashboardFilterKeys, key: string, optionKey: DashboardOptionsKeys): void {
    console.log('onUpdateParams', value, filterKey, key, optionKey);
    this.store.dispatch(updateParams({ key, value, filterKey, optionKey }));
  }

  getFilterDisplayName(propertyName: string) {
    //case statement to return the display name for the filter
    switch (propertyName) {
      case 'selectedTenants':
        return 'Tenant';
      case 'selectedMasterCompanies':
        return 'Master Customer';
      case 'selectedEndCompanies':
        return 'End Customer';
      case 'selectedServiceBilledTos':
        return 'Service Billed To';
      case 'selectedServiceTypes':
        return 'Service Type';
      case 'selectedProviders':
        return 'Provider';
      default:
        return propertyName;
    }
  }

  onClearFilterClicked($event: any, filter: any) {
    $event.stopPropagation();
    this.onFilterChanged(filter.initialValue, filter.name);
  }
}
