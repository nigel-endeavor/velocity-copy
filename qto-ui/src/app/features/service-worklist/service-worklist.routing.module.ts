import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { ServiceWorklistComponent } from './service-worklist.component';

const routes: Route[] = [
  {
    path: '',
    component: ServiceWorklistComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ServiceWorklistRoutingModule {}
