import { Injectable } from "@angular/core";
import { AbstractModelService } from "./abstract-model.service";
import { ServiceCyberView } from "../models/service-cyber-view.model";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ServiceCyberViewService extends AbstractModelService<ServiceCyberView> {
  override path = '/serviceCyberViews';

  // retrieves service types found in the inventory
  getServiceTypes(): Observable<string[]> {
    return this.http.get<string[]>(this.getUrl() + '/serviceTypes');
  }
}
