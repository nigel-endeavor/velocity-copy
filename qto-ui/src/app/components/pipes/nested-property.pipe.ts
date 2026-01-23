import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'nestedProperty' })
export class NestedPropertyPipe implements PipeTransform {
  transform(obj: any, propertyName: string): any {
    if (!obj || typeof obj !== 'object') {
      return undefined;
    }

    const propertyNames = propertyName.split('.');
    let value = obj;
    for (const prop of propertyNames) {
      if (value && typeof value === 'object') {
        value = value[prop];
      } else {
        value = undefined;
        break;
      }
    }

    return value;
  }
}
