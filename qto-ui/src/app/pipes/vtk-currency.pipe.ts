import { CurrencyPipe } from "@angular/common";
import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
  standalone: true,
  name: 'vtkCurrency'
})
export class VtkCurrencyPipe extends CurrencyPipe implements PipeTransform {
  //@ts-ignore
  override transform(value: number | string | null | undefined, isDisabled: boolean = false): string | null {
    if (value == null || value === '') {
      return null;
    }
    const valueFormat = isDisabled ? '1.2-2' : '1.0-2';
    if (typeof value === 'number') {
      return super.transform(value, 'USD', 'symbol', valueFormat);
    } else {
      const sanitzedValue = value.replace(/\D/g, '');
      return super.transform(sanitzedValue, 'USD', 'symbol', valueFormat);
    }
  }
}