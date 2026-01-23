import { Component, ElementRef, Renderer2, ViewChild } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { Observable, Subject, combineLatest, debounceTime, filter, take, map } from 'rxjs';
import { companyViewTableActions, companyViewTableSelectors } from './config/table.config';
import {
  getAppliedFilters,
  getColumns,
  getFilters,
  getMetaData,
  getSelectedCustomer,
  getSelectedTab,
  getSelectedTenant,
  getTabs
} from './ngrx/customer-details.selectors';
import * as actions from './ngrx/customer-details.actions';
import { CompanyView } from 'src/app/models/company-view.model';
import { Company } from 'src/app/models/company.model';
import { Router } from '@angular/router';
import { CommonColumn } from '../../interfaces/columns.interface';
import { getBaseUrl } from '../../utilities';
import { Permissions, SecurityUtilService } from "../../services/security-util.service";

@Component({
  selector: 'app-company-worklist',
  templateUrl: './company-worklist.component.html',
  styleUrls: ['../worklist-styles.scss', './company-worklist.component.scss']
})
export class CompanyWorklistComponent {
  public columns$ = this.store.pipe(select(getColumns));
  public metaData$: Observable<any> = this.store.pipe(select(getMetaData));

  public tableData$: Observable<any> = this.store.pipe(select(companyViewTableSelectors.getTableData));
  public isLoading$ = this.store.pipe(select(companyViewTableSelectors.getTableDataIsLoading));
  public tableTotal$: Observable<number> = this.store.pipe(select(companyViewTableSelectors.getTableTotal));
  public searchCriteria$: Observable<any> = this.store.pipe(select(getFilters), filter(item => !!item));

  public selectedTenant$ = this.store.select<Company | null>(getSelectedTenant);
  public selectedCustomer$ = this.store.select<Company | null>(getSelectedCustomer);
  public selectedTab$ = this.store.select<string>(getSelectedTab);

  public appliedFilters$: Observable<any> = this.store.pipe(select(getAppliedFilters));
  //filters that are not masterCustomerId
  public filteredFilters$ = this.appliedFilters$.pipe(
        map(filters => filters.filter((filter: { name: string; }) => filter.name !== 'masterCustomerId'))
);
  public subject: Subject<{ key: string, value: any }> = new Subject();

  public masterCustomerId = 0;
  public showFilter = true;
  selectedCompany: CompanyView | null;
  editEnabled: boolean = this.securityUtils.userHasPermission(Permissions.ORDER_WRITE) || this.securityUtils.userHasPermission(Permissions.INVENTORY_WRITE);


  constructor(
    private store: Store,
    private router: Router,
    private renderer: Renderer2,
    private securityUtils: SecurityUtilService
  ) { }

  ngOnInit(): void {
    console.log('Company Worklist Component Initialized');
    this.store.dispatch(actions.setSelectedTab({ tab: 'End Customers' }));
    this.selectedCustomer$.subscribe((customer) => {
      this.store.dispatch(actions.updateFilters({ key: 'type', value: 'End Customer' }));
      if (customer) {
        this.store.dispatch(actions.updateFilters({ key: 'masterCustomerId', value: customer.id }));
        this.masterCustomerId = customer.id;
      }
    });

    this.subject.pipe(debounceTime(500)).subscribe((props: any) => {
      this.store.dispatch(actions.updateFilters(props));
    });
  }

  onSearch(event: any) {
    this.selectedCompany = null;
    const value = event.target.value;
    this.subject.next({
      key: 'search', value
    });
  }

  onChangeSortClicked(event: any) {
    this.store.dispatch(actions.updateSort({ sort: event }));
  }

  onFilterChanged(value: string | boolean | number, key: string): void {
    this.selectedCompany = null;
    this.store.dispatch(actions.updateFilters({ key, value }));

  }

  clearFilters() {
    this.store.dispatch(actions.clearFilters());
    this.store.dispatch(actions.updateFilters({ key: 'masterCustomerId', value: this.masterCustomerId }));
  }

  onClearFilterClicked($event: any, filter: any) {
    $event.stopPropagation();
    this.onFilterChanged(filter.initialValue, filter.name);
  }

  getFilterDisplayName(propertyName: string): Observable<string> {
    return this.columns$.pipe(
      take(1),
      map((columns: CommonColumn[]) => {
        switch (propertyName) {
          case 'search':
            return 'Search';
          default:
            let name = columns.find(col => col.propertyName === propertyName)?.name;
            return name ? name : propertyName;
        }
      })
    );
  }

  onCompanyClicked(company: CompanyView): void {
    this.selectedCompany = structuredClone(company);
  }

  onCustomerDblClicked(value: CompanyView): void {
    this.store.dispatch(actions.setSelectedTab({ tab: 'Details' }));
    if (value.type == 'Master Customer') {
      this.router.navigate(['customers', value.id]);
    } else {
      this.router.navigate(['customers/endCustomers', value.id]);
    }
  }

  onClosePreviewPaneClicked(): void {
    this.selectedCompany = null;
  }

  onOpenInNewTabClicked(): void {

    const baseUrl = getBaseUrl();
    let url = '';
    if (this.contextMenuCompany.type == 'Master Customer') {
      url = `${baseUrl}customers/${this.contextMenuCompany.id}`;
    } else {
      url = `${baseUrl}endCustomers/${this.contextMenuCompany.id}`;
    }
    window.open(url);
  }

  onAddClicked(): void {
    this.store.dispatch(actions.setSelectedTab({ tab: 'Details' }));
    this.selectedCustomer$.pipe(take(1)).subscribe(customer => {
      if (customer) {
        this.router.navigate(['customers', 'masterCustomers', customer.id, 'endCustomers', 'new']);
      }
    });
  }

  //shows/hides the given column
  onColSelectClicked(col: CommonColumn): void {
    this.store.dispatch(actions.toggleColumn({ columnName: col.propertyName }));
  }

  exportTable(fileName: string): void {
    this.store.dispatch(companyViewTableActions.exportTable({ fileName }));
  }

  updateTableData(): void {
    this.store.dispatch(companyViewTableActions.loadTableData());
  }

  onLimitChanged(value: number): void {
    this.onFilterChanged(value, 'limit');
    this.onFilterChanged(0, 'offset');
}

  @ViewChild('contextMenu') contextMenuRef: ElementRef;
  contextMenuStyle = {};
  contextMenuCompany: any;
  onCompanyRightClicked(data: any): void {
    const event = data.event;
    const company = data.row;
    this.contextMenuStyle = {
      'display': 'block',
      'position': 'absolute',
      'left.px': event.clientX,
      'top.px': event.clientY
    }
    this.contextMenuCompany = company;
    //listens for click outside of context menu, closes menu
    let listenerFn = this.renderer.listen('window', 'click', (e: any) => {
      if (!e.target.contains(this.contextMenuRef.nativeElement)) {
        this.contextMenuStyle = {
          'display': 'none',
        }
        this.contextMenuCompany = null;
        listenerFn(); //removes click listener
      }
    });
  }
}
