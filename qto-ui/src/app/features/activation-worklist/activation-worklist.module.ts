import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivationWorklistComponent } from './activation-worklist.component';
import { ActivationViewTableComponent } from './activation-view-table.component';
import { AbstractTableModule } from '../abstract-table/abstract-table.module';
import { ActivationWorklistRoutingModule } from './activation-worklist.routing.module';
import { ApplicationWrapperModule } from '../../application-wrapper/application-wrapper.module';
import { DropdownModule } from '../../components/dropdown/dropdown.module';
import { MatLegacyProgressSpinnerModule as MatProgressSpinnerModule } from '@angular/material/legacy-progress-spinner';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
import { MatLegacyCheckboxModule as MatCheckboxModule } from '@angular/material/legacy-checkbox';
import { activationTableConfig } from './configs/table.config';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { ActivationWorklistEffects } from './ngrx/activation-worklist.effects';
import { activationWorklistFeatureKey, activationWorklistReducer, initialState } from './ngrx/activation-worklist.reducer';
import { NgxDaterangepickerMd } from 'ngx-daterangepicker-material';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MatMenuModule } from '@angular/material/menu';
import { SubjectCustomWorklistModule } from "../../components/subject-custom-worklist/subject-custom-worklist.module";
import { MatTooltipModule } from "@angular/material/tooltip";

@NgModule({
  declarations: [
    ActivationWorklistComponent,
    ActivationViewTableComponent
  ],
    imports: [
        CommonModule,
        FormsModule,
        ActivationWorklistRoutingModule,
        AbstractTableModule.forRoot(activationTableConfig),
        DragDropModule,
        ApplicationWrapperModule,
        DropdownModule,
        MatProgressSpinnerModule,
        MatIconModule,
        MatCheckboxModule,
        NgxDaterangepickerMd.forRoot(),
        StoreModule.forFeature(activationWorklistFeatureKey, activationWorklistReducer, {
            initialState
        }),
        EffectsModule.forFeature([ActivationWorklistEffects]),
        MatMenuModule,
        SubjectCustomWorklistModule,
        MatTooltipModule
    ]
})
export class ActivationWorklistModule { }
