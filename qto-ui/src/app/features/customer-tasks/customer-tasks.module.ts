import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { StoreModule } from '@ngrx/store';
import { customerTasksFeatureKey, customerTasksReducer, initialState } from './ngrx/customer-tasks.reducer';
import { EffectsModule } from '@ngrx/effects';
import { CustomerTasksEffects } from './ngrx/customer-tasks.effects';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatSelectModule } from '@angular/material/select';
import { ApplicationWrapperModule } from '../../application-wrapper/application-wrapper.module';
import { MatLegacyProgressSpinnerModule } from '@angular/material/legacy-progress-spinner';
import { AddressesComponent } from '../../components/addresses/addresses.component';
import { ValidatorsModule } from '../../directives/validators.module';
import { DropdownModule } from '../../components/dropdown/dropdown.module';
import { MatDialogModule } from '@angular/material/dialog';
import { TaskManagerModule } from '../task-manager/task-manager.module';
import { DatepickerModule } from '../../components/datepicker/datepicker.module';
import { CustomerTasksComponent } from './customer-tasks.component';

@NgModule({
  declarations: [
    CustomerTasksComponent
  ],
  imports: [
    ApplicationWrapperModule,
    AddressesComponent,
    ValidatorsModule,
    CommonModule,
    FormsModule,
    StoreModule.forFeature(customerTasksFeatureKey, customerTasksReducer, {
      initialState
    }),
    EffectsModule.forFeature([CustomerTasksEffects]),
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
    DatepickerModule,
    MatLegacyProgressSpinnerModule,
    DropdownModule,
    MatDialogModule,
    TaskManagerModule
  ],
  exports: [
    CustomerTasksComponent
  ]
})
export class CustomerTasksModule { }
