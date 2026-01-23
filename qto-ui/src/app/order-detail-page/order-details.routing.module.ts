import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { OrderDetailPageComponent } from './order-detail-page.component';
import { ReadonlyGuard } from './guards/readonly-guard';
import { LocationGeneralComponent } from './location/location-general/location-general.component';
import { ServiceGeneralComponent } from './service/service-general/service-general.component';
import { LocationMilestonesComponent } from '../components/milestones/location-milestones.component';
import { LocationContactsComponent } from '../components/contacts/locations/location-contacts.component';
import { LocationCommunicationComponent } from './location/location-communication/location-communication.component';
import { LocationComponent } from './location/location.component';
import { ServiceMilestonesComponent } from '../components/milestones/service-milestones.component';
import { ServiceComponent } from './service/service.component';
import { ServiceActivationComponent } from './service/service-activation/service-activation.component';
import { ServiceScheduleComponent } from './service/service-schedule/service-schedule.component';
import { ServiceTechnicalComponent } from './service/service-technical/service-technical.component';
import { DisputesComponent } from '../components/disputes/disputes.component';
import { LocationDisputesComponents } from '../components/disputes/location-disputes.component';
import { ServiceAzDetailsComponent } from './service/service-az-details/service-az-details.component';
import { ServiceBrokerageComponent } from './service/service-brokerage/service-brokerage.component';
import { LocationBrokerageComponent } from './location/location-brokerage/location-brokerage.component';
import { ServiceEquipmentComponent } from './service/service-equipment/service-equipment.component';

const routes: Route[] = [
  {
    path: '',
    component: OrderDetailPageComponent,
    canActivate: [ReadonlyGuard],
    children: [
      {
        path: 'location/:id',
        component: LocationComponent,
        children : [
          {
            path: '',
            redirectTo: 'general',
            pathMatch: 'full'
          },
          {
            path: 'general',
            component: LocationGeneralComponent
          },
          {
            path: 'milestones',
            component: LocationMilestonesComponent
          },
          {
            path: 'contacts',
            component: LocationContactsComponent,
          },
          {
            path: 'communication',
            component: LocationCommunicationComponent
          },
          {
            path: 'disputes',
            component: LocationDisputesComponents
          },
          {
            path: 'brokerage',
            component: LocationBrokerageComponent
          },
          {
            path: 'service/:id',
            component: ServiceComponent,
            children: [
              {
                path: '',
                redirectTo: 'general',
                pathMatch: 'full'
              },
              {
                path: 'general',
                component: ServiceGeneralComponent
              },
              {
                path: 'milestones',
                component: ServiceMilestonesComponent
              },
              {
                path: 'technical',
                component: ServiceTechnicalComponent
              },
              {
                path: 'azDetails',
                component: ServiceAzDetailsComponent
              },
              {
                path: 'equipment',
                component: ServiceEquipmentComponent
              },
              {
                path: 'schedule',
                component: ServiceScheduleComponent,
              },
              {
                path: 'activation',
                component: ServiceActivationComponent,
              },
              {
                path: 'disputes',
                component: DisputesComponent
              },
              {
                path: 'brokerage',
                component: ServiceBrokerageComponent
              }
            ]
          }
        ]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class OrderDetailsRoutingModule {}
