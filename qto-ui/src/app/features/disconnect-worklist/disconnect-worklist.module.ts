import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AbstractTableModule } from '../abstract-table/abstract-table.module';
import { DisconnectsWorklistRoutingModule } from './disconnnect-worklist.routing.module';
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
import { initialState, disconnectWorklistFeatureKey, disconnectWorklistReducer } from './ngrx/disconnect-worklist.reducer';
import { DisconnectWorklistEffects } from './ngrx/disconnect-worklist.effects';
import { disconnectTableConfig } from './configs/table.config';
import { DisconnectWorklistComponent } from './disconnect-worklist.component';
import { DisconnectTableComponent } from './disconnect-table.component';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MatTooltipModule } from "@angular/material/tooltip";
import { MatMenuModule } from '@angular/material/menu';
import { SubjectCustomWorklistModule } from "../../components/subject-custom-worklist/subject-custom-worklist.module";

@NgModule({
    declarations: [
        DisconnectWorklistComponent,
        DisconnectTableComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        DisconnectsWorklistRoutingModule,
        AbstractTableModule.forRoot(disconnectTableConfig),
        DragDropModule,
        ApplicationWrapperModule,
        DropdownModule,
        MatProgressSpinnerModule,
        MatIconModule,
        MatCheckboxModule,
        MatMenuModule,
        JeopsComponent,
        NgxDaterangepickerMd.forRoot(),
        StoreModule.forFeature(disconnectWorklistFeatureKey, disconnectWorklistReducer, {
            initialState
        }),
        EffectsModule.forFeature([DisconnectWorklistEffects]),

        // Standalone components
        NoteComponent,
        MatDividerModule,
        MatTooltipModule,
        SubjectCustomWorklistModule
    ]
})
export class DisconnectsWorklistModule { }
