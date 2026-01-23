import { Observable } from "rxjs";
import { AbstractModelService } from "./abstract-model.service";
import { CustomField, CustomFieldTabValue } from "../models/custom-field.model";
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class CustomFieldService extends AbstractModelService<CustomField> {
  override path = '/customfields';

  findByTab(tab: CustomFieldTabValue): Observable<CustomField[]> {
    let url = this.getUrl() + '?tab=' + tab;
    return this.http.get<CustomField[]>(url);
  }
}