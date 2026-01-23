import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AbstractTableModule } from '../abstract-table/abstract-table.module';
import { EndCustomersWorklistRoutingModule } from './end-customers-worklist.routing.module';
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
import { initialState, endCustomersWorklistFeatureKey, endCustomersWorklistReducer } from './ngrx/end-customers-worklist.reducer';
import { EndCustomersWorklistEffects } from './ngrx/end-customers-worklist.effects';
import { customerTableConfig } from './configs/table.config';
import { EndCustomersWorklistComponent } from './end-customers-worklist.component';
import { EndCustomersWorklistTableComponent } from './end-customers-worklist-table.component';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MatTooltipModule } from "@angular/material/tooltip";
import { MatMenuModule } from '@angular/material/menu';
import { CustomerTasksModule } from "../customer-tasks/customer-tasks.module";

@NgModule({
    declarations: [
        EndCustomersWorklistComponent,
        EndCustomersWorklistTableComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        EndCustomersWorklistRoutingModule,
        AbstractTableModule.forRoot(customerTableConfig),
        DragDropModule,
        ApplicationWrapperModule,
        DropdownModule,
        MatProgressSpinnerModule,
        MatIconModule,
        MatCheckboxModule,
        MatMenuModule,
        JeopsComponent,
        NgxDaterangepickerMd.forRoot(),
        StoreModule.forFeature(endCustomersWorklistFeatureKey, endCustomersWorklistReducer, {
            initialState
        }),
        EffectsModule.forFeature([EndCustomersWorklistEffects]),

        // Standalone components
        NoteComponent,
        MatDividerModule,
        MatTooltipModule,
        CustomerTasksModule
    ]
})
export class EndCustomersWorklistModule { }
