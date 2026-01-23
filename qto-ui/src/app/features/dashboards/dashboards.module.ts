import { NgModule } from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { DashboardsComponent } from './dashboards.component';
import { DashboardsRoutingModule } from './dashboards.routing.module';
import { DashboardWipComponent } from './components/dashboard-wip/dashboard-wip.component';
import { DashboardProvidersComponent } from './components/dashboard-providers/dashboard-providers.component';
import { DashboardFinancialsComponent } from './components/dashboard-financials/dashboard-financials.component';
import { DashboardKpiComponent } from './components/dashboard-kpi/dashboard-kpi.component';
import { DropdownModule } from '../../components/dropdown/dropdown.module';
import { ApplicationWrapperModule } from '../../application-wrapper/application-wrapper.module';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatLegacyCheckboxModule as MatCheckboxModule } from '@angular/material/legacy-checkbox';
import { dashboardsFeatureKey, dashboardsReducer, initialState } from './ngrx/dashboards.reducer';
import { DashboardsEffects } from './ngrx/dashboards.effects';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { LoadingSpinnerComponent } from 'src/app/components/loading-spinner/loading-spinner.component';
import { MatMenuModule } from "@angular/material/menu";
import { DashboardActivationsComponent } from './components/dashboard-activations/dashboard-activations.component';
import { DashboardInventoryComponent } from './components/dashboard-inventory/dashboard-inventory.component';

@NgModule({
  declarations: [
    DashboardsComponent,
    DashboardWipComponent,
    DashboardProvidersComponent,
    DashboardFinancialsComponent,
    DashboardKpiComponent,
    DashboardActivationsComponent,
    DashboardInventoryComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    DashboardsRoutingModule,
    ApplicationWrapperModule,
    MatProgressSpinnerModule,
    MatCheckboxModule,
    DropdownModule,
    LoadingSpinnerComponent,

    StoreModule.forFeature(dashboardsFeatureKey, dashboardsReducer, {
      initialState
    }),
    EffectsModule.forFeature([DashboardsEffects]),
    MatIconModule,
    MatTooltipModule,
    NgOptimizedImage,
    MatMenuModule,
  ]
})
export class DashboardsModule { }
