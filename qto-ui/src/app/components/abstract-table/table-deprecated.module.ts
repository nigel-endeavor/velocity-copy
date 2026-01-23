import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableDeprecatedComponent } from './table-deprecated.component';
import { DropdownModule } from '../dropdown/dropdown.module';
import { MatLegacyCheckboxModule as MatCheckboxModule } from '@angular/material/legacy-checkbox';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LocalStoreModule } from '../../features/local-store/local-store.module';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { tableDeprecatedReducer, tableDeprecatedStoreFeatureKey, initialState } from './store/table-deprecated.reducer';
import { TableDeprecatedEffects } from './store/table-deprecated.effects';
import { MatLegacyDialogModule as MatDialogModule}  from '@angular/material/legacy-dialog';
import { MultiEditModule } from '../../features/multi-edit/multi-edit.module';
import { MatLegacyProgressSpinnerModule as MatProgressSpinnerModule } from '@angular/material/legacy-progress-spinner';
import { MatIconModule } from '@angular/material/icon';

// @ts-ignore
@NgModule({
  declarations: [
    TableDeprecatedComponent
  ],
    imports: [
        CommonModule,
        MatCheckboxModule,
        FormsModule,
        ReactiveFormsModule,
        MatProgressSpinnerModule,
        LocalStoreModule,
        DropdownModule,
        MatDialogModule,
        MultiEditModule,
        StoreModule.forFeature(tableDeprecatedStoreFeatureKey, tableDeprecatedReducer, {
            initialState
        }),
        EffectsModule.forFeature([TableDeprecatedEffects]),
        MatIconModule,
    ]})
export class TableDeprecatedModule { }
