import { Component, OnInit } from '@angular/core';
import { plainToInstance } from 'class-transformer';
import { ActivationAttempt } from 'src/app/models/activation-attempt.model';
import { ActivationAttemptService } from 'src/app/services/activation-attempt.service';
import { Service } from 'src/app/models/service.model';
import { OrderEditService } from '../../order-edit.service';
import { ActivatedRoute } from '@angular/router';
import {select, Store} from '@ngrx/store';
import { reloadOrder, setSelectedActivationId } from '../../ngrx/order-details.actions';
import {getIsInventory, getSelectedLocationOrService} from "../../ngrx/order-details.selectors";
import {Location} from "../../../models/location.model";
import { ActivationSchedule } from 'src/app/models/activation-schedule.model';

@Component({
  selector: 'app-service-activation',
  templateUrl: './service-activation.component.html',
  styleUrls: ['./service-activation.component.scss']
})
export class ServiceActivationComponent implements OnInit {
  public selectedLocationOrService$ = this.store.pipe(select(getSelectedLocationOrService));

  serviceId: number;
  companyId: number;
  requirementTemplateId: number;

  attempts: ActivationAttempt[];
  selectedAttempt: ActivationAttempt;

  isLoading: boolean = false;
  isInventory: boolean;

  constructor(
    private activationAttemptService: ActivationAttemptService,
    public oes: OrderEditService,
    private route: ActivatedRoute,
    private store: Store
  ) {
    this.store.pipe(select(getIsInventory)).subscribe(res => {
      this.isInventory = res;
    });
  }

  ngOnInit(): void {
    this.selectedLocationOrService$.subscribe((service: Service | Location | null) => {
      if (service instanceof Service) {
        this.serviceId = service.id;
        this.companyId = this.oes.order!.company.id;
        this.requirementTemplateId = this.oes.order!.locations.find(l => l.id == service.locationId)!.requirementTemplateId;
        this.activationAttemptService.findByServiceId(this.serviceId).subscribe((res: ActivationAttempt[]) => {
          this.attempts = plainToInstance(ActivationAttempt, res);
          if (this.attempts.length > 0) {
            this.route.queryParams.subscribe(params => {
              let activationId = params['activationId'];
              this.selectedAttempt = this.attempts.find(a => a.id == activationId) || this.attempts[0];
            });
          }
        });
      }
    });
  }

  onTabClicked(attempt: ActivationAttempt): void {
    this.selectedAttempt = attempt;
  }

  reloadOrder() {
    this.store.dispatch(setSelectedActivationId({ selectedActivationId: this.selectedAttempt.id }));
    this.store.dispatch(reloadOrder());
  }

  //this is the cancel action for an existing schedule
  onCancelDispatch(schedule: ActivationSchedule): void {
    this.isLoading = true;
    //surcharges
    //check to see if the current date is the same as the requested date
    let applySameDayCancelSurcharge: boolean = false;
    if (schedule && schedule.requestedDate) {
      let today = new Date();
      let scheduleDate = new Date(schedule.requestedDate);
      if (scheduleDate && today.getFullYear() === scheduleDate.getFullYear() && today.getMonth() === scheduleDate.getMonth() && today.getDate() === scheduleDate.getDate()) {
        applySameDayCancelSurcharge = true;
      }
    }
    this.activationAttemptService.cancel(this.selectedAttempt.id, applySameDayCancelSurcharge).subscribe(res => {
      this.activationAttemptService.findByServiceId(this.serviceId).subscribe((res: ActivationAttempt[]) => {
        this.attempts = res;
        this.selectedAttempt = this.attempts[0];
        this.reloadOrder();
        this.isLoading = false;
      });
    },
    error => {
      this.isLoading = false;
      throw error;
    });
  }

  //this is the rollback action for an existing schedule
  onRollbackPush(schedule: ActivationSchedule): void {
    this.isLoading = true;
    this.activationAttemptService.rollback(this.selectedAttempt.id).subscribe(res => {
      this.activationAttemptService.findByServiceId(this.serviceId).subscribe((res: ActivationAttempt[]) => {
        this.attempts = res;
        this.selectedAttempt = this.attempts[0];
        this.reloadOrder();
        this.isLoading = false;
      });
    },
    error => {
      this.isLoading = false;
      throw error;
    });
  }
}
