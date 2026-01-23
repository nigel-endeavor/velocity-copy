import { Injectable } from "@angular/core";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { switchMap, map, of, mergeMap, forkJoin, catchError } from "rxjs";
import { CompanyService } from "src/app/services/company.service";
import * as actions from "./configuration.actions";
import { CompanySearchCriteria } from "src/app/models/company-search-criteria";
import { PaginatedResult } from "src/app/models/paginated-result.model";
import { Company } from "src/app/models/company.model";
import { Store } from "@ngrx/store";
import { ContactService } from "../../../services/contact.service";
import { plainToClass } from "class-transformer";
import { Contact, ContactType } from "../../../models/contact.model";
import { SubjectInterface } from "../../../models/subject.model";
import { SubjectService } from "../../../services/subject.service";

@Injectable()
export class ConfigurationEffects {

  constructor(
    private actions$: Actions,
    private companyService: CompanyService,
    private contactService: ContactService,
    private subjectService: SubjectService,
    private store: Store
  ) { }

  public onLoadTenants$ = createEffect(() => this.actions$.pipe(
    ofType(actions.loadTenants),
    switchMap((action) => {
      let tenantSearchCriteria = new CompanySearchCriteria();
      tenantSearchCriteria.type = 'Vertek Client';
      return this.companyService.search(tenantSearchCriteria)
        .pipe(
          map((result: PaginatedResult<Company>) => actions.loadTenantsSuccess({ tenants: result }))
        );
    })
  ));

  public onLoadSelectedCustomer$ = createEffect(() => this.actions$.pipe(
    ofType(actions.loadSelectedCustomer),
    switchMap((action) => {
      return this.companyService.retrieve(action.customerId).
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
        companyId ? this.contactService.findByCompanyIdAndType(companyId, ContactType.AUTH) : of(new Contact(companyId, ContactType.AUTH))
      ]).pipe(
        switchMap(([billing, tech, sales, auth]) => {
          let billingContact = billing ? plainToClass(Contact, billing) : new Contact(companyId, ContactType.BILLING);
          let techContact = tech ? plainToClass(Contact, tech) : new Contact(companyId, ContactType.TECH);
          let salesContact = sales ? plainToClass(Contact, sales) : new Contact(companyId, ContactType.SALES);
          let authContact = auth ? plainToClass(Contact, auth) : new Contact(companyId, ContactType.AUTH);
          return of(actions.setSelectedCustomerSuccess({ billingContact, techContact, salesContact, authContact }));
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
  )
);
}