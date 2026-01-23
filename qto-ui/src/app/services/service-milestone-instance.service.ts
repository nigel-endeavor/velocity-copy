import { Injectable } from '@angular/core';
import { ServiceMilestoneInstance } from '../models/milestone-instance.model';
import { AbstractMilestoneInstanceService } from './abstract-milestone-instance.service';

@Injectable({
  providedIn: 'root'
})
export class ServiceMilestoneInstanceService extends AbstractMilestoneInstanceService<ServiceMilestoneInstance> {
  override path = '/serviceMilestoneInstances';
  override parentParamName: string = 'serviceId';
}
