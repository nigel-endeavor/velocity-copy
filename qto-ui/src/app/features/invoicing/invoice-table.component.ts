import { Component } from '@angular/core';
import { Store } from "@ngrx/store";
import { AbstractTableComponent } from "../abstract-table/abstract-table.component";
import { SecurityUtilService } from "../../services/security-util.service";
import { InvoiceService } from "../../services/invoice.service";
import { INVOICE_COLUMNS } from './data/invoice-table-columns.consts';
import { CommonColumn } from '../../interfaces/columns.interface';
import { Invoice } from '../../models/invoice.model';

@Component({
  selector: 'app-invoice-table',
  templateUrl: '../abstract-table/abstract-table.component.html',
  styleUrls: ['../abstract-table/abstract-table.component.scss']
})
export class InvoiceTableComponent extends AbstractTableComponent<Invoice> {
  override persistFilters = true;
  constructor(
    private invoiceService: InvoiceService,
    public override store: Store,
    public override securityUtils: SecurityUtilService,
  ) {
    super(store, securityUtils);
  }

  override columns: CommonColumn[] = INVOICE_COLUMNS;


  override getModelService() {
    return this.invoiceService;
  }

  override getStatusColor(row: any) {
    return '#FFFFFF';
  }
}