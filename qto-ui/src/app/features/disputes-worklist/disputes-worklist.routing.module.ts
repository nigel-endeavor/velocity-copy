import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { DisputeWorklistComponent } from './dispute-worklist.component';

const routes: Route[] = [
  {
    path: '',
    component: DisputeWorklistComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DisputesWorklistRoutingModule {}
