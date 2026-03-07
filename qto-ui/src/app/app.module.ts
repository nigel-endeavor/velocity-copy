import { ErrorHandler, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { DropdownModule } from './components/dropdown/dropdown.module';
import { ApplicationWrapperModule } from './application-wrapper/application-wrapper.module';
import { TableDeprecatedModule } from './components/abstract-table/table-deprecated.module';
import { NoteComponent } from './components/note/note.component';
import { JeopsComponent } from './components/jeops/jeops.component';
import { LoadingSpinnerComponent } from './components/loading-spinner/loading-spinner.component';
import { AttachmentsComponent } from './components/attachments/attachments.component';
import { MsalInterceptor, MsalModule, MsalRedirectComponent } from '@azure/msal-angular';
import { InteractionType, PublicClientApplication } from '@azure/msal-browser';
import { environment } from '../environments/environment';
import { PermissionGuard } from './guards/permission-guard';
import { ErrorDialogComponent } from './components/error-dialog/error-dialog.component';
import { GlobalErrorHandler } from './services/global-error-handler';
import { AddressesComponent } from './components/addresses/addresses.component';
import { JeopsFullComponent } from './components/jeops/jeops-full/jeops-full.component';
import { LocationMilestonesComponent } from './components/milestones/location-milestones.component';
import { AbstractMilestonesComponent } from './components/milestones/abstract-milestones.component';
import { ServiceMilestonesComponent } from './components/milestones/service-milestones.component';
import { JeopsTableComponent } from './components/jeops/jeops-full/jeops-table.component';
import { EndCustomerComponent } from './components/end-customer/end-customer.component';
import { RequirementTemplatesComponent } from './components/requirement-templates/requirement-templates.component';
import { LocationContactsComponent } from './components/contacts/locations/location-contacts.component';
import { LocationContactsTableComponent } from './components/contacts/locations/location-contacts-table.component';
import { ContactComponent } from './components/contacts/contact.component';
import { MetaReducer, StoreModule } from '@ngrx/store';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { LocalStoreModule } from './features/local-store/local-store.module';
import { EffectsModule } from '@ngrx/effects';
import { demoStoreFeatureKey, demoStoreReducer, initialState as demoStoreInitialState } from './features/demo-mode/demo-store.reducer';
import { DemoStoreEffects } from './features/demo-mode/demo-store.effects';
import { DemoInterceptor } from './interceptors/demoable.interceptor';
import { syncLocalStorage } from './utilities/meta-reducers/sync-local-store';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatLegacyCheckboxModule as MatCheckboxModule } from '@angular/material/legacy-checkbox';
import { MatNativeDateModule } from '@angular/material/core';

import { MatLegacyProgressSpinnerModule as MatProgressSpinnerModule } from '@angular/material/legacy-progress-spinner';
import { ServiceCancelCloneComponent } from './components/service-cancel-clone/service-cancel-clone.component';
import { CancelCloneDialogComponent } from './components/service-cancel-clone/cancel-clone-dialog/cancel-clone-dialog.component';
import { ValidatorsModule } from './directives/validators.module';
import { MatIconModule } from '@angular/material/icon';
import { TOASTR_TOKEN, Toastr } from './services/toastr.service';
import { NgxDaterangepickerMd } from 'ngx-daterangepicker-material';
import { EmailTemplateDialogModule } from './components/email-template-dialog/email-template-dialog.module';
import { DatepickerModule } from './components/datepicker/datepicker.module';
import { APP_BASE_HREF, CommonModule } from '@angular/common';
import { AdminComponent } from './components/admin/admin.component';
import { AdminGuard } from './guards/admin-guard';
import {
  initialState as orderDetailsInitialState,
  orderDetailsFeatureKey,
  orderDetailsReducer
} from './order-detail-page/ngrx/order-details.reducer';
import { OrderDetailsEffects } from './order-detail-page/ngrx/order-details.effects';
import { NetworkInventoryComponent } from './network-inventory/network-inventory.component';
import { CdkDropList, DragDropModule } from "@angular/cdk/drag-drop";
import { MatTableModule } from "@angular/material/table";
import { NewOrderWizardComponent } from './components/new-order-wizard/wizard/new-order-wizard.component';
import { VtkSelectComponent } from './components/vtk-select/vtk-select.component';
import { DtoTableComponent } from './components/new-order-wizard/dto-table/dto-table.component';
import { ChangeTenantComponent } from './components/change-tenant/change-tenant.component';
import { ChangeTenantGuard } from './guards/change-tenant-guard';
import { VtkCurrencyPipe } from './pipes/vtk-currency.pipe';
import { VtkPercentPipe } from "./pipes/vtk-percent.pipe";
import { OrderCreateGuard } from './guards/order-create-guard';
import { FileImportGuard } from './guards/file-import-guard';
import { CustomFieldsComponent } from './components/custom-fields/custom-fields.component';
import { MatDialogModule } from '@angular/material/dialog';
import { OptionsDialogComponent } from './components/milestones/options-dialog.component';
import { MatCardModule } from '@angular/material/card';
import { ConfirmationDialogComponent } from './components/confirmation-dialog/confirmation-dialog.component';
import { CustomersComponent } from './components/customers/customers.component';
import { AutomatedEmailComponent } from './components/automated-email/automated-email.component';

export const metaReducers: MetaReducer<any>[] = [ syncLocalStorage ];
declare const toastr: Toastr;

@NgModule({
  declarations: [
    AppComponent,
    LandingPageComponent,
    ErrorDialogComponent,
    AdminComponent,
    NetworkInventoryComponent,
    NewOrderWizardComponent,
    DtoTableComponent,
    ChangeTenantComponent,
    OptionsDialogComponent,
    ConfirmationDialogComponent,
    CustomersComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CommonModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    DropdownModule,
    DatepickerModule,
    TableDeprecatedModule,
    EmailTemplateDialogModule,
    MatNativeDateModule,
    MatProgressSpinnerModule,
    ValidatorsModule,
    MatIconModule,
    DragDropModule,
    CdkDropList,
    MatTableModule,
    MatDialogModule,
    MatCardModule,
    MsalModule.forRoot(new PublicClientApplication({
        auth: {
            clientId: environment.azureClientId,
            authority: environment.azureAuthority,
            redirectUri: environment.azureRedirectUri,
            postLogoutRedirectUri: window.location.protocol + '//' + window.location.host + environment.baseHref
        },
        cache: {
            cacheLocation: 'localStorage',
        }
    }), {
        interactionType: InteractionType.Redirect
    }, {
        interactionType: InteractionType.Redirect,
        protectedResourceMap: new Map([
            [environment.appUrl + '/*', ['api://' + environment.azureClientId + '/qto']]
        ]),
    }),
    LocalStoreModule.forRoot(),
    StoreModule.forRoot({
      [demoStoreFeatureKey]: demoStoreReducer,
      [orderDetailsFeatureKey]: orderDetailsReducer
    }, {
        metaReducers,
        initialState: {
            [demoStoreFeatureKey]: {
                ...demoStoreInitialState,
            },
            [orderDetailsFeatureKey]: {
                ...orderDetailsInitialState
            }
        },
    }),
    // Instrumentation must be imported after importing StoreModule (config is optional)
    StoreDevtoolsModule.instrument({
      maxAge: 25, // Retains last 25 states
      logOnly: environment.production, // Restrict extension to log-only mode
    }),
    EffectsModule.forRoot([
      DemoStoreEffects,
      OrderDetailsEffects
    ]),
    BrowserAnimationsModule,
    MatCheckboxModule,
    ApplicationWrapperModule,
    NgxDaterangepickerMd.forRoot(),
    VtkSelectComponent,

    //Standalone components
    JeopsComponent,
    JeopsFullComponent,
    JeopsTableComponent,
    AddressesComponent,
    AttachmentsComponent,
    LoadingSpinnerComponent,
    AbstractMilestonesComponent,
    LocationMilestonesComponent,
    ServiceMilestonesComponent,
    ContactComponent,
    LocationContactsTableComponent,
    LocationContactsComponent,
    EndCustomerComponent,
    RequirementTemplatesComponent,
    ServiceCancelCloneComponent,
    CancelCloneDialogComponent,
    NoteComponent,
    CustomFieldsComponent,
    VtkCurrencyPipe,
    VtkPercentPipe,
    AutomatedEmailComponent
  ],
  providers: [
    PermissionGuard,
    AdminGuard,
    ChangeTenantGuard,
    OrderCreateGuard,
    FileImportGuard,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: DemoInterceptor,
      multi: true,
    },
    { provide: ErrorHandler, useClass: GlobalErrorHandler },
    { provide: TOASTR_TOKEN, useValue: toastr },
    { provide: APP_BASE_HREF, useValue: environment.baseHref }
  ],
  bootstrap: [AppComponent, MsalRedirectComponent]
})
export class AppModule {}
