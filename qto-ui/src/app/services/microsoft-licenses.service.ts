import { Injectable } from '@angular/core';
import { AbstractModelService } from './abstract-model.service';
import { MicrosoftLicenses } from '../models/microsoft-licenses.model';

@Injectable({
  providedIn: 'root'
})
export class MicrosoftLicensesService extends AbstractModelService<MicrosoftLicenses> {
  override path = '/microsoftLicenses';
}