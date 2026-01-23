import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';;
import { AbstractModelService } from './abstract-model.service';
import { TaskGroup } from '../models/task-group.model';
import { PaginatedResult } from '../models/paginated-result.model';

@Injectable({
  providedIn: 'root'
})
export class TaskGroupService extends AbstractModelService<TaskGroup> {
  override path = '/taskGroups';

  getTaskGroups(active: boolean | null): Observable<PaginatedResult<TaskGroup>> {
    let url = this.getUrl() + '?limit=10000';

    if (active != null) {
      url += `&active=${active}`;
    }
    return this.http.get<PaginatedResult<TaskGroup>>(url);
  }
}
