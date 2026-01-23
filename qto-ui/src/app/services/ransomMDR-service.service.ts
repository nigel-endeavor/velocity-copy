import { Injectable } from "@angular/core";
import { AbstractModelService } from "./abstract-model.service";
import { RansomMDRService } from "../models/ransomMdr-service.model";

@Injectable({
  providedIn: 'root'
})
export class RansomMDRServiceService extends AbstractModelService<RansomMDRService> {
  override path = '/ransomMDRService';
}