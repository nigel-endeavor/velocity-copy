import { Injectable } from '@angular/core';
import { AbstractModelService } from './abstract-model.service';
import { GService } from '../models/g-service.model';

@Injectable({
  providedIn: 'root'
})
export class GServiceService extends AbstractModelService<GService> {
  override path = '/4G5GServices';
}
