import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LocationMilestoneInstance } from '../models/milestone-instance.model';
import { AbstractMilestoneInstanceService } from './abstract-milestone-instance.service';
import { AbstractModelService } from './abstract-model.service';

@Injectable({
  providedIn: 'root'
})
export class LocationMilestoneInstanceService extends AbstractMilestoneInstanceService<LocationMilestoneInstance> {
  override path = '/locationMilestoneInstances';
  override parentParamName: string = 'locationId';
}
