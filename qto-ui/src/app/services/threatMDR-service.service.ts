import { Injectable } from "@angular/core";
import { AbstractModelService } from "./abstract-model.service";
import { ThreatMDRService } from "../models/threatMdr-service.model";

@Injectable({
  providedIn: 'root'
})
export class ThreatMDRServiceService extends AbstractModelService<ThreatMDRService> {
  override path = '/threatMDRService';
}