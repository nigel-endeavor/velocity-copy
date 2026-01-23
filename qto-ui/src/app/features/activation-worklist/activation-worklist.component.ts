import { Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ActivationView } from '../../models/activation-view.model';
import { debounceTime, filter, map, Observable, Subject, take } from 'rxjs';
import { select, Store } from '@ngrx/store';
import { activationTableSelectors, activationTableActions } from './configs/table.config';
import * as activationWorklistActions from './ngrx/activation-worklist.actions';
import {
  loadLookupValuesByKey,
  loadMetaData,
  toggleColumn,
  updateFilters,
  updateSort,
  reorderColumns,
  loadDropdownContent,
  resetFilters
} from './ngrx/activation-worklist.actions';
import { CommonColumn } from '../../interfaces/columns.interface';
import { getColumns, getFilters, getMetaData, getStatusesValues, getParamsByKey, getCustomersByKeyAsOptions, getAppliedFilters } from './ngrx/activation-worklist.selectors';
import * as dayjs from 'dayjs';
import { DateRange } from '../../interfaces/date-range.interface';
import { getBaseUrl } from 'src/app/utilities';
import { ActivationWorklistFilterKeys, ActivationWorklistOptionKeys } from './data/activation-worklist.consts';
import { SubjectCustomWorklist } from "../../models/subject-custom-worklist.model";
import { SubjectCustomWorklistService } from "../../services/subject-custom-worklist.service";
import { MatLegacyDialog as MatDialog } from "@angular/material/legacy-dialog";
import { ComponentType } from "@angular/cdk/overlay";
import {
  SubjectCustomWorklistComponent
} from "../../components/subject-custom-worklist/subject-custom-worklist.component";

@Component({
  selector: 'app-activation-worklist',
  templateUrl: './activation-worklist.component.html',
  styleUrls: ['../worklist-styles.scss', './activation-worklist.component.scss']
})
export class ActivationWorklistComponent implements OnInit {

  selectedActivation: any;

  public isEmptySet = false;
  public clickedCohort = '';
  public nullifyier:any = undefined;
  public nullCounter = 0;


  public tableTotal$: Observable<number> = this.store.pipe(select(activationTableSelectors.getTableTotal));
  public isLoading$ = this.store.pipe(select(activationTableSelectors.getTableDataIsLoading));
  public searchCriteria$: Observable<any> = this.store.pipe(select(getFilters), filter(item => !!item));
  public tableData$: Observable<any[]> = this.store.pipe(select(activationTableSelectors.getTableData));
  public activationStatusOpts$ = this.store.pipe(select(getStatusesValues));
  public metaData$ = this.store.pipe(select(getMetaData));


  public customerOptions$ = this.store.pipe(
    select(getCustomersByKeyAsOptions('masterCustomers')),
    map((res: any[]) => {
      return [
        'Empty',
        ...res
      ]
    })
  );
  public masterCustomerParams$ = this.store.pipe(select(getParamsByKey('masterCustomerSearchCriteria')));

  public columns$ = this.store.pipe(select(getColumns));
  public appliedFilters$: Observable<any> = this.store.pipe(select(getAppliedFilters));

  private subject: Subject<{ key: string, value: any }> = new Subject();

  public ranges: any = {
    'Today': [dayjs(), dayjs()],
    'Yesterday': [dayjs().subtract(1, 'days'), dayjs().subtract(1, 'days')],
    'Last 7 Days': [dayjs().subtract(6, 'days'), dayjs()],
    'Last 30 Days': [dayjs().subtract(29, 'days'), dayjs()],
    'This Month': [dayjs().startOf('month'), dayjs().endOf('month')],
    'Last Month': [dayjs().subtract(1, 'month').startOf('month'), dayjs().subtract(1, 'month').endOf('month')],
    'Empty Date': [undefined, undefined],
  }

  public cyberClient: boolean = false;
  public telecomClient: boolean = false;
  public customWorklists: SubjectCustomWorklist[] = [];
  public worklistName: string = 'activationWorklist';
  public hideList: boolean = false;

  constructor(
    private store: Store,
    private router: Router,
    private renderer: Renderer2,
    private customWorklistService: SubjectCustomWorklistService,
    private customWorklistDialog: MatDialog
  ) {
    this.subject.pipe(debounceTime(500)).subscribe((props: any) => {
      this.store.dispatch(updateFilters(props));
    });
  }

  ngOnInit(): void {
    this.customWorklistService.getCustomWorklists(this.worklistName).subscribe((res: SubjectCustomWorklist[]) => {
      this.customWorklists = res;
    });
    this.telecomClient = localStorage.getItem('TELECOM_CLIENT') === 'true';
    this.cyberClient = localStorage.getItem('CYBER_SECURITY_CLIENT') === 'true';
    this.store.dispatch(activationTableActions.loadTableData());
    this.store.dispatch(loadLookupValuesByKey({
      key: 'statuses',
      lookupKey: 'ACTIVATION_ATTEMPT_STATUS'
    }));
    this.store.dispatch(loadDropdownContent({ filterKey: 'masterCustomerSearchCriteria', optionKey: 'masterCustomers'}));
  }

  onWorklistSelectChanged(value: string): void {
    localStorage.setItem('worklist', value);
    this.router.navigate(['/' + value]);
  }

  onActivationClicked(activation: ActivationView): void {
    this.selectedActivation = activation;
  }

  onActivationDblClicked(activation: ActivationView): void {
    this.router.navigate(['/order', activation.orderId, 'location', activation.locationId, 'service', activation.serviceId, 'activation'], { queryParams: { activationId: activation.id } })
  }

  onSearch(event: any): void {
    this.selectedActivation = null;
    const value = event.target.value;
    this.subject.next({
      key: 'search', value
    });
    this.getWorklistMeta();
  }

  @ViewChild('contextMenu') contextMenuRef: ElementRef;
  contextMenuStyle = {};
  contextMenuActivation: any;
  onActivationRightClicked(data: any): void {
    const event = data.event;
    const activation = data.row;
    this.contextMenuStyle = {
      'display': 'block',
      'position': 'absolute',
      'left.px': event.clientX,
      'top.px': event.clientY
    }
    this.contextMenuActivation = activation;
    //listens for click outside of context menu, closes menu
    let listenerFn = this.renderer.listen('window', 'click', (e: any) => {
      if (!e.target.contains(this.contextMenuRef.nativeElement)) {
        this.contextMenuStyle = {
          'display': 'none',
        }
        this.contextMenuActivation = null;
        listenerFn(); //removes click listener
      }
    });
  }

  onOpenInNewTabClicked(): void {
    const url = getBaseUrl()
      + 'order/' + this.contextMenuActivation.orderId
      + '/location/' + this.contextMenuActivation.locationId
      + '/service/' + this.contextMenuActivation.serviceId
      + '/activation?activationId=' + this.contextMenuActivation.id;
    window.open(url);
  }

  getWorklistMeta(): void {
    this.store.dispatch(loadMetaData());
  }

  onColSelectClicked(col: CommonColumn): void {
    this.store.dispatch(toggleColumn({ columnName: col.propertyName}));
  }

  checkIsEmpty(value: { label: string, dates: any[] }): void {
    this.isEmptySet = value.label.includes('Empty');
    this.clickedCohort = value.label;
  };

  onDateRangeFilter(value: DateRange, searchCriteria: any, col: string): void {
    if (this.isEmptySet && this.nullCounter === 0) {
      this.nullifyier = value;
      setTimeout(() => {
        this.nullCounter++;
        this.nullifyier = null
      }, 0)
      this.store.dispatch(updateFilters({
        key: col,
        // @ts-ignore
        value: {
          isEmpty: true,
          dateCohort: this.clickedCohort,
          dateRange: null
        }
      }))
    } else {
      this.nullCounter = 0;
      const formattedDates = {
        startDate: value.startDate ? new Date(value.startDate).toISOString().split('T')[0] : null,
        endDate: value.endDate ? new Date(value.endDate).toISOString().split('T')[0]: null
      };

      if ((formattedDates.startDate != searchCriteria[col].dateRange?.startDate) ||
        (formattedDates.endDate != searchCriteria[col].dateRange?.endDate)
      ) {
        this.store.dispatch(updateFilters({
          key: col,
          // @ts-ignore
          value: {
            isEmpty: false,
            dateCohort: this.clickedCohort,
            dateRange: value.startDate === null && value.endDate === null ? null : formattedDates
          }
        }))
      }
    }
    this.clickedCohort = '';
  }

  onChangeSortClicked(event: any) {
    this.store.dispatch(updateSort({ sort: event }));
  }

  onFilterChanged(value: string | boolean | number, key: string):void {
    this.selectedActivation = null;
    this.store.dispatch(updateFilters({ key, value}));
  }

  onLimitChanged(value: number): void {
    this.onFilterChanged(value, 'limit');
    this.onFilterChanged(0, 'offset');
  }

  exportTable(fileName: string): void {
    this.store.dispatch(activationTableActions.exportTable({ fileName }));
  }

  reorderColumns(event: { columns: CommonColumn[]}): void {
    this.store.dispatch(reorderColumns({ columns: event.columns }));
  }

  onUpdateParams(value: string | number, filterKey: ActivationWorklistFilterKeys, key: string, optionKey: ActivationWorklistOptionKeys): void {
    this.store.dispatch(activationWorklistActions.updateParams({ key, value, filterKey, optionKey }));
  }

  resetFilters() {
    this.store.dispatch(resetFilters());
  }

  getFilterDisplayName(propertyName: string): Observable<string> {
    return this.columns$.pipe(
      take(1),
      map((columns: CommonColumn[]) => {
        switch (propertyName) {
          case 'search':
            return 'Search';
          case 'disputeOpen':
            return 'Dispute Open';
          default:
            let name = columns.find(col => col.propertyName === propertyName)?.name;
            return name ? name : propertyName;
        }
      })
    );
  }

  onClearFilterClicked($event: any, filter: any) {
    $event.stopPropagation();
    this.onFilterChanged(filter.initialValue, filter.name);
  }

  onClearScheduledCheckInTimeClicked($event: any, filter: any) {
    // Separate function for this because it has a default value
    $event.stopPropagation();
    const newObject = {
      ...filter.initialValue,
      dateRange: undefined
    };
    this.onFilterChanged(newObject, filter.name);
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
