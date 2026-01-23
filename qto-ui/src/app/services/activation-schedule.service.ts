import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ActivationSchedule } from '../models/activation-schedule.model';
import { AbstractModelService } from './abstract-model.service';

@Injectable({
  providedIn: 'root'
})
export class ActivationScheduleService extends AbstractModelService<ActivationSchedule> {
  override path = '/activationSchedules';

  findByServiceId(serviceId: number): Observable<ActivationSchedule[]> {
    if (!serviceId) {
      return new Observable<ActivationSchedule[]>();
    }
    const url = this.getUrl() + '?serviceId=' + serviceId;
    return this.http.get<ActivationSchedule[]>(url);
  }

  pushToFtdi(schedule: ActivationSchedule | null): Observable<ActivationSchedule> {
    const url = this.getUrl() + '/pushToFtdi';
    return this.http.put<ActivationSchedule>(url, schedule);
  }
}
