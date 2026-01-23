import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ServiceHistoryComponent } from './service-history.component';
import { ServiceHistoryTableComponent } from './service-history-table.component';
import { AbstractTableModule } from 'src/app/features/abstract-table/abstract-table.module';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { EffectsModule } from '@ngrx/effects';
import { StoreModule } from '@ngrx/store';
import { ServiceHistoryEffects } from './store/service-history.effects';
import { serviceHistoryFeatureKey, initialState, serviceHistoryReducer } from './store/service-history.reducer';
import { serviceHistoryTableConfig } from './config/table.config';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { DatepickerModule } from '../datepicker/datepicker.module';
import { MatLegacyProgressSpinnerModule } from '@angular/material/legacy-progress-spinner';



@NgModule({
  declarations: [
    ServiceHistoryComponent,
    ServiceHistoryTableComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    AbstractTableModule.forRoot(serviceHistoryTableConfig),
    StoreModule.forFeature(serviceHistoryFeatureKey, serviceHistoryReducer, {
      initialState
    }),
    EffectsModule.forFeature([ServiceHistoryEffects]),
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
    MatLegacyProgressSpinnerModule
  ]
})
export class ServiceHistoryModule { }
