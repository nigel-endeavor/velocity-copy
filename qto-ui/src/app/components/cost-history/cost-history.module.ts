import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CostHistoryComponent } from './cost-history.component';
import { CostHistoryTableComponent } from './cost-history-table.component';
import { AbstractTableModule } from 'src/app/features/abstract-table/abstract-table.module';
import { costHistoryTableConfig } from './config/table.config';
import { costHistoryFeatureKey, costHistoryReducer, initialState } from './store/cost-history.reducer';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { StoreModule } from '@ngrx/store';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { EffectsModule } from '@ngrx/effects';
import { DatepickerModule } from '../datepicker/datepicker.module';
import { CostHistoryEffects } from './store/cost-history.effects';
import { DropdownModule } from '../dropdown/dropdown.module';
import { MatLegacyProgressSpinnerModule } from '@angular/material/legacy-progress-spinner';



@NgModule({
  declarations: [
    CostHistoryComponent,
    CostHistoryTableComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    AbstractTableModule.forRoot(costHistoryTableConfig),
    StoreModule.forFeature(costHistoryFeatureKey, costHistoryReducer, {
      initialState
    }),
    EffectsModule.forFeature([CostHistoryEffects]),
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
    MatLegacyProgressSpinnerModule
  ]
})
export class CostHistoryModule { }
