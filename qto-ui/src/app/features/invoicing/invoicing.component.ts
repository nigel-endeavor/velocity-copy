import { Component, OnInit } from '@angular/core';
import { Store, select } from '@ngrx/store';
import { updateFilters, updateSort } from './store/invoicing.actions';
import { getColumns, getFilters, getSelectedInvoice, getVertekClientsAsOptions } from './store/invoicing.selectors';
import { Observable, debounceTime, filter, take } from 'rxjs';
import { invoicingTableActions, invoicingTableSelectors } from './configs/table.config';
import * as actions from './store/invoicing.actions';
import { BaseWorklistClass } from '../../utilities/cards/base-worklist.class';
import { InvoiceDialogComponent } from './invoice-dialog/invoice-dialog.component';
import { ComponentType } from '@angular/cdk/portal';
import { MatLegacyDialog as MatDialog } from '@angular/material/legacy-dialog';
import { Invoice } from '../../models/invoice.model';
import { SecurityUtilService } from 'src/app/services/security-util.service';

@Component({
  selector: 'app-invoicing',
  templateUrl: './invoicing.component.html',
  styleUrls: ['./invoicing.component.scss']
})
export class InvoicingComponent extends BaseWorklistClass implements OnInit {
  public columns$ = this.store.pipe(select(getColumns));
  public tableData$: Observable<any[]> = this.store.pipe(select(invoicingTableSelectors.getTableData));
  public isLoading$ = this.store.pipe(select(invoicingTableSelectors.getTableDataIsLoading));
  public tableTotal$: Observable<number> = this.store.pipe(select(invoicingTableSelectors.getTableTotal));
  public searchCriteria$: Observable<any> = this.store.pipe(select(getFilters), filter(item => !!item));

  public selectedInvoice$ = this.store.pipe(select(getSelectedInvoice));
  public vertekClientOptions$ = this.store.pipe(select(getVertekClientsAsOptions));

  constructor(private store: Store, public invoiceDialog: MatDialog, public securityUtils: SecurityUtilService) {
    super();
  }

  ngOnInit(): void {
    this.store.dispatch(invoicingTableActions.loadTableData());
    this.subject.pipe(debounceTime(500)).subscribe((props: any) => {
      this.store.dispatch(updateFilters(props));
    });
    this.store.dispatch(actions.loadVertekClients());
  }

  onChangeSortClicked(event: any) {
    this.store.dispatch(updateSort({ sort: event }));
  }

  onSearch(event: any): void {
    const value = event.target.value;
    this.subject.next({
      key: 'search', value
    });
  }

  onFilterChanged(value: string | boolean | number, key: string): void {
    this.store.dispatch(actions.setSelectedInvoice({ invoice: undefined }));
    this.store.dispatch(updateFilters({ key, value}));
  }

  exportTable(fileName: string): void {
    this.store.dispatch(invoicingTableActions.exportTable({ fileName }));
  }

  onAddClicked(): void {
    this.store.dispatch(actions.createInvoice());
    const component: ComponentType<InvoiceDialogComponent> = InvoiceDialogComponent;

    const dialogRef = this.invoiceDialog.open(component, {
      width: '20vw'
    });
  }

  onInvoiceDblClicked(invoice: Invoice) {
    this.store.dispatch(actions.setSelectedInvoice({ invoice }));
    const component: ComponentType<InvoiceDialogComponent> = InvoiceDialogComponent;

    const dialogRef = this.invoiceDialog.open(component, {
      width: '95vw'
    });
  }
}
