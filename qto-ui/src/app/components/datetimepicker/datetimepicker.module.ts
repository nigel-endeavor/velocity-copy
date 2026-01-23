import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DatetimepickerComponent } from './datetimepicker.component';
import { FormsModule } from '@angular/forms';
import { DatepickerModule } from '../datepicker/datepicker.module';



@NgModule({
  declarations: [
    DatetimepickerComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    DatepickerModule
  ],
  exports: [
    DatetimepickerComponent
  ]
})
export class DatetimepickerModule { }
