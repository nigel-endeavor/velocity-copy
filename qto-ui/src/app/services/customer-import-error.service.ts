import { Injectable } from "@angular/core";
import { AbstractModelService } from "./abstract-model.service";
import { CustomerImportError } from "../models/customer-import-error.model";

@Injectable({
  providedIn: 'root'
})
export class CustomerImportErrorService extends AbstractModelService<CustomerImportError> {
  override path = '/customerImportErrors';
}