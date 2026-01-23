import {
  Component,
  ContentChild,
  ElementRef,
  EventEmitter,
  Input, OnChanges,
  OnDestroy,
  OnInit,
  Output, QueryList, SimpleChanges,
  TemplateRef,
  ViewChild,
  ViewChildren
} from '@angular/core';
import { BaseSearchCriteria } from '../../models/base-search-criteria.model';
import {select, Store } from '@ngrx/store';

import { Permissions, SecurityUtilService } from '../../services/security-util.service';
import { AbstractBaseModel } from '../../models/abstract-base-model';
import { CommonColumn } from '../../interfaces/columns.interface';
import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';
import { editEnabled, getIsReadOnly } from 'src/app/order-detail-page/ngrx/order-details.selectors';

//card model
export interface Card<T> { data: T; meta: { page: number }; type?: string; }

@Component({
  selector: 'app-abstract-table',
  templateUrl: './abstract-table.component.html',
  styleUrls: ['./abstract-table.component.scss']
})
export class AbstractTableComponent<T extends AbstractBaseModel> implements OnInit, OnDestroy, OnChanges {
  public editEnabled$ = this.store.pipe(select(editEnabled));
  public isReadOnly$ = this.store.pipe(select(getIsReadOnly))

  @Input() cardViewSelected: boolean;
  cardPageCount: number = 3;
  @Output() rowClick = new EventEmitter<T>();
  @Output() setActive = new EventEmitter<T>();
  @Output() setActiveAll = new EventEmitter<T[]>();
  @Output() rowDblClick = new EventEmitter<T>();
  @Output() rowRightClick = new EventEmitter<{row: T, event: any}>();
  @Output() pageClicked = new EventEmitter<number>();
  @Output() changeSortClicked = new EventEmitter<{col: string, dir: string}>();
  @Output() reorderColumns = new EventEmitter<{ columns: CommonColumn[] }>();
  @Output() limitChanged = new EventEmitter<number>();

  //template references for card content
  @ContentChild('cardHeader') cardHeader: TemplateRef<any> | null = null;
  @ContentChild('cardBody') cardBody: TemplateRef<any> | null = null;
  @ContentChild('cardFooter') cardFooter: TemplateRef<any> | null = null;

  @ViewChild('headerTable') headerTable: ElementRef;
  @ViewChild('tWrapHead') tWrapHead: ElementRef;
  @ViewChild('tWrapBody') tWrapBody: ElementRef;
  @ViewChildren('tr', { read: ElementRef }) trElements: QueryList<ElementRef>;
  @ViewChildren('resizer', { read: ElementRef }) resizerElements: QueryList<ElementRef>;

  persistFilters = false;
  updatedItemChanged = false;
  @Input() showMultiEdit = false;
  allComplete = false;
  indeterminate = false;

  allowLimitToggle = false;
  limitToggleOpts = [25, 50, 100];

  @Input() updatedItem: T;
  cards: Card<T>[] = [];
  @Input() columns: CommonColumn[] = [];

  @Input() statusColorHandler: Function;
  @Input() searchCriteria: BaseSearchCriteria; //subclass should override

  noteIconColor: string = '#3871C1';
  jeopIconColor: string = '#f5c000';
  disconnectIconColor: string = '#F44336';
  macIconColor: string = '#00E000';
  disputeIconColor: string = '#FFBC09';
  linkedIconColor: string = '#3871C1';
  bundledIconColor: string = '#3871C1';

  @Input() selectedIds: number[] = [];
  @Input() tableData: any[] = [];
  @Input() tableTotal: number | null = 0;
  @Input() draggable = false;
  constructor(
    public store: Store,
    public securityUtils: SecurityUtilService,
  ) {
  }

  ngOnInit() {
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['updatedItem'] && changes['updatedItem'].currentValue.id) {
      this.updatedItemChanged = true;
    }

    if (changes['columns']) {
      setTimeout(() => {
        this.initResizableColumns();
      }, 1);
    }

    if (changes['tableData']) {
      this.tableData = this.tableData.map(item => {
        return {
          ...item,
          // @ts-ignore
          selected: this.selectedIds.includes(item.id)
        }
      });
      const selectedItemsArray = this.tableData.map(item => item.selected);
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

      //populates cards
      this.cards = [];
      this.tableData.forEach(row => {
        this.cards.push({ data: row, meta: { page: 0 } });
      });
      setTimeout(() => {
        this.initResizableColumns();
      }, 1);

      if (this.updatedItem && this.updatedItemChanged) {
        setTimeout(() => {
          let index = this.tableData.findIndex(r => r.id == this.updatedItem.id);
          let elementRef = this.trElements.toArray()[index]?.nativeElement as HTMLElement;
          this.onRowClicked(this.updatedItem, elementRef);
          elementRef.scrollIntoView();
        }, 0);
      }
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
    return this.statusColorHandler(row);
  }

  showJeopIcon(row: any): boolean {
    return false;
  }

  showNoteIcon(row: any): boolean {
    return false;
  }

  showDisconnectIcon(row: any): boolean {
    return false;
  }

  showMacIcon(row: any): boolean {
    return false;
  }
  
  showDisputeIcon(row: any): boolean {
    return false;
  }

  showDisputeFollowUpIcon(row: any): boolean {
    return false;
  }

  showLinkedIcon(row: any): boolean {
    return false;
  }

  showBundledIcon(row: any): boolean {
    return false;
  }

  //returns text for pagination header
  getPaginationText(): string {
    
    // @ts-ignore
    const startIndex = (+this.tableTotal > 0) ? 1 + this.searchCriteria.offset : 0;
    // @ts-ignore
    const endIndex = (this.searchCriteria.offset + this.searchCriteria.limit < +this.tableTotal) ?
      this.searchCriteria.offset + this.searchCriteria.limit : this.tableTotal;
    return 'Displaying ' + startIndex + ' - ' + endIndex + ' of ' + this.tableTotal;
  }

  //toggles grid/card view
  toggleSelectedView(): void {
    this.cardViewSelected = !this.cardViewSelected;
    localStorage.setItem('cardViewSelected', this.cardViewSelected.toString());
    setTimeout(() => { //fails if done outside setTimeout
      this.initResizableColumns();
    }, 0);
  }

  getProgressBarWidth(row: any, col?: CommonColumn): string {
    const value = (col) ? row[col.propertyName] : row['progress'];
    if (typeof(value) === 'number') {
      return (value <= 100) ?  value + '%' : '100%';
    }
    return '0%';
  }

  onNextPageClicked(): void {
    this.pageClicked.emit(+this.searchCriteria.offset + +this.searchCriteria.limit);
  }

  onPrevPageClicked(): void {
    this.pageClicked.emit(+this.searchCriteria.offset - +this.searchCriteria.limit);
  }

  nextPageDisabled(): boolean {
    // @ts-ignore
    return +this.searchCriteria.offset + +this.searchCriteria.limit >= +this.tableTotal;
  }

  prevPageDisabled(): boolean {
    return this.searchCriteria.offset === 0;
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


  //changes selected page on given card
  onCardPageClicked(event: any, card: any, page: number): void {
    event.stopPropagation(); //stops click action from triggering parent card's click action
    card.meta.page = page;
  }

  resizerStyles = {};
  resizing = false;
  //initializes resizable columns
  private initResizableColumns(): void {
    if (!this.cardViewSelected) {
      const headerTable = this.headerTable.nativeElement;
      if (!headerTable) { return; }

      let index = 0;
      if (this.showMultiEdit) {
        index++;
      }
      this.resizerElements.forEach(resizer => {
        let i = index++;
        this.resizerStyles = {'height': `${headerTable.offsetHeight - 10}px`};
        if (resizer.nativeElement.parentElement) {
          const th = resizer.nativeElement.parentElement;
          const tdArr = (this.trElements.map(row => row.nativeElement.children[i]) as HTMLElement[]);
          tdArr.forEach(td => td.style.width = th.style.width);
          this.createColumnResizeEventListeners(th, resizer.nativeElement, tdArr);
        }
      });
    }
  }

  //creates event listeners for column resizing
  private createColumnResizeEventListeners(th: HTMLElement, resizer: HTMLElement, tdArr: HTMLElement[]): void {
    let self = this;
    // Track the current position of mouse
    let x = 0;
    let w = 0;

    const mouseDownHandler = function(e: any) {
      self.resizing = true;
      e.stopPropagation();
      // Get the current mouse position
      x = e.clientX;
      console.log('x', x);
      // Calculate the current width of column
      const styles = window.getComputedStyle(th);
      console.log('th', th);
      w = parseInt(styles.width, 10);
      console.log('w', w);
      // Attach listeners for document's events
      document.addEventListener('mousemove', mouseMoveHandler);
      document.addEventListener('mouseup', mouseUpHandler);
    };

    const mouseMoveHandler = function(e: any) {
      // If the mouse is out of the window, and the last column resizer is being dragged, scroll the table as far as it will go
      if (e.clientX > window.innerWidth && self.resizerElements.toArray().findIndex(item => item.nativeElement === resizer) === self.resizerElements.length - 1) {
        let header = self.tWrapHead.nativeElement;
        let body = self.tWrapBody.nativeElement;
        header.scrollLeft = header.scrollWidth;
        body.scrollLeft = header.scrollWidth;
      }
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
      setTimeout(() => {
        self.resizing = false;
      });
    };

    resizer.addEventListener('mousedown', mouseDownHandler);
  }

  updateAllComplete(newValue: boolean, row: any) {
    this.tableData = this.tableData.map(item => {
      return (item.id === row.id) ? {...item, selected: newValue } : item;
    });
    this.setActive.emit({
      ...row,
      selected: newValue
    });

    if (this.tableData.some(item => !item.selected) && this.tableData.some(item => item.selected)) {
      this.allComplete = false;
      this.indeterminate = true;
    } else if (this.tableData.some(item => item.selected)) {
      this.allComplete = true;
      this.indeterminate = false;
    } else {
      this.allComplete = false;
      this.indeterminate = false;
    }

  }

  setAll(checked: boolean): void {
    this.tableData = this.tableData.map(item => ({
      ...item,
      selected: checked
    }))
    this.setActiveAll.emit( this.tableData );

    this.allComplete = true;
    this.indeterminate = false;
  }

  ngOnDestroy() {
    // this.store.dispatch(clearStore());
  }

  changeSort(col: string): void {
    if (this.resizing) {
      return;
    }
    let sortDir = 'ASC';
    if (this.searchCriteria.sortField === col && this.searchCriteria.sortDir === 'ASC') {
      sortDir = 'DESC';
    } else if (this.searchCriteria.sortField === col && this.searchCriteria.sortDir === 'DESC') {
      sortDir = '';
      col = '';
    }
    this.changeSortClicked.emit({col, dir: sortDir});
  }

  onLimitChange(limit: any): void {
    this.limitChanged.emit(limit);
  }

  onHeaderScroll(event: any): void {
    let header = this.tWrapHead.nativeElement;
    let body = this.tWrapBody.nativeElement;
    if (header && body) {
      body.scrollLeft = header.scrollLeft;
    }
  }

  onBodyScroll(event: any): void {
    let header = this.tWrapHead.nativeElement;
    let body = this.tWrapBody.nativeElement;
    if (header && body) {
      header.scrollLeft = body.scrollLeft;
    }
  }

  dropTable(event: CdkDragDrop<CommonColumn[]>): void {
    const initalCols = [...this.columns];
    const cols = initalCols.filter(item => !item.hidden);
    const droppedCol = cols[event.currentIndex];
    const prevIndex = this.columns.findIndex((d) => d === event.item.data);
    const currentIndex = this.columns.findIndex((d) => d === droppedCol);
    moveItemInArray(initalCols, prevIndex, currentIndex);
    this.reorderColumns.emit({ columns: initalCols});
  }
}
