import { Component, Inject, OnInit } from '@angular/core';
import { MAT_LEGACY_DIALOG_DATA as MAT_DIALOG_DATA, MatLegacyDialogRef as MatDialogRef } from '@angular/material/legacy-dialog';
import { Store, select } from '@ngrx/store';
import { updateFilters, updateSort, pageDestroyed } from './store/invoice-charges.actions';
import * as invoicingActions from '../store/invoicing.actions';
import { Invoice } from '../../../models/invoice.model';
import { getSelectedInvoice, getVertekClientsAsOptions } from '../store/invoicing.selectors';
import { BaseWorklistClass } from '../../../utilities/cards/base-worklist.class';
import { invoicingChargeTableActions, invoicingChargeTableSelectors } from './configs/table.config';
import { getColumns, getFilters } from './store/invoice-charges.selectors';
import { Observable, filter } from 'rxjs';
import { SecurityUtilService } from 'src/app/services/security-util.service';

@Component({
  selector: 'app-invoice-dialog',
  templateUrl: './invoice-dialog.component.html',
  styleUrls: ['./invoice-dialog.component.scss']
})
export class InvoiceDialogComponent extends BaseWorklistClass implements OnInit {
  public columns$ = this.store.pipe(select(getColumns));
  public tableData$: Observable<any[]> = this.store.pipe(select(invoicingChargeTableSelectors.getTableData));
  public isLoading$ = this.store.pipe(select(invoicingChargeTableSelectors.getTableDataIsLoading));
  public tableTotal$: Observable<number> = this.store.pipe(select(invoicingChargeTableSelectors.getTableTotal));
  public searchCriteria$: Observable<any> = this.store.pipe(select(getFilters), filter(item => !!item));

  public selectedInvoice$ = this.store.pipe(select(getSelectedInvoice));
  public vertekClientOptions$ = this.store.pipe(select(getVertekClientsAsOptions));

  constructor(private dialogRef: MatDialogRef<InvoiceDialogComponent>, private store: Store, public securityUtils: SecurityUtilService,
    @Inject(MAT_DIALOG_DATA) public data: {
      invoice: Invoice
    }) {
    super();
    dialogRef.backdropClick().subscribe(() => {
      this.onClose();
    });
  }

  ngOnInit(): void {
    this.selectedInvoice$.subscribe(invoice => {
      if (invoice) {
        this.store.dispatch(updateFilters({ key: 'invoiceId', value: invoice.id }));
      }
    });
    this.vertekClientOptions$.subscribe(options => {
      if (options && options.length === 1) {
        this.onPropertyUpdate('clientName', { value: options[0] });
      }
    });
  }

  onClose() {
    this.store.dispatch(pageDestroyed());
    this.dialogRef.close();
  }

  onCancel() {
    this.store.dispatch(pageDestroyed());
    this.dialogRef.close();
  }

  onSearch(event: any): void {
    const value = event.target.value;
    this.subject.next({
      key: 'search', value
    });
  }

  onPropertyUpdate(property: string, event: any) {
    this.store.dispatch(invoicingActions.updateInvoice({ key: property, value: event.value }));
  }

  save(invoice: Invoice) {
    this.store.dispatch(invoicingActions.saveInvoice({ invoice }));
    this.dialogRef.close();
  }

  onChangeSortClicked(event: any) {
    this.store.dispatch(updateSort({ sort: event }));
  }

  onFilterChanged(value: string | boolean | number, key: string): void {
    this.store.dispatch(updateFilters({ key, value}));
  }

  exportTable(fileName: string): void {
    this.store.dispatch(invoicingChargeTableActions.exportTable({ fileName }));
  }

  finalize(invoice: Invoice): void {
    this.store.dispatch(invoicingActions.finalizeInvoice({ invoice }));
    this.dialogRef.close();
  }

  unfinalize(invoice: Invoice): void {
    this.store.dispatch(invoicingActions.finalizeInvoice({ invoice }));
    this.dialogRef.close();
  }
}
