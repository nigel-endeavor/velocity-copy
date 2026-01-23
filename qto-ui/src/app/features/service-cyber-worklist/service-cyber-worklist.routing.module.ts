import { Route, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { ServiceCyberWorklistComponent } from "./service-cyber-worklist.component";

const routes: Route[] = [
  {
    path: '',
    component: ServiceCyberWorklistComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ServiceCyberWorklistRoutingModule {}
