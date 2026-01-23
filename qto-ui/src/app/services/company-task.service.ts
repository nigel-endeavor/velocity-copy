import { Injectable } from '@angular/core';
import { AbstractModelService } from './abstract-model.service';
import { CompanyTask } from '../models/company-task.model';

@Injectable({
  providedIn: 'root'
})
export class CompanyTaskService extends AbstractModelService<CompanyTask> {
  override path = '/companyTasks';
}
