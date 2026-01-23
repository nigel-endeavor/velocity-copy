import { Injectable } from "@angular/core";
import { ServiceCustomFieldValue } from "../models/service-custom-field-value.model";
import { AbstractModelService } from "./abstract-model.service";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ServiceCustomFieldValueService extends AbstractModelService<ServiceCustomFieldValue> {
  override path = '/serviceCustomfieldValues';

  findByServiceId(serviceId: number): Observable<ServiceCustomFieldValue[]> {
    return this.http.get<ServiceCustomFieldValue[]>(this.getUrl() + '?serviceId=' + serviceId);
  }

  saveValues(values: ServiceCustomFieldValue[]): Observable<ServiceCustomFieldValue[]> {
    return this.http.post<ServiceCustomFieldValue[]>(this.getUrl() + '/saveValues', { values });
  }
}