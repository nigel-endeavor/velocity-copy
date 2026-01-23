import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LocationWorklistComponent } from './location-worklist.component';
import { LocationViewTableComponent } from './location-view-table.component';
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
import { locationWorklistFeatureKey, locationWorklistReducer, initialState } from './ngrx/location-worklist.reducer';
import { LocationWorklistEffects } from './ngrx/location-worklist.effects';
import { NgxDaterangepickerMd } from 'ngx-daterangepicker-material';
import { MatDividerModule } from '@angular/material/divider';
import { DragDropModule } from '@angular/cdk/drag-drop';
import {MatTooltipModule} from "@angular/material/tooltip";
import { locationTableConfig } from './configs/ordering-table.config';
import { MatMenuModule } from '@angular/material/menu';
import {MatDialogModule} from "@angular/material/dialog";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import { VtkCurrencyPipe } from 'src/app/pipes/vtk-currency.pipe';
import { SubjectCustomWorklistModule } from "../../components/subject-custom-worklist/subject-custom-worklist.module";

@NgModule({
  declarations: [
    LocationWorklistComponent,
    LocationViewTableComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    LocationsWorklistRoutingModule,
    AbstractTableModule.forRoot(locationTableConfig),
    DragDropModule,
    ApplicationWrapperModule,
    DropdownModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatCheckboxModule,
    MatMenuModule,
    MatAutocompleteModule,
    JeopsComponent,
    NgxDaterangepickerMd.forRoot(),
    StoreModule.forFeature(locationWorklistFeatureKey, locationWorklistReducer, {
      initialState
    }),
    EffectsModule.forFeature([LocationWorklistEffects]),

    // Standalone components
    NoteComponent,
    MatDividerModule,
    MatTooltipModule,
    VtkCurrencyPipe,
    SubjectCustomWorklistModule
  ]
})
export class LocationsWorklistModule { }
