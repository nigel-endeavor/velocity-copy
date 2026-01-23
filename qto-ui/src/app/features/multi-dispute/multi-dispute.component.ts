import { Component, Inject, ViewChild } from '@angular/core';
import { Store, select } from '@ngrx/store';
import { getDispute, getDisputeTypes, getDisputeAssignments } from './store/multi-dispute.selectors';
import { MAT_LEGACY_DIALOG_DATA, MatLegacyDialogRef } from '@angular/material/legacy-dialog';
import { loadDisputeAssignments, loadDisputeTypes, loadInternalOnlyDefault, pageDestroyed, submitDispute, updateDisputeProperty } from './store/multi-dispute.actions';
import { take } from 'rxjs';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-multi-dispute',
  templateUrl: './multi-dispute.component.html',
  styleUrls: ['./multi-dispute.component.scss', '../../order-detail-page/form-styles.scss']
})
export class MultiDisputeComponent {

  @ViewChild('form') form: NgForm;
  formSubmitted: boolean = false;

  dispute$ = this.store.pipe(select(getDispute));
  disputeTypes$ = this.store.pipe(select(getDisputeTypes));
  disputeAssignments$ = this.store.pipe(select(getDisputeAssignments));

  constructor(
    private store: Store,
    private dialogRef: MatLegacyDialogRef<MultiDisputeComponent>,
    @Inject(MAT_LEGACY_DIALOG_DATA) public data: {
      serviceIds: number[]
    }
  ) {
    dialogRef.backdropClick().subscribe(() => {
      this.onClose();
    });
  }

  ngOnInit(): void {
    this.store.dispatch(loadDisputeTypes());
    this.store.dispatch(loadDisputeAssignments());
    this.store.dispatch(loadInternalOnlyDefault());
  }

  ngOnDestroy(): void {
    this.store.dispatch(pageDestroyed());
  }

  onClose(): void {
    this.dialogRef.close();
  }

  updateDisputeProperty(property: string, event: any): void {
    if (!event) {
      this.store.dispatch(updateDisputeProperty({ key: property, value: null }));
      this.form.controls[property].setValue('');
    } else {
      let value = event.target ? event.target.value : event.value;
      if (['amountDisputedMrc', 'amountDisputedNrc'].includes(property)) {
        value = parseFloat(value.replace('$', '').replace(',', ''));
      }
      this.store.dispatch(updateDisputeProperty({ key: property, value }));
    }
  }

  onSubmit(): void {
    this.dispute$.pipe(take(1)).subscribe(dispute => {
      this.store.dispatch(submitDispute({ serviceIds: this.data.serviceIds, dispute }));
      this.formSubmitted = true;
    });
  }

}
