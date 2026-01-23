import { Injectable } from '@angular/core';
import { Address } from '../models/address.model';
import { AbstractModelService } from './abstract-model.service';

@Injectable({
  providedIn: 'root'
})
export class AddressService extends AbstractModelService<Address> {
  override path = '/addresses'
}
