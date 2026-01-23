import { CommonModule, DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { plainToClass, plainToInstance } from 'class-transformer';
import { NgxDaterangepickerMd } from 'ngx-daterangepicker-material';
import { combineLatestWith, Observable, Subject, take } from 'rxjs';
import { MilestoneDisplaySetInclude } from '../../models/milestone-display-set-include.model';
import { MilestoneDisplaySet } from '../../models/milestone-display-set.model';
import { MilestoneInstance } from '../../models/milestone-instance.model';
import { Milestone } from '../../models/milestone.model';
import { OrderEditService } from '../../order-detail-page/order-edit.service';
import { AbstractMilestoneInstanceService } from '../../services/abstract-milestone-instance.service';
import { MilestoneDisplaySetService } from '../../services/milestone-display-set.service';
import { SecurityUtilService, Permissions } from '../../services/security-util.service';
import { JeopsComponent } from '../jeops/jeops.component';
import { NoteComponent } from '../note/note.component';
import { MatDividerModule } from '@angular/material/divider';
import { DatepickerModule } from '../datepicker/datepicker.module';
import { DatetimepickerModule } from '../datetimepicker/datetimepicker.module';
import { MatLegacyProgressSpinnerModule as MatProgressSpinnerModule } from '@angular/material/legacy-progress-spinner';
import { select, Store } from '@ngrx/store';
import { reloadOrder } from '../../order-detail-page/ngrx/order-details.actions';
import { editEnabled, getSelectedLocationOrService, getIsReadOnly, getIsInventory } from '../../order-detail-page/ngrx/order-details.selectors';
import { Service } from '../../models/service.model';
import { Location } from '../../models/location.model';
import { Jeop } from 'src/app/models/jeop.model';
import { MatLegacyDialog, MatLegacyDialogModule, MatLegacyDialogRef } from '@angular/material/legacy-dialog';
import { ComponentType } from '@angular/cdk/portal';
import { JeopService } from 'src/app/services/jeop.service';

@Component({
  standalone: true,
  templateUrl: './abstract-milestones.component.html',
  styleUrls: ['./abstract-milestones.component.scss'],
  imports: [
    CommonModule,
    NoteComponent,
    JeopsComponent,
    DatePipe,
    FormsModule,
    NgxDaterangepickerMd,
    MatDividerModule,
    DatepickerModule,
    DatetimepickerModule,
    MatProgressSpinnerModule
  ]
})
export class AbstractMilestonesComponent<T extends MilestoneInstance> implements OnInit {

  parentId: number;
  companyId: number;
  displayGroup: string;
  progressPercent: number;
  sequenceNumber: number;
  collapseIndex: number;
  originalCompleteDate: Date | null;

  @ViewChild('milestoneForm') milestoneForm: NgForm;

  displaySet: MilestoneDisplaySet;
  displayGroups: { sectionName: string, displaySetIncludes: MilestoneDisplaySetInclude[] }[] = [{
    sectionName: 'Order Request Received',
    displaySetIncludes: []
  }, {
    sectionName: 'Location Provisioning',
    displaySetIncludes: []
  }, {
    sectionName: 'Activation',
    displaySetIncludes: []
  }, {
    sectionName: 'Location Onboarding',
    displaySetIncludes: []
  }, {
    sectionName: 'Product Onboarding',
    displaySetIncludes: []
  }, {
    sectionName: 'Final and Held',
    displaySetIncludes: []
  }, {
    sectionName: 'QA & Billing',
    displaySetIncludes: []
  }];
  instances: T[];
  collapseCompleted: boolean = false;
  level: string;
  jeops: Jeop[];

  private _milestoneDisplaySetService: MilestoneDisplaySetService;
  public _oes: OrderEditService;
  public _store: Store;
  public _securityUtils: SecurityUtilService;
  private _dialogService: MatLegacyDialog;
  private _jeopService: JeopService;
  public isLoading$ = new Subject<boolean>();
  public editEnabled$ = new Observable<boolean>;
  public isReadOnly$ = new Observable<boolean>;
  public isReadOnly: boolean = false;
  public selectedLocationOrService$ = new Observable<Location | Service | null>;
  public isInventory$ = new Observable<boolean>;
  serviceType: string;
  public cyberClient: boolean = false;
  public telecomClient: boolean = false;


  constructor(
    milestoneDisplaySetService: MilestoneDisplaySetService,
    oes: OrderEditService,
    securityUtils: SecurityUtilService,
    store: Store,
    dialogService: MatLegacyDialog,
    jeopService: JeopService
  ) {
    this._milestoneDisplaySetService = milestoneDisplaySetService;
    this._oes = oes;
    this._securityUtils = securityUtils;
    this._store = store;
    this._dialogService = dialogService;
    this._jeopService = jeopService;
    this.editEnabled$ = this._store.pipe(select(editEnabled));
    this.isReadOnly$ = this._store.pipe(select(getIsReadOnly))
    this.isInventory$ = this._store.pipe(select(getIsInventory))
    this.selectedLocationOrService$ = this._store.pipe(select(getSelectedLocationOrService));
  }

  ngOnInit(): void {
    this.companyId = this._oes.order!.company.id;
    this.telecomClient = localStorage.getItem('TELECOM_CLIENT') === 'true';
    this.cyberClient = localStorage.getItem('CYBER_SECURITY_CLIENT') === 'true';

    this.isReadOnly$.subscribe((res) => {
      this.isReadOnly = res;
    });
    if (this.displayGroup == 'LOCATION_MILESTONE') {
      this.level = 'location';
    } else {
      this.level = 'service'
    }
    if (!this.displayGroup) {
      throw Error('MilestoneComponent subclass should override displayGroup');
    }

    this.selectedLocationOrService$.subscribe((res) => {
      if (res && res.id) {
        if (res instanceof Service) {
          this.serviceType = res.type;
        }
        this.parentId = res.id;
        this.progressPercent = res.progressPercentage;
        this.displayGroups.forEach(group => {
          group.displaySetIncludes = [];
        });

        const displaySetRequest = this._milestoneDisplaySetService.getDisplaySet(this.displayGroup);
        const milestoneInstanceRequest = this.getMilestoneInstanceService().getInstances(this.parentId);
        displaySetRequest.pipe(
          combineLatestWith(milestoneInstanceRequest)
        ).subscribe(([displaySet, instances]) => {
          //clear out displaySetInclude for each displayGroup
          this.displayGroups.forEach(group => {
            group.displaySetIncludes = [];
          });
          this.displaySet = plainToClass(MilestoneDisplaySet, displaySet);
          this.displaySet.displaySetIncludes.forEach(item => {
            switch (true) {
              case item.sequence < 1000:
                this.displayGroups[0].displaySetIncludes.push(item)
                break;
              case item.sequence < 2000:
                this.displayGroups[1].displaySetIncludes.push(item)
                break;
              case item.sequence < 2500:
                this.displayGroups[2].displaySetIncludes.push(item)
                break;
                // used for cyber security milestones
              case item.sequence < 2750:
                this.displayGroups[3].displaySetIncludes.push(item)
                break;
              case item.sequence < 3000:
                this.displayGroups[4].displaySetIncludes.push(item)
                break;
              case item.sequence < 4000:
                this.displayGroups[5].displaySetIncludes.push(item)
                break;
              case item.sequence < 5000:
                this.displayGroups[6].displaySetIncludes.push(item)
                break;
            }
          });
          this.instances = plainToInstance(this.getMilestoneInstanceClass(), instances);
          this.setCollapseIndex();
          this.instances.forEach(instance => {
            if (instance.milestone.code == 'COMPLETE') {
              this.originalCompleteDate = instance.milestoneDate;
            }
          });

          setTimeout(() => {
            this.displaySet.displaySetIncludes.forEach(include => {
              this.milestoneForm.form.controls[include.milestone.code]?.markAsPristine();
            });
          }, 0);
        });
      }
    });
  }

  getMilestoneInstanceService(): AbstractMilestoneInstanceService<T> {
    throw Error('Method not implemented');
  }

  getNewInstance(milestone: Milestone): T {
    throw Error('Method not implemented');
  }

  getMilestoneInstanceClass(): any {
    throw Error('Method not implemented');
  }

  onSaveClicked(instance: T): void {
    if (this.jeops
        && !["NETWORK_PROVIDER_CONSTRUCTION_START", "CUSTOMER_REQUESTED_INSTALL", "ON_HOLD"].includes(instance.milestone.code)) {
      let openJeops = this.jeops.filter(jeop => jeop.endDate == null);
      if (openJeops.length > 0) {
        const component: ComponentType<CloseJeopsDialog> = CloseJeopsDialog;
        const dialogRef = this._dialogService.open(component, {});
        dialogRef.afterClosed().subscribe((result: boolean) => {
          if (result) {
            openJeops.forEach(jeop => {
              jeop.endDate = new Date();
              this._jeopService.save(jeop).subscribe();
            });
          }
        });
      }
    }
    if (instance.milestoneDate === undefined) {
      instance.milestoneDate = new Date();
    }
    this.isLoading$.next(true);
    this.getMilestoneInstanceService().save(instance).subscribe(
      (res: T) => {
        const index = this.instances.findIndex(i => i.milestone.id == instance.milestone.id);
        if (res.milestoneDate == null) {
          this.instances[index] = this.getNewInstance(res.milestone)
        } else {
          this.instances[index] = plainToClass(this.getMilestoneInstanceClass(), res);
        }
        this.setCollapseIndex();
        this._store.dispatch(reloadOrder());
        //if a terminal milestone is set, reload the milestone instances. Also reload if the Network FOC is set
        // because it could also update the Provider Order Submitted milestone
        if (['CHANGE_IN_ASSIGNMENT', 'COMPLETE', 'CANCELLED', 'NETWORK_PROVIDER_FOC'].includes(instance.milestone.code)) {
          this.getMilestoneInstanceService().getInstances(this.parentId).subscribe(res => {
            this.instances = res;
          })
        }
      },
      (err) => {
      if (instance.milestone.code == 'COMPLETE') {
        instance.milestoneDate = this.originalCompleteDate;
        this.isLoading$.next(false);
        throw err;
      }
        instance.milestoneDate = null;
        this.isLoading$.next(false);
        throw err;
      }, () => {
        this.isLoading$.next(false);
      }
    );
  }

  getInstance(include: MilestoneDisplaySetInclude): T {
    let instance = this.instances.find(i => i.milestone.id == include.milestone.id);
    if (!instance) {
      instance = this.getNewInstance(include.milestone);
      this.instances.push(instance);
    }
    return instance;
  }

  //determines whether a milestone date field can be updated
  isDisabled(include: MilestoneDisplaySetInclude, instance: T): boolean {
    return (instance.id != null && !include.adjustable) || include.workflowDriven;
  }

  //gets appropriate date format for date input
  getDateFormat(include: MilestoneDisplaySetInclude, input: HTMLInputElement): string {
    if (input.type == 'text' || input.type == 'date') {
      return include.hasTime ? 'short' : 'MM/dd/yyyy';
    }
    return include.hasTime ? 'yyyy-MM-ddThh:mm' : 'yyyy-MM-dd';
  }

  //gets todays date as a string, used for setting the max property on date input
  getToday(includeTime: boolean): string {
    const today = new Date();
    let dd: any = today.getDate();
    let mm: any = today.getMonth() + 1;
    let yyyy = today.getFullYear();
    if (dd < 10) {
      dd = '0' + dd;
    }
    if (mm < 10) {
      mm = '0' + mm;
    }
    let dateString = yyyy + '-' + mm + '-' + dd;
    return (includeTime) ? dateString + 'T00:00' : dateString;
  }

  getMaxDate(includeTime: boolean): string {
    return (includeTime) ? '9999-12-31T00:00' : '9999-12-31';
  }

  onCollapseClicked(): void {
    this.collapseCompleted = !this.collapseCompleted;
  }

  setCollapseIndex(): void {
    const lastPopulated = this.displaySet.displaySetIncludes
      .slice().reverse().find(inc => {
        const instance = this.instances.find(i => i.milestone.id == inc.milestone.id);
        return instance?.milestoneDate;
      });
    this.collapseIndex = (lastPopulated) ? this.displaySet.displaySetIncludes.indexOf(lastPopulated) : -1;
    this.sequenceNumber = (this.collapseIndex > -1) ? this.displaySet.displaySetIncludes[this.collapseIndex].sequence : -1;
  }

  getStyle() {
    let height = 0;
    if (!this.collapseCompleted) {
      height = 35 * this.collapseIndex + (this.collapseIndex ? 35 : 0);
    }
    if (this.level === 'service') {
      if (this.sequenceNumber > 0) {
        switch (true) {
          case this.sequenceNumber < 1000:
            height += 36
            break;
          case this.sequenceNumber < 2000:
            height += 72
            break;
          case this.sequenceNumber < 3000:
            if (this.cyberClient) {
              height += 72
            } else {
              height += 108
            }
            break;
          case this.sequenceNumber < 4000:
            if (this.cyberClient) {
              height += 108
            } else {
              height += 144
            }
            break;
          default:
            height += 180;
            break;
        }
      }
    }

    return { height: height + 'px' }
  }

  getVisible(sectionIndex: number, milestoneIndex: number) {
    let result = 0;
    const realIndex = this.displayGroups.map(item => item.displaySetIncludes.length)
      .reduce((acc, curr, index) => sectionIndex === 0 ? 0 :
        index < sectionIndex ? acc + curr : acc, result);
    return !this.collapseCompleted || realIndex + milestoneIndex > this.collapseIndex;
  }
}


@Component({
  selector: 'close-jeops-dialog',
  templateUrl: './close-jeops-dialog.html',
  styleUrls: ['./abstract-milestones.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatLegacyDialogModule
  ]
})
export class CloseJeopsDialog {

  constructor(
    public dialogRef: MatLegacyDialogRef<CloseJeopsDialog>
  ) { }

}
