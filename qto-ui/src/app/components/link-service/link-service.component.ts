import { Component, Inject, OnInit } from '@angular/core';
import { getColumns, getFilters, getSelectedItems, getSelectedItemsIds } from './store/link-service.selectors';
import * as actions from './store/link-service.actions';
import { Store, select } from '@ngrx/store';
import { Observable, Subject, debounceTime, filter } from 'rxjs';
import { linkServiceTableActions, linkServiceTableSelectors } from './config/table.config';
import { MAT_LEGACY_DIALOG_DATA as MAT_DIALOG_DATA, MatLegacyDialogRef as MatDialogRef } from '@angular/material/legacy-dialog';
import { ServiceView } from "../../models/service-view.model";
import { addSelectedItems, removeUnselectedItems } from "./store/link-service.actions";


@Component({
  selector: 'app-link-service',
  templateUrl: './link-service.component.html',
  styleUrls: ['./link-service.component.scss']
})
export class LinkServiceComponent implements OnInit {
  title : string;
  multiSelect = false;

  public columns$ = this.store.pipe(select(getColumns));
  public tableData$: Observable<any> = this.store.pipe(select(linkServiceTableSelectors.getTableData));
  public isLoading$ = this.store.pipe(select(linkServiceTableSelectors.getTableDataIsLoading));
  public tableTotal$: Observable<number> = this.store.pipe(select(linkServiceTableSelectors.getTableTotal));
  public searchCriteria$: Observable<any> = this.store.pipe(select(getFilters), filter(item => !!item));
  public selectedItemsIds$: Observable<any> = this.store.pipe(select(getSelectedItemsIds));
  public subject: Subject<{ key: string, value: any }> = new Subject();

  constructor(
    private dialogRef: MatDialogRef<LinkServiceComponent>,
    private store: Store,
    @Inject(MAT_DIALOG_DATA) public data: {
      companyId: number,
      locationId: number,
      type: string,
      from: string,
      incomingServiceId: number,
      selectedParentServiceId: number | null
    }
  ) {
    dialogRef.backdropClick().subscribe(() => {
      this.onClose();
    });
  }

  ngOnInit(): void {
    switch (this.data.type) {
      case 'Inventory':
        this.title = 'Link to Inventory';
        this.multiSelect = false;
        break;
      case 'Link':
        this.title = 'Link to Parent Service';
        this.multiSelect = true;
        break;
      case 'Bundle':
        this.title = 'Bundle Services';
        this.multiSelect = true;
        break;
      default:
        this.title = 'No Title';
    }
    this.store.dispatch(actions.updateFilters({ key: 'companyId', value: this.data.companyId }, ));
    this.store.dispatch(actions.updateFilters({ key: 'locationId', value: this.data.locationId }));
    this.store.dispatch(actions.updateFilters({ key: 'linkBundleType', value: this.data.type }));
    this.store.dispatch(actions.updateFilters({ key: 'serviceId', value: this.data.incomingServiceId }));
    this.store.dispatch(actions.updateFilters({ key: 'linkBundleFrom', value: this.data.from }));
    this.store.dispatch(linkServiceTableActions.loadTableData());

    this.subject.pipe(debounceTime(500)).subscribe((props: any) => {
      this.store.dispatch(actions.updateFilters(props));
    });
  }
  ngOnDestroy(): void {
    this.store.dispatch(actions.pageDestroyed());
  }

  onSelectAllClick(value: ServiceView[]):void {
    if (value[0] && value[0].selected) {
      this.store.dispatch(addSelectedItems({value: value}));
    } else {
      this.store.dispatch(removeUnselectedItems({value: value}));
    }
  }

  onSearch(event: any): void {
    this.data.selectedParentServiceId = null;
    const value = event.target.value;
    this.subject.next({
      key: 'search', value
    });
  }

  onChangeSortClicked(event: any) {
    this.store.dispatch(actions.updateSort({ sort: event }));
  }

  onFilterChanged(value: string | boolean | number, key: string): void {
    this.store.dispatch(actions.updateFilters({ key, value }));
  }

  onClose() {
    this.store.dispatch(actions.clearFilters());
    this.dialogRef.close();
  }

  save(items: number[]) {
    if (this.data.type === 'Inventory') {
      this.store.dispatch(actions.saveLinkInventory({
        incomingServiceId: this.data.incomingServiceId,
        selectedItem: this.data.selectedParentServiceId, linkType: this.data.type
      }));
      this.dialogRef.close();
    } else {
      this.store.dispatch(actions.saveLink({
        incomingServiceId: this.data.incomingServiceId,
        selectedItems: items, linkType: this.data.type
      }));
      this.dialogRef.close();
    }
  }
}
