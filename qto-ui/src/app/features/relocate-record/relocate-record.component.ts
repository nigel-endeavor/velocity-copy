import { Component, Inject } from '@angular/core';
import { MAT_LEGACY_DIALOG_DATA as MAT_DIALOG_DATA, MatLegacyDialogRef as MatDialogRef } from '@angular/material/legacy-dialog';
import { select, Store } from '@ngrx/store';
import { Observable, Subject, debounceTime, filter } from 'rxjs';
import { relocateRecordTableActions, relocateRecordTableSelectors } from './config/table.config';
import * as actions from './ngrx/relocate-record.actions';
import { getColumns, getFilters } from './ngrx/relocate-record.selectors';

@Component({
  selector: 'app-relocate-record',
  templateUrl: './relocate-record.component.html',
  styleUrls: ['./relocate-record.component.scss']
})
export class RelocateRecordComponent {

  public columns$ = this.store.pipe(select(getColumns));
  public tableData$: Observable<any> = this.store.pipe(select(relocateRecordTableSelectors.getTableData));
  public isLoading$ = this.store.pipe(select(relocateRecordTableSelectors.getTableDataIsLoading));
  public tableTotal$: Observable<number> = this.store.pipe(select(relocateRecordTableSelectors.getTableTotal));
  public searchCriteria$: Observable<any> = this.store.pipe(select(getFilters), filter(item => !!item));
  public subject: Subject<{ key: string, value: any }> = new Subject();

  constructor(private dialogRef: MatDialogRef<RelocateRecordComponent>,
    private store: Store,
    @Inject(MAT_DIALOG_DATA) public data: {
      companyId: number,
      locationId: number,
      output: any,
    }) {
    dialogRef.backdropClick().subscribe(() => {
      this.onClose();
    });
  }

  ngOnInit(): void {
    this.store.dispatch(actions.updateFilters({ key: 'parentCompanyId', value: this.data.companyId }));
    this.store.dispatch(actions.updateFilters({ key: 'relocateLocationId', value: this.data.locationId }));
    this.store.dispatch(relocateRecordTableActions.loadTableData());
    this.subject.pipe(debounceTime(500)).subscribe((props: any) => {
      this.store.dispatch(actions.updateFilters(props));
    });
  }

  onSearch(event: any): void {
    this.data.output = null;
    const value = event.target.value;
    this.subject.next({
      key: 'search', value
    });
  }

  onClose() {
    this.store.dispatch(actions.clearFilters());
    this.dialogRef.close();
  }
  onChangeSortClicked(event: any) {
    this.store.dispatch(actions.updateSort({ sort: event }));
  }

  onFilterChanged(value: string | boolean | number, key: string): void {
    this.store.dispatch(actions.updateFilters({ key, value }));
  }
  onRowClicked(event: any) {
    this.data.output = {
      locationId: event.id,
      orderId: event.orderId
    };
  }
}
