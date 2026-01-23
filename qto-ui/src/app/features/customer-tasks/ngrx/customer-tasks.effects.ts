import { Injectable } from '@angular/core';

import { Actions, concatLatestFrom, createEffect, ofType } from '@ngrx/effects';
import { catchError, mergeMap, switchMap } from 'rxjs/operators';
import { select, Store } from '@ngrx/store';

import * as actions from './customer-tasks.actions';
import { CustomerTasksState } from './customer-tasks.reducer';
import { forkJoin, map, of} from 'rxjs';

import { CompanyService } from '../../../services/company.service';
import { SubjectService } from '../../../services/subject.service';
import { SubjectInterface } from '../../../models/subject.model';
import { CompanyTaskService } from '../../../services/company-task.service';
import { CompanyTask } from '../../../models/company-task.model';
import { TaskGroupService } from '../../../services/task-group.service';

@Injectable()
export class CustomerTasksEffects {

  constructor(
    private actions$: Actions,
    private store: Store<CustomerTasksState>,
    private customerService: CompanyService,
    private subjectService: SubjectService,
    private customerTaskService: CompanyTaskService,
    private taskGroupService: TaskGroupService
  ) {}

  public onLoadSubjects$ = createEffect(() => this.actions$.pipe(
    ofType(actions.loadProvisioners),
    switchMap((action) => {
      return this.subjectService.getSubjects().pipe(
        map((subjects: SubjectInterface[]) => actions.loadProvisionersSuccess({ subjects })),
        catchError(error => of(actions.loadProvisionersFailure(error)))
      )
    })
  ));

  public onSaveCustomerTask$ = createEffect(() => this.actions$.pipe(
    ofType(actions.saveCustomerTask),
    mergeMap((action) => {
      // Save the customer task and send the result to save customer task success
      return this.customerTaskService.save(action.customerTask).pipe(
        map((customerTask) => actions.saveCustomerTaskSuccess({ customerId: customerTask.companyId, customerTask }))
      );
    })
  ));
  
  
  public onLoadCustomerTasks$ = createEffect(() => this.actions$.pipe(
    ofType(actions.saveCustomerTaskSuccess, actions.loadCustomerTasks),
    switchMap((action) => {
      //get company tasks from the company id from the action
      return this.customerService.getCompanyTasks(action.customerId).pipe(
        map((customerTasks: CompanyTask[]) => actions.loadCustomerTasksSuccess({ customerTasks }))
      );
    })
  ))

  public onLoadTaskGroup$ = createEffect(() => this.actions$.pipe(
    ofType(actions.loadTaskGroup),
    switchMap((action) => {
      return this.taskGroupService.retrieve(action.taskGroupId).pipe(
        map((taskGroup) => actions.loadTaskGroupSuccess({ taskGroup }))
      )
    })
  ))
}
