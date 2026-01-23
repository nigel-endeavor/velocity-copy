import { Directive, Input } from '@angular/core';
import { AbstractControl, NG_VALIDATORS, ValidationErrors, Validator } from "@angular/forms";

@Directive({
  selector: '[appPhoneValidator]',
  providers: [{provide: NG_VALIDATORS, useExisting: PhoneValidatorDirective, multi: true}]
})
export class PhoneValidatorDirective  implements Validator {

  @Input() appPhoneValidator: string;

  constructor() { }

  registerOnValidatorChange(fn: () => void): void {
  }

  validate(control: AbstractControl): ValidationErrors | null {
    if (!control.value) { return null; }
    if (control.value == 0) { return null; }

    // Separate phone number and extension if present
    const parts = control.value.split('x');
    const phone = parts[0].trim();
    const extension = parts.length > 1 ? parts[1].trim() : '';

    const autoComplete = (phone.length == 10 && !phone.includes("("));
    const validPhone = phone.length == 14 || (phone.length == 15 && autoComplete);

    // Validate extension if present
    const validExtension = extension === '' || /^[0-9]+$/.test(extension);

    return validPhone && validExtension ? null : {phoneNumber: {value: control.value}};
  }
}