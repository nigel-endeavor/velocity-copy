import { Component, ElementRef, HostListener, Input, OnInit, Renderer2, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LocationView } from '../../models/location-view.model';
import { NoteComponent } from '../../components/note/note.component';
import { select, Store } from '@ngrx/store';
import * as locationWorklistSelectors from './ngrx/location-worklist.selectors';
import * as locationInventoryWorklistSelectors from './ngrx-inventory/location-inventory-worklist.selectors';
import { locationTableActions, locationTableSelectors } from './configs/ordering-table.config';
import { debounceTime, Observable, filter, take, map } from 'rxjs';
import * as locationWorklistActions from './ngrx/location-worklist.actions';
import * as locationInventoryWorklistActions from './ngrx-inventory/location-inventory-worklist.actions';
import { CommonColumn } from '../../interfaces/columns.interface';
import { DateRange } from '../../interfaces/date-range.interface';
import { BaseWorklistClass } from '../../utilities/cards/base-worklist.class';
import { locationInventoryTableActions, locationInventoryTableSelectors } from './configs/inventory-table.config';
import { editEnabled, getIsReadOnly} from 'src/app/order-detail-page/ngrx/order-details.selectors';
import { loadUserStatuses } from 'src/app/order-detail-page/ngrx/order-details.actions';
import { getBaseUrl } from 'src/app/utilities';
import { setSyncEnabled } from '../../utilities/meta-reducers/sync-local-store';
import { TerminalLocationStatuses } from 'src/app/models/constants/terminal-location-statuses';
import { SubjectCustomWorklist } from "../../models/subject-custom-worklist.model";
import { SubjectCustomWorklistService } from "../../services/subject-custom-worklist.service";
import { MatLegacyDialog as MatDialog } from "@angular/material/legacy-dialog";
import { ComponentType } from "@angular/cdk/overlay";
import {
  SubjectCustomWorklistComponent
} from "../../components/subject-custom-worklist/subject-custom-worklist.component";

@Component({
  selector: 'app-location-worklist',
  templateUrl: './location-worklist.component.html',
  styleUrls: ['../worklist-styles.scss', './location-worklist.component.scss']
})
export class LocationWorklistComponent extends BaseWorklistClass implements OnInit {
  @Input() orderId: number;
  public editEnabled$ = this.store.pipe(select(editEnabled));
  public isReadOnly$ = this.store.pipe(select(getIsReadOnly))
  @ViewChild('note') note: NoteComponent;
  @ViewChild('filter') filter: ElementRef;

  isInventory: boolean = false;
  editEnabled: boolean;
  selectedLocation: any;

  public provisionerFilterOptions$: Observable<any>;
  public subOrderTypes$: Observable<any>;
  public serviceTypeOptions$: Observable<any>;
  public companyNameTypes$: Observable<any>;
  public customerOptions$: Observable<any>;
  public customerParams$: Observable<any>;
  public endCustomerOptions$: Observable<any>;
  public endCustomerParams$: Observable<any>;
  public provisionersParams$: Observable<any>;
  public CompanyNameParams$: Observable<any>;
  public provisionerOptions$: Observable<any>;

  public jeopResponsibilityOptions$: Observable<any>;

  worklistActions: any;
  tableActions: any;
  worklistSelectors: any;
  tableSelectors: any;

  public isLoading$: Observable<any>;
  public cardViewOn$: Observable<any>;
  public searchCriteria$: Observable<any>;
  public tableTotal$: Observable<number>;
  public tableData$: Observable<any[]>;
  public updatedItem$: Observable<any>;
  public statusesValues$: Observable<any>;
  public metaData$: Observable<any>;
  public appliedFilters$: Observable<any[]>;
  public parentCompanyNameoptions$: Observable<any>;
  public columns$: Observable<any>;
  private stateSnapshot: any;
  public terminalLocationStatuses: string[] = Object.values(TerminalLocationStatuses);

  public cyberClient: boolean = false;
  public telecomClient: boolean = false;
  public customWorklists: SubjectCustomWorklist[] = [];
  public worklistName: string = 'locationWorklist';
  public hideList: boolean = false;

  constructor(public router: Router,
              private renderer: Renderer2,
              private store: Store,
              private route: ActivatedRoute,
              private customWorklistService: SubjectCustomWorklistService,
              private customWorklistDialog: MatDialog
  ) {
    super();
  }

  ngOnInit(): void {
    this.customWorklistService.getCustomWorklists(this.worklistName).subscribe((res: SubjectCustomWorklist[]) => {
      this.customWorklists = res;
    });
    this.telecomClient = localStorage.getItem('TELECOM_CLIENT') === 'true';
    this.cyberClient = localStorage.getItem('CYBER_SECURITY_CLIENT') === 'true';
    this.store.dispatch(loadUserStatuses());
    //initialize actions and selectors
    let inventory = this.route.snapshot.data['inventory'];
    if (inventory) {
      this.isInventory = true;
      this.worklistActions = locationInventoryWorklistActions;
      this.tableActions = locationInventoryTableActions;
      this.worklistSelectors = locationInventoryWorklistSelectors;
      this.tableSelectors = locationInventoryTableSelectors;
      this.worklistName = 'locationInventoryWorklist';
    } else {
      this.worklistActions = locationWorklistActions;
      this.tableActions = locationTableActions;
      this.worklistSelectors = locationWorklistSelectors;
      this.tableSelectors = locationTableSelectors;
    }
    this.customWorklistService.getCustomWorklists(this.worklistName).subscribe((res: SubjectCustomWorklist[]) => {
      this.customWorklists = res;
    });
    this.store.dispatch(this.worklistActions.loadServiceTypes());
    this.serviceTypeOptions$ = this.store.pipe(select(this.worklistSelectors.getServiceTypes));
    this.telecomClient = localStorage.getItem('TELECOM_CLIENT') === 'true';
    this.cyberClient = localStorage.getItem('CYBER_SECURITY_CLIENT') === 'true';

    //initialize observables
    this.provisionerFilterOptions$ = this.store.pipe(select(this.worklistSelectors.getProvisionersAsOptions));
    this.customerOptions$ = this.store.pipe(select(this.worklistSelectors.getCustomersAsOptions));
    this.customerParams$ = this.store.pipe(select(this.worklistSelectors.getCustomersParams));
    this.provisionersParams$ = this.store.pipe(select(this.worklistSelectors.getProvisionersParams));
    this.isLoading$ = this.store.pipe(select(this.tableSelectors.getTableDataIsLoading));
    this.cardViewOn$ = this.store.pipe(select(this.worklistSelectors.getCardViewSelected));
    this.searchCriteria$ = this.store.pipe(select(this.worklistSelectors.getFilters), filter(item => !!item));
    this.tableTotal$ = this.store.pipe(select(this.tableSelectors.getTableTotal));
    this.tableData$ = this.store.pipe(select(this.tableSelectors.getTableData));
    this.updatedItem$ = this.store.pipe(select(this.worklistSelectors.getUpdatedItem));
    this.statusesValues$ = this.store.pipe(select(this.worklistSelectors.getStatusesValues));
    this.columns$ = this.store.pipe(select(this.worklistSelectors.getColumns));
    this.appliedFilters$ = this.store.pipe(select(this.worklistSelectors.getAppliedFilters));
    this.editEnabled$.subscribe((res) => {
      this.editEnabled = res;
    });
    this.provisionerOptions$ = this.store.pipe(
      select(this.worklistSelectors.getProvisionersAsOptions),
      map(res => {
        if (res) {
          // @ts-ignore
          this.provisionersWithEmpty = ['Empty', ...res];
        }
        return res
    }));
    if (this.isInventory) {
      this.metaData$ = this.store.pipe(select(this.worklistSelectors.getMetaData));
    }

    //load data
    this.route.queryParams.subscribe(params => {
      if (Object.keys(params).length !== 0) {
        // Take a snapshot of the state
        this.store.pipe(take(1)).subscribe(state => {
          this.stateSnapshot = { ...state };
        });
        const parentCompanyName = params['parentCompanyName'];
        const companyName = params['companyName'];
        const provider = params['provider'];
        const locationStatus = params['locationStatus'];
        const serviceType = params['serviceType'];
        const provisioner = params['provisioner'];
        const vertekProjectManager = params['vertekProjectManager'];
        const projectManager = params['projectManager'];
        const openJeopResponsibilities = params['openJeopResponsibilities'];
        const hideTerminalStatuses = params['hideTerminalStatuses'];

        setSyncEnabled(false);

        this.store.dispatch(this.worklistActions.updateFilters({ key: 'parentCompanyName', value: parentCompanyName }));
        this.store.dispatch(this.worklistActions.updateFilters({ key: 'companyName', value: companyName }));
        this.store.dispatch(this.worklistActions.updateFilters({ key: 'provider', value: provider }));
        this.store.dispatch(this.worklistActions.updateFilters({ key: 'locationStatus', value: locationStatus }));
        this.store.dispatch(this.worklistActions.updateFilters({ key: 'serviceType', value: serviceType }));
        this.store.dispatch(this.worklistActions.updateFilters({ key: 'provisioner', value: provisioner }));
        this.store.dispatch(this.worklistActions.updateFilters({ key: 'vertekProjectManager', value: vertekProjectManager }));
        this.store.dispatch(this.worklistActions.updateFilters({ key: 'projectManager', value: projectManager }));
        this.store.dispatch(this.worklistActions.updateFilters({ key: 'openJeopResponsibilities', value: openJeopResponsibilities }));
        this.store.dispatch(this.worklistActions.updateFilters({ key: 'hideTerminalStatuses', value: hideTerminalStatuses }));
      } else {
        this.store.dispatch(this.tableActions.loadTableData());
      }
    });
    this.store.dispatch(this.worklistActions.loadLookupValuesByKey({key: 'statuses', lookupKey: 'LOCATION_STATUS'}));
    this.store.dispatch(this.worklistActions.loadProvisioners({ orderId: null}));
    this.store.dispatch(this.worklistActions.loadCustomers());

    // this.store.dispatch(this.worklistActions.loadLookupValuesByKey({
    //   key: 'serviceTypes',
    //   lookupKey: 'TENANT_SERVICE_TYPES'
    // }));

    if (this.isInventory) {
      this.store.dispatch(this.worklistActions.loadMetaData());
      this.parentCompanyNameoptions$ = this.store.pipe(select(this.worklistSelectors.getParentCustomerOptions));
      this.CompanyNameParams$ = this.store.pipe(select(this.worklistSelectors.getCompanyNameParams));
      this.store.dispatch(this.worklistActions.loadLookupValuesByKey({key: 'subOrderTypes', lookupKey: 'SUB_ORDER_TYPE'}));
      this.subOrderTypes$ = this.store.pipe(select(this.worklistSelectors.getSubOrderTypeOptions));
      this.companyNameTypes$ = this.store.pipe(select(this.worklistSelectors.getCompanyNameOptions));
      this.store.dispatch(this.worklistActions.loadCompanyName());
    } else {
      this.jeopResponsibilityOptions$ = this.store.pipe(
        select(this.worklistSelectors.getJeopResponsibility),
        map(res => {
          if (res) {
            // @ts-ignore
            return res.map(item => item.display);
          }
          return res;
        })
      );
      this.store.dispatch(this.worklistActions.loadLookupValuesByKey({
        key: 'jeopardyResponsibility',
        lookupKey: 'jeopardy_responsibility'
      }));
      this.endCustomerOptions$ = this.store.pipe(select(this.worklistSelectors.getEndCustomersAsOptions));
      this.endCustomerParams$ = this.store.pipe(select(this.worklistSelectors.getEndCustomersParams));
      this.store.dispatch(this.worklistActions.loadEndCustomers());
    }

    this.subject.pipe(debounceTime(500)).subscribe((props: any) => {
      this.store.dispatch(this.worklistActions.updateFilters(props));
    });
  }

  ngOnDestroy(): void {
    if (this.stateSnapshot) {
      this.store.dispatch(this.worklistActions.updateFilters(this.stateSnapshot.serviceWorklist.filters));
      setSyncEnabled(true);
    }
  }

  onWorklistSelectChanged(value: string): void {
    if (this.isInventory) {
      localStorage.setItem('inventory-worklist', value);
      this.router.navigate(['/inventory/' + value]);
    } else {
      localStorage.setItem('worklist', value);
      this.router.navigate(['/' + value]);
    }
  }

  onLocationClicked(location: LocationView): void {
    this.selectedLocation = structuredClone(location);
    this.store.dispatch(this.worklistActions.loadProvisioners({ orderId: this.selectedLocation.orderId}));
  }

  onLocationDblClicked(location: LocationView): void {
    if (this.isInventory) {
      this.router.navigate(['inventory', 'order', location.orderId, 'location', location.id]);
    } else {
      this.router.navigate(['order', location.orderId, 'location', location.id]);
    }
  }

  onClosePreviewPaneClicked(): void {
    this.selectedLocation = null;
  }

  onFilterChanged(value: string | boolean | number, key: string):void {
    this.selectedLocation = null;
    this.store.dispatch(this.worklistActions.updateFilters({ key, value}));
  }

  onLimitChanged(value: number): void {
    this.onFilterChanged(value, 'limit');
    this.onFilterChanged(0, 'offset');
  }

  onSearch(event: any): void {
    this.selectedLocation = null;
    const value = event.target.value;
    this.subject.next({
      key: 'search', value
    });
  }

  onAssignedToChanged(value: string): void {
    this.selectedLocation = null;
    this.store.dispatch(this.worklistActions.updateFilters({ key: 'provisioner', value: value ? value.split(', ') : []}));
  }

  clickUpdate(): void {
    this.note.save();
    this.store.dispatch(this.worklistActions.updateLocaion({ location: this.selectedLocation }));
  }

  @ViewChild('contextMenu') contextMenuRef: ElementRef;
  contextMenuStyle = {};
  contextMenuLocation: any;
  onLocationRightClicked(data: any) {
    const event = data.event;
    const location = data.row;
    this.contextMenuStyle = {
      'display': 'block',
      'position': 'absolute',
      'left.px': event.clientX,
      'top.px': event.clientY
    }
    this.contextMenuLocation = location;
    //listens for click outside of context menu, closes menu
    let listenerFn = this.renderer.listen('window', 'click', (e: any) => {
      if (!e.target.contains(this.contextMenuRef.nativeElement)) {
        this.contextMenuStyle = {
          'display': 'none',
        }
        this.contextMenuLocation = null;
        listenerFn(); //removes click listener
      }
    });
  }

  onOpenInNewTabClicked(): void {
    const url = `${getBaseUrl()}${this.isInventory ? 'inventory/' : ''}order/${this.contextMenuLocation.orderId}/location/${this.contextMenuLocation.id}`;
    window.open(url);
  }

  onFiltersClicked(): void {
    if (this.filter.nativeElement.classList.contains('open')) {
      this.filter.nativeElement.classList.remove('open');
    } else {
      this.filter.nativeElement.classList.add('open');
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

  onFilter(event: any, col: CommonColumn): void {
    const value = event.target.value;
    if (col.type == 'date') {
      //@ts-ignore
      this.store.dispatch(updateFilters(value, col.propertyName));
    } else {
      this.selectedLocation = null;
      this.subject.next({
        key: col.propertyName, value
      })
    }
  }

  onDateComparisonFilter(value: string, searchCriteria: any, col: CommonColumn): void {
    this.store.dispatch(this.worklistActions.updateFilters({ key: col.propertyName, value: {
      ...searchCriteria[col.propertyName],
        comparison: value
      }
    }))
  }

  onDateRangeFilter(value: DateRange, searchCriteria: any, col: CommonColumn): void {
    if (this.isEmptySet && this.nullConunter === 0) {
      this.nullifyier = value;
      setTimeout(() => {
        this.nullConunter++;
        this.nullifyier = null
      }, 0)
      this.store.dispatch(this.worklistActions.updateFilters({
        key: col.propertyName,
        // @ts-ignore
        value: {
          isEmpty: true,
          dateRange: null
        }
      }))
    } else {
      this.nullConunter = 0;
      const formattedDates = {
        startDate: value.startDate ? new Date(value.startDate).toISOString().split('T')[0] : null,
        endDate: value.endDate ? new Date(value.endDate).toISOString().split('T')[0]: null
      };

      if ((formattedDates.startDate != searchCriteria[col.propertyName].dateRange?.startDate) ||
        (formattedDates.endDate != searchCriteria[col.propertyName].dateRange?.endDate)
      ) {
        this.store.dispatch(this.worklistActions.updateFilters({
          key: col.propertyName,
          // @ts-ignore
          value: {
            isEmpty: false,
            dateRange: value.startDate === null && value.endDate === null ? null : formattedDates
          }
        }))
      }
    }
  }

  clearFilters() {
    this.store.dispatch(this.worklistActions.clearFilters());
  }

  onClearFilterClicked($event: any, filter: any) {
    $event.stopPropagation();
    this.onFilterChanged(filter.initialValue, filter.name);
  }

  exportTable(fileName: string): void {
    this.store.dispatch(this.tableActions.exportTable({ fileName }));
  }

  toggleSelectedView(): void {
    this.store.dispatch(this.worklistActions.toggleView());
  }

  //shows/hides the given column
  onColSelectClicked(col: CommonColumn): void {
    this.store.dispatch(this.worklistActions.toggleColumn({ columnName: col.propertyName}));
  }

  onChangeSortClicked(event: any) {
    this.store.dispatch(this.worklistActions.updateSort({ sort: event }));
  }

  onSearchCustomers(event: string): void {
    this.store.dispatch(this.worklistActions.updateParams({ key: 'name', value: event }));
  }

  onSearchCompanyNames(event: string): void {
    this.store.dispatch(this.worklistActions.updateCompanyNameParams({ key: 'name', value: event }));
  }

  onSearchEndCustomers(event: string): void {
    this.store.dispatch(this.worklistActions.updateEndCustomerParams({ key: 'name', value: event }));
  }

  onSearchProvisioners(event: string): void {
    this.store.dispatch(this.worklistActions.updateProvisionersParams({ key: 'name', value: event }));
  }

  onLoadNextPage(event: number): void {
    this.store.dispatch(this.worklistActions.updateParams({ key: 'offset', value: event }));
  }

  onLoadNextCompanyPage(event: number): void {
    this.store.dispatch(this.worklistActions.updateCompanyNameParams({ key: 'offset', value: event }));
  }

  onLoadNextEndCustomerPage(event: number): void {
    this.store.dispatch(this.worklistActions.updateEndCustomerParams({ key: 'offset', value: event }));
  }

  onLoadNextProvisionersPage(event: number): void {
    this.store.dispatch(this.worklistActions.updateProvisionersParams({ key: 'offset', value: event }));
  }

  reorderColumns(event: { columns: CommonColumn[]}): void {
    this.store.dispatch(this.worklistActions.reorderColumns({ columns: event.columns }));
  }

  getFilterDisplayName(propertyName: string): Observable<string> {
    return this.columns$.pipe(
      take(1),
      map((columns: CommonColumn[]) => {
        switch (propertyName) {
          case 'parentCompanyName':
            return 'Master Customer';
          case 'hideTerminalStatuses':
            return 'Hide Terminal Statuses';
          case 'macOnly':
            return 'MAC Orders Only';
          case 'search':
            return 'Search';
          case 'macdOpen':
            return 'MACD Open';
          case 'disputeOpen':
            return 'Dispute Open';
          case 'activeOnly':
            return 'Active Only';
          case 'subOrderTypes':
            return 'Sub Order Types';
          case 'companyName':
            return 'End Customer';
          default:
            let name = columns.find(col => col.propertyName === propertyName)?.name;
            return name ? name : propertyName;
        }
      })
    );
  }

  manageCustomWorklist(event: Event, customWorklist: any) {
    event.stopPropagation();
    const existingNames: string[] = this.customWorklists.map(w => w.name);
    let isEdit: boolean = false;
    if (customWorklist) {
      isEdit = true;
    } else {
      customWorklist = new SubjectCustomWorklist();
      customWorklist.worklistName = this.worklistName;
    }
    const component: ComponentType<SubjectCustomWorklistComponent> = SubjectCustomWorklistComponent;
    const dialogRef = this.customWorklistDialog.open(component, {
      width: '300px',
      data: {
        customWorklist: customWorklist,
        isEdit: isEdit,
        existingNames: existingNames,
        worklistName: this.worklistName
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      this.customWorklistService.getCustomWorklists(this.worklistName).subscribe((res: SubjectCustomWorklist[]) => {
        this.customWorklists = res;
      });
    });
  }

  onCustomWorklistClicked(customWorklist: SubjectCustomWorklist) {
    if (typeof customWorklist.content === "string") {
      this.hideList = true;
      localStorage.setItem(this.worklistName, customWorklist.content);
      this.customWorklistService.saveLastViewedDate(customWorklist).subscribe(() => {
        this.hideList = false;
        window.location.reload();
      });
    }
  }

  toggleFavorite(event: Event, customWorklist: any) {
    event.stopPropagation(); // Prevent triggering other click handlers
    customWorklist.favorite = !customWorklist.favorite; // Toggle the favorite flag
    console.log('Favorite toggled:', customWorklist);
    this.customWorklistService.saveFavoriteWorklist(customWorklist).subscribe(() => {
      this.customWorklistService.getCustomWorklists(this.worklistName).subscribe((res: SubjectCustomWorklist[]) => {
        this.customWorklists = res;
      });
    });
  }

  favoriteCustomWorklists(): any[] {
    return this.customWorklists.filter(item => item.favorite);
  }

  availableCustomWorklists(): any[] {
    return this.customWorklists.filter(item => !item.favorite);
  }
}
