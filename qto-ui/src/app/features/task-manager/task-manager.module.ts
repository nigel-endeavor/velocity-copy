import { NgModule } from "@angular/core";
import { TaskManagerComponent } from "./task-manager.component";
import { StoreModule } from "@ngrx/store";
import { EffectsModule } from "@ngrx/effects";
import { taskManagerFeatureKey, taskManagerReducer, initialState } from "./ngrx/task-manager.reducer";
import { TaskManagerEffects } from "./ngrx/task-manager.effects";
import { FormsModule } from "@angular/forms";
import { CommonModule } from "@angular/common";
import { MatSelectModule } from "@angular/material/select";
import { CdkDrag, CdkDropList } from "@angular/cdk/drag-drop";
import { MatIconModule } from "@angular/material/icon";
import { MatDialogModule } from "@angular/material/dialog";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { TaskGroupTableComponent } from "./task-group-table.component";
import { taskGroupTableConfig } from "./configs/table.configs";
import { AbstractTableModule } from "../abstract-table/abstract-table.module";
import { MatCheckboxModule } from "@angular/material/checkbox";
import { MatTooltipModule } from "@angular/material/tooltip";

@NgModule({
  declarations: [
    TaskManagerComponent,
    TaskGroupTableComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    MatCheckboxModule,
    MatSelectModule,
    MatIconModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatTooltipModule,
    CdkDropList,
    CdkDrag,
    AbstractTableModule.forRoot(taskGroupTableConfig),
    StoreModule.forFeature(taskManagerFeatureKey, taskManagerReducer, {
      initialState
    }),
    EffectsModule.forFeature([TaskManagerEffects])
  ]
})
export class TaskManagerModule { }