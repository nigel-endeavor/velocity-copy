import { Component, Inject, OnInit } from '@angular/core';
import { getColumns, getFilters } from './store/link-location.selectors';
import { Store, select } from '@ngrx/store';
import { Observable, Subject, debounceTime } from 'rxjs';
import { linkLocationTableActions, linkLocationTableSelectors } from './config/table.config';
import { MAT_LEGACY_DIALOG_DATA as MAT_DIALOG_DATA, MatLegacyDialogRef as MatDialogRef } from '@angular/material/legacy-dialog';
import * as actions from './store/link-location.actions';

@Component({
  selector: 'app-link-location',
  templateUrl: './link-location.component.html',
  styleUrls: ['./link-location.component.scss']
})
export class LinkLocationComponent implements OnInit {

  public columns$ = this.store.pipe(select(getColumns));
  public tableData$: Observable<any> = this.store.pipe(select(linkLocationTableSelectors.getTableData));
  public isLoading$ = this.store.pipe(select(linkLocationTableSelectors.getTableDataIsLoading));
  public tableTotal$: Observable<number> = this.store.pipe(select(linkLocationTableSelectors.getTableTotal));
  public searchCriteria$: Observable<any> = this.store.pipe(select(getFilters));
  public subject: Subject<{ key: string, value: any }> = new Subject();

  constructor(
    private dialogRef: MatDialogRef<LinkLocationComponent>,
    private store: Store,
    @Inject(MAT_DIALOG_DATA) public data: {
      companyId: number,
      locationId: number,
      selectedParentLocationId: number | null
    }
  ) {
    dialogRef.backdropClick().subscribe(() => {
      this.onClose();
    });
  }

  ngOnInit(): void {
    console.log('LinkLocationComponent ngOnInit');
    this.store.dispatch(actions.updateFilters({ key: 'parentCompanyId', value: this.data.companyId }));
    this.store.dispatch(linkLocationTableActions.loadTableData());

    this.subject.pipe(debounceTime(500)).subscribe((props: any) => {
      this.store.dispatch(actions.updateFilters(props));
    });
  }

  onSearch(event: any): void {
    this.data.selectedParentLocationId = null;
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

}
