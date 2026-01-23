import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ImportActivityWorklistRoutingModule } from './import-activity-worklist-routing.module';
import { ImportActivityWorklistComponent } from './import-activity-worklist.component';
import { ImportActivityTableComponent } from './import-activity-table.component';
import { MatLegacyCheckboxModule as MatCheckboxModule } from '@angular/material/legacy-checkbox';
import { StoreModule } from '@ngrx/store';
import { importActivityWorklistFeatureKey, importActivityWorklistReducer, initialState } from './ngrx/import-activity-worklist.reducer';
import { EffectsModule } from '@ngrx/effects';
import { ImportActivityWorklistEffects } from './ngrx/import-activity-worklist.effects';
import { AbstractTableModule } from '../abstract-table/abstract-table.module';
import { importActivityTableConfig } from './configs/table.config';
import { ApplicationWrapperModule } from '../../application-wrapper/application-wrapper.module';
import { MatLegacyProgressSpinnerModule as MatProgressSpinnerModule } from '@angular/material/legacy-progress-spinner';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
import { FileImportDialogComponent } from '../../components/file-import-dialog/file-import-dialog.component';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { DropdownModule } from '../../components/dropdown/dropdown.module';
import { DragDropModule } from '@angular/cdk/drag-drop';


@NgModule({
  declarations: [
    ImportActivityWorklistComponent,
    ImportActivityTableComponent,
    FileImportDialogComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    DropdownModule,
    DragDropModule,
    ImportActivityWorklistRoutingModule,
    AbstractTableModule.forRoot(importActivityTableConfig),
    ApplicationWrapperModule,
    MatProgressSpinnerModule,
    MatCheckboxModule,
    MatDialogModule,
    MatIconModule,
    MatFormFieldModule,
    MatSelectModule,
    MatButtonModule,
    StoreModule.forFeature(importActivityWorklistFeatureKey, importActivityWorklistReducer, {
      initialState
    }),
    EffectsModule.forFeature([ImportActivityWorklistEffects])
  ]
})
export class ImportActivityWorklistModule { }
