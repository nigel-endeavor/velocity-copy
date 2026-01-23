import { CurrencyPipe } from "@angular/common";
import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
  standalone: true,
  name: 'vtkPercent'
})
export class VtkPercentPipe implements PipeTransform {
  transform(value: number, isDisabled: boolean = false): string {
    if (value == null) {
      return '';
    }
    return value + '%';
  }
}
