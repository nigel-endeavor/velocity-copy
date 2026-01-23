import { Component, Inject } from '@angular/core';
import {
  MAT_LEGACY_DIALOG_DATA as MAT_DIALOG_DATA,
  MatLegacyDialogRef as MatDialogRef
} from '@angular/material/legacy-dialog';
import { SubjectCustomWorklistService } from "../../services/subject-custom-worklist.service";
import { SubjectCustomWorklist } from "../../models/subject-custom-worklist.model";

@Component({
  selector: 'app-custom-worklist-dialog',
  templateUrl: './subject-custom-worklist.component.html',
  styleUrls: ['./subject-custom-worklist-component.scss']
})
export class SubjectCustomWorklistComponent {
  nameExists = false;
  updateView = false;

  constructor(
    public dialogRef: MatDialogRef<SubjectCustomWorklistComponent>,
    public customWorklistService: SubjectCustomWorklistService,
    @Inject(MAT_DIALOG_DATA) public data: {
      customWorklist: SubjectCustomWorklist,
      isEdit: boolean,
      existingNames: string[],
      worklistName: string
    }
  ) {
  }


  onCancel() {
    console.log('Cancel clicked');
    this.dialogRef.close();
  }

  onDelete(item: MouseEvent) {
    console.log('Delete clicked');
    this.customWorklistService.deleteCustomWorklist(this.data.customWorklist.id).subscribe(() => {
      this.dialogRef.close({
      });
    });
  }

  onSave(item: MouseEvent) {
    if ((!this.data.isEdit && this.data.existingNames.includes(this.data.customWorklist.name))
          || this.data.existingNames.filter(w => w === this.data.customWorklist.name).length > 1) {
      this.nameExists = true;
    } else {
      if (this.updateView || !this.data.isEdit) {
        this.data.customWorklist.content = localStorage.getItem(this.data.worklistName)
      }
      this.customWorklistService.save(this.data.customWorklist).subscribe(() => {
        this.dialogRef.close({});
      });
    }
  }
}
