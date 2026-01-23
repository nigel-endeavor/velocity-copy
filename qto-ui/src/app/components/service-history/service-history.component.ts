import { Component, ElementRef, Inject, OnInit, Renderer2, ViewChild } from '@angular/core';
import { MAT_LEGACY_DIALOG_DATA as MAT_DIALOG_DATA, MatLegacyDialogRef as MatDialogRef } from '@angular/material/legacy-dialog';
import { Store, select } from '@ngrx/store';
import { getColumns, getFilters } from './store/service-history.selectors';
import { Observable, filter } from 'rxjs';
import { serviceHistoryTableActions, serviceHistoryTableSelectors } from './config/table.config';
import * as actions from './store/service-history.actions';
import { ServiceHistoryView } from 'src/app/models/service-history-view.model';
import { getBaseUrl } from 'src/app/utilities';

@Component({
  selector: 'app-service-history',
  templateUrl: './service-history.component.html',
  styleUrls: ['./service-history.component.scss']
})
export class ServiceHistoryComponent implements OnInit {

  public columns$ = this.store.pipe(select(getColumns));
  public tableData$: Observable<any[]> = this.store.pipe(select(serviceHistoryTableSelectors.getTableData));
  public isLoading$ = this.store.pipe(select(serviceHistoryTableSelectors.getTableDataIsLoading));
  public tableTotal$: Observable<number> = this.store.pipe(select(serviceHistoryTableSelectors.getTableTotal));
  public searchCriteria$: Observable<any> = this.store.pipe(select(getFilters), filter(item => !!item));

  constructor(
    private dialogRef: MatDialogRef<ServiceHistoryComponent>,
    private store: Store,
    private renderer: Renderer2,
    @Inject(MAT_DIALOG_DATA) public data: {
      id: number,
    }
  ) {
    dialogRef.backdropClick().subscribe(() => {
      this.onClose();
    });
  }

  ngOnInit(): void {
    this.store.dispatch(actions.updateFilters({ key: 'serviceId', value: this.data.id }));
    this.store.dispatch(serviceHistoryTableActions.loadTableData());
  }

  onChangeSortClicked(event: any) {
    this.store.dispatch(actions.updateSort({ sort: event }));
  }

  onFilterChanged(value: string | boolean | number, key: string):void {
    this.store.dispatch(actions.updateFilters({ key, value}));
  }

  onRowDblClicked(serviceHistory: ServiceHistoryView | null) {
    if (!serviceHistory) {
      return;
    }

    const url = getBaseUrl()
      + (serviceHistory.orderType == 'Inventory' ? 'inventory/' : '')
      + 'order/' + serviceHistory.orderId
      + '/location/' + serviceHistory.locationId
      + '/service/' + serviceHistory.id;
    window.open(url);
  }

  onClose() {
    this.dialogRef.close();
  }

  @ViewChild('contextMenu') contextMenuRef: ElementRef;
  contextMenuStyle = {};
  contextMenuService: ServiceHistoryView | null;
  onRowRightClicked(data: any) {
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

  onOpenParentClicked(): void {
    if (!this.contextMenuService || !this.contextMenuService.parentServiceLink) {
      return;
    }
    const url = getBaseUrl() + this.contextMenuService.parentServiceLink;
    window.open(url);
  }

}
