import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AbstractTableModule } from '../abstract-table/abstract-table.module';
import { MasterCustomersWorklistRoutingModule } from './master-customers-worklist.routing.module';
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
import { initialState, masterCustomersWorklistFeatureKey, masterCustomersWorklistReducer } from './ngrx/master-customers-worklist.reducer';
import { MasterCustomersWorklistEffects } from './ngrx/master-customers-worklist.effects';
import { customerTableConfig } from './configs/table.config';
import { MasterCustomersWorklistComponent } from './master-customers-worklist.component';
import { MasterCustomersWorklistTableComponent } from './master-customers-worklist-table.component';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MatTooltipModule } from "@angular/material/tooltip";
import { MatMenuModule } from '@angular/material/menu';
import { CustomerTasksModule } from '../customer-tasks/customer-tasks.module';

@NgModule({
    declarations: [
        MasterCustomersWorklistComponent,
        MasterCustomersWorklistTableComponent
    ],    
    imports: [
        CommonModule,
        FormsModule,
        MasterCustomersWorklistRoutingModule,
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
        StoreModule.forFeature(masterCustomersWorklistFeatureKey, masterCustomersWorklistReducer, {
            initialState
        }),
        EffectsModule.forFeature([MasterCustomersWorklistEffects]),

        // Standalone components
        NoteComponent,
        MatDividerModule,
        MatTooltipModule,
        CustomerTasksModule
    ]
})
export class MasterCustomersWorklistModule { }
