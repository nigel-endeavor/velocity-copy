import { Component, Inject } from "@angular/core";
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogActions, MatDialogContent } from "@angular/material/dialog";

@Component({
  selector: 'confirmation-dialog',
  templateUrl: './confirmation-dialog.component.html',
  styleUrls: ['./confirmation-dialog.component.scss', '../../order-detail-page/form-styles.scss']
})
export class ConfirmationDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<ConfirmationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  get title(): string {
    return this.data.title || 'Confirmation';
  }

  get message(): string {
    return this.data.message || 'Are you sure?';
  }
}