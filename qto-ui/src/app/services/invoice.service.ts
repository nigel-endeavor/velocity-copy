import { Injectable } from '@angular/core';
import { AbstractModelService } from './abstract-model.service';
import { Invoice } from '../models/invoice.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InvoiceService extends AbstractModelService<Invoice> {
  override path = '/invoices';

  override save(entity: Invoice): Observable<Invoice> {
    if (entity.id) {
      return super.save(entity);
    } else {
      return this.http.post<Invoice>(`${this.getUrl()}/${encodeURIComponent(entity.clientName)}`, entity);
    }
  }

  finalize(entity: Invoice): Observable<Invoice> {
    const url = `${this.getUrl()}/${entity.id}/finalize`;
    return this.http.put<Invoice>(url, entity);
  }
}
