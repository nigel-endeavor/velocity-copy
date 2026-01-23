import { Injectable } from "@angular/core";
import { AbstractModelService } from "./abstract-model.service";
import { Observable } from "rxjs";
import { LocationCustomFieldValue } from "../models/location-custom-field-value.model";

@Injectable({
  providedIn: 'root'
})
export class LocationCustomFieldValueService extends AbstractModelService<LocationCustomFieldValue> {
  override path = '/locationCustomfieldValues';

  findByLocationId(locationId: number): Observable<LocationCustomFieldValue[]> {
    return this.http.get<LocationCustomFieldValue[]>(this.getUrl() + '?locationId=' + locationId);
  }

  saveValues(values: LocationCustomFieldValue[]): Observable<LocationCustomFieldValue[]> {
    return this.http.post<LocationCustomFieldValue[]>(this.getUrl() + '/saveValues', values);
  }
}