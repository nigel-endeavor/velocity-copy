import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ImportActivityWorklistComponent } from './import-activity-worklist.component';

const routes: Routes = [
  {
    path: '',
    component: ImportActivityWorklistComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ImportActivityWorklistRoutingModule { }
