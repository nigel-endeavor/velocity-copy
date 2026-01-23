import { Injectable } from "@angular/core";
import { AbstractModelService } from "./abstract-model.service";
import { EngineeringInfoProtectionService } from "../models/engineering-Info-protection-service.model";

@Injectable({
  providedIn: 'root'
})
export class EngineeringInfoProtectionServiceService extends AbstractModelService<EngineeringInfoProtectionService> {
  override path = '/engineeringInfoProtectionService';
}
