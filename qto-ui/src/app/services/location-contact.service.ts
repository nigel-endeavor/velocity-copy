import { Injectable } from '@angular/core';
import { LocationContact } from '../models/location-contact.model';
import { AbstractModelService } from './abstract-model.service';

@Injectable({
  providedIn: 'root'
})
export class LocationContactService extends AbstractModelService<LocationContact> {
  override path = '/locationContacts';
}
