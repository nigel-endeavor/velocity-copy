import { Component, ElementRef, HostListener, Inject, OnInit, Renderer2, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NoteComponent } from '../../components/note/note.component';
import { ServiceView } from '../../models/service-view.model';
import { TerminalServiceStatuses } from '../../models/constants/terminal-service-statuses';
import { debounceTime, filter, map, Observable, Subscription, take } from 'rxjs';
import { select, Store } from '@ngrx/store';
import { serviceTableActions, serviceTableSelectors } from './configs/table.config';
import { CommonColumn } from '../../interfaces/columns.interface';
import { MultiEditFailureInterface } from '../../interfaces/multiEditFailure.interface';
import { ComponentType } from '@angular/cdk/overlay';
import { MultiEditComponent } from '../multi-edit/components/multi-edit.component';
import { ORDERING_MULTIEDIT_EDITABLE_SECTIONS, MILESTONE_CODES } from './data/ordering-multiedit-editable-sections.const';
import { MAT_LEGACY_DIALOG_DATA as MAT_DIALOG_DATA, MatLegacyDialog as MatDialog, MatLegacyDialogModule as MatDialogModule, MatLegacyDialogRef as MatDialogRef } from '@angular/material/legacy-dialog';
import { DateRange } from '../../interfaces/date-range.interface';
import { MultiEditService } from '../../services/multiEdit.service';
import { BaseWorklistClass } from '../../utilities/cards/base-worklist.class';
import { ServiceWorklistFilterKeys, ServiceWorklistOptionsKeys } from './data/services-worklist.consts';
import { CommonDropdownSearchCriteria } from '../../interfaces/commonSearch.interface';
import { SERVICE_STATUSES } from './data/service-statuses.enum';
import * as serviceWorklistSelectors from './ngrx/service-worklist.selectors';
import * as serviceInventoryWorklistSelectors from './ngrx-inventory/service-inventory-worklist.selectors';
import * as serviceInventoryWorklistActions from './ngrx-inventory/service-inventory-worklist.actions';
import { serviceInventoryTableActions, serviceInventoryTableSelectors } from './configs/service-inventory-table.configs';
import * as serviceWorklistActions from './ngrx/service-worklist.actions';
import { editEnabled, getIsReadOnly} from 'src/app/order-detail-page/ngrx/order-details.selectors';
import { loadUserStatuses } from 'src/app/order-detail-page/ngrx/order-details.actions';
import { NewMacdComponent } from '../macds/new-macd/new-macd.component';
import { MacdService } from '../../services/macd.service';
import { getBaseUrl } from 'src/app/utilities';
import { setSyncEnabled } from '../../utilities/meta-reducers/sync-local-store';
import { INVENTORY_MULTIEDIT_EDITABLE_SECTIONS } from './data/inventory-multiedit-editable-sections.const';
import { MultiDisputeComponent } from '../multi-dispute/multi-dispute.component';
import { SubjectCustomWorklist } from "../../models/subject-custom-worklist.model";
import { SubjectCustomWorklistService } from "../../services/subject-custom-worklist.service";
import {
  SubjectCustomWorklistComponent
} from "../../components/subject-custom-worklist/subject-custom-worklist.component";
@Component({
  selector: 'app-service-worklist',
  templateUrl: './service-worklist.component.html',
  styleUrls: ['../worklist-styles.scss', './service-worklist.component.scss']
})
export class ServiceWorklistComponent extends BaseWorklistClass implements OnInit {
  public editEnabled$ = this.store.pipe(select(editEnabled));
  public isReadOnly$ = this.store.pipe(select(getIsReadOnly))

  @ViewChild('note') note: NoteComponent;
  @ViewChild('filter') filter: ElementRef;

  isInventory: boolean = false;
  selectedService: ServiceView | null;
  editEnabled: boolean;

  public provisionersWithEmpty: string[] = [];
  public statusOptions: string[] = Object.values(SERVICE_STATUSES);
  public terminalServiceStatuses: string[] = Object.values(TerminalServiceStatuses);
  public workflowViewOpts: string[] = ['Order Receipt', 'Pending Order Submission', 'Site Surveys Pending', 'Construction Pending', 'Pending FOC Assignment', 'Upcoming FOC', 'Past Due FOC', 'Circuit Complete', 'On Hold', 'Today\'s Follow Ups'];

  public serviceWorklistViews$: Observable<string[]>;

  public tableTotal$: Observable<number>;
  public isLoading$: Observable<any>;
  public tableData$: Observable<any[]>;
  public searchCriteria$: Observable<any>;
  public providerOptions$: Observable<any>;
  public serviceBilledToOptions$: Observable<any>;
  public provisionerOptions$: Observable<any>;
  public qaManagerOptions$: Observable<any>;
  public customerOptions$: Observable<any>;
  public endCustomerOptions$: Observable<any>;
  public masterCustomerParams$: Observable<any>;
  public endCustomerParams$: Observable<any>;
  public provisionersParams$: Observable<any>;
  public qaManagerParams$: Observable<any>;
  public providersParams$: Observable<any>;
  public columns$: Observable<any>;
  public cardViewOn$: Observable<any>;
  public updatedItem$: Observable<any>;
  public selectedItemsIds$: Observable<any>;
  public multiEditDropdownConfig$: Observable<any>;
  public levelOfEffort$: Observable<any>;
  public multieditParamsConfig$: Observable<any>;
  public clientManagers$: Observable<any>;
  public orderTypeOptions$: Observable<any>;
  public subOrderTypeOptions$: Observable<any>;
  public serviceTypeOptions$: Observable<any>;
  public appliedFilters$: Observable<any[]>;
  public contractTermOptions$: Observable<any>;
  public disputeTypeOptions$ : Observable<any>;
  public projectNameOptions$: Observable<any>;
  public jeopResponsibilityOptions$: Observable<any>;

  private multiEditSubscription: Subscription;
  private multiMacdSubscription: Subscription;

  public multieditParamsConfig: {
    provisioner: CommonDropdownSearchCriteria,
    masterCustomer: CommonDropdownSearchCriteria,
    provider: CommonDropdownSearchCriteria,
  };
  public showFilter = true;
  worklistActions: any;
  tableActions: any;
  worklistSelectors: any;
  tableSelectors: any;
  metaData$: Observable<any>;

  private stateSnapshot: any;
  public cyberClient: boolean = false;
  public telecomClient: boolean = false;
  public customWorklists: SubjectCustomWorklist[] = [];
  public worklistName: string = 'serviceWorklist';
  public hideList: boolean = false;

  constructor(
    private router: Router,
    private renderer: Renderer2,
    private store: Store,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private promptDialog: MatDialog,
    private multiEditService: MultiEditService,
    private macdService: MacdService,
    private newMacdDialog: MatDialog,
    private customWorklistService: SubjectCustomWorklistService,
    private customWorklistDialog: MatDialog
  ) {
    super();
  }

  ngOnInit(): void {
    this.telecomClient = localStorage.getItem('TELECOM_CLIENT') === 'true';
    this.cyberClient = localStorage.getItem('CYBER_SECURITY_CLIENT') === 'true';
    this.store.dispatch(loadUserStatuses());
    //initialize actions and selectors
    let inventory = this.route.snapshot.data['inventory'];
    
    if (inventory) {
      this.isInventory = true;
      this.worklistActions = serviceInventoryWorklistActions;
      this.tableActions = serviceInventoryTableActions;
      this.worklistSelectors = serviceInventoryWorklistSelectors;
      this.tableSelectors = serviceInventoryTableSelectors;
      this.worklistName = 'serviceInventoryWorklist';
    } else {
      this.worklistActions = serviceWorklistActions;
      this.tableActions = serviceTableActions;
      this.worklistSelectors = serviceWorklistSelectors;
      this.tableSelectors = serviceTableSelectors; 
    }

    this.store.dispatch(this.worklistActions.loadServiceTypes());
    this.customWorklistService.getCustomWorklists(this.worklistName).subscribe((res: SubjectCustomWorklist[]) => {
      this.customWorklists = res;
    });
    this.serviceTypeOptions$ = this.store.pipe(select(this.worklistSelectors.getServiceTypes));
    this.tableTotal$ = this.store.pipe(select(this.tableSelectors.getTableTotal));
    this.isLoading$ = this.store.pipe(select(this.tableSelectors.getTableDataIsLoading));
    this.tableData$ = this.store.pipe(select(this.tableSelectors.getTableData));
    this.searchCriteria$ = this.store.pipe(select(this.worklistSelectors.getFilters), filter(item => !!item));
    this.masterCustomerParams$ = this.store.pipe(select(this.worklistSelectors.getParamsByKey('masterCustomerSearchCriteria')));
    this.endCustomerParams$ = this.store.pipe(select(this.worklistSelectors.getParamsByKey('endCustomerSearchCriteria')));
    this.provisionersParams$ = this.store.pipe(select(this.worklistSelectors.getParamsByKey('provisionersSearchCriteria')));
    this.qaManagerParams$ = this.store.pipe(select(this.worklistSelectors.getParamsByKey('qaManagerSearchCriteria')));
    this.providersParams$ = this.store.pipe(select(this.worklistSelectors.getParamsByKey('providersSearchCriteria')));
    this.providerOptions$ = this.store.pipe(select(this.worklistSelectors.getProvidersValues));
    this.serviceBilledToOptions$=this.store.pipe(select(this.worklistSelectors.getServiceBilledToValues));
    this.appliedFilters$ = this.store.pipe(select(this.worklistSelectors.getAppliedFilters));
    this.editEnabled$.subscribe((res) => {
      this.editEnabled = res;
    });
    this.customerOptions$ = this.store.pipe(
      // @ts-ignore
      select(this.worklistSelectors.getCustomersByKeyAsOptions('masterCustomers')),
      map((res: any[]) => {
        return [
          'Empty',
          ...res
        ]
      })
    );
    this.endCustomerOptions$ = this.store.pipe(
      // @ts-ignore
      select(this.worklistSelectors.getCustomersByKeyAsOptions('customers')),
      map((res: any[]) => {
        return [
          'Empty',
          ...res
        ]
      })
    );

    this.provisionerOptions$ = this.store.pipe(
      select(this.worklistSelectors.getProvisionersAsOptions),
      map(res => {
        if (res) {
          // @ts-ignore
          this.provisionersWithEmpty = ['Empty', ...res];
        }
        return res
    }));
    this.columns$ = this.store.pipe(select(this.worklistSelectors.getColumns));
    this.cardViewOn$ = this.store.pipe(select(this.worklistSelectors.getCardViewSelected));
    this.updatedItem$ = this.store.pipe(select(this.worklistSelectors.getUpdatedItem));
    this.selectedItemsIds$ = this.store.pipe(select(this.worklistSelectors.getSelectedItemsIds));
    this.multiEditDropdownConfig$ = this.store.pipe(select(this.worklistSelectors.getMultiEditDropdownConfig));
    this.multieditParamsConfig$ = this.store.pipe(select(this.worklistSelectors.getMultieditParamsConfig));

    //initialize observables used by ordering only
    if (!this.isInventory) {
      this.levelOfEffort$ = this.store.pipe(
        select(this.worklistSelectors.getLevelOfEffort),
        map((loe: any) => {
          if (!loe) {
            return ['Empty'];
          }
          return ['Empty', ...loe?.map((item: any) => item.levelOfEffort)];
        }));
      this.clientManagers$ = this.store.pipe(
        select(this.worklistSelectors.getClientManagers),
        map(res => {
          if (res) {
            // @ts-ignore
            return ['Empty', ...res.map(item => item.display)];
          }
          return res
      }));
      this.serviceWorklistViews$ = this.store.pipe(select(this.worklistSelectors.getFilterBuilderNames));
      this.qaManagerOptions$ = this.store.pipe(select(this.worklistSelectors.getQaManagersAsOptions));
      this.projectNameOptions$ = this.store.pipe(
        select(this.worklistSelectors.getProjectNames),
        map(res => {
          if (res) {
            // @ts-ignore
            return res.map(item => item.display);
          }
          return res
        }));
      this.store.dispatch(this.worklistActions.loadLookupValuesByKey({
        key: 'projectNames',
        lookupKey: 'PROJECT_NAME'
      }));
    }

    //initialize observables used by inventory only
    if (this.isInventory) {
      this.store.dispatch(this.worklistActions.loadMetaData());
      this.metaData$ = this.store.pipe(select(this.worklistSelectors.getMetaData));
      this.orderTypeOptions$ = this.store.pipe(select(this.worklistSelectors.getOrderTypes));
      this.subOrderTypeOptions$ = this.store.pipe(select(this.worklistSelectors.getSubOrderTypes));
      this.contractTermOptions$ = this.store.pipe(select(this.worklistSelectors.getContractTermValues));
      this.disputeTypeOptions$ = this.store.pipe(select(this.worklistSelectors.getDisputeTypeValues));
      this.store.dispatch(this.worklistActions.loadLookupValuesByKey({
        key: 'orderTypes',
        lookupKey: 'ORDER_TYPE'
      }));
      this.store.dispatch(this.worklistActions.loadLookupValuesByKey({
        key: 'subOrderTypes',
        lookupKey: 'SUB_ORDER_TYPE'
      }));
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
        const status = params['status'];
        const serviceType = params['serviceType'];
        const provisioner = params['provisioner'];
        const vertekProjectManager = params['vertekProjectManager'];
        const projectManager = params['projectManager'];
        const openJeopResponsibilities = params['openJeopResponsibilities'];
        const hideTerminalStatuses = params['hideTerminalStatuses'];
        const serviceBilledTo = params['serviceBilledTo'];
        const workflowView = params['workflowView'];

        setSyncEnabled(false);

        this.store.dispatch(this.worklistActions.updateFilters({ key: 'parentCompanyName', value: parentCompanyName }));
        this.store.dispatch(this.worklistActions.updateFilters({ key: 'companyName', value: companyName }));
        this.store.dispatch(this.worklistActions.updateFilters({ key: 'provider', value: provider }));
        this.store.dispatch(this.worklistActions.updateFilters({ key: 'status', value: status }));
        this.store.dispatch(this.worklistActions.updateFilters({ key: 'serviceType', value: serviceType }));
        this.store.dispatch(this.worklistActions.updateFilters({ key: 'provisioner', value: provisioner }));
        this.store.dispatch(this.worklistActions.updateFilters({ key: 'vertekProjectManager', value: vertekProjectManager }));
        this.store.dispatch(this.worklistActions.updateFilters({ key: 'projectManager', value: projectManager }));
        this.store.dispatch(this.worklistActions.updateFilters({ key: 'openJeopResponsibilities', value: openJeopResponsibilities }));
        this.store.dispatch(this.worklistActions.updateFilters({ key: 'hideTerminalStatuses', value: hideTerminalStatuses }));
        this.store.dispatch(this.worklistActions.updateFilters({ key: 'serviceBilledTo', value: serviceBilledTo }));
        this.store.dispatch(this.worklistActions.updateFilters({ key: 'workflowView', value: workflowView }));
      } else {
        this.store.dispatch(this.tableActions.loadTableData());
      }
    });
    this.store.dispatch(this.worklistActions.loadLookupValuesByKey({
      key: 'clientManagers',
      lookupKey: 'CLIENT_PROJECT_MANAGER'
    }));
    this.store.dispatch(this.worklistActions.loadLookupValuesByKey({
      key: 'serviceJeopardy',
      lookupKey: 'service_jeopardy'
    }));
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
    this.store.dispatch(this.worklistActions.loadLookupValuesByKey({
      key: 'speed',
      lookupKey: 'SPEED'
    }));
    this.store.dispatch(this.worklistActions.loadLookupValuesByKey({
      key: 'protocols',
      lookupKey: 'NETWORK_PROTOCOL'
    }));
    this.store.dispatch(this.worklistActions.loadLookupValuesByKey({
      key: 'mediaTypes',
      lookupKey: 'MEDIA_TYPE'
    }));
    this.store.dispatch(this.worklistActions.loadLookupValuesByKey({
      key: 'contractTerms',
      lookupKey: 'CONTRACT_TERM'
    }));
    this.store.dispatch(this.worklistActions.loadLookupValuesByKey({
      key: 'disputeTypes',
      lookupKey: 'DISPUTE_TYPE'
    }));
    this.store.dispatch(this.worklistActions.loadLookupValuesByKey({
      key: 'serviceBilledTo',
      lookupKey: 'SERVICE_BILLED_TO'
    }));
    this.store.dispatch(this.worklistActions.loadDropdownContent({ filterKey: 'provisionersSearchCriteria', optionKey: 'provisioners', orderId: null}));
    this.store.dispatch(this.worklistActions.loadDropdownContent({ filterKey: 'qaManagerSearchCriteria', optionKey: 'qaManagers', orderId: null}));
    this.store.dispatch(this.worklistActions.loadDropdownContent({ filterKey: 'endCustomerSearchCriteria', optionKey: 'customers'}));
    this.store.dispatch(this.worklistActions.loadDropdownContent({ filterKey: 'masterCustomerSearchCriteria', optionKey: 'masterCustomers'}));
    this.store.dispatch(this.worklistActions.loadDropdownContent({ filterKey: 'providersSearchCriteria', optionKey: 'providers'}));

    this.store.dispatch(this.worklistActions.loadLevelOfEffort());

    this.subject.pipe(debounceTime(500)).subscribe((props: any) => {
      this.store.dispatch(this.worklistActions.updateFilters(props));
    });

    if (!this.isInventory) {
      this.multieditParamsConfig$.subscribe(res => {
        this.multieditParamsConfig = res;
      });

      this.multiEditSubscription = this.multiEditService.onMultiEdit.subscribe(() => {
        this.promptDialog.open(PromptDialog, {
          data: {
            message: 'Your multi edit request is being processed. Refresh the page to see your updates.',
          },
        });
      });
    }

    if (this.isInventory) {
      this.multiEditSubscription = this.multiEditService.onMultiEdit.subscribe(() => {
        this.promptDialog.open(PromptDialog, {
          data: {
            message: 'Your multi edit request is being processed. Refresh the page to see your updates.',
          },
        });
      });
    } else {
      this.multiMacdSubscription = this.macdService.onMacd.subscribe(() => {
        this.promptDialog.open(PromptDialog, {
          data: {
            message: 'Your multi MACD request is being processed.',
          },
        });
      });
    }
  }

  ngOnDestroy(): void {
    if (this.multiMacdSubscription) {
      this.multiMacdSubscription.unsubscribe();
    }
    if (this.multiEditSubscription) {
      this.multiEditSubscription.unsubscribe();
    }
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
      if (!this.telecomClient && this.cyberClient) {
        value = 'cyberService'
      }
      localStorage.setItem('worklist', value);
      this.router.navigate(['/' + value]);
    }
  }

  onServiceClicked(service: ServiceView): void {
    this.selectedService = structuredClone(service);
  }

  onServiceDblClicked(service: ServiceView): void {
    if (this.isInventory) {
      this.router.navigate(['inventory', 'order', service.orderId, 'location', service.locationId, 'service', service.id]);
    } else {
      this.router.navigate(['order', service.orderId, 'location', service.locationId, 'service', service.id]);
    }
  }

  onClosePreviewPaneClicked(): void {
    this.selectedService = null;
  }

  onFilterChanged(value: string | boolean | number, key: string): void {
    this.selectedService = null;
    this.store.dispatch(this.worklistActions.updateFilters({ key, value }));
  }

  onLimitChanged(value: number): void {
    this.onFilterChanged(value, 'limit');
    this.onFilterChanged(0, 'offset');
  }

  onSelectAllClick(value: ServiceView[]): void {
    if (value[0].selected) {
      this.store.dispatch(this.worklistActions.addSelectedItems({ value: value }));
    } else {
      this.store.dispatch(this.worklistActions.removeUnselectedItems({ value: value }));
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
      'position': 'absolute',
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
    const baseUrl = getBaseUrl();
    const url = `${baseUrl}${this.isInventory ? 'inventory/' : ''}order/${this.contextMenuService.orderId}/location/${this.contextMenuService.locationId}/service/${this.contextMenuService.id}`;
    window.open(url);
  }


  clickUpdate(): void {
    this.note.save();
    // @ts-ignore
    this.store.dispatch(this.worklistActions.updateService({ service: this.selectedService }));
  }

  toggleSelectedView(): void {
    this.store.dispatch(this.worklistActions.toggleView());
  }

  //shows/hides the given column
  onColSelectClicked(col: CommonColumn): void {
    this.store.dispatch(this.worklistActions.toggleColumn({ columnName: col.propertyName }));
  }

  //shows/hides dateRange
  onColDateRangeClicked(col: CommonColumn, dateRange?: string): void {
    this.store.dispatch(this.worklistActions.toggleDaterangeColumn({ columnName: col.propertyName }));
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
    this.store.dispatch(this.tableActions.exportTable({ fileName }));
  }

  onChangeSortClicked(event: any) {
    this.store.dispatch(this.worklistActions.updateSort({ sort: event }));
  }

  openMultiEditDialog(items: number[], cantEdit?: MultiEditFailureInterface[]) {
    const component: ComponentType<MultiEditComponent> = MultiEditComponent;

    const dialogRef = this.dialog.open(component, {
      data: {
        header: 'Service Order Multi Edit',
        selectLists: this.multiEditDropdownConfig$,
        editableSections: this.isInventory ? INVENTORY_MULTIEDIT_EDITABLE_SECTIONS : ORDERING_MULTIEDIT_EDITABLE_SECTIONS,
        searchParams: {
          ...this.multieditParamsConfig
        },
        updateFunctions: {
          provisioner: (value: string | number, key: string) => {
            this.onUpdateParams(value, 'provisionersSearchCriteria', key, 'provisioners')
          },
          masterCustomer: (value: string | number, key: string) => {
            this.onUpdateParams(value, 'masterCustomerSearchCriteria', key, 'masterCustomers')
          },
          endCustomer: (value: string | number, key: string) => {
            this.onUpdateParams(value, 'endCustomerSearchCriteria', key, 'customers')
          },
          provider: (value: string | number, key: string) => {
            this.onUpdateParams(value, 'providersSearchCriteria', key, 'providers')
          }
        },
        cantEdit
      },
      panelClass: 'modal-dialog',
    });

    dialogRef.afterClosed().pipe(take(1)).subscribe((result: any) => {
      if (result) {
        this.store.dispatch(this.worklistActions.sendMultieEdit({
          formResult: result,
          milestoneCodes: MILESTONE_CODES
        }));
      }
    });
  }

  openMacdDialog(items: number[]) {
    const component: ComponentType<NewMacdComponent> = NewMacdComponent;
    const dialogRef = this.newMacdDialog.open(component, {
      data: {
        serviceIds: items,
        isMultiMacd: true
      },
      width: '1000px',
      panelClass: 'modal-dialog'
    });

    dialogRef.afterClosed().pipe(take(1)).subscribe(result => {
      // I don't think we want to do anything here, but catching for now
    });
  }

  openDisputeDialog(serviceIds: number[]) {
    const component: ComponentType<MultiDisputeComponent> = MultiDisputeComponent;
    const dialogRef = this.dialog.open(component, {
      data: {
        serviceIds
      },
      panelClass: 'modal-dialog'
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
    this.store.dispatch(this.worklistActions.clearFilters());
  }

  onClearFilterClicked($event: any, filter: any) {
    $event.stopPropagation();
    this.onFilterChanged(filter.initialValue, filter.name);
  }

  onFilter(event: any, col: CommonColumn): void {
    const value = event.target.value;
    if (col.type == 'date') {
      //@ts-ignore
      this.store.dispatch(this.worklistActions.updateFilters(value, col.propertyName));
    } else {
      this.selectedService = null;
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
      this.store.dispatch(this.worklistActions.updateFilters({
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
        this.store.dispatch(this.worklistActions.updateFilters({
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

  onUpdateParams(value: string | number, filterKey: ServiceWorklistFilterKeys, key: string, optionKey: ServiceWorklistOptionsKeys): void {
    this.store.dispatch(this.worklistActions.updateParams({ key, value, filterKey, optionKey }));
  }

  reorderColumns(event: { columns: CommonColumn[]}): void {
    this.store.dispatch(this.worklistActions.reorderColumns({ columns: event.columns }));
  }

  onDisputeOpenClicked(): void {
    this.router.navigate(['/inventory', 'order', this.selectedService?.orderId, 'location', this.selectedService?.locationId, 'service', this.selectedService?.id, 'disputes']);
  }

  presetChanged(event: string):void {
    this.store.dispatch(this.worklistActions.setFilterBuilder({ fbName: event }));
  }

  getFilterDisplayName(propertyName: string): Observable<string> {
    return this.columns$.pipe(
      take(1),
      map((columns: CommonColumn[]) => {
        switch (propertyName) {
          case 'orderType':
            return 'Open Order Type';
          case 'subOrderType':
            return 'Open Sub Order Type';
          case 'serviceType':
            return 'Service Type';
          case 'workflowView':
            return 'Workflow View';
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
  constructor(public dialogRef: MatDialogRef<PromptDialog>, @Inject(MAT_DIALOG_DATA) public data: PromptContent) { }

  onClose() {
    this.dialogRef.close();
  }
}
