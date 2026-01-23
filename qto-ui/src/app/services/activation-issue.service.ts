import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ActivationIssue } from '../models/activation-issue.model';
import { AbstractModelService } from './abstract-model.service';

@Injectable({
  providedIn: 'root'
})
export class ActivationIssueService extends AbstractModelService<ActivationIssue> {
  override path = '/activationIssues';
  
  findByActivationAttemptId(activationAttemptId: number): Observable<ActivationIssue[]> {
    const url = this.getUrl() + '?activationAttemptId=' + activationAttemptId;
    return this.http.get<ActivationIssue[]>(url);
  }
}
