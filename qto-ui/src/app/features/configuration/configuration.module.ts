import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfigurationComponent } from './configuration.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { StoreModule } from '@ngrx/store';
import { configurationFeatureKey, configurationReducer, initialState } from './ngrx/configuration.reducer';
import { EffectsModule } from '@ngrx/effects';
import { ConfigurationEffects } from './ngrx/configuration.effects';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatSelectModule } from '@angular/material/select';
import { ConfigurationRoutingModule } from './configuration.routing.module';
import { ApplicationWrapperModule } from 'src/app/application-wrapper/application-wrapper.module';
import { MatLegacyProgressSpinnerModule } from '@angular/material/legacy-progress-spinner';
import { AddressesComponent } from 'src/app/components/addresses/addresses.component';
import { ValidatorsModule } from 'src/app/directives/validators.module';
import { DropdownModule } from 'src/app/components/dropdown/dropdown.module';
import { LookupTypesComponent } from './components/lookup-types/lookup-types.component';
import { ConfigurationFormComponent } from './components/configuration-form/configuration-form.component';
import { MatDialogModule } from '@angular/material/dialog';

@NgModule({
  declarations: [
    ConfigurationComponent,
    ConfigurationFormComponent,
    LookupTypesComponent,
    ConfigurationFormComponent
  ],
  imports: [
    ApplicationWrapperModule,
    AddressesComponent,
    ValidatorsModule,
    ConfigurationRoutingModule,
    CommonModule,
    FormsModule,
    StoreModule.forFeature(configurationFeatureKey, configurationReducer, {
      initialState
    }),
    EffectsModule.forFeature([ConfigurationEffects]),
    ReactiveFormsModule,
    DragDropModule,
    MatSelectModule,
    MatCardModule,
    MatInputModule,
    MatFormFieldModule,
    MatIconModule,
    MatDatepickerModule,
    MatCheckboxModule,
    MatListModule,
    MatLegacyProgressSpinnerModule,
    DropdownModule,
    MatDialogModule
  ]
})
export class ConfigurationModule { }
