import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatSelectModule } from "@angular/material/select";
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from "@angular/material/form-field";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { SurchargeDialogComponent } from './surcharge-dialog.component';
import { ServiceSurchargeTableComponent } from './service-surcharge-table.component';
import { surchargeTableConfig } from './configs/table.config';
import { AbstractTableModule } from '../../features/abstract-table/abstract-table.module';
import { StoreModule } from '@ngrx/store';
import { surchargeDialogFeatureKey, surchargeTableReducer, initialState } from './store/surcharge-dialog.reducer';
import { DatepickerModule } from '../datepicker/datepicker.module';
import { EffectsModule } from '@ngrx/effects';
import { SurchargeDialogEffects } from './store/surcharge-dialog.effects';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { DragDropModule } from '@angular/cdk/drag-drop';

@NgModule({
  declarations: [
    SurchargeDialogComponent,
    ServiceSurchargeTableComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    AbstractTableModule.forRoot(surchargeTableConfig),
    StoreModule.forFeature(surchargeDialogFeatureKey, surchargeTableReducer, {
      initialState
    }),
    EffectsModule.forFeature([SurchargeDialogEffects]),
    ReactiveFormsModule,
    DragDropModule,
    MatSelectModule,
    MatCardModule,
    MatInputModule,
    MatFormFieldModule,
    MatIconModule,
    MatDatepickerModule,
    MatCheckboxModule,
    DatepickerModule
  ]
})
export class SurchargesDialogModule { }
