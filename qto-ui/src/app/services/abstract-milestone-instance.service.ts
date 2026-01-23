import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MilestoneInstance } from '../models/milestone-instance.model';
import { AbstractModelService } from './abstract-model.service';

@Injectable({
  providedIn: 'root'
})
export class AbstractMilestoneInstanceService<T extends MilestoneInstance> extends AbstractModelService<T> {
  parentParamName: string;

  getInstances(parentId: number): Observable<T[]> {
    const url = this.getUrl() + '?' + this.parentParamName + '=' + parentId;
    return this.http.get<T[]>(url);
  }
}
