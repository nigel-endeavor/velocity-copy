import { Component, ElementRef, HostListener, Inject, OnInit, Renderer2, ViewChild } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { NoteComponent } from '../../components/note/note.component';
import { SecurityUtilService, Permissions } from '../../services/security-util.service';
import { TerminalServiceStatuses } from '../../models/constants/terminal-service-statuses';
import { combineLatest, debounceTime, distinctUntilChanged, filter, map, Observable, Subscription, switchMap, take, tap } from 'rxjs';
import { select, Store } from '@ngrx/store';
import {
  getFilters,
  getColumns,
  getCardViewSelected,
  getUpdatedItem,
  getSelectedItemsIds,
  getAppliedFilters,
  getMetaData,
  getParamsByKey,
  getAssignablesAsOptions
} from './ngrx/master-customers-worklist.selectors';
import { customerTableActions, customerTableSelectors } from './configs/table.config';
import {
  toggleColumn,
  updateFilters,
  updateSort,
  addSelectedItems,
  removeUnselectedItems,
  toggleDaterangeColumn,
  clearFilters,
  updateParams,
  reorderColumns,
  pageDestroyed,
  loadDropdownContent
} from './ngrx/master-customers-worklist.actions';
import { CommonColumn } from '../../interfaces/columns.interface';
import { MAT_LEGACY_DIALOG_DATA as MAT_DIALOG_DATA, MatLegacyDialog as MatDialog, MatLegacyDialogModule as MatDialogModule, MatLegacyDialogRef as MatDialogRef } from '@angular/material/legacy-dialog';
import { DateRange } from '../../interfaces/date-range.interface';
import { BaseWorklistClass } from '../../utilities/cards/base-worklist.class';
import { MasterCustomersWorklistFilterKeys, MasterCustomersWorklistOptionsKeys } from './data/master-customers-worklist.consts';
import { CommonDropdownSearchCriteria } from '../../interfaces/commonSearch.interface';
import { editEnabled, getIsReadOnly } from 'src/app/order-detail-page/ngrx/order-details.selectors';
import { CompanyView } from '../../models/company-view.model';
import { getBaseUrl } from '../../utilities';
import { setSyncEnabled } from '../../utilities/meta-reducers/sync-local-store';

@Component({
  selector: 'app-master-customers-worklist',
  templateUrl: './master-customers-worklist.component.html',
  styleUrls: ['../worklist-styles.scss', './master-customers-worklist.component.scss']
})
export class MasterCustomersWorklistComponent extends BaseWorklistClass implements OnInit {
  public selectedTab: string = '';
  private stateSnapshot: any;

  public editEnabled$ = this.store.pipe(select(editEnabled));
  public isReadOnly$ = this.store.pipe(select(getIsReadOnly))
  public subjectsWithEmpty: string[] = [];
  public showMultiedit = false;

  @ViewChild('note') note: NoteComponent;
  @ViewChild('filter') filter: ElementRef;

  selectedCustomer: CompanyView | null;
  public terminalServiceStatuses: string[] = Object.values(TerminalServiceStatuses);

  public metaData$: Observable<any> = this.store.pipe(select(getMetaData));
  public tableTotal$: Observable<number> = this.store.pipe(select(customerTableSelectors.getTableTotal));
  public isLoading$ = this.store.pipe(select(customerTableSelectors.getTableDataIsLoading));
  public tableData$: Observable<any[]> = this.store.pipe(select(customerTableSelectors.getTableData));
  public searchCriteria$: Observable<any> = this.store.pipe(select(getFilters), filter(item => !!item));

  public assignablesWithEmpty: string[] = [];
  public assignablesOptions$: Observable<any> = this.store.pipe(
    select(getAssignablesAsOptions),
    map(res => {
      if (res) {
        // @ts-ignore
        this.assignablesWithEmpty = ['Empty', ...res];
      }
      return res
  }));
  public assignablesParams$: Observable<any> = this.store.pipe(select(getParamsByKey('assignablesSearchCriteria')));

  public columns$ = this.store.pipe(select(getColumns));
  public cardViewOn$ = this.store.pipe(select(getCardViewSelected));
  public appliedFilters$: Observable<any> = this.store.pipe(select(getAppliedFilters));
  public updatedItem$: Observable<any> = this.store.pipe(select(getUpdatedItem));
  public selectedItemsIds$: Observable<any> = this.store.pipe(select(getSelectedItemsIds));
  public multieditParamsConfig: {
    masterCustomer: CommonDropdownSearchCriteria,
    provider: CommonDropdownSearchCriteria,
  };
  public showFilter = true;
  editEnabled: boolean = this.securityUtils.userHasPermission(Permissions.ORDER_WRITE) || this.securityUtils.userHasPermission(Permissions.INVENTORY_WRITE);

  constructor(
    private router: Router,
    private renderer: Renderer2,
    private store: Store,
    private securityUtils: SecurityUtilService
  ) {
    super();
  }

  ngOnInit(): void {
    this.store.dispatch(customerTableActions.loadTableData());
    this.store.dispatch(loadDropdownContent({ filterKey: 'assignablesSearchCriteria', optionKey: 'assignables', orderId: null}));
    this.subject.pipe(debounceTime(500)).subscribe((props: any) => {
      this.store.dispatch(updateFilters(props));
    });
  }

  ngOnDestroy(): void {
    this.store.dispatch(pageDestroyed());
    if (this.stateSnapshot) {
      this.store.dispatch(updateFilters(this.stateSnapshot.masterCustomerWorklist.filters));
      setSyncEnabled(true);
    }
  }

  onWorklistSelectChanged(value: string): void {
    let target = `${value === 'Master Customers' ? 'master' : 'end'}Customers`;
    localStorage.setItem('customers-worklist', target);
    this.router.navigate([`/customers/${target}`]);
  }

  onCustomerClicked(customer: CompanyView): void {
    this.selectedCustomer = structuredClone(customer);
  }

  onCustomerDblClicked(customer: CompanyView): void {
    this.router.navigate(['/customers/masterCustomers', customer.id]);
  }

  onClosePreviewPaneClicked(): void {
    this.selectedCustomer = null;
  }

  onFilterChanged(value: string | boolean | number, key: string): void {
    this.selectedCustomer = null;
    this.store.dispatch(updateFilters({ key, value }));
  }

  onLimitChanged(value: number): void {
    this.onFilterChanged(value, 'limit');
    this.onFilterChanged(0, 'offset');
  }

  onSelectAllClick(value: CompanyView[]): void {
    if (value[0].selected) {
      this.store.dispatch(addSelectedItems({ value: value }));
    } else {
      this.store.dispatch(removeUnselectedItems({ value: value }));
    }
  }

  onSearch(event: any): void {
    this.selectedCustomer = null;
    const value = event.target.value;
    this.subject.next({
      key: 'search', value
    });
  }

  @ViewChild('contextMenu') contextMenuRef: ElementRef;
  contextMenuStyle = {};
  contextMenuCustomer: any;
  onCustomerRightClicked(data: any): void {
    const event = data.event;
    const customer = data.row;
    this.contextMenuStyle = {
      'display': 'block',
      'position': 'absolute',
      'left.px': event.clientX,
      'top.px': event.clientY
    }
    this.contextMenuCustomer = customer;
    //listens for click outside of context menu, closes menu
    let listenerFn = this.renderer.listen('window', 'click', (e: any) => {
      if (!e.target.contains(this.contextMenuRef.nativeElement)) {
        this.contextMenuStyle = {
          'display': 'none',
        }
        this.contextMenuCustomer = null;
        listenerFn(); //removes click listener
      }
    });
  }

  onOpenInNewTabClicked(): void {
    const type = this.contextMenuCustomer.type === 'Master Customer' ? 'masterCustomers' : 'endCustomers';
    const url = `${getBaseUrl()}customers/${type}/${this.contextMenuCustomer.id}`;
    window.open(url);
  }

  clickUpdate(): void {
    this.note.save();
    // @ts-ignore
    this.store.dispatch(updateService({ service: this.selectedService }));
  }

  //shows/hides the given column
  onColSelectClicked(col: CommonColumn): void {
    this.store.dispatch(toggleColumn({ columnName: col.propertyName }));
  }

  //shows/hides dateRange
  onColDateRangeClicked(col: CommonColumn, dateRange?: string): void {
    this.store.dispatch(toggleDaterangeColumn({ columnName: col.propertyName }));
    setTimeout(() => {
      if (dateRange) {
        document.getElementById(dateRange)?.click();
        const scrollTop = document.getElementById('container')?.scrollTop || 0;
        const offsetTop = document.getElementById(col.propertyName)?.offsetTop || 0;
        const clientHeight = document.getElementById('container')?.clientHeight || 0;
        const dateRangeContainerHeight = 333 || 0;
        if ((scrollTop === 0) || ((offsetTop - scrollTop + dateRangeContainerHeight) > clientHeight)) {
          if ((offsetTop + dateRangeContainerHeight > clientHeight) || ((offsetTop - scrollTop + dateRangeContainerHeight) > clientHeight)) {
            setTimeout(() => {
              document.getElementsByClassName('md-drppicker')[0]
              .setAttribute('style', 'top: auto; bottom: 4em');
            }, 10);
          } else {
            setTimeout(() => {
              document.getElementsByClassName('md-drppicker')[0]
              .setAttribute('style', 'top: ' + offsetTop + 'px; bottom: auto');
            }, 10);
          }
        } else {
          setTimeout(() => {
            document.getElementsByClassName('md-drppicker')[0]
            .setAttribute('style', 'top: ' + (offsetTop - scrollTop) + 'px; bottom: auto');
          }, 10);
        }
      }
    }, 100);
  }

  exportTable(fileName: string): void {
    this.store.dispatch(customerTableActions.exportTable({ fileName }));
  }

  onChangeSortClicked(event: any) {
    this.store.dispatch(updateSort({ sort: event }));
  }

  onFiltersClicked(): void {
    if (this.filter.nativeElement.classList.contains('open')) {
      this.filter.nativeElement.classList.remove('open');
    } else {
      this.filter.nativeElement.classList.add('open');
    }
  }

  clearFilters() {
    this.store.dispatch(clearFilters());
  }

  onClearFilterClicked($event: any, filter: any) {
    $event.stopPropagation();
    this.onFilterChanged(filter.initialValue, filter.name);
  }

  onFilter(event: any, col: CommonColumn): void {
    const value = event.target.value;
    if (col.type == 'date') {
      //@ts-ignore
      this.store.dispatch(updateFilters(value, col.propertyName));
    } else {
      this.selectedCustomer = null;
      this.subject.next({ value, key: col.propertyName });
    }
  }

  onDateRangeFilter(value: DateRange, searchCriteria: any, col: CommonColumn): void {
    if (this.isEmptySet && this.nullConunter === 0) {
      this.nullifyier = value;
      setTimeout(() => {
        this.nullConunter++;
        this.nullifyier = null
      }, 0)
      this.store.dispatch(updateFilters({
        key: col.propertyName,
        // @ts-ignore
        value: {
          isEmpty: true,
          dateRange: null
        }
      }));
      this.onColDateRangeClicked(col);
    } else {
      this.nullConunter = 0;
      const formattedDates = {
        startDate: value.startDate ? new Date(value.startDate).toISOString().split('T')[0] : null,
        endDate: value.endDate ? new Date(value.endDate).toISOString().split('T')[0] : null
      };

      if ((formattedDates.startDate != searchCriteria[col.propertyName].dateRange?.startDate) ||
        (formattedDates.endDate != searchCriteria[col.propertyName].dateRange?.endDate)
      ) {
        this.store.dispatch(updateFilters({
          key: col.propertyName,
          // @ts-ignore
          value: {
            isEmpty: false,
            dateRange: value.startDate === null && value.endDate === null ? null : formattedDates
          }
        }));
        this.onColDateRangeClicked(col);

      }
    }
  }

  //listens for document click events, to close filter dialog on outside click
  @HostListener('document:click', ['$event']) toggleOpen(event: Event) {
    if (this.filter && this.filter.nativeElement.classList.contains('open')) {
      //@ts-ignore
      let parent = event.target.parentElement;
      while (parent && parent.classList && !parent.classList.contains('filter-content-row')) {
        parent = parent.parentElement;
      }
      if (!this.filter.nativeElement.contains(event.target) && !parent && !(event.target as HTMLElement)?.classList.contains('ng-star-inserted')) {
        this.filter.nativeElement.classList.remove('open');
      }
    }
  }

  onUpdateParams(value: string | number, filterKey: MasterCustomersWorklistFilterKeys, key: string, optionKey: MasterCustomersWorklistOptionsKeys): void {
    this.store.dispatch(updateParams({ key, value, filterKey, optionKey }));
  }

  reorderColumns(event: { columns: CommonColumn[] }): void {
    this.store.dispatch(reorderColumns({ columns: event.columns }));
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

  onAddClicked(): void {
    this.router.navigate(['customers', 'masterCustomers', 'new']);
  }

  updateTableData(): void {
    this.store.dispatch(customerTableActions.loadTableData());
  }
}

export interface PromptContent {
  message: string;
}

@Component({
  selector: 'prompt-dialog',
  templateUrl: './prompt-dialog.html',
  styleUrls: ['../worklist-styles.scss'],
  standalone: true,
  imports: [MatDialogModule],
})
export class PromptDialog {
  constructor(public dialogRef: MatDialogRef<PromptDialog>, @Inject(MAT_DIALOG_DATA) public data: PromptContent) { }

  onClose() {
    this.dialogRef.close();
  }
}
