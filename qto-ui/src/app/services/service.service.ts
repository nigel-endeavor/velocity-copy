import { AbstractModelService } from './abstract-model.service';
import { Service } from '../models/service.model';
import { Injectable } from '@angular/core';
import { Location } from '../models/location.model';

@Injectable({
  providedIn: 'root'
})
export class ServiceService extends AbstractModelService<Service> {
  override path = '/services';

  getInventoryLocation(id: number) {
    return this.http.get<Location>(`${this.getUrl()}/${id}/inventoryLocation`);
  }

  getRelatedMacds(id: number) {
    return this.http.get<Service[]>(`${this.getUrl()}/${id}/relatedMacds`);
  }

}
