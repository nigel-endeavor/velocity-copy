import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AbstractTableModule } from '../abstract-table/abstract-table.module';
import { LocationsWorklistRoutingModule } from './locations-worklist.routing.module';
import { NoteComponent } from '../../components/note/note.component';
import { ApplicationWrapperModule } from '../../application-wrapper/application-wrapper.module';
import { DropdownModule } from '../../components/dropdown/dropdown.module';
import { MatLegacyProgressSpinnerModule as MatProgressSpinnerModule } from '@angular/material/legacy-progress-spinner';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
import { MatLegacyCheckboxModule as MatCheckboxModule } from '@angular/material/legacy-checkbox';
import { JeopsComponent } from '../../components/jeops/jeops.component';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { NgxDaterangepickerMd } from 'ngx-daterangepicker-material';
import { MatDividerModule } from '@angular/material/divider';
import { initialState, locationInventoryWorklistFeatureKey, locationInventoryWorklistReducer } from './ngrx-inventory/location-inventory-worklist.reducer';
import { LocationInventoryWorklistEffects } from './ngrx-inventory/location-inventory-worklist.effects';
import { locationInventoryTableConfig } from './configs/inventory-table.config';
import { MatMenuModule } from '@angular/material/menu';

import { MatTooltipModule } from '@angular/material/tooltip';

@NgModule({
  declarations: [],
    imports: [
        CommonModule,
        FormsModule,
        LocationsWorklistRoutingModule,
        AbstractTableModule.forRoot(locationInventoryTableConfig),
        ApplicationWrapperModule,
        DropdownModule,
        MatProgressSpinnerModule,
        MatIconModule,
        MatCheckboxModule,
        MatMenuModule,
        JeopsComponent,
        MatTooltipModule,
        NgxDaterangepickerMd.forRoot(),
        StoreModule.forFeature(locationInventoryWorklistFeatureKey, locationInventoryWorklistReducer, {
            initialState
        }),
        EffectsModule.forFeature([LocationInventoryWorklistEffects]),

        // Standalone components
        NoteComponent,
        MatDividerModule
    ]
})
export class LocationsInventoryWorklistModule { }
