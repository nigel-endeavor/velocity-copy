import { Injectable } from "@angular/core";
import { AbstractModelService } from "./abstract-model.service";
import { Cyber360MXDRService } from "../models/cyber360MXDR-service.model";

@Injectable({
  providedIn: 'root'
})
export class Cyber360MXDRServiceService extends AbstractModelService<Cyber360MXDRService> {
  override path = '/cyber360MXDRService';
}
