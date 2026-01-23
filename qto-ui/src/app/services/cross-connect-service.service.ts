import { Injectable } from '@angular/core';
import { AbstractModelService } from './abstract-model.service';
import { CrossConnectService } from '../models/cross-connect-service.model';

@Injectable({
  providedIn: 'root'
})
export class CrossConnectServiceService extends AbstractModelService<CrossConnectService> {
  override path = '/crossConnectService';
}
