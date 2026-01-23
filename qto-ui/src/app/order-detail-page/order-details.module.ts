import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatLegacyCheckboxModule as MatCheckboxModule } from '@angular/material/legacy-checkbox';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatLegacyDialogModule as MatDialogModule}  from '@angular/material/legacy-dialog';
import { MatLegacyProgressSpinnerModule as MatProgressSpinnerModule } from '@angular/material/legacy-progress-spinner';
import { OrderDetailPageComponent } from './order-detail-page.component';
import { LocationServiceListComponent } from './location-service-list/location-service-list.component';
import { LocationGeneralComponent } from './location/location-general/location-general.component';
import { ServiceGeneralComponent } from './service/service-general/service-general.component';
import { ServiceScheduleComponent } from './service/service-schedule/service-schedule.component';
import { ServiceActivationComponent } from './service/service-activation/service-activation.component';
import { ActivationAttemptComponent } from './service/service-activation/activation-attempt/activation-attempt.component';
import { ActivationIssuesComponent } from './service/service-activation/activation-attempt/activation-issues/activation-issues.component';
import { ActivationScheduleComponent } from './service/service-schedule/activation-schedule/activation-schedule.component';
import { LocationCommunicationComponent } from './location/location-communication/location-communication.component';
import { ServiceTechnicalBroadbandComponent } from './service/service-technical/service-technical-broadband/service-technical-broadband.component';
import { ServiceTechnicalDiaComponent } from './service/service-technical/service-technical-dia/service-technical-dia.component';
import { DropdownModule } from '../components/dropdown/dropdown.module';
import { ValidatorsModule } from '../directives/validators.module';
import { AttachmentsComponent } from '../components/attachments/attachments.component';
import { NoteComponent } from '../components/note/note.component';
import { JeopsComponent } from '../components/jeops/jeops.component';
import { AddressesComponent } from '../components/addresses/addresses.component';
import { LoadingSpinnerComponent } from '../components/loading-spinner/loading-spinner.component';
import { ApplicationWrapperModule } from '../application-wrapper/application-wrapper.module';
import { LocationMilestonesComponent } from '../components/milestones/location-milestones.component';
import { ContactComponent } from '../components/contacts/contact.component';
import { LocationContactsComponent } from '../components/contacts/locations/location-contacts.component';
import { ServiceMilestonesComponent } from '../components/milestones/service-milestones.component';
import { EndCustomerComponent } from '../components/end-customer/end-customer.component';
import { RequirementTemplatesComponent } from '../components/requirement-templates/requirement-templates.component';
import { ServiceCancelCloneComponent } from '../components/service-cancel-clone/service-cancel-clone.component';
import { OrderDetailsRoutingModule } from './order-details.routing.module';
import { ReadonlyGuard } from './guards/readonly-guard';
import { ServiceTechnicalUcaasComponent } from './service/service-technical/service-technical-ucaas/service-technical-ucaas.component';
import { ServiceTechnicalGComponent } from './service/service-technical/service-technical-g/service-technical-g.component';
import { MatLegacySelectModule as MatSelectModule } from '@angular/material/legacy-select';
import { LocationComponent } from './location/location.component';
import { ServiceComponent } from './service/service.component';
import { ServiceTechnicalComponent } from './service/service-technical/service-technical.component';
import { AbstractMilestonesComponent } from '../components/milestones/abstract-milestones.component';
import { NgxDaterangepickerMd } from 'ngx-daterangepicker-material';
import { MatDividerModule } from '@angular/material/divider';
import { ToggleComponent } from '../components/toggle/toggle.component';
import { DatepickerModule } from '../components/datepicker/datepicker.module';
import { DatetimepickerModule } from '../components/datetimepicker/datetimepicker.module';
import { SurchargesDialogModule } from '../components/surcharges/surcharge-dialog.module';
import { DisputesModule } from '../components/disputes/disputes.module';
import { ServiceHistoryModule } from '../components/service-history/service-history.module';
import { CostHistoryModule } from '../components/cost-history/cost-history.module';
import { NewMacdModule } from '../features/macds/new-macd/new-macd.module';
import { LinkServiceModule } from '../components/link-service/link-service.module';
import { LinkLocationModule } from '../components/link-location/link-location.module';
import { ServiceTechnicalCrossConnectComponent } from './service/service-technical/service-technical-crossconnect/service-technical-crossconnect.component';
import { ServiceTechnicalEthernetComponent } from './service/service-technical/service-technical-ethernet/service-technical-ethernet.component';
import { ServiceAzDetailsComponent } from './service/service-az-details/service-az-details.component';
import { ServiceAzEthernetComponent } from './service/service-az-details/service-az-ethernet/service-az-ethernet.component';
import { RelocateRecordModule } from '../features/relocate-record/relocate-record.module';
import { RelocateInventoryRecordModule } from '../features/relocate-inventory-record/relocate-inventory-record.module';
import { VtkSelectComponent } from '../components/vtk-select/vtk-select.component';
import { ServiceTechnicalTelevisionComponent } from './service/service-technical/service-technical-television/service-technical-television.component';
import { MatTabsModule } from '@angular/material/tabs';
import { MatTooltipModule } from '@angular/material/tooltip';
import { ServiceBrokerageComponent } from './service/service-brokerage/service-brokerage.component';
import { LocationBrokerageComponent } from './location/location-brokerage/location-brokerage.component';
import { VtkCurrencyPipe } from '../pipes/vtk-currency.pipe';
import { ServiceTechnicalMplsComponent } from './service/service-technical/service-technical-mpls/service-technical-mpls.component';
import { CustomFieldsComponent } from '../components/custom-fields/custom-fields.component';
import { VtkPercentPipe } from '../pipes/vtk-percent.pipe';
import { ServiceTechnicalThreatMDRComponent } from './service/service-technical/service-technical-threatMDR/service-technical-threatMDR.component';
import { ServiceEquipmentComponent } from './service/service-equipment/service-equipment.component';
import { ServiceEquipmentModule } from './service/service-equipment/service-equipment.module';
import { ServiceTechnicalRiskMDRComponent } from './service/service-technical/service-technical-riskMDR/service-technical-riskMDR.component';
import { ServiceTechnicalRansomMDRComponent } from './service/service-technical/service-technical-ransomMDR/service-technical-ransomMDR.component';
import { ServiceTechnicalEngineeringIAMComponent } from './service/service-technical/service-technical-engineering-IAM/service-technical-engineering-IAM.component';
import { ServiceTechnicalEngineeringMDMComponent } from "./service/service-technical/service-technical-engineering-MDM/service-technical-engineering-MDM.component";
import {
  ServiceTechnicalEngineeringEndpointComponent
} from "./service/service-technical/service-technical-engineering-endpoint/service-technical-engineering-endpoint.component"
import {
  ServiceTechnicalEngineeringInfoProtectionComponent
} from "./service/service-technical/service-technical-engineering-info-protection/service-technical-engineering-info-protection.component";
import { ServiceTechnicalEngineeringEmailMessagingComponent } from './service/service-technical/service-technical-engineering-email-messaging/service-technical-engineeringEmailMessaging.component';
import { ServiceTechnicalCyber360MXDRComponent } from './service/service-technical/service-technical-cyber360MXDR/service-technical-cyber360MXDR.component';
import { ServiceTechnicalMicrosoftLicensesComponent } from './service/service-technical/service-technical-microsoft-licenses/service-technical-microsoft-licenses.component';


// @ts-ignore
@NgModule({
  declarations: [
    OrderDetailPageComponent,
    LocationServiceListComponent,
    LocationGeneralComponent,
    ServiceScheduleComponent,
    ServiceActivationComponent,
    ActivationAttemptComponent,
    ActivationIssuesComponent,
    ActivationScheduleComponent,
    LocationCommunicationComponent,
    ServiceTechnicalBroadbandComponent,
    ServiceTechnicalDiaComponent,
    ServiceGeneralComponent,
    ServiceTechnicalUcaasComponent,
    ServiceTechnicalGComponent,
    ServiceTechnicalCrossConnectComponent,
    LocationComponent,
    ServiceComponent,
    ServiceTechnicalComponent,
    ServiceTechnicalEthernetComponent,
    ServiceAzDetailsComponent,
    ServiceAzEthernetComponent,
    ServiceTechnicalTelevisionComponent,
    ServiceBrokerageComponent,
    LocationBrokerageComponent,
    ServiceTechnicalMplsComponent,
    ServiceTechnicalThreatMDRComponent,
    ServiceTechnicalRiskMDRComponent,
    ServiceTechnicalRansomMDRComponent,
    ServiceTechnicalEngineeringIAMComponent,
    ServiceTechnicalEngineeringMDMComponent,
    ServiceTechnicalEngineeringEndpointComponent,
    ServiceTechnicalCyber360MXDRComponent,
    ServiceTechnicalEngineeringInfoProtectionComponent,
    ServiceTechnicalEngineeringEmailMessagingComponent,
    ServiceTechnicalMicrosoftLicensesComponent
  ],
  imports: [
    CommonModule,
    MatCheckboxModule,
    MatSelectModule,
    FormsModule,
    ReactiveFormsModule,
    MatProgressSpinnerModule,
    MatDialogModule,
    DropdownModule,
    DatepickerModule,
    DatetimepickerModule,
    ValidatorsModule,
    OrderDetailsRoutingModule,
    ApplicationWrapperModule,
    NgxDaterangepickerMd.forRoot(),
    MatDividerModule,
    SurchargesDialogModule,
    ServiceHistoryModule,
    CostHistoryModule,
    LinkServiceModule,
    LinkLocationModule,
    RelocateRecordModule,
    RelocateInventoryRecordModule,
    //Standalone Components
    AttachmentsComponent,
    NoteComponent,
    JeopsComponent,
    AddressesComponent,
    LoadingSpinnerComponent,
    AbstractMilestonesComponent,
    LocationMilestonesComponent,
    ContactComponent,
    LocationContactsComponent,
    ServiceMilestonesComponent,
    EndCustomerComponent,
    RequirementTemplatesComponent,
    ServiceCancelCloneComponent,
    ToggleComponent,
    DisputesModule,
    NewMacdModule,
    VtkSelectComponent,
    MatTabsModule,
    MatTooltipModule,
    VtkCurrencyPipe,
    CustomFieldsComponent,
    VtkPercentPipe,
    ServiceEquipmentModule
  ],
  providers: [
    ReadonlyGuard
  ]
})
export class OrderDetailsModule { }
