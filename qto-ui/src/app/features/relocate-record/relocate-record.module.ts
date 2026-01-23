import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RelocateRecordComponent } from './relocate-record.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { initialState, relocateRecordFeatureKey, relocateRecordReducer } from './ngrx/relocate-record.reducer';
import { StoreModule } from '@ngrx/store';
import { RelocateRecordTableComponent } from 'src/app/features/relocate-record/relocate-record-table.component';
import { AbstractTableModule } from 'src/app/features/abstract-table/abstract-table.module';
import { relocateRecordTableConfig } from './config/table.config';
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
import { DatepickerModule } from 'src/app/components/datepicker/datepicker.module';
import { DropdownModule } from 'src/app/components/dropdown/dropdown.module';
import { RelocateRecordEffects } from "src/app/features/relocate-record/ngrx/relocate-record.effects"

@NgModule({
  declarations: [
    RelocateRecordComponent, 
    RelocateRecordTableComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    AbstractTableModule.forRoot(relocateRecordTableConfig),
    StoreModule.forFeature(relocateRecordFeatureKey, relocateRecordReducer, {
      initialState
    }),
    EffectsModule.forFeature([RelocateRecordEffects]),
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
export class RelocateRecordModule { }