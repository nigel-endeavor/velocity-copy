import { Injectable } from '@angular/core';
import { AbstractModelService } from './abstract-model.service';
import { ServiceEquipment } from '../models/service-equipment-model';

@Injectable({
  providedIn: 'root'
})
export class ServiceEquipmentService extends AbstractModelService<ServiceEquipment> {
  override path = '/serviceEquipment';
}
