import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { EndCustomersWorklistComponent } from './end-customers-worklist.component';

const routes: Route[] = [
  {
    path: '',
    component: EndCustomersWorklistComponent,
    children: [
      { path: '', loadChildren: () => import('../customer-details/customer-details.module').then(m => m.CustomerDetailsModule) }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class EndCustomersWorklistRoutingModule {}
