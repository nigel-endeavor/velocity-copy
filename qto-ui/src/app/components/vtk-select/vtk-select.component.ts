import { CommonModule } from '@angular/common';
import { Component, EventEmitter, forwardRef, Input, Output } from '@angular/core';
import { ControlValueAccessor, FormsModule, NG_VALUE_ACCESSOR, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'vtk-select',
  templateUrl: './vtk-select.component.html',
  styleUrls: ['./vtk-select.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => VtkSelectComponent),
      multi: true
    }
  ]
})
export class VtkSelectComponent implements ControlValueAccessor {

  @Input() ngModel: any;
  @Output() ngModelChange: any = new EventEmitter<any>();
  @Input() name: string;
  @Input() disabled: boolean = false;
  @Input() isReadOnly: boolean = false;
  @Input() options: any[] = [];

  //ControlValueAccessor methods
  writeValue(value: any): void {
    this.ngModel = value;
  }

  registerOnChange(fn: any): void {
    this.ngModelChange.subscribe(fn);
  }

  registerOnTouched(fn: any): void {}

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

}
