import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { ActivationWorklistComponent } from './activation-worklist.component';

const routes: Route[] = [
  {
    path: '',
    component: ActivationWorklistComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ActivationWorklistRoutingModule {}
