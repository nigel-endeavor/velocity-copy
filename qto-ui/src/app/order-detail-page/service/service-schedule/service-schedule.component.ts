import { Component, OnInit } from '@angular/core';
import { plainToInstance } from 'class-transformer';
import { ActivationSchedule } from '../../../models/activation-schedule.model';
import { ActivationScheduleService } from '../../../services/activation-schedule.service';
import { Service } from '../../../models/service.model';
import { OrderEditService } from '../../order-edit.service';
import { select, Store } from '@ngrx/store';
import { reloadOrder } from '../../ngrx/order-details.actions';
import { editEnabled, getSelectedLocationOrService, getIsInventory } from '../../ngrx/order-details.selectors';
import {Location} from "../../../models/location.model";

@Component({
  selector: 'app-service-schedule',
  templateUrl: './service-schedule.component.html',
  styleUrls: ['./service-schedule.component.scss']
})
export class ServiceScheduleComponent implements OnInit {
  public editEnabled$ = this.store.pipe(select(editEnabled));

  serviceId: number;
  companyId: number;

  schedules: ActivationSchedule[];
  selectedSchedule: ActivationSchedule | null;

  isLoading: boolean = false;
  public isInventory: boolean;

  constructor(
    private activationScheduleService: ActivationScheduleService,
    public oes: OrderEditService,
    private store: Store
  ) {
    this.store.pipe(select(getIsInventory)).subscribe(res => {
      this.isInventory = res;
    });
  }

  ngOnInit(): void {
    this.store.pipe(select(getSelectedLocationOrService)).subscribe((service: Service | Location | null) => {
      if (service instanceof Service) {
        this.serviceId = service.id;
        this.companyId = this.oes.order!.company.id;
        this.activationScheduleService.findByServiceId(this.serviceId).subscribe((res: ActivationSchedule[]) => {
          this.schedules = plainToInstance(ActivationSchedule, res);
          if (this.schedules.length > 0) {
            this.selectedSchedule = this.schedules[0];
          }
        });
      }
    })
  }

  onTabClicked(schedule: ActivationSchedule): void {
    this.selectedSchedule = schedule;
  }

  onNewClicked(): void {
    let newSchedule = new ActivationSchedule();
    newSchedule.serviceId = this.serviceId;
    this.schedules.unshift(newSchedule);
    this.selectedSchedule = this.schedules[0];
  }

  //this is the cancel action for a new schedule
  onScheduleCanceled(): void {
    this.selectedSchedule = null;
    this.schedules.splice(0, 1);
    if (this.schedules.length > 0) {
      this.selectedSchedule = this.schedules[0];
    }
  }

  onScheduleSaved(): void {
    this.isLoading = true;
    //surcharges
    //check to see if the current date is the same as the requested date
    if (this.selectedSchedule && this.selectedSchedule.requestedDate) {
      let today = new Date();
      let scheduleDate = new Date(this.selectedSchedule.requestedDate);
      //check to see if the current date is the same as the requested date
      if (scheduleDate && today.getFullYear() === scheduleDate.getFullYear() && today.getMonth() === scheduleDate.getMonth() && today.getDate() === scheduleDate.getDate()) {
        this.selectedSchedule!.applySameDayTurnUpSurcharge = true;
      }
    }

    this.activationScheduleService.save(this.selectedSchedule!).subscribe(res => {
      this.activationScheduleService.findByServiceId(this.serviceId).subscribe((res: ActivationSchedule[]) => {
        this.schedules = res;
        this.selectedSchedule = this.schedules[0];
        this.store.dispatch(reloadOrder());
        this.isLoading = false;
      });
    },
    error => {
      this.isLoading = false;
      throw error;
    });
  }

  onPushToFtdi(): void {
    this.isLoading = true;
    this.activationScheduleService.pushToFtdi(this.selectedSchedule).subscribe(res => {
      this.activationScheduleService.findByServiceId(this.serviceId).subscribe((res: ActivationSchedule[]) => {
        this.schedules = res;
        this.selectedSchedule = this.schedules[0];
        this.store.dispatch(reloadOrder());
        this.isLoading = false;
      });
    },
    error => {
      this.isLoading = false;
      throw error;
    });
  }

}
