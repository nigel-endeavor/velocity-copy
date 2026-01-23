import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HelpDeskComponent } from './components/help-desk/help-desk.component';
import { MsalGuard } from '@azure/msal-angular';

const routes: Routes = [
  {
    path: '', component: HelpDeskComponent, canActivate: [MsalGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
