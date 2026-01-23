import { Injectable } from '@angular/core';
import { TelevisionService } from '../models/television-service.model';
import { AbstractModelService } from './abstract-model.service';

@Injectable({
  providedIn: 'root'
})
export class TelevisionServiceService extends AbstractModelService<TelevisionService> {
  override path = '/televisionService';
}
