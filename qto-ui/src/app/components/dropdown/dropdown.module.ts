import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DropdownComponent } from './dropdown.component';
import { DropdownDirective } from './dropdown.directive';
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    DropdownComponent,
    DropdownDirective
  ],
  imports: [
    CommonModule,
    FormsModule
  ],
  exports: [
    DropdownComponent,
    DropdownDirective
  ]
})
export class DropdownModule { }
