import { Injectable } from '@angular/core';
import { LevelOfEffort } from '../models/level-of-effort.model';
import { AbstractModelService } from './abstract-model.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LevelOfEffortService extends AbstractModelService<LevelOfEffort> {
  override path = '/levelOfEffort';

  findByCompanyId(companyId: number | null): Observable<LevelOfEffort[]> {
    let url = this.getUrl();
    if (companyId) {
      url += '?companyId=' + companyId;
    }
    return this.http.get<LevelOfEffort[]>(url);
  }
}
