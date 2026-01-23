import { Injectable } from "@angular/core";
import { ServiceHistoryView } from "../models/service-history-view.model";
import { AbstractModelService } from "./abstract-model.service";

@Injectable({
  providedIn: 'root'
})
export class ServiceHistoryViewService extends AbstractModelService<ServiceHistoryView> {
  override path = '/serviceHistory';
}