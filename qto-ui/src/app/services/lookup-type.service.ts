import { Injectable } from "@angular/core";
import { AbstractModelService } from "./abstract-model.service";
import { LookupType } from "../models/lookup-type.model";
import { LookupValue } from "../models/lookup-value.model";

@Injectable({
  providedIn: 'root'
})
export class LookupTypeService extends AbstractModelService<LookupType> {
  override path = '/lookupTypes';

  setValues(lookupType: LookupType, values: LookupValue[]) {
    lookupType.values = values;
    let url = this.getUrl() + '/' + lookupType.id;
    return this.http.put<void>(url, lookupType);
  }
}