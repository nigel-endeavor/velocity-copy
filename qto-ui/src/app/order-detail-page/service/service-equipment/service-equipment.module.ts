import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApplicationWrapperModule } from '../../../application-wrapper/application-wrapper.module';
import { DropdownModule } from '../../../components/dropdown/dropdown.module';
import { MatLegacyProgressSpinnerModule as MatProgressSpinnerModule } from '@angular/material/legacy-progress-spinner';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
import { MatLegacyCheckboxModule as MatCheckboxModule } from '@angular/material/legacy-checkbox';
import { JeopsComponent } from '../../../components/jeops/jeops.component';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { NgxDaterangepickerMd } from 'ngx-daterangepicker-material';
import { MatDividerModule } from '@angular/material/divider';
import { initialState, serviceEquipmentFeatureKey, serviceEquipmentReducer } from './ngrx/service-equipment.reducer';
import { ServiceEquipmentEffects } from './ngrx/service-equipment.effects';
import { equipmentTableConfig } from './configs/table.config';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MatTooltipModule } from "@angular/material/tooltip";
import { MatMenuModule } from '@angular/material/menu';
import { ServiceEquipmentComponent } from './service-equipment.component';
import { AbstractTableModule } from '../../../features/abstract-table/abstract-table.module';
import { ValidatorsModule } from '../../../directives/validators.module';
import { AddressesComponent } from '../../../components/addresses/addresses.component';
import { ServiceEquipmentTableComponent } from './service-equipment-table.component';
import { EquipmentComponent } from './equipment.component';
import { MatDialogContent, MatDialogModule, MatDialogTitle } from '@angular/material/dialog';

@NgModule({
    declarations: [
        ServiceEquipmentComponent,
        ServiceEquipmentTableComponent
    ],    
    imports: [
        EquipmentComponent,
        CommonModule,
        FormsModule,
        AbstractTableModule.forRoot(equipmentTableConfig),
        DragDropModule,
        ApplicationWrapperModule,
        DropdownModule,
        MatProgressSpinnerModule,
        MatIconModule,
        MatCheckboxModule,
        MatMenuModule,
        JeopsComponent,
        NgxDaterangepickerMd.forRoot(),
        StoreModule.forFeature(serviceEquipmentFeatureKey, serviceEquipmentReducer, {
            initialState
        }),
        EffectsModule.forFeature([ServiceEquipmentEffects]),

        // Standalone components
        MatDividerModule,
        MatTooltipModule,
        ValidatorsModule,
        AddressesComponent,
        MatDialogModule
    ],
    exports: [
        ServiceEquipmentComponent
    ]
})
export class ServiceEquipmentModule { }
