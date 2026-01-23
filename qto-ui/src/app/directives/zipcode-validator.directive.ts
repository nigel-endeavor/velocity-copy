import { Directive, Input } from '@angular/core';
import { AbstractControl, NG_VALIDATORS, ValidationErrors, Validator } from "@angular/forms";

@Directive({
  selector: '[appZipcodeValidator]',
  providers: [{provide: NG_VALIDATORS, useExisting: ZipcodeValidatorDirective, multi: true}]
})
export class ZipcodeValidatorDirective implements Validator {

  @Input() appZipcodeValidator: string;

  constructor() {
  }

  validate(control: AbstractControl<any, any>): ValidationErrors | null {
    if (!control.value) {
      // consider empty value as valid. Use required validator separately if needed.
      return null;
    }
    const usZipRegex = /^\d{5}(-\d{4})?$/;
    const caPostalRegex = /^[A-Za-z]\d[A-Za-z] ?\d[A-Za-z]\d$/;
    const valid = usZipRegex.test(control.value) || caPostalRegex.test(control.value);
    return valid ? null : {'usCaZipInvalid': true};
  }

  registerOnValidatorChange?(fn: () => void): void {
  }

}
