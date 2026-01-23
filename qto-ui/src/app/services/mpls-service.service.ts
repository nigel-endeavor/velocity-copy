import { Injectable } from "@angular/core";
import { MplsService } from "../models/mpls-service.model";
import { AbstractModelService } from "./abstract-model.service";

@Injectable({
  providedIn: 'root'
})
export class MplsServiceService extends AbstractModelService<MplsService> {
  override path = '/mplsServices';
}