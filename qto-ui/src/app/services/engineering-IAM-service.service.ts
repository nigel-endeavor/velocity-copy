import { Injectable } from "@angular/core";
import { AbstractModelService } from "./abstract-model.service";
import { EngineeringIAMService } from "../models/engineering-IAM-service.model";

@Injectable({
  providedIn: 'root'
})
export class EngineeringIAMServiceService extends AbstractModelService<EngineeringIAMService> {
  override path = '/engineeringIAMService';
}