import { Injectable } from '@angular/core';

import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, switchMap } from 'rxjs/operators';
import { Store } from '@ngrx/store';
import * as actions from './invoicing.actions';
import { of } from 'rxjs';

import { InvoicingState } from './invoicing.reducer';
import { InvoiceService } from '../../../services/invoice.service';
import { HttpErrorResponse } from '@angular/common/http';
import { invoicingTableActions } from '../configs/table.config';
import { Invoice } from '../../../models/invoice.model';
import { CompanySearchCriteria } from '../../../models/company-search-criteria';
import { PaginatedResult } from '../../../models/paginated-result.model';
import { Company } from '../../../models/company.model';
import { CompanyService } from '../../../services/company.service';

@Injectable()
export class InvoicingEffects {

  constructor(
    private actions$: Actions,
    private store: Store<InvoicingState>,
    private invoiceService: InvoiceService,
    private companyService: CompanyService
  ) {}

  public saveInvoice = createEffect(() => this.actions$.pipe(
    ofType(
      actions.saveInvoice
    ),
    switchMap((action) => {
      return this.invoiceService.save(action.invoice).pipe(
        switchMap((invoice: Invoice) => [actions.saveInvoiceSuccess({ invoice }), invoicingTableActions.loadTableData()])
      );
    })
  ))

  public finalizeInvoice = createEffect(() => this.actions$.pipe(
    ofType(
      actions.finalizeInvoice
    ),
    switchMap((action) => {
      return this.invoiceService.finalize(action.invoice).pipe(
        switchMap((invoice: Invoice) => [actions.finalizeInvoiceSuccess({ invoice }), invoicingTableActions.loadTableData()]),
      );
    })
  ))

  public onLoadVertekClients$ = createEffect(() => this.actions$.pipe(
    ofType(actions.loadVertekClients),
    switchMap((action) => {
      let companyCriteria = new CompanySearchCriteria();
      companyCriteria.type = 'Vertek Client';
      return this.companyService.search(companyCriteria).pipe(
        map((vertekClients: PaginatedResult<Company>) => actions.loadVertekClientsSuccess({ vertekClients })),
        catchError(error => of(actions.loadVertekClientsFailure(error)))
      )
    })
  ));
}
