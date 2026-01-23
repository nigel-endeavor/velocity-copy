import { Component, ElementRef, HostListener, Inject, OnInit, Renderer2, ViewChild } from "@angular/core";

import { BaseWorklistClass } from "../../utilities/cards/base-worklist.class";
import { select, Store } from "@ngrx/store";
import { editEnabled, getIsReadOnly } from "../../order-detail-page/ngrx/order-details.selectors";
import { NoteComponent } from "../../components/note/note.component";
import { ActivatedRoute, Router } from "@angular/router";
import {
  MAT_LEGACY_DIALOG_DATA as MAT_DIALOG_DATA,
  MatLegacyDialog as MatDialog,
  MatLegacyDialogModule as MatDialogModule,
  MatLegacyDialogRef as MatDialogRef
} from "@angular/material/legacy-dialog";
import { ServiceCyberView } from "../../models/service-cyber-view.model";
import { SERVICE_STATUSES } from "../service-worklist/data/service-statuses.enum";
import { TerminalServiceStatuses } from "../../models/constants/terminal-service-statuses";
import { debounceTime, filter, map, Observable, Subject, Subscription, take } from "rxjs";
import { CommonDropdownSearchCriteria } from "../../interfaces/commonSearch.interface";
import { loadUserStatuses } from "../../order-detail-page/ngrx/order-details.actions";
import { serviceCyberTableActions, serviceCyberTableSelectors } from "./configs/table.config";
import {
  getAppliedFilters,
  getCardViewSelected,
  getColumns,
  getCustomersByKeyAsOptions,
  getFilters,
  geti90ProjectManagersAsOptions,
  getMultieditConfig,
  getMultieditParamsConfig,
  getParamsByKey,
  getProvidersValues,
  getProvisionersAsOptions,
  getSelectedItemsIds,
  getServiceTypes,
  getUpdatedItem
} from "./ngrx/service-cyber-worklist.selectors";
import { ServiceCyberMultiEditService } from "../../services/service-cyber-multiedit.service";
import { setSyncEnabled } from "../../utilities/meta-reducers/sync-local-store";
import {
  addSelectedItems,
  clearFilters,
  loadDropdownContent,
  loadLookupValuesByKey,
  pageDestroyed,
  removeUnselectedItems,
  reorderColumns,
  sendMultieEdit,
  toggleColumn,
  toggleDaterangeColumn,
  toggleView,
  updateFilters,
  updateParams,
  updateServiceCyber,
  updateSort
} from "./ngrx/service-cyber-worklist.actions";
import { getBaseUrl } from "../../utilities";
import { CommonColumn } from "../../interfaces/columns.interface";
import { MultiEditFailureInterface } from "../../interfaces/multiEditFailure.interface";
import { ComponentType } from "@angular/cdk/overlay";
import { MultiEditComponent } from "../multi-edit/components/multi-edit.component";
import { EDITABLE_SECTIONS, MILESTONE_CODES } from "./data/editable-sections.const"
import { DateRange } from "../../interfaces/date-range.interface";
import { ServiceCyberWorklistFilterKeys, ServiceCyberWorklistOptionsKeys } from "./data/service-cyber-worklist.const";
import { SubjectCustomWorklist } from "../../models/subject-custom-worklist.model";
import { SubjectCustomWorklistService } from "../../services/subject-custom-worklist.service";
import {
  SubjectCustomWorklistComponent
} from "../../components/subject-custom-worklist/subject-custom-worklist.component";
import { takeUntil } from "rxjs/operators";


@Component({
  selector: 'app-service-cyber-worklist',
  templateUrl: './service-cyber-worklist.component.html',
  styleUrls: ['../worklist-styles.scss', './service-cyber-worklist.component.scss']
})
export class ServiceCyberWorklistComponent extends BaseWorklistClass implements OnInit {
  public editEnabled$ = this.store.pipe(select(editEnabled));
  public isReadOnly$ = this.store.pipe(select(getIsReadOnly))

  @ViewChild('note') note: NoteComponent;
  @ViewChild('filter') filter: ElementRef;

  selectedService: ServiceCyberView | null;
  editEnabled: boolean;

  public provisionersWithEmpty: string[] = [];
  public i90ProjectManagerswithEmpty: string[] = [];
  public statusOptions: string[] = Object.values(SERVICE_STATUSES);
  public terminalServiceStatuses: string[] = Object.values(TerminalServiceStatuses);
  public workflowViewOpts: string[] = [];


  public serviceTypeOptions$: Observable<any> = this.store.pipe(select(getServiceTypes));
  public isLoading$ = this.store.pipe(select(serviceCyberTableSelectors.getTableDataIsLoading));
  public tableTotal$: Observable<number> = this.store.pipe(select(serviceCyberTableSelectors.getTableTotal));
  public tableData$: Observable<any[]> = this.store.pipe(select(serviceCyberTableSelectors.getTableData));
  public searchCriteria$: Observable<any> = this.store.pipe(select(getFilters), filter(item => !!item));
  public providerOptions$ = this.store.pipe(select(getProvidersValues));
  public provisionerOptions$: Observable<any>;
  public i90ProjectManagerOptions$: Observable<any>;
  public customerOptions$ = this.store.pipe(
    select(getCustomersByKeyAsOptions('masterCustomers')),
    map((res: any[]) => {
      return [
        'Empty',
        ...res
      ]
    })
  );
  public endCustomerOptions$ = this.store.pipe(
    select(getCustomersByKeyAsOptions('customers')),
    map((res: any[]) => {
      return [
        'Empty',
        ...res
      ]
    })
  );

  public masterCustomerParams$ = this.store.pipe(select(getParamsByKey('masterCustomerSearchCriteria')));
  public endCustomerParams$ = this.store.pipe(select(getParamsByKey('endCustomerSearchCriteria')));
  public providersParams$ = this.store.pipe(select(getParamsByKey('providersSearchCriteria')));
  public provisionersParams$ = this.store.pipe(select(getParamsByKey('provisionersSearchCriteria')));
  public i90ProjectManagersParams$ = this.store.pipe(select(getParamsByKey('i90ProjectManagersSearchCriteria')));
  public columns$ = this.store.pipe(select(getColumns));
  public cardViewOn$ = this.store.pipe(select(getCardViewSelected));
  public appliedFilters$: Observable<any> = this.store.pipe(select(getAppliedFilters));
  public updatedItem$: Observable<any> = this.store.pipe(select(getUpdatedItem));
  public selectedItemsIds$: Observable<any> = this.store.pipe(select(getSelectedItemsIds));
  public multieditConfig$ = this.store.pipe(select(getMultieditConfig));
  public multieditParamsConfig$ = this.store.pipe(select(getMultieditParamsConfig));
  public multieditParamsConfig: {
    provisioner: CommonDropdownSearchCriteria,
    masterCustomer: CommonDropdownSearchCriteria,
    provider: CommonDropdownSearchCriteria,
    i90ProjectManager: CommonDropdownSearchCriteria,
  };
  public showFilter = true;

  private multiEditSubscription: Subscription;
  private stateSnapshot: any;
  public cyberClient: boolean = false;
  public telecomClient: boolean = false;
  public customWorklists: SubjectCustomWorklist[] = [];
  public worklistName: string = 'serviceCyberWorklist';
  public hideList: boolean = false;

  constructor(
    private router: Router,
    private renderer: Renderer2,
    private store: Store,
    private dialog: MatDialog,
    private promptDialog: MatDialog,
    private serviceCyberMultiEditService: ServiceCyberMultiEditService,
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
    this.route.queryParams.subscribe(params => {
      if (Object.keys(params).length !== 0) {
        // Take a snapshot of the state
        this.store.pipe(take(1)).subscribe(state => {
          this.stateSnapshot = {...state};
        });
        const status = params['status'];
        const hideTerminalStatuses = params['hideTerminalStatuses'];

        setSyncEnabled(false);
        this.store.dispatch(updateFilters({key: 'status', value: status}));
        this.store.dispatch(updateFilters({ key: 'hideTerminalStatuses', value: hideTerminalStatuses }));
      } else {
        this.store.dispatch(serviceCyberTableActions.loadTableData());
      }
    });

    this.store.dispatch(loadUserStatuses());
    this.store.dispatch(loadDropdownContent({filterKey: 'masterCustomerSearchCriteria', optionKey: 'masterCustomers'}));
    this.store.dispatch(loadDropdownContent({filterKey: 'endCustomerSearchCriteria', optionKey: 'customers'}));
    this.store.dispatch(loadDropdownContent({filterKey: 'providersSearchCriteria', optionKey: 'providers'}));
    this.store.dispatch(loadDropdownContent({ filterKey: 'provisionersSearchCriteria', optionKey: 'provisioners', orderId: null}));
    this.store.dispatch(loadDropdownContent({ filterKey: 'i90ProjectManagersSearchCriteria', optionKey: 'i90ProjectManagers', orderId: null}))
    this.store.dispatch(loadLookupValuesByKey({key: "serviceTypes", lookupKey: "TENANT_SERVICE_TYPES"}));
    this.provisionerOptions$ = this.store.pipe(
      select(getProvisionersAsOptions),
      map(res => {
        if (res) {
          // @ts-ignore
          this.provisionersWithEmpty = ['Empty', ...res];
        }
        return res
      }));
    this.i90ProjectManagerOptions$ = this.store.pipe(
      select(geti90ProjectManagersAsOptions),
      map(res => {
        if (res) {
          // @ts-ignore
          this.i90ProjectManagerswithEmpty = ['Empty', ...res];
        }
        return res
      }));
    this.subject.pipe(debounceTime(500)).subscribe((props: any) => {
      this.store.dispatch(updateFilters(props));
    });

    this.multieditParamsConfig$.subscribe(res => {
      this.multieditParamsConfig = res;
    });

    this.multiEditSubscription = this.serviceCyberMultiEditService.onMultiEdit.subscribe(() => {
      this.promptDialog.open(PromptDialog, {
        data: {
          message: 'Your multi edit request is being processed. Refresh the page to see your updates.',
        },
      });
    });
  }

  ngOnDestroy(): void {
    if (this.multiEditSubscription) {
      this.multiEditSubscription.unsubscribe();
    }

    if (this.stateSnapshot) {
      this.store.dispatch(updateFilters(this.stateSnapshot.serviceWorklist.filters));
      setSyncEnabled(true);
    }
    this.store.dispatch(pageDestroyed());
  }

  onWorklistSelectChanged(value: string): void {
    if (this.telecomClient && !this.cyberClient) {
      value = 'service'
    }
    localStorage.setItem('worklist', value);
    this.router.navigate(['/' + value]);
  }

  onServiceClicked(service: ServiceCyberView): void {
    this.selectedService = structuredClone(service);
  }

  onServiceDblClicked(service: ServiceCyberView): void {
    this.router.navigate(['/order', service.orderId, 'location', service.locationId, 'service', service.id])
  }

  onClosePreviewPaneClicked(): void {
    this.selectedService = null;
  }

  onFilterChanged(value: string | boolean | number, key: string): void {
    this.selectedService = null;
    this.store.dispatch(updateFilters({key, value}));
  }

  onLimitChanged(value: number): void {
    this.onFilterChanged(value, 'limit');
    this.onFilterChanged(0, 'offset');
  }

  onSelectAllClick(value: ServiceCyberView[]): void {
    if (value[0].selected) {
      this.store.dispatch(addSelectedItems({value: value}));
    } else {
      this.store.dispatch(removeUnselectedItems({value: value}));
    }
  }

  onSearch(event: any): void {
    this.selectedService = null;
    const value = event.target.value;
    this.subject.next({
      key: 'search', value
    });
  }

  @ViewChild('contextMenu') contextMenuRef: ElementRef;
  contextMenuStyle = {};
  contextMenuService: any;

  onServiceRightClicked(data: any): void {
    const event = data.event;
    const service = data.row;
    this.contextMenuStyle = {
      'display': 'block',
      'position': 'fixed',
      'left.px': event.clientX,
      'top.px': event.clientY
    }
    this.contextMenuService = service;
    //listens for click outside of context menu, closes menu
    let listenerFn = this.renderer.listen('window', 'click', (e: any) => {
      if (!e.target.contains(this.contextMenuRef.nativeElement)) {
        this.contextMenuStyle = {
          'display': 'none',
        }
        this.contextMenuService = null;
        listenerFn(); //removes click listener
      }
    });
  }

  onOpenInNewTabClicked(): void {
    const url = getBaseUrl()
      + 'order/' + this.contextMenuService.orderId
      + '/location/' + this.contextMenuService.locationId
      + '/service/' + this.contextMenuService.id
    window.open(url);
  }

  clickUpdate(): void {
    this.note.save();
    // @ts-ignore
    this.store.dispatch(updateServiceCyber({serviceCyberView: this.selectedService}));
  }

  // getIsReadonlyUser(): boolean {
  //   return !this.securityUtils.userHasPermission(Permissions.USER);
  // }

  toggleSelectedView(): void {
    this.store.dispatch(toggleView());
  }

  //shows/hides the given column
  onColSelectClicked(col: CommonColumn): void {
    this.store.dispatch(toggleColumn({columnName: col.propertyName}));
  }

  //shows/hides dateRange
  onColDateRangeClicked(col: CommonColumn, dateRange?: string): void {
    this.store.dispatch(toggleDaterangeColumn({columnName: col.propertyName}));
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
    this.store.dispatch(serviceCyberTableActions.exportTable({fileName}));
  }

  onChangeSortClicked(event: any) {
    this.store.dispatch(updateSort({sort: event}));
  }

  openDialog(items: number[], cantEdit?: MultiEditFailureInterface[]) {
    const component: ComponentType<MultiEditComponent> = MultiEditComponent;
    const dialogRef = this.dialog.open(component, {
      width: '900px',
      data: {
        header: 'Cyber Order Multi Edit',
        selectLists: this.multieditConfig$,
        editableSections: EDITABLE_SECTIONS,
        searchParams: {
          ...this.multieditParamsConfig
        },
        updateFunctions: {
          masterCustomer: (value: string | number, key: string) => {
            this.onUpdateParams(value, 'masterCustomerSearchCriteria', key, 'masterCustomers')
          },
          provider: (value: string | number, key: string) => {
            this.onUpdateParams(value, 'providersSearchCriteria', key, 'providers')
          },
        },
        cantEdit
      },
      panelClass: 'modal-dialog',
    });

    dialogRef.afterClosed().pipe(take(1)).subscribe((result: any) => {
      if (result) {

        this.store.dispatch(sendMultieEdit({
          formResult: result,
          milestoneCodes: MILESTONE_CODES
        }));
      }
    });
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
      this.selectedService = null;
      this.subject.next({value, key: col.propertyName});
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

  onUpdateParams(value: string | number, filterKey: ServiceCyberWorklistFilterKeys, key: string, optionKey: ServiceCyberWorklistOptionsKeys): void {
    this.store.dispatch(updateParams({key, value, filterKey, optionKey}));
  }

  reorderColumns(event: { columns: CommonColumn[] }): void {
    this.store.dispatch(reorderColumns({columns: event.columns}));
  }

  getFilterDisplayName(propertyName: string): Observable<string> {
    return this.columns$.pipe(
      take(1),
      map((columns: CommonColumn[]) => {
        switch (propertyName) {
          case 'hideTerminalStatuses':
            return 'Hide Terminal Statuses';
          case 'macOnly':
            return 'MAC Orders Only';
          case 'search':
            return 'Search';
          case 'serviceType':
            return 'Service Type';
          case 'pendingDisconnect':
            return 'Pending Disconnect';
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
  constructor(public dialogRef: MatDialogRef<PromptDialog>, @Inject(MAT_DIALOG_DATA) public data: PromptContent) {
  }

  onClose() {
    this.dialogRef.close();
  }
}



