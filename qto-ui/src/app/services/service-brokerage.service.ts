import { AbstractModelService } from './abstract-model.service';
import { Injectable } from '@angular/core';
import { ServiceBrokerage } from '../models/service-brokerage-model';

@Injectable({
  providedIn: 'root'
})
export class ServiceBrokerageService extends AbstractModelService<ServiceBrokerage> {
  override path = '/serviceBrokerages';

}
