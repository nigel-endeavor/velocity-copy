import { Injectable } from "@angular/core";
import { AbstractModelService } from "./abstract-model.service";
import { EngineeringMDMService } from "../models/engineering-MDM-service.model";

@Injectable({
  providedIn: 'root'
})
export class EngineeringMDMServiceService extends AbstractModelService<EngineeringMDMService> {
  override path = '/engineeringMDMService';
}
