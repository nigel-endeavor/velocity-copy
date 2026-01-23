
import { MatInputModule } from '@angular/material/input';
import { NgModule } from "@angular/core";
import { SubjectCustomWorklistComponent } from "./subject-custom-worklist.component";
import { MatFormFieldModule } from "@angular/material/form-field";
import { FormsModule } from "@angular/forms";
import { MatCheckboxModule } from "@angular/material/checkbox";
import { MatDialogModule } from "@angular/material/dialog";
import { MatButtonModule } from "@angular/material/button";
import { NgIf } from "@angular/common";

@NgModule({
  declarations: [
    SubjectCustomWorklistComponent
  ],
  imports: [
    MatInputModule,
    MatFormFieldModule,
    MatDialogModule,
    MatCheckboxModule,
    FormsModule,
    MatButtonModule,
    NgIf
  ]
})
export class SubjectCustomWorklistModule { }
