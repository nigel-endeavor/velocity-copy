import { Injectable } from '@angular/core';
import { AbstractModelService } from './abstract-model.service';
import { InvoiceCharge } from '../models/invoice-charge.model';

@Injectable({
  providedIn: 'root'
})
export class InvoiceChargeService extends AbstractModelService<InvoiceCharge> {
  override path = '/invoiceCharges';
}
