import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ServiceWorklistComponent } from './service-worklist.component';
import { ServiceViewTableComponent } from './service-view-table.component';
import { AbstractTableModule } from '../abstract-table/abstract-table.module';
import { ServiceWorklistRoutingModule } from './service-worklist.routing.module';
import { ApplicationWrapperModule } from '../../application-wrapper/application-wrapper.module';
import { DropdownModule } from '../../components/dropdown/dropdown.module';
import { MatLegacyProgressSpinnerModule as MatProgressSpinnerModule } from '@angular/material/legacy-progress-spinner';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
import { MatLegacyCheckboxModule as MatCheckboxModule } from '@angular/material/legacy-checkbox';
import { serviceTableConfig } from './configs/table.config';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { ServiceWorklistEffects } from './ngrx/service-worklist.effects';
import { serviceWorklistFeatureKey, serviceWorklistReducer, initialState } from './ngrx/service-worklist.reducer';
import { NgxDaterangepickerMd } from 'ngx-daterangepicker-material';
import { JeopsComponent } from '../../components/jeops/jeops.component';
import { NoteComponent } from '../../components/note/note.component';
import { MatLegacyDialogModule as MatDialogModule } from '@angular/material/legacy-dialog';
import { MatDividerModule } from '@angular/material/divider';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MatMenuModule } from '@angular/material/menu';
import { DatepickerModule } from '../../components/datepicker/datepicker.module';
import { SubjectCustomWorklistModule } from "../../components/subject-custom-worklist/subject-custom-worklist.module";
import { MatTooltipModule } from "@angular/material/tooltip";

@NgModule({
  declarations: [
    ServiceWorklistComponent,
    ServiceViewTableComponent
  ],
    imports: [
        CommonModule,
        FormsModule,
        ServiceWorklistRoutingModule,
        AbstractTableModule.forRoot(serviceTableConfig),

        DragDropModule,
        ApplicationWrapperModule,
        DropdownModule,
        MatProgressSpinnerModule,
        MatIconModule,
        MatCheckboxModule,
        MatMenuModule,
        MatDialogModule,
        NgxDaterangepickerMd.forRoot(),
        StoreModule.forFeature(serviceWorklistFeatureKey, serviceWorklistReducer, {
            initialState
        }),
        EffectsModule.forFeature([ServiceWorklistEffects]),

        JeopsComponent,
        NoteComponent,
        MatTooltipModule,
        MatDividerModule,
        DatepickerModule,
        SubjectCustomWorklistModule,
        MatTooltipModule
    ]
})
export class ServiceWorklistModule { }
