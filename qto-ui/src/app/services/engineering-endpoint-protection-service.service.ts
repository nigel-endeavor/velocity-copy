import { Injectable } from "@angular/core";
import { AbstractModelService } from "./abstract-model.service";
import { EngineeringEndpointProtectionService } from "../models/engineering-endpoint-service.model";

@Injectable({
  providedIn: 'root'
})
export class EngineeringEndpointProtectionServiceService extends AbstractModelService<EngineeringEndpointProtectionService> {
  override path = '/engineeringEndpointService';
}
