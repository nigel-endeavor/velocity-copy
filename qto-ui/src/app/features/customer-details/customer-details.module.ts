import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { StoreModule } from '@ngrx/store';
import { customerDetailsFeatureKey, customerDetailsReducer, initialState } from './ngrx/customer-details.reducer';
import { EffectsModule } from '@ngrx/effects';
import { CustomerDetailsEffects } from './ngrx/customer-details.effects';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MatCardModule } from '@angular/material/card';
import { MatLegacyCheckboxModule as MatCheckboxModule } from '@angular/material/legacy-checkbox';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatSelectModule } from '@angular/material/select';
import { CustomerDetailsRoutingModule } from './customer-details.routing.module';
import { ApplicationWrapperModule } from '../../application-wrapper/application-wrapper.module';
import { MatLegacyProgressSpinnerModule } from '@angular/material/legacy-progress-spinner';
import { AddressesComponent } from '../../components/addresses/addresses.component';
import { ValidatorsModule } from '../../directives/validators.module';
import { DropdownModule } from '../../components/dropdown/dropdown.module';
import { MatDialogModule } from '@angular/material/dialog';
import { CustomerDetailsComponent } from './customer-details.component';
import { CustomerManagementComponent } from './customer-management.component';
import { AbstractTableModule } from '../abstract-table/abstract-table.module';
import { companyViewTableConfig } from './config/table.config';
import { CompanyWorklistComponent } from './company-worklist.component';
import { CompanyViewTableComponent } from './company-view-table.component';
import { TaskManagerModule } from '../task-manager/task-manager.module';
import { DatepickerModule } from '../../components/datepicker/datepicker.module';
import { CustomerTasksModule } from '../customer-tasks/customer-tasks.module';
import { AttachmentsComponent } from "../../components/attachments/attachments.component";
import { ContactComponent } from "../../components/contacts/contact.component";
import { AutomatedEmailComponent } from 'src/app/components/automated-email/automated-email.component';
import { MatMenuModule } from "@angular/material/menu";
import { NgxDaterangepickerMd } from "ngx-daterangepicker-material";

@NgModule({
  declarations: [
    CustomerDetailsComponent,
    CustomerManagementComponent,
    CompanyWorklistComponent,
    CompanyViewTableComponent
  ],
  imports: [
    ApplicationWrapperModule,
    AddressesComponent,
    ValidatorsModule,
    CustomerDetailsRoutingModule,
    CommonModule,
    FormsModule,
    AbstractTableModule.forRoot(companyViewTableConfig),
    StoreModule.forFeature(customerDetailsFeatureKey, customerDetailsReducer, {
      initialState
    }),
    EffectsModule.forFeature([CustomerDetailsEffects]),
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
    TaskManagerModule,
    CustomerTasksModule,
    AttachmentsComponent,
    ContactComponent,
    AutomatedEmailComponent,
    MatMenuModule,
    NgxDaterangepickerMd
  ]
})
export class CustomerDetailsModule { }
