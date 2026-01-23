import {
  ChangeDetectionStrategy,
  Component,
  ContentChild,
  ElementRef,
  EventEmitter,
  HostListener, OnDestroy,
  OnInit,
  Output,
  TemplateRef,
  ViewChild
} from '@angular/core';
import { Subject, take, debounceTime } from 'rxjs';
import { BaseSearchCriteria, DateSearchCriteriaField } from '../../models/base-search-criteria.model';
import { PaginatedResult } from '../../models/paginated-result.model';
import { select, Store } from '@ngrx/store';
import * as selectors from './store/table-deprecated.selectors';
import {
  addSelectedItems,
  clearStore,
  removeSelectedItems,
  addSelectedItem,
  loadProvisioners,
  loadLookupValuesByKey,
  sendMultieEdit
} from './store/table-deprecated.actions';
import { MatLegacyDialog as MatDialog } from '@angular/material/legacy-dialog';
import { ComponentType } from '@angular/cdk/overlay';
import { MultiEditComponent } from '../../features/multi-edit/components/multi-edit.component';
import { ORDERING_MULTIEDIT_EDITABLE_SECTIONS, MILESTONE_CODES } from '../../features/service-worklist/data/ordering-multiedit-editable-sections.const';
import { SubjectInterface } from '../../models/subject.model';
import { LookupValue } from '../../models/lookup-value.model';
import { MultiEditFailureInterface } from '../../interfaces/multiEditFailure.interface';
import { SecurityUtilService } from '../../services/security-util.service';
import { plainToInstance } from 'class-transformer';
import { AbstractBaseModel } from '../../models/abstract-base-model';
import { editEnabled, getIsReadOnly } from 'src/app/order-detail-page/ngrx/order-details.selectors';

//card model
export interface Card<T> { data: T; meta: { page: number }; type?: string; }
//column model
export class Column {
  name: string;
  propertyName: string;
  type?: string;
  hidden?: boolean;
  filterable?: boolean = true;
  width?: string = '100px';
}

@Component({
  templateUrl: './table-deprecated.component.html',
  styleUrls: ['./table-deprecated.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TableDeprecatedComponent<T extends AbstractBaseModel> implements OnInit, OnDestroy {
  public editEnabled$ = this.store.pipe(select(editEnabled));
  public isReadOnly$ = this.store.pipe(select(getIsReadOnly))
  cardViewSelected: boolean = localStorage.getItem('cardViewSelected') == 'true';
  cardPageCount: number = 3;
  @Output() rowClick = new EventEmitter<T>();
  @Output() rowDblClick = new EventEmitter<T>();
  @Output() rowRightClick = new EventEmitter<{row: T, event: any}>();

  //template references for card content
  @ContentChild('cardHeader') cardHeader: TemplateRef<any> | null = null;
  @ContentChild('cardBody') cardBody: TemplateRef<any> | null = null;
  @ContentChild('cardFooter') cardFooter: TemplateRef<any> | null = null;

  @ViewChild('filter') filter: ElementRef;

  showFilter = true;
  persistFilters = false;
  showMultiEdit = false;
  allComplete = false;
  indeterminate = false;

  rowSubject = new Subject<T[]>();
  cards: Card<T>[] = [];
  rows: any[] = [];
  columns: Column[] = [];

  searchCriteria: BaseSearchCriteria; //subclass should override

  dateComparisonOpts = ['BEFORE', 'ON', 'AFTER'];

  //default search criteria fields, can be overridden
  offset: number = 0;
  limit: number = 25;
  total: number = 0;
  sortField: string;
  sortDir: string = 'ASC';

  noteIconColor: string = '#3871C1';
  jeopIconColor: string = '#f5c000';

  selectedItems$ = this.store.pipe(select(selectors.getSelectedItems));
  cantUpdate$ = this.store.pipe(select(selectors.getCantUpdate));
  selectedIds: number[] = [];
  multieditConfig$ = this.store.pipe(select(selectors.getMultieditConfig));
  isLoading$ = this.store.pipe(select(selectors.getIsLoading));
  multieditConfig: {
    provisioner: SubjectInterface[],
    vertekProjectManager: SubjectInterface[],
    clientProjectManager: LookupValue[],
    jeopDescription: LookupValue[],
    jeopResponsibility: LookupValue[],
    provider: LookupValue[],
    uploadSpeed: LookupValue[],
    downloadSpeed: LookupValue[],
    jeopAssignedTo: SubjectInterface[],
  };

  private subject: Subject<string> = new Subject();

  constructor(
    public store: Store,
    public dialog: MatDialog,
    public securityUtils: SecurityUtilService
  ) {
  }

  ngOnInit() {
    this.columns = plainToInstance(Column, this.columns);

    this.selectedItems$.subscribe(res => {
      this.selectedIds = res.map(item => item.id);
    });

    this.cantUpdate$.subscribe(res => {
      if (res.length) {
        this.openDialog(res.map(item => item.service.id), res);
      }
    });


    this.multieditConfig$.subscribe(res => {
      this.multieditConfig = res;
    });
    if (this.persistFilters) {
      this.initSearchCriteria();
    }

    if (!this.searchCriteria) {
      this.searchCriteria = new BaseSearchCriteria();
    }
    this.searchCriteria.limit = this.limit;
    this.searchCriteria.offset = this.offset;
    if (!this.searchCriteria.sortField) {
      this.searchCriteria.sortField = this.sortField;
    }
    if (!this.searchCriteria.sortDir) {
      this.searchCriteria.sortDir = this.sortDir;
    }

    this.isLoading$.subscribe(res => {
      if (!res) {
        this.fetch();
      }
    });

    const savedColString = localStorage.getItem(this.getModelService().path + '-columns');
    if (savedColString) {
      let savedCols: { [key: string]: any } = JSON.parse(savedColString);
      for (const [key, value] of Object.entries(savedCols)) {
        let col = this.columns.find(c => c.name == key);
        if (col) {
          col.hidden = value;
        }
      }
    }

    this.subject.pipe(debounceTime(500)).subscribe(() => {
      this.fetch();
    });
  }

  fetch(): void {
    this.rows = [];
    this.cards = [];

    if (this.persistFilters) {
      localStorage.setItem('/locationViews-filters', JSON.stringify(this.searchCriteria));
    }

    if (this.showMultiEdit) {
      this.store.dispatch(loadProvisioners());
      this.store.dispatch(loadLookupValuesByKey({
        key: 'clientManagers',
        lookupKey: 'CLIENT_PROJECT_MANAGER'
      }));
      this.store.dispatch(loadLookupValuesByKey({
        key: 'serviceJeopardy',
        lookupKey: 'service_jeopardy'
      }));
      this.store.dispatch(loadLookupValuesByKey({
        key: 'jeopardyResponsibility',
        lookupKey: 'jeopardy_responsibility'
      }));
      this.store.dispatch(loadLookupValuesByKey({
        key: 'providers',
        lookupKey: 'PROVIDER'
      }));
      this.store.dispatch(loadLookupValuesByKey({
        key: 'speed',
        lookupKey: 'SPEED'
      }));
      this.store.dispatch(loadLookupValuesByKey({
        key: 'protocols',
        lookupKey: 'NETWORK_PROTOCOL'
      }));
      this.store.dispatch(loadLookupValuesByKey({
        key: 'mediaTypes',
        lookupKey: 'MEDIA_TYPE'
      }));
    }

    this.getModelService().search(this.searchCriteria).subscribe((res: PaginatedResult<T>) => {
      this.rows = res.collection.map(item => {
        return {
        ...item,
          // @ts-ignore
          selected: this.selectedIds.includes(item.id)
        }
      });
      const selectedItemsArray = this.rows.map(item => item.selected);
      if (selectedItemsArray.includes(true) && !selectedItemsArray.includes(false)){
        this.allComplete = true;
        this.indeterminate = false;
      } else if (selectedItemsArray.includes(true) && selectedItemsArray.includes(false)) {
        this.allComplete = false;
        this.indeterminate = true;
      } else {
        this.allComplete = false;
        this.indeterminate = false;
      }
      this.rowSubject.next(this.rows);
      this.offset = res.offset;
      this.limit = res.limit;
      this.total = res.total;

      //populates cards
      this.cards = [];
      this.rows.forEach(row => {
        this.cards.push({ data: row, meta: { page: 0 } });
      });
      setTimeout(() => {
        this.initResizableColumns();
      }, 1);
    });
  }

  initSearchCriteria(): void {
    const savedFilterString = localStorage.getItem(this.getModelService().path + '-filters');
    if (savedFilterString) {
      this.searchCriteria = {
        ...this.searchCriteria,
        ...JSON.parse(savedFilterString)
      };
    }
  }

  //returns the service to poplate the table, subclasses should implement
  getModelService(): any {
    console.error('model service not provided');
  }

  //used to determine if incoming click action is single or double click
  isSingleClick: boolean = true;
  //emits rowClick event, parent component will listen for event and handle action
  onRowClicked(row: T, elementRef: HTMLElement) {
    this.isSingleClick = true;
    setTimeout(() => {
      if (this.isSingleClick) {
        this.rowClick.emit(row);
        this.highlightElement(elementRef);
      }
    }, 200);
  }

  //emits rowDblClick event, parent component will listen for event and handle action
  onRowDblClicked(row: T) {
    this.isSingleClick = false;
    this.rowDblClick.emit(row);
  }

  onMouseUp(row: T, event: any) {
    if (event.which === 3) { //event.which 3 corresponds to right click
      this.rowRightClick.emit({row, event});
    }
  }

  prevHighlightedElement: HTMLElement;
  highlightElement(telementRefr: HTMLElement): void {
    telementRefr.style.backgroundColor = '#DDDDDD';
    if (this.prevHighlightedElement) {
      this.prevHighlightedElement.style.backgroundColor = '';
    }
    this.prevHighlightedElement = telementRefr;
  }

  //subclasses should override this method with status to color mapping
  getStatusColor(row: any): string {
    return 'transparent';
  }

  showJeopIcon(row: any): boolean {
    return false;
  }

  showNoteIcon(row: any): boolean {
    return false;
  }

  //returns text for pagination header
  getPaginationText(): string {
    const startIndex = (this.total > 0) ? 1 + this.offset : 0;
    const endIndex = (this.offset + this.limit < this.total) ? this.offset + this.limit : this.total;
    return 'Displaying ' + startIndex + ' - ' + endIndex + ' of ' + this.total;
  }

  //toggles grid/card view
  toggleSelectedView(): void {
    this.cardViewSelected = !this.cardViewSelected;
    localStorage.setItem('cardViewSelected', this.cardViewSelected.toString());
    setTimeout(() => { //fails if done outside setTimeout
      this.initResizableColumns();
    }, 0);
  }

  //retrieves the value of a cell
  getCellValue(row: any, col: Column): string {
    return row[col.propertyName] ? row[col.propertyName] : '';
  }

  getCriteriaModelValue(col: Column, subName?: string): string {
    //@ts-ignore
    return subName ? this.searchCriteria[col.propertyName][subName] : this.searchCriteria[col.propertyName]
  }

  getSortFieldColName(): string {
    const col = this.columns.find(col => col.propertyName === this.searchCriteria.sortField);
    return col ? col.name : ''
  }

  //calculates progress bar width
  getProgressBarWidth(row: any, col?: Column): string {
    const value = (col) ? this.getCellValue(row, col) : row['progress'];
    if (typeof(value) === 'number') {
      return (value <= 100) ?  value + '%' : '100%';
    }
    return '0%';
  }

  onFiltersClicked(): void {
    if (this.filter.nativeElement.classList.contains('open')) {
      this.filter.nativeElement.classList.remove('open');
    } else {
      this.filter.nativeElement.classList.add('open');
    }
  }

  //listens for document click events, to close filter dialog on outside click
  @HostListener('document:click', ['$event']) toggleOpen(event: Event): void {
    if (this.filter && this.filter.nativeElement.classList.contains('open')) {
      if (!this.filter.nativeElement.contains(event.target)) {
        this.filter.nativeElement.classList.remove('open');
      }
    }
  }

  onHeaderScroll(event: any): void {
    let header = document.querySelector('#tWrapHead');
    let body = document.querySelector('#tWrapBody');
    if (header && body) {
      body.scrollLeft = header.scrollLeft;
    }
  }

  onBodyScroll(event: any): void {
    let header = document.querySelector('#tWrapHead');
    let body = document.querySelector('#tWrapBody');
    if (header && body) {
      header.scrollLeft = body.scrollLeft;
    }
  }

  onFilter(event: any, col: Column): void {
    const value = event.target.value;
    if (col.type == 'date') {
      //@ts-ignore
      this.searchCriteria[col.propertyName].date = value;
    } else {
      //@ts-ignore
      this.searchCriteria[col.propertyName] = value;
    }
    localStorage.setItem(this.getModelService().path + '-filters', JSON.stringify(this.searchCriteria));

    this.subject.next('');
  }

  onDateComparisonFilter(value: string, col: Column): void {
    //@ts-ignore
    this.searchCriteria[col.propertyName].comparison = value;
    localStorage.setItem(this.getModelService().path + '-filters', JSON.stringify(this.searchCriteria));
    this.subject.next('');
  }

  onNextPageClicked(): void {
    this.searchCriteria.offset = this.offset + this.limit;
    this.fetch();
  }

  onPrevPageClicked(): void {
    this.searchCriteria.offset = this.offset - this.limit;
    this.fetch();
  }

  nextPageDisabled(): boolean {
    return this.offset + this.limit >= this.total;
  }

  prevPageDisabled(): boolean {
    return this.offset === 0;
  }

  //exports the current grid view to excel, fileName can be passed in
  export(fileName?: string): void {
    this.searchCriteria.format = 'xlsx';
    this.columns.forEach(col => {
      if (!col.hidden) {
        this.searchCriteria.fields += col.propertyName + ',';
        this.searchCriteria.headers += col.name + ',';
      }
    });
    //removes trailing commas
    this.searchCriteria.fields = this.searchCriteria.fields.slice(0, -1);
    this.searchCriteria.headers = this.searchCriteria.headers.slice(0, -1);

    this.getModelService().export(this.searchCriteria).subscribe((res: any) => {
      let downloadLink = document.createElement('a');
      downloadLink.href = window.URL.createObjectURL(new Blob([res], { type: res.type }));
      if (fileName) {
        downloadLink.setAttribute('download', fileName);
      }
      document.body.appendChild(downloadLink);
      downloadLink.click();
      downloadLink.parentNode?.removeChild(downloadLink);

      this.searchCriteria.format = '';
      this.searchCriteria.fields = '';
      this.searchCriteria.headers = '';
    });
  }

  onSortFieldChanged(event: string) {
    let sortCol = this.columns.find(col => col.name === event);
    if (sortCol) {
      this.searchCriteria.sortField = sortCol.propertyName;
      this.fetch();
    }
  }

  onSortDirChanged(event: string) {
    this.searchCriteria.sortDir = event;
    this.fetch();
  }

  getColNames() {
    return this.columns.map(col => col.name);
  }

  //changes selected page on given card
  onCardPageClicked(event: any, card: any, page: number): void {
    event.stopPropagation(); //stops click action from triggering parent card's click action
    card.meta.page = page;
  }

  resizerStyles = {};
  //initializes resizable columns
  private initResizableColumns(): void {
    const headerTable = document.getElementById('headerTable');
    if (!headerTable) { return; }

    const rowElements: HTMLElement[] = Array.from(document.getElementsByClassName('table-row') as HTMLCollectionOf<HTMLElement>);
    const resizerElements: HTMLElement[] = Array.from(headerTable.getElementsByClassName('resizer') as HTMLCollectionOf<HTMLElement>);

    let index = 0;
    if (this.showMultiEdit) {
      index++;
    }
    resizerElements.forEach(resizer => {
      let i = index++;
      this.resizerStyles = {'height': `${headerTable.offsetHeight - 10}px`};
      if (resizer.parentElement) {
        const th = resizer.parentElement;
        const tdArr = (rowElements.map(row => row.children[i]) as HTMLElement[]);
        this.createColumnResizeEventListeners(th, resizer, tdArr);
      }
    });
  }

  //creates event listeners for column resizing
  private createColumnResizeEventListeners(th: HTMLElement, resizer: HTMLElement, tdArr: HTMLElement[]): void {
    // Track the current position of mouse
    let x = 0;
    let w = 0;

    const mouseDownHandler = function(e: any) {
      // Get the current mouse position
      x = e.clientX;
      // Calculate the current width of column
      const styles = window.getComputedStyle(th);
      w = parseInt(styles.width, 10);
      // Attach listeners for document's events
      document.addEventListener('mousemove', mouseMoveHandler);
      document.addEventListener('mouseup', mouseUpHandler);
    };

    const mouseMoveHandler = function(e: any) {
      // Determine how far the mouse has been moved
      const dx = e.clientX - x;

      // Update the width of column
      let width = `${w + dx}px`
      th.style.width = width;
      tdArr.forEach(td => td.style.width = th.style.width);
    };

    // When user releases the mouse, remove the existing event listeners
    const mouseUpHandler = function () {
      document.removeEventListener('mousemove', mouseMoveHandler);
      document.removeEventListener('mouseup', mouseUpHandler);
    };

    resizer.addEventListener('mousedown', mouseDownHandler);
  }

  //shows/hides the given column
  onColSelectClicked(col: Column): void {
    col.hidden = !col.hidden;
    setTimeout(() => {
      this.initResizableColumns();
    }, 1);

    let savedCols: { [key: string]: any } = {};
    this.columns.forEach(col => {
      savedCols[col.name] = col.hidden;
    });
    localStorage.setItem(this.getModelService().path + '-columns', JSON.stringify(savedCols));
  }
  updateAllComplete(row: any) {
    if (this.rows.some(item => !item.selected) && this.rows.some(item => item.selected)) {
      this.allComplete = false;
      this.indeterminate = true;
    } else if (this.rows.some(item => item.selected)) {
      this.allComplete = true;
      this.indeterminate = false;
    } else {
      this.allComplete = false;
      this.indeterminate = false;
    }
    if (row.selected) {
      this.store.dispatch(addSelectedItem({
        selectedItem: {...row}
      }));
    } else {
      this.store.dispatch(removeSelectedItems({
        unSelectedItems: [{...row}]
      }))
    }
  }

  setAll(checked: boolean) {
    this.rows = this.rows.map(item => ({
      ...item,
      selected: checked
    }));

    if (checked) {
      this.store.dispatch(addSelectedItems({
        selectedItems: [...this.rows.map(row => ({
            ...row
        }))]
      }));
    } else {
      this.store.dispatch(removeSelectedItems({
        unSelectedItems: [...this.rows.map(row => ({
          ...row
        }))]
      }))
    }
    this.allComplete = true;
    this.indeterminate = false;
  }

  openDialog(items: number[], cantEdit?: MultiEditFailureInterface[]) {
    const component: ComponentType<MultiEditComponent> = MultiEditComponent;

    const dialogRef = this.dialog.open(component, {
      data: {
        selectedIds: items,
        selectLists: {
          ...this.multieditConfig
        },
        editableSections: ORDERING_MULTIEDIT_EDITABLE_SECTIONS,
        cantEdit
      },
      width: '500px',
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

  ngOnDestroy() {
    this.store.dispatch(clearStore());
  }

  clearFilters() {
    this.columns.forEach(col => {
      if (col.type === 'date') {
        // @ts-ignore
        this.searchCriteria[col.propertyName] = new DateSearchCriteriaField();
      } else {
        // @ts-ignore
        this.searchCriteria[col.propertyName] = ''
      }
    })
    localStorage.setItem(this.getModelService().path + '-filters', JSON.stringify(this.searchCriteria));
    this.fetch();
  }
}
