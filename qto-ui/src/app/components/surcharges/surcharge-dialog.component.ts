import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MAT_LEGACY_DIALOG_DATA as MAT_DIALOG_DATA, MatLegacyDialogRef as MatDialogRef } from '@angular/material/legacy-dialog';
import { Store, select } from '@ngrx/store';
import { getColumns, getFilters, getSelectedSurcharge, getSurchargeTypes } from './store/surcharge-dialog.selectors';
import * as actions from './store/surcharge-dialog.actions';
import { Observable, filter } from 'rxjs';
import { surchargeTableActions, surchargeTableSelectors } from './configs/table.config';
import { updateSort } from './store/surcharge-dialog.actions';
import { NgForm } from '@angular/forms';
import { ServiceSurcharge } from '../../models/service-surcharge-model';

@Component({
  selector: 'app-surcharges-dialog',
  templateUrl: './surcharge-dialog.component.html',
  styleUrls: ['./surcharge-dialog.component.scss']
})
export class SurchargeDialogComponent implements OnInit {
  showForm: boolean = false;

  public columns$ = this.store.pipe(select(getColumns));
  public tableData$: Observable<any[]> = this.store.pipe(select(surchargeTableSelectors.getTableData));
  public isLoading$ = this.store.pipe(select(surchargeTableSelectors.getTableDataIsLoading));
  public tableTotal$: Observable<number> = this.store.pipe(select(surchargeTableSelectors.getTableTotal));
  public searchCriteria$: Observable<any> = this.store.pipe(select(getFilters), filter(item => !!item));
  @ViewChild('surchargeForm') surchargeForm: NgForm;

  selectedSurcharge$ = this.store.pipe(select(getSelectedSurcharge));
  surchargeTypes$ = this.store.pipe(select(getSurchargeTypes));

  constructor(private dialogRef: MatDialogRef<SurchargeDialogComponent>,
    private store: Store,
    @Inject(MAT_DIALOG_DATA) public data: {
      id: number,
      companyId: number,
      level: string,
      isInventory: boolean
    })
  {
    
    dialogRef.backdropClick().subscribe(() => {
      this.onClose();
    });
  }

  ngOnInit(): void {
    this.store.dispatch(surchargeTableActions.loadTableData());
    this.store.dispatch(actions.updateFilters({ key: 'serviceId', value: this.data.id }));
    this.store.dispatch(actions.loadSurchargeTypes({ companyId: this.data.companyId }));
  }

  onChangeSortClicked(event: any) {
    this.store.dispatch(updateSort({ sort: event }));
  }

  onFilterChanged(value: string | boolean | number, key: string):void {
    this.store.dispatch(actions.setSelectedSurcharge({ surcharge: undefined }))
    this.store.dispatch(actions.updateFilters({ key, value}));
  }

  onAddClicked() {
    this.store.dispatch(actions.createSurcharge({ id: this.data.id }));
  }

  onSurchargeDblClicked(surcharge: ServiceSurcharge) {
    if (this.data.isInventory) {
      return;
    }
    this.store.dispatch(actions.setSelectedSurcharge({ surcharge }));
  }

  onPropertyUpdate(key: string, event: any) {
    //pass key/value to actions for updating field
    this.store.dispatch(actions.updateSurcharge({ key: key, value: event.value }));
  }

  save(surcharge: ServiceSurcharge) {
    this.store.dispatch(actions.saveSurcharge({ surcharge }));
  }

  onDelete(surcharge: ServiceSurcharge) {
    this.store.dispatch(actions.deleteSurcharge({ surcharge }));
  }

  onCancel() {
    this.store.dispatch(actions.pageDestroyed());
  }

  onClose() {
    this.store.dispatch(actions.pageDestroyed());
    this.dialogRef.close();
  }
  // this is required since it's an object, not a primitive
  compareSurchargeTypes(option: any, value: any) : boolean {
    return option.id === value.id;
  }
}
