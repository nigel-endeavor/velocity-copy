import { Injectable } from "@angular/core";
import { EthernetService } from "../models/ethernet-service.model";
import { AbstractModelService } from "./abstract-model.service";

@Injectable({
  providedIn: 'root'
})
export class EthernetServiceService extends AbstractModelService<EthernetService> {
  override path = '/ethernetServices';
}