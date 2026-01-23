import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ActivationAttempt } from '../models/activation-attempt.model';
import { AbstractModelService } from './abstract-model.service';

@Injectable({
  providedIn: 'root'
})
export class ActivationAttemptService extends AbstractModelService<ActivationAttempt> {
  override path = '/activationAttempts';

  findByServiceId(serviceId: number): Observable<ActivationAttempt[]> {
    if (!serviceId) {
      return new Observable<ActivationAttempt[]>();
    }
    const url = this.getUrl() + '?serviceId=' + serviceId;
    return this.http.get<ActivationAttempt[]>(url);
  }

  cancel(activationAttemptId: number, applySameDayCancelSurcharge: boolean): Observable<ActivationAttempt> {
    const url = this.getUrl() + '/' + activationAttemptId + '/cancel';
    return this.http.post<ActivationAttempt>(url, {applySameDayCancelSurcharge: applySameDayCancelSurcharge});
  }

  rollback(activationAttemptId: number): Observable<ActivationAttempt> {
    const url = this.getUrl() + '/' + activationAttemptId + '/rollback';
    return this.http.post<ActivationAttempt>(url, null);
  }
}
