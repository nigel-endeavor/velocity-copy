import { Pipe, PipeTransform } from '@angular/core';
import moment from 'moment';

@Pipe({ name: 'relativeTime' })
export class RelativeTimePipe implements PipeTransform {
    transform(inputDate:Date):string{
        return moment(inputDate).fromNow();
    }
}
