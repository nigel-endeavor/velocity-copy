import { Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { getIsLoading, getSelectedTab, getSelectedTenant, getTabs } from './ngrx/configuration.selectors';
import { Company } from 'src/app/models/company.model';
import * as actions from './ngrx/configuration.actions';
import { Router } from '@angular/router';
import { setUserHasLookupAdmin } from './ngrx/configuration.actions';
import { SecurityUtilService, Permissions } from 'src/app/services/security-util.service';

@Component({
  selector: 'app-configuration',
  templateUrl: './configuration.component.html',
  styleUrls: ['./configuration.component.scss']
})
export class ConfigurationComponent {

  public selectedTenant$ = this.store.select<Company | null>(getSelectedTenant);
  public isLoading$ = this.store.select(getIsLoading);
  public tabs$ = this.store.select<string[]>(getTabs);
  public selectedTab$ = this.store.select<string>(getSelectedTab);

  constructor(
    private store: Store,
    public router: Router,
    private securityUtilService: SecurityUtilService
  ) { }

  ngOnInit(): void {
    this.store.dispatch(actions.loadTenants());
    this.store.dispatch(setUserHasLookupAdmin({ hasLookupAdmin: this.securityUtilService.userHasPermission(Permissions.LOOKUP_ADMIN) }));
  }

  ngOnDestroy(): void {
    this.store.dispatch(actions.pageDestroyed());
    this.store.dispatch(actions.setSelectedTab({ tab: 'Lookups' }));
  }

  onTenantCrumbClicked() {
    this.store.dispatch(actions.setSelectedCustomer({ customer: null }));
    this.store.dispatch(actions.setSelectedTab({ tab: 'Lookups' }));
    this.router.navigate(['/configuration', 'lookups']);
  }

  onTabClicked(tab: string) {
    this.store.dispatch(actions.setSelectedTab({ tab }));
    if (tab == 'Lookups') {
      this.router.navigate(['/configuration', 'lookups']);
    } else if (tab == 'Configuration') {
      this.router.navigate(['/configuration', 'configuration']);
    }
  }

}
