import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InvoicingComponent } from './invoicing.component';
import { ApplicationWrapperModule } from '../../application-wrapper/application-wrapper.module';
import { InvoicingRoutingModule } from './invoicing.routing.module';
import { InvoiceTableComponent } from './invoice-table.component';
import { invoiceTableConfig } from './configs/table.config';
import { AbstractTableModule } from '../abstract-table/abstract-table.module';
import { StoreModule } from '@ngrx/store';
import { initialState, invoiceTableReducer, invoicingFeatureKey } from './store/invoicing.reducer';
import { InvoicingEffects } from './store/invoicing.effects';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { EffectsModule } from '@ngrx/effects';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSelectModule } from "@angular/material/select";
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from '@angular/material/icon';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { DatepickerModule } from '../../components/datepicker/datepicker.module';
import { DropdownModule } from '../../components/dropdown/dropdown.module';
import { InvoiceDialogModule } from './invoice-dialog/invoice-dialog.module';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { DragDropModule } from '@angular/cdk/drag-drop';

@NgModule({
  declarations: [
    InvoicingComponent,
    InvoiceTableComponent
  ],

  imports: [
    CommonModule,
    InvoicingRoutingModule,
    ApplicationWrapperModule,
    AbstractTableModule.forRoot(invoiceTableConfig),
    StoreModule.forFeature(invoicingFeatureKey, invoiceTableReducer, {
      initialState
    }),
    EffectsModule.forFeature([InvoicingEffects]),
    DragDropModule,
    ReactiveFormsModule,
    FormsModule,
    MatSelectModule,
    MatCardModule,
    MatInputModule,
    MatFormFieldModule,
    MatIconModule,
    MatDatepickerModule,
    MatCheckboxModule,
    MatProgressSpinnerModule,
    DatepickerModule,
    DropdownModule,
    InvoiceDialogModule
  ]
})
export class InvoicingModule { }
