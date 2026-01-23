import { Injectable } from '@angular/core';
import { AbstractModelService } from './abstract-model.service';
import { UcaasService } from '../models/ucaas-service.model';

@Injectable({
  providedIn: 'root'
})
export class UcaasServiceService extends AbstractModelService<UcaasService> {
  override path = '/ucaasServices';
}
