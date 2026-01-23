import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { DashboardsComponent } from "./dashboards.component";

const routes: Route[] = [
  {
    path: '',
    component: DashboardsComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DashboardsRoutingModule {}
