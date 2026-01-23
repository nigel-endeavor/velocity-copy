import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LinkLocationComponent } from './link-location.component';
import { LinkLocationTableComponent } from './link-location-table.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AbstractTableModule } from 'src/app/features/abstract-table/abstract-table.module';
import { linkLocationTableConfig } from './config/table.config';
import { StoreModule } from '@ngrx/store';
import { initialState, linkLocationFeatureKey, linkLocationReducer } from './store/link-location.reducer';
import { EffectsModule } from '@ngrx/effects';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatLegacyDialogModule } from '@angular/material/legacy-dialog';
import { MatSelectModule } from '@angular/material/select';
import { DatepickerModule } from '../datepicker/datepicker.module';
import { DropdownModule } from '../dropdown/dropdown.module';
import { LinkLocationEffects } from './store/link-location.effects';



@NgModule({
  declarations: [
    LinkLocationComponent,
    LinkLocationTableComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    AbstractTableModule.forRoot(linkLocationTableConfig),
    StoreModule.forFeature(linkLocationFeatureKey, linkLocationReducer, {
      initialState
    }),
    EffectsModule.forFeature([LinkLocationEffects]),
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
export class LinkLocationModule { }
