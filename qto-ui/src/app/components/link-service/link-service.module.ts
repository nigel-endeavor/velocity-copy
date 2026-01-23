import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LinkServiceComponent } from './link-service.component';
import { LinkServiceTableComponent } from './link-service-table.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AbstractTableModule } from 'src/app/features/abstract-table/abstract-table.module';
import { linkServiceTableConfig } from './config/table.config';
import { StoreModule } from '@ngrx/store';
import { initialState, linkServiceFeatureKey, linkServiceReducer } from './store/link-service.reducer';
import { EffectsModule } from '@ngrx/effects';
import { LinkServiceEffects } from './store/link-service.effects';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { DatepickerModule } from '../datepicker/datepicker.module';
import { DropdownModule } from '../dropdown/dropdown.module';
import { MatLegacyDialogModule } from '@angular/material/legacy-dialog';



@NgModule({
  declarations: [
    LinkServiceComponent,
    LinkServiceTableComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    AbstractTableModule.forRoot(linkServiceTableConfig),
    StoreModule.forFeature(linkServiceFeatureKey, linkServiceReducer, {
      initialState
    }),
    EffectsModule.forFeature([LinkServiceEffects]),
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
export class LinkServiceModule { }
