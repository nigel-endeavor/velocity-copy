import { Component, Inject } from "@angular/core";
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogActions, MatDialogContent } from "@angular/material/dialog";

@Component({
  selector: 'options-dialog',
  templateUrl: './options-dialog.component.html',
  styleUrls: ['./options-dialog.component.scss', '../../order-detail-page/form-styles.scss']
})
export class OptionsDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<OptionsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  get title(): string {
    return this.data.title || 'Pick an Option';
  }

  get message(): string {
    return this.data.message || 'Before you can proceed, you\'ll need to select an option.';
  }

  get options(): any[] {
    return this.data.options || [];
  }

  selectOption(option: any) {
    this.dialogRef.close(option);
  }
}