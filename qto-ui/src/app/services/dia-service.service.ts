import { Injectable } from '@angular/core';
import { DiaService } from '../models/dia-service.model';
import { AbstractModelService } from './abstract-model.service';

@Injectable({
  providedIn: 'root'
})
export class DiaServiceService extends AbstractModelService<DiaService> {
  override path = '/diaServices';
}
