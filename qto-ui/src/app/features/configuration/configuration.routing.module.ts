import { Route, RouterModule } from "@angular/router";
import { ConfigurationComponent } from "./configuration.component";
import { NgModule } from "@angular/core";
import { LookupTypesComponent } from "./components/lookup-types/lookup-types.component";
import { ConfigurationFormComponent } from "./components/configuration-form/configuration-form.component";

const routes: Route[] = [
  {
    path: '',
    component: ConfigurationComponent,
    children: [
      {
        path: 'lookups',
        component: LookupTypesComponent
      },
      {
        path: 'configuration',
        component: ConfigurationFormComponent
      }
    ]
  }
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ConfigurationRoutingModule {}