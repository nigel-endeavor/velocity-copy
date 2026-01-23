import { Injectable } from "@angular/core";
import { AbstractModelService } from "./abstract-model.service";
import { Contact, ContactType } from "../models/contact.model";
import { Observable } from "rxjs";
import { OrderContact } from "../models/order-contact";

@Injectable({
  providedIn: 'root'
})
export class ContactService extends AbstractModelService<Contact> {
  override path = '/contacts';

  findByCompanyIdAndType(companyId: number, type: ContactType): Observable<Contact> {
    let url = this.getUrl() + '?companyId=' + companyId + '&type=' + type;
    return this.http.get<Contact>(url);
  }

  findByOrderId(orderId: number): Observable<OrderContact[]> {
    let url = this.getUrl() + '/order?orderId=' + orderId;
    return this.http.get<OrderContact[]>(url);
  }
}