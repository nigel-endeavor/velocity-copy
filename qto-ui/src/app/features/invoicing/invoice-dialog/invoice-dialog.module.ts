import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StoreModule } from '@ngrx/store';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { EffectsModule } from '@ngrx/effects';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSelectModule } from "@angular/material/select";
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from '@angular/material/icon';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { InvoiceDialogComponent } from './invoice-dialog.component';
import { AbstractTableModule } from '../../abstract-table/abstract-table.module';
import { invoiceChargeTableConfig } from './configs/table.config';
import { invoiceChargeTableFeatureKey, invoiceChargeTableReducer, initialState } from './store/invoice-charges.reducer';
import { DatepickerModule } from '../../../components/datepicker/datepicker.module';
import { DropdownModule } from '../../../components/dropdown/dropdown.module';
import { InvoiceChargeEffects } from './store/invoice-charges.effects';
import { InvoiceChargeTableComponent } from './invoice-charge-table.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { DragDropModule } from '@angular/cdk/drag-drop';

@NgModule({
  declarations: [
    InvoiceDialogComponent,
    InvoiceChargeTableComponent
  ],

  imports: [
    CommonModule,
    AbstractTableModule.forRoot(invoiceChargeTableConfig),
    StoreModule.forFeature(invoiceChargeTableFeatureKey, invoiceChargeTableReducer, {
      initialState
    }),
    EffectsModule.forFeature([InvoiceChargeEffects]),
    ReactiveFormsModule,
    DragDropModule,
    FormsModule,
    MatSelectModule,
    MatCardModule,
    MatInputModule,
    MatFormFieldModule,
    MatIconModule,
    MatDatepickerModule,
    MatCheckboxModule,
    DatepickerModule,
    DropdownModule,
    MatProgressSpinnerModule
  ]
})
export class InvoiceDialogModule { }
