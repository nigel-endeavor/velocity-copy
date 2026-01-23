import { Route, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { CustomerDetailsComponent } from "./customer-details.component";
import { CustomerManagementComponent } from "./customer-management.component";
import { CompanyWorklistComponent } from "./company-worklist.component";

const routes: Route[] = [
  { path: '', component: CustomerManagementComponent,
    children: [
      {
        path: '',
        component: CustomerDetailsComponent,
      },
      {
        path: 'endCustomers',
        component: CompanyWorklistComponent,
      },
      {
        path: 'endCustomers/new',
        component: CustomerDetailsComponent,
      }
    ]
   }
  // ,
  // { path: ':customerId/endCustomers', component: EndCustomersWorklistComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CustomerDetailsRoutingModule {}