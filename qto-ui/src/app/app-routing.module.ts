import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ApplicationWrapperComponent } from './application-wrapper/application-wrapper.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { PermissionGuard } from './guards/permission-guard';
import { AdminComponent } from './components/admin/admin.component';
import { AdminGuard } from './guards/admin-guard';
import { NetworkInventoryComponent } from './network-inventory/network-inventory.component';
import { CustomersComponent } from './components/customers/customers.component';
import { NewOrderWizardComponent } from './components/new-order-wizard/wizard/new-order-wizard.component';
import { ChangeTenantComponent } from './components/change-tenant/change-tenant.component';
import { ChangeTenantGuard } from './guards/change-tenant-guard';
import { OrderCreateGuard } from './guards/order-create-guard';
import { FileImportGuard } from './guards/file-import-guard';

const routes: Routes = [
  { path: '', component: LandingPageComponent },
  { path: 'code', redirectTo: '', },
  {
    path: '', canActivate: [PermissionGuard], children: [

      { path: 'services',
        loadChildren: () => import('./features/service-worklist/service-worklist.module').then(m => m.ServiceWorklistModule)
      },
      { path: 'cyberServices',
        loadChildren: () => import('./features/service-cyber-worklist/service-cyber-worklist.module').then(m => m.ServiceCyberWorklistModule)
      },
      {
        path: 'order',
        children: [
          {
            path: 'new',
            component: NewOrderWizardComponent,
            canActivate: [OrderCreateGuard]
          },
          { path: ':id',
            loadChildren: () => import('./order-detail-page/order-details.module').then(m => m.OrderDetailsModule)
          }
        ]
      },
      { path: 'locations',
        loadChildren: () => import('./features/locations-worklist/locations-worklist.module').then(m => m.LocationsWorklistModule)
      },
      { path: 'disconnects',
        loadChildren: () => import('./features/disconnect-worklist/disconnect-worklist.module').then(m => m.DisconnectsWorklistModule)
      },
      { path: 'activations',
        loadChildren: () => import('./features/activation-worklist/activation-worklist.module').then(m => m.ActivationWorklistModule)
      },
      { path: 'quote', component: ApplicationWrapperComponent },
      { path: 'expenses', component: ApplicationWrapperComponent },
      { path: 'invoicing',
        loadChildren: () => import('./features/invoicing/invoicing.module').then(m => m.InvoicingModule)
      },
      { path: 'reports',
        loadChildren: () => import('./features/dashboards/dashboards.module').then(m => m.DashboardsModule)
      },
      {
        path: 'import',
        loadChildren: () => import('./features/import-activity-worklist/import-activity-worklist.module').then(m => m.ImportActivityWorklistModule),
        canActivate: [FileImportGuard]
      },
      { path: 'admin', component: AdminComponent, canActivate: [AdminGuard]},
      { path: 'changeTenant', component: ChangeTenantComponent, canActivate: [ChangeTenantGuard]},
      {
        path: 'inventory', component: NetworkInventoryComponent, children: [
          { path: 'locations',
            loadChildren: () => import('./features/locations-worklist/locations-inventory-worklist.module').then(m => m.LocationsInventoryWorklistModule),
            data: { inventory: true }
          },
          { path: 'services',
            loadChildren: () => import('./features/service-worklist/service-inventory-worklist.module').then(m => m.ServiceInventoryWorklistModule),
            data: { inventory: true }
          },
          { path: 'disputes',
            loadChildren: () => import('./features/disputes-worklist/disputes-worklist.module').then(m => m.DisputesWorklistModule),
            data: { inventory: true }
          },
          { path: 'order/:id',
            loadChildren: () => import('./order-detail-page/order-details.module').then(m => m.OrderDetailsModule),
            data: { inventory: true }
          }
        ]
      },
      {
        path: 'customers', component: CustomersComponent, children: [
          { path: 'masterCustomers',
            loadChildren: () => import('./features/master-customers-worklist/master-customers-worklist.module').then(m => m.MasterCustomersWorklistModule)
          },
          { path: 'endCustomers',
            loadChildren: () => import('./features/end-customers-worklist/end-customers-worklist.module').then(m => m.EndCustomersWorklistModule)
          },
          {
            path: 'masterCustomers/:customerId',
            loadChildren: () => import('./features/customer-details/customer-details.module').then(m => m.CustomerDetailsModule)
          },
          {
            path: 'endCustomers/:customerId',
            loadChildren: () => import('./features/customer-details/customer-details.module').then(m => m.CustomerDetailsModule)
          },
          {
            path: 'new',
            loadChildren: () => import('./features/customer-details/customer-details.module').then(m => m.CustomerDetailsModule)
          }
        ]
      },
      { path: 'configuration',
        loadChildren: () => import('./features/configuration/configuration.module').then(m => m.ConfigurationModule)
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    useHash: true
   })],
  exports: [RouterModule]
})
export class AppRoutingModule { }

