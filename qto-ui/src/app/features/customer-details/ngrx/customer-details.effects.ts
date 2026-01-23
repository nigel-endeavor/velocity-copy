import { Injectable } from '@angular/core';

import { Actions, concatLatestFrom, createEffect, ofType } from '@ngrx/effects';
import { catchError, mergeMap, switchMap } from 'rxjs/operators';
import { select, Store } from '@ngrx/store';

import * as actions from './customer-details.actions';
import { CustomerDetailsState, CustomersMeta } from './customer-details.reducer';
import { forkJoin, map, of} from 'rxjs';

import { CompanyService } from '../../../services/company.service';
import { CompanySearchCriteria } from '../../../models/company-search-criteria';
import { PaginatedResult } from '../../../models/paginated-result.model';
import { Company } from '../../../models/company.model';
import { SubjectService } from '../../../services/subject.service';
import { ContactService } from '../../../services/contact.service';
import { Contact, ContactType } from '../../../models/contact.model';
import { plainToClass } from "class-transformer";
import { SubjectInterface } from '../../../models/subject.model';
import { CompanyTaskService } from '../../../services/company-task.service';
import { CompanyTask } from '../../../models/company-task.model';
import { TaskGroupService } from '../../../services/task-group.service';
import { TaskGroup } from '../../../models/task-group.model';;
import { CompanyViewService } from "../../../services/company-view.service";
import { companyViewTableActions } from "../config/table.config";
import { getFilters } from "./customer-details.selectors";

@Injectable()
export class CustomerDetailsEffects {

  constructor(
    private actions$: Actions,
    private store: Store<CustomerDetailsState>,
    private customerService: CompanyService,
    private companyViewService: CompanyViewService,
    private contactService: ContactService,
    private subjectService: SubjectService,
    private customerTaskService: CompanyTaskService,
    private taskGroupService: TaskGroupService
  ) {}

  public onLoadMetaData$ = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadMetaData,
      actions.updateFilters,
      actions.clearFilters,
      companyViewTableActions.loadTableData
    ),
    concatLatestFrom(() => [
      this.store.pipe(select(getFilters)),
    ]),
    switchMap(([_, searchCriteria]) => {
      return this.companyViewService.getCustomerWorklistMeta(searchCriteria).pipe(
        map((meta: CustomersMeta) => {
          return actions.loadMetaDataSuccess(meta)
        }),
        catchError(error => of(actions.loadMetaDataFailure(error)))
      )
    })
    )
  );


  public onLoadTenants$ = createEffect(() => this.actions$.pipe(
    ofType(actions.loadTenants),
    switchMap((action) => {
      let tenantSearchCriteria = new CompanySearchCriteria();
      tenantSearchCriteria.type = 'Vertek Client';
      return this.customerService.search(tenantSearchCriteria)
        .pipe(
          map((result: PaginatedResult<Company>) => actions.loadTenantsSuccess({ tenants: result }))
        );
    })
  ));

  public onLoadSelectedCustomer$ = createEffect(() => this.actions$.pipe(
    ofType(actions.loadSelectedCustomer),
    switchMap((action) => {
      return this.customerService.retrieve(action.customerId).
        pipe(
          map((result: Company) => actions.setSelectedCustomer({ customer: result }))
        );
    })
  ));

  onSetSelectedCustomer$ = createEffect(() => this.actions$.pipe(
    ofType(actions.setSelectedCustomer),
    mergeMap((action) => {
      let companyId = action.customer ? action.customer.id : null;
      return forkJoin([
        companyId ? this.contactService.findByCompanyIdAndType(companyId, ContactType.BILLING) : of(new Contact(companyId, ContactType.BILLING)),
        companyId ? this.contactService.findByCompanyIdAndType(companyId, ContactType.TECH) : of(new Contact(companyId, ContactType.TECH)),
        companyId ? this.contactService.findByCompanyIdAndType(companyId, ContactType.SALES) : of(new Contact(companyId, ContactType.SALES)),
        companyId ? this.contactService.findByCompanyIdAndType(companyId, ContactType.AUTH) : of(new Contact(companyId, ContactType.AUTH)),
        companyId ? this.customerService.getCompanyTasks(companyId) : of([]),
        this.taskGroupService.getTaskGroups(true)
      ]).pipe(
        switchMap(([billing, tech, sales, auth, customerTasks, taskGroups]) => {
          let billingContact = billing ? plainToClass(Contact, billing) : new Contact(companyId, ContactType.BILLING);
          let techContact = tech ? plainToClass(Contact, tech) : new Contact(companyId, ContactType.TECH);
          let salesContact = sales ? plainToClass(Contact, sales) : new Contact(companyId, ContactType.SALES);
          let authContact = auth ? plainToClass(Contact, auth) : new Contact(companyId, ContactType.AUTH);
          let taskGroupsOptions: TaskGroup[] = [];
          if (taskGroups && typeof taskGroups === 'object' && 'collection' in taskGroups) {
            taskGroupsOptions = taskGroups.collection;  // Extract the collection
          }
          return of(actions.setSelectedCustomerSuccess({ billingContact, techContact, salesContact, authContact, customerTasks, taskGroups: taskGroupsOptions }));
        })
      )
    })
  ));

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
    switchMap((action) => {
      //save the customer task and send the result to save customer task success
      return this.customerTaskService.save(action.customerTask).pipe(
        map((customerTask) => actions.saveCustomerTaskSuccess({ customerTask }))
      );
    })
  ));

  public onLoadCustomerTasks$ = createEffect(() => this.actions$.pipe(
    ofType(actions.saveCustomerTaskSuccess),
    switchMap((action) => {
      //get company tasks from the company id from the action
      return this.customerService.getCompanyTasks(action.customerTask.companyId).pipe(
        map((customerTasks: CompanyTask[]) => actions.loadCustomerTasksSuccess({ customerTasks }))
      );
    })
  ))

}
