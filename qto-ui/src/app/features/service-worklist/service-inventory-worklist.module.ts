import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { MatLegacyCheckboxModule as MatCheckboxModule } from '@angular/material/legacy-checkbox';
import { MatDialogModule } from "@angular/material/dialog";
import { MatDividerModule } from "@angular/material/divider";
import { MatIconModule } from "@angular/material/icon";
import { MatLegacyProgressSpinnerModule as MatProgressSpinnerModule } from '@angular/material/legacy-progress-spinner';
import { MatTooltipModule } from "@angular/material/tooltip";
import { EffectsModule } from "@ngrx/effects";
import { StoreModule } from "@ngrx/store";
import { NgxDaterangepickerMd } from "ngx-daterangepicker-material";
import { ApplicationWrapperModule } from "src/app/application-wrapper/application-wrapper.module";
import { DropdownModule } from "src/app/components/dropdown/dropdown.module";
import { JeopsComponent } from "src/app/components/jeops/jeops.component";
import { NoteComponent } from "src/app/components/note/note.component";
import { AbstractTableModule } from "../abstract-table/abstract-table.module";
import { initialState, serviceInventoryWorklistFeatureKey, serviceInventoryWorklistReducer } from './ngrx-inventory/service-inventory-worklist.reducer';
import { ServiceWorklistRoutingModule } from "./service-worklist.routing.module";
import { serviceInventoryTableConfig } from "./configs/service-inventory-table.configs";
import { ServiceInventoryWorklistEffects } from "./ngrx-inventory/service-inventory-worklist.effect";
import { MatMenuModule } from "@angular/material/menu";

@NgModule({
    declarations: [],
    imports: [
        CommonModule,
        FormsModule,
        ServiceWorklistRoutingModule,
        AbstractTableModule.forRoot(serviceInventoryTableConfig),
        ApplicationWrapperModule,
        DropdownModule,
        MatProgressSpinnerModule,
        MatIconModule,
        MatCheckboxModule,
        MatMenuModule,
        MatDialogModule,
        NgxDaterangepickerMd.forRoot(),
        StoreModule.forFeature(serviceInventoryWorklistFeatureKey, serviceInventoryWorklistReducer, {
            initialState
        }),
        EffectsModule.forFeature([ServiceInventoryWorklistEffects]),

        JeopsComponent,
        NoteComponent,
        MatTooltipModule,
        MatDividerModule
    ]
    })

      export class ServiceInventoryWorklistModule { }