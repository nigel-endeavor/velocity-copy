import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RelocateInventoryRecordComponent } from './relocate-inventory-record.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { initialState, relocateInventoryRecordFeatureKey, relocateRecordReducer } from './ngrx/relocate-inventory-record.reducer';
import { StoreModule } from '@ngrx/store';
import { RelocateInventoryRecordTableComponent } from './relocate-inventory-record-table.component';
import { AbstractTableModule } from '../../features/abstract-table/abstract-table.module';
import { relocateInventoryRecordTableConfig } from './config/table.config';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatLegacyDialogModule } from '@angular/material/legacy-dialog';
import { MatSelectModule } from '@angular/material/select';
import { EffectsModule } from '@ngrx/effects';
import { DatepickerModule } from '../../components/datepicker/datepicker.module';
import { DropdownModule } from '../../components/dropdown/dropdown.module';
import { RelocateInventoryRecordEffects } from "./ngrx/relocate-inventory-record.effects"

@NgModule({
  declarations: [
    RelocateInventoryRecordComponent, 
    RelocateInventoryRecordTableComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    AbstractTableModule.forRoot(relocateInventoryRecordTableConfig),
    StoreModule.forFeature(relocateInventoryRecordFeatureKey, relocateRecordReducer, {
      initialState
    }),
    EffectsModule.forFeature([RelocateInventoryRecordEffects]),
    DragDropModule,
    ReactiveFormsModule,
    DragDropModule,
    MatSelectModule,
    MatCardModule,
    MatInputModule,
    MatFormFieldModule,
    MatIconModule,
    MatDatepickerModule,
    MatCheckboxModule,
    DatepickerModule,
    DropdownModule,
    MatLegacyDialogModule
  ]
})
export class RelocateInventoryRecordModule { }