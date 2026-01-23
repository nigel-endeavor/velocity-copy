import { NgModule } from "@angular/core";
import { ServiceCyberTableComponent } from "./service-cyber-table.component";
import { ServiceCyberWorklistComponent } from "./service-cyber-worklist.component";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { AbstractTableModule } from "../abstract-table/abstract-table.module";
import { DragDropModule } from "@angular/cdk/drag-drop";
import { ApplicationWrapperModule } from "../../application-wrapper/application-wrapper.module";
import { DropdownModule } from "../../components/dropdown/dropdown.module";
import { MatLegacyProgressSpinnerModule as MatProgressSpinnerModule } from "@angular/material/legacy-progress-spinner";
import { MatIconModule } from "@angular/material/icon";
import { MatLegacyCheckboxModule as MatCheckboxModule } from "@angular/material/legacy-checkbox";
import { MatMenuModule } from "@angular/material/menu";
import { JeopsComponent } from "../../components/jeops/jeops.component";
import { NgxDaterangepickerMd } from "ngx-daterangepicker-material";
import { StoreModule } from "@ngrx/store";
import { EffectsModule } from "@ngrx/effects";
import { NoteComponent } from "../../components/note/note.component";
import { MatDividerModule } from "@angular/material/divider";
import { MatTooltipModule } from "@angular/material/tooltip";
import { ServiceCyberWorklistRoutingModule } from "./service-cyber-worklist.routing.module";
import { serviceCyberTableConfig } from "./configs/table.config";
import { serviceCyberWorklistFeatureKey, serviceCyberWorklistReducer, initialState } from "./ngrx/service-cyber-worklist.reducer";
import { ServiceCyberWorklistEffects } from "./ngrx/service-cyber-worklist.effects";
import { SubjectCustomWorklistModule } from "../../components/subject-custom-worklist/subject-custom-worklist.module";
import { MatDialogModule } from "@angular/material/dialog";
import {
  SubjectCustomWorklistComponent
} from "../../components/subject-custom-worklist/subject-custom-worklist.component";


@NgModule({
  declarations: [
    ServiceCyberWorklistComponent,
    ServiceCyberTableComponent
  ],
  imports: [
        CommonModule,
        FormsModule,
        ServiceCyberWorklistRoutingModule,
        AbstractTableModule.forRoot(serviceCyberTableConfig),
        DragDropModule,
        ApplicationWrapperModule,
        DropdownModule,
        MatProgressSpinnerModule,
        MatIconModule,
        MatCheckboxModule,
        MatMenuModule,
        JeopsComponent,
        NgxDaterangepickerMd.forRoot(),
        StoreModule.forFeature(serviceCyberWorklistFeatureKey, serviceCyberWorklistReducer, {
            initialState
        }),
        EffectsModule.forFeature([ServiceCyberWorklistEffects]),

        // Standalone components
        NoteComponent,
        MatDividerModule,
        MatTooltipModule,
        SubjectCustomWorklistModule
    ]
})
export class ServiceCyberWorklistModule { }
