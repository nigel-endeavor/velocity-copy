import { Component } from '@angular/core';
import { Store } from "@ngrx/store";
import { AbstractTableComponent } from '../../abstract-table/abstract-table.component';
import { InvoiceCharge } from '../../../models/invoice-charge.model';
import { InvoiceChargeService } from '../../../services/invoice-charge.service';
import { SecurityUtilService } from '../../../services/security-util.service';
import { INVOICE_CHARGE_COLUMNS } from './data/invoice-charge-table-columns.consts';
import { CommonColumn } from '../../../interfaces/columns.interface';

@Component({
  selector: 'app-invoice-charge-table',
  templateUrl: '../../abstract-table/abstract-table.component.html',
  styleUrls: ['./invoice-charge-table.component.scss']
})
export class InvoiceChargeTableComponent extends AbstractTableComponent<InvoiceCharge> {
  override persistFilters = true;
  constructor(
    private invoiceChargeService: InvoiceChargeService,
    public override store: Store,
    public override securityUtils: SecurityUtilService,
  ) {
    super(store, securityUtils);
  }
  
  override columns: CommonColumn[] = INVOICE_CHARGE_COLUMNS;


  override getModelService() {
    return this.invoiceChargeService;
  }

  override getStatusColor(row: any) {
    return '#FFFFFF';
  }
}