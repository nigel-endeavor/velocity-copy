import { Injectable } from '@angular/core';
import { Company } from '../models/company.model';
import { AbstractModelService } from './abstract-model.service';
import { CompanyTask } from '../models/company-task.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CompanyService extends AbstractModelService<Company> {
  override path = '/companies';

  getCompanyTasks(companyId: number): Observable<CompanyTask[]> {
    return this.http.get<CompanyTask[]>(`${this.getUrl()}/${companyId}/tasks`);
  }
}
