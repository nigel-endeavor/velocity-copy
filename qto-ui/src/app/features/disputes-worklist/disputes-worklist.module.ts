import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AbstractTableModule } from '../abstract-table/abstract-table.module';
import { DisputesWorklistRoutingModule } from './disputes-worklist.routing.module';
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
import { initialState, disputeWorklistFeatureKey, disputeWorklistReducer } from './ngrx/dispute-worklist.reducer';
import { DisputeWorklistEffects } from './ngrx/dispute-worklist.effects';
import { disputeTableConfig } from './configs/table.config';
import { DisputeWorklistComponent } from './dispute-worklist.component';
import { DisputeWorklistTableComponent } from './dispute-worklist-table.component';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MatTooltipModule } from "@angular/material/tooltip";
import { MatMenuModule } from '@angular/material/menu';
import { SubjectCustomWorklistModule } from "../../components/subject-custom-worklist/subject-custom-worklist.module";

@NgModule({
    declarations: [
        DisputeWorklistComponent,
        DisputeWorklistTableComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        DisputesWorklistRoutingModule,
        AbstractTableModule.forRoot(disputeTableConfig),
        DragDropModule,
        ApplicationWrapperModule,
        DropdownModule,
        MatProgressSpinnerModule,
        MatIconModule,
        MatCheckboxModule,
        MatMenuModule,
        JeopsComponent,
        NgxDaterangepickerMd.forRoot(),
        StoreModule.forFeature(disputeWorklistFeatureKey, disputeWorklistReducer, {
            initialState
        }),
        EffectsModule.forFeature([DisputeWorklistEffects]),

        // Standalone components
        NoteComponent,
        MatDividerModule,
        MatTooltipModule,
        SubjectCustomWorklistModule
    ]
})
export class DisputesWorklistModule { }
