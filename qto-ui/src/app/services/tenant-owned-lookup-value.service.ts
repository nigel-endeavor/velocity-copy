import { Injectable } from '@angular/core';
import { TenantOwnedLookupValue } from '../models/tenant-lookup-owned-value.model';
import { AbstractModelService } from './abstract-model.service';

@Injectable({
  providedIn: 'root'
})
export class TenantOwnedLookupValueService extends AbstractModelService<TenantOwnedLookupValue> {
  override path = '/tenantOwnedLookupValues';
}
