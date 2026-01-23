import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { LocationWorklistComponent } from './location-worklist.component';

const routes: Route[] = [
  {
    path: '',
    component: LocationWorklistComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class LocationsWorklistRoutingModule {}
