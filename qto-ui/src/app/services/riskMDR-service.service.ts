import { Injectable } from "@angular/core";
import { AbstractModelService } from "./abstract-model.service";
import { RiskMDRService } from "../models/riskMdr-service.model";

@Injectable({
  providedIn: 'root'
})
export class RiskMDRServiceService extends AbstractModelService<RiskMDRService> {
  override path = '/riskMDRService';
}