import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DisputesComponent } from './disputes.component';
import { DisputesTableComponent } from './disputes-table.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AbstractTableModule } from 'src/app/features/abstract-table/abstract-table.module';
import { StoreModule } from '@ngrx/store';
import { disputesFeatureKey, disputesReducer, initialState } from './store/disputes.reducer';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MatLegacySelectModule } from '@angular/material/legacy-select';
import { MatLegacyCardModule } from '@angular/material/legacy-card';
import { MatLegacyInputModule } from '@angular/material/legacy-input';
import { MatLegacyFormFieldModule } from '@angular/material/legacy-form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { DatepickerModule } from '../datepicker/datepicker.module';
import { disputesTableConfig } from './configs/table.config';
import { MatLegacyCheckboxModule } from '@angular/material/legacy-checkbox';
import { EffectsModule } from '@ngrx/effects';
import { DisputesEffects } from './store/disputes.effects';
import { LoadingSpinnerComponent } from '../loading-spinner/loading-spinner.component';
import { LocationDisputesComponents } from './location-disputes.component';
import { NoteComponent } from '../note/note.component';
import { VtkCurrencyPipe } from 'src/app/pipes/vtk-currency.pipe';

@NgModule({
  declarations: [
    DisputesComponent,
    DisputesTableComponent,
    LocationDisputesComponents
  ],
  imports: [
    CommonModule,
    FormsModule,
    AbstractTableModule.forRoot(disputesTableConfig),
    StoreModule.forFeature(disputesFeatureKey, disputesReducer, {
      initialState
    }),
    EffectsModule.forFeature([DisputesEffects]),
    ReactiveFormsModule,
    DragDropModule,
    MatLegacySelectModule,
    MatLegacyCardModule,
    MatLegacyInputModule,
    MatLegacyFormFieldModule,
    MatIconModule,
    MatDatepickerModule,
    MatLegacyCheckboxModule,
    DatepickerModule,
    LoadingSpinnerComponent,
    NoteComponent,
    VtkCurrencyPipe
  ]
})
export class DisputesModule { }
