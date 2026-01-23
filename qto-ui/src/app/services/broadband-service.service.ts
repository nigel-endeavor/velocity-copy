import { Injectable } from '@angular/core';
import { BroadbandService } from '../models/broadband-service.model';
import { AbstractModelService } from './abstract-model.service';

@Injectable({
  providedIn: 'root'
})
export class BroadbandServiceService extends AbstractModelService<BroadbandService> {
  override path = '/broadbandService';
}
