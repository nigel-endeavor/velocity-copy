import { ChangeDetectorRef, Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { getIsLoading, getSelectedCustomer, getSelectedTab, getSelectedTenant, getTabs } from './ngrx/customer-details.selectors';
import { Company } from 'src/app/models/company.model';
import * as actions from './ngrx/customer-details.actions';
import { ActivatedRoute, Router } from '@angular/router';
import { map, take } from 'rxjs';
import { CompanyType } from '../../models/constants/company-type';

@Component({
  selector: 'app-customer-management',
  templateUrl: './customer-management.component.html',
  styleUrls: ['./customer-management.component.scss']
})
export class CustomerManagementComponent {

  public selectedTenant$ = this.store.select<Company | null>(getSelectedTenant);
  public selectedCustomer$ = this.store.select<Company | null>(getSelectedCustomer);

  public isLoading$ = this.store.select(getIsLoading);

  public tabs$ = this.store.select<string[]>(getTabs);
  public tabs: string[] = [];
  public selectedTab$ = this.store.select<string>(getSelectedTab);
  public selectedTab: string = '';

  company: Company;
  editingCustomerInfo: boolean = false;
  editingAddress: boolean = false;
  editingPermanentAccontNotes: boolean = false;
  showAddressDialog: boolean = false;
  editingBillingContact: boolean = false;
  editingTechContact: boolean = false;
  editingSalesContact: boolean = false;
  editingAuthContact: boolean = false;
  editingMasterCustomer: boolean = false;

  constructor(
    private store: Store,
    private route: ActivatedRoute,
    public router: Router,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    console.log('Customer Management Component Initialized');
    this.store.dispatch(actions.loadTenants());
    //load selected customer if customerId is present
    this.route.params.subscribe(params => {
      if (params['customerId']) {
        setTimeout(() => {
          this.editingCustomerInfo = false;
          this.editingAddress = false;
          this.editingPermanentAccontNotes = false;
          this.editingBillingContact = false;
          this.editingTechContact = false;
          if (params['customerId'] === 'new') {
            let newCompany = new Company();
            newCompany.active = true;
            if (this.company) {
              newCompany.type = CompanyType.END_CUSTOMER;
              newCompany.parentCompany = this.company;
            } else {
              newCompany.type = CompanyType.MASTER_CUSTOMER;
            }
            this.store.dispatch(actions.setSelectedCustomer({ customer: newCompany }));
            this.editingCustomerInfo = true;
          } else {
            this.store.dispatch(actions.loadSelectedCustomer({ customerId: params['customerId'] }));
          }
        });
      }
    });

    this.tabs$.subscribe(tabs => {
      this.tabs = tabs;
    });
    this.selectedTab$.subscribe(tab => {
      this.selectedTab = tab;
      this.cdr.detectChanges();
    });
  }

  ngOnDestroy(): void {
    this.store.dispatch(actions.pageDestroyed());
    this.store.dispatch(actions.setSelectedCustomer({ customer: null }));
    const currentUrl = this.router.url;
    if (currentUrl === '/endCustomers') {
      this.store.dispatch(actions.setSelectedTab({ tab: 'End Customers' }));
    } else {
      this.store.dispatch(actions.setSelectedTab({ tab: 'Master Customers' }));
    }
  }

  onTenantCrumbClicked() {
    this.store.dispatch(actions.setSelectedCustomer({ customer: null }));
    this.store.dispatch(actions.setSelectedTab({ tab: 'Master Customers' }));
    this.router.navigate(['/customers/masterCustomers']);
  }

  onMasterCustomerCrumbClicked() {
    this.store.dispatch(actions.pageDestroyed());
    this.selectedCustomer$.pipe(take(1)).subscribe(customer => {
      if (customer?.type == 'End Customer') {
        this.store.dispatch(actions.setSelectedTab({ tab: 'Details' }));
        this.router.navigate(['/customers/masterCustomers', customer.parentCompany.id])
      }
    });
  }

  onTabClicked(tab: string) {
    this.store.dispatch(actions.setSelectedTab({ tab }));
    if (tab.includes('Details')) {
      this.selectedCustomer$.pipe(
        take(1),
        map(customer => {
          if (customer && customer.type == 'End Customer') {
            this.router.navigate(['/customers/endCustomers', customer.id]);
          } else if (customer) {
            this.router.navigate(['/customers/masterCustomers', customer.id]);
          } else {
            this.router.navigate(['/customers/masterCustomers']);
          }
        }
      )).subscribe();
    } else if (tab.includes('End Customers')) {
      this.selectedCustomer$.pipe(
        take(1),
        map(customer => {
          if (customer) {
            this.router.navigate(['/customers/masterCustomers', customer.id, 'endCustomers']);
          } else {
            this.router.navigate(['/customers/masterCustomers']);
          }
        }
      )).subscribe();
    }
  }

}
