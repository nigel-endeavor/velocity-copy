import { NgModule } from '@angular/core';
import { IpAddressValidatorDirective } from './ip-address-validator.directive';
import { PhoneValidatorDirective } from './phone-validator.directive';
import { PhoneMaskDirective } from './phone-mask.directive';
import { ZipcodeValidatorDirective } from "./zipcode-validator.directive";

// @ts-ignore
@NgModule({
  declarations: [
    IpAddressValidatorDirective,
    PhoneValidatorDirective,
    PhoneMaskDirective,
    ZipcodeValidatorDirective
  ],
  exports: [
    IpAddressValidatorDirective,
    PhoneValidatorDirective,
    PhoneMaskDirective,
    ZipcodeValidatorDirective
  ]})
export class ValidatorsModule { }
