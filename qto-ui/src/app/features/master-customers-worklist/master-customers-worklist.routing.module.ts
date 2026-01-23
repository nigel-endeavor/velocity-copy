import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { MasterCustomersWorklistComponent } from './master-customers-worklist.component';

const routes: Route[] = [
  {
    path: '',
    component: MasterCustomersWorklistComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MasterCustomersWorklistRoutingModule {}
