import * as dayjs from 'dayjs';
import { Subject } from 'rxjs';
import { CommonColumn } from '../../interfaces/columns.interface';
import { getStatusColor } from '../../utilities';

export class BaseWorklistClass {
  public isEmptySet = false;
  public subject: Subject<{ key: string, value: any }> = new Subject();
  public selected: {startDate: dayjs.Dayjs, endDate: dayjs.Dayjs};
  public nullifyier:any = undefined;
  public nullConunter = 0;



  public ranges: any = {
    'Today': [dayjs(), dayjs()],
    'Yesterday': [dayjs().subtract(1, 'days'), dayjs().subtract(1, 'days')],
    'Last 7 Days': [dayjs().subtract(6, 'days'), dayjs()],
    'Last 30 Days': [dayjs().subtract(29, 'days'), dayjs()],
    'This Month': [dayjs().startOf('month'), dayjs().endOf('month')],
    'Last Month': [dayjs().subtract(1, 'month').startOf('month'), dayjs().subtract(1, 'month').endOf('month')],
    'Empty Date': [undefined, undefined],
  }

  getProgressBarColor(card: any): string {
    return getStatusColor(card.locationStatus || card.status);
  }

  //calculates progress bar width
  getProgressBarWidth(card: any): string {
    return card.progressPercentage + '%';
  }

  checkIsEmpty(value: { label: string, dates: any[] }): void {
    this.isEmptySet = value.label.includes('Empty');
  };

  getCriteriaModelValue(col: CommonColumn, searchCriteria: any, subName?: string): string {
    return subName ? searchCriteria[col.propertyName][subName] : searchCriteria[col.propertyName]
  }

}
