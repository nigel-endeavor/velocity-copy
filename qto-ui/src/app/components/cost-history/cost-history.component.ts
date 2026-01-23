import { Component, Inject, OnInit, Renderer2 } from '@angular/core';
import { getColumns, getFilters, getMeta } from './store/cost-history.selectors';
import * as actions from './store/cost-history.actions';
import { Store, select } from '@ngrx/store';
import { MAT_LEGACY_DIALOG_DATA as MAT_DIALOG_DATA, MatLegacyDialogRef as MatDialogRef } from '@angular/material/legacy-dialog';
import { costHistoryTableActions, costHistoryTableSelectors } from './config/table.config';
import { Observable, filter, map } from 'rxjs';
import { first } from 'rxjs/operators';
import { CostHistory } from 'src/app/models/cost-history.model';

@Component({
  selector: 'app-cost-history',
  templateUrl: './cost-history.component.html',
  styleUrls: ['./cost-history.component.scss']
})
export class CostHistoryComponent implements OnInit {

  public columns$ = this.store.pipe(select(getColumns));
  public tableData$: Observable<any> = this.store.pipe(select(costHistoryTableSelectors.getTableData));
  public isLoading$ = this.store.pipe(select(costHistoryTableSelectors.getTableDataIsLoading));
  public tableTotal$: Observable<number> = this.store.pipe(select(costHistoryTableSelectors.getTableTotal));
  public searchCriteria$: Observable<any> = this.store.pipe(select(getFilters), filter(item => !!item));
  public meta$: Observable<any> = this.store.pipe(select(getMeta));
  
  public serviceIdOpts$: Observable<any> = this.store.pipe(
    //@ts-ignore
    select(costHistoryTableSelectors.getTableData),
    filter((res: CostHistory[]) => res.length > 0),
    first(),
    map((res: CostHistory[]) => {
      let serviceIds: number[] = [];
      res.forEach(item => {
        if (!serviceIds.includes(item.serviceId)) {
          serviceIds.push(item.serviceId);
        }
      });
      return serviceIds;
    })
  );

  costTypeOpts = ['MRC', 'NRC', 'Annual Recurring Cost'];

  constructor(
    private dialogRef: MatDialogRef<CostHistoryComponent>,
    private store: Store,
    private renderer: Renderer2,
    @Inject(MAT_DIALOG_DATA) public data: {
      id: number,
      type: string
    }
  ) {
    dialogRef.backdropClick().subscribe(() => {
      this.onClose();
    });
  }

  ngOnInit(): void {
    this.store.dispatch(actions.updateFilters({ key: this.data.type + 'Id', value: this.data.id }));
    this.store.dispatch(costHistoryTableActions.loadTableData());
    this.store.dispatch(actions.loadMetaData());
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
