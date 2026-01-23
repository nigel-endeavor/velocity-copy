import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MilestoneDisplaySet } from '../models/milestone-display-set.model';
import { AbstractModelService } from './abstract-model.service';

@Injectable({
  providedIn: 'root'
})
export class MilestoneDisplaySetService extends AbstractModelService<MilestoneDisplaySet> {
  override path = '/milestoneDisplaySets';

  getDisplaySet(displayGroup: string): Observable<MilestoneDisplaySet> {
    const url = this.getUrl() + '?displayGroup=' + displayGroup;
    return this.http.get<MilestoneDisplaySet>(url);
  }
}
