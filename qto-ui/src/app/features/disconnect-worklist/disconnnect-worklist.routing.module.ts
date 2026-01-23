import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { DisconnectWorklistComponent } from './disconnect-worklist.component';

const routes: Route[] = [
  {
    path: '',
    component: DisconnectWorklistComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DisconnectsWorklistRoutingModule {}
