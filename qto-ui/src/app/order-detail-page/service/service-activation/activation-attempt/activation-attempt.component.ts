import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { plainToClass } from 'class-transformer';
import { ActivationAttemptRequirement } from 'src/app/models/activation-attempt-requirement.model';
import { ActivationAttempt } from 'src/app/models/activation-attempt.model';
import { ActivationAttemptService } from 'src/app/services/activation-attempt.service';
import { LookupValueService } from 'src/app/services/lookup-value.service';
import { RequirementTemplateService } from 'src/app/services/requirement-template.service';
import { RequirementTemplate } from 'src/app/models/requirement-template.model';
import { OrderEditService } from 'src/app/order-detail-page/order-edit.service';
import { SecurityUtilService } from 'src/app/services/security-util.service';
import { ComponentType } from '@angular/cdk/overlay';
import { EmailTemplateDialogComponent } from 'src/app/components/email-template-dialog/email-template-dialog.component';
import { MatLegacyDialog as MatDialog } from '@angular/material/legacy-dialog';
import { MatDialog as NewMatDialog } from '@angular/material/dialog';
import { Observable, take } from 'rxjs';
import { SurchargeDialogComponent } from '../../../../components/surcharges/surcharge-dialog.component';
import { SubjectService } from 'src/app/services/subject.service';
import { SubjectInterface } from 'src/app/models/subject.model';
import { ActivationIssueService } from 'src/app/services/activation-issue.service';
import { ActivationIssue } from 'src/app/models/activation-issue.model';
import { Service } from '../../../../models/service.model';
import { select, Store } from '@ngrx/store';
import { activationAttemptEditEnabled, editEnabled, getInventoryLocation, getIsInventory, getIsOrderWrite, getIsReadOnly, getSelectedLocation } from '../../../ngrx/order-details.selectors';
import { ActivationScheduleService } from 'src/app/services/activation-schedule.service';
import { ActivationSchedule } from 'src/app/models/activation-schedule.model';
import { loadInventoryLocation, loadUserStatuses } from 'src/app/order-detail-page/ngrx/order-details.actions';
import { OptionsDialogComponent } from '../../../../components/milestones/options-dialog.component';
import { addressToString } from '../../../../utilities';

@Component({
  selector: 'app-activation-attempt',
  templateUrl: './activation-attempt.component.html',
  styleUrls: ['../../../form-styles.scss', './activation-attempt.component.scss']
})
export class ActivationAttemptComponent implements OnInit {
  public editEnabled$ = this.store.pipe(select(editEnabled));
  public isReadOnly$ = this.store.pipe(select(getIsReadOnly));
  public userHasOrderWrite$ = this.store.pipe(select(getIsOrderWrite));
  public userHasOrderWriteTerminal$ = this.store.pipe(select(getIsOrderWrite));
  public isInventory: boolean;
  public inventoryLocation$: Observable<any>;
  public selectedLocation$: Observable<any>;

  @Input() attempt: ActivationAttempt = {} as ActivationAttempt;
  @Input() companyId: number;
  @Input() requirementTemplateId: number;
  @Output() activationSaved = new EventEmitter<void>();
  @Output() cancelDispatch = new EventEmitter<ActivationSchedule>();
  @Output() rollbackPush = new EventEmitter<ActivationSchedule>();

  @ViewChild('activationForm') activationForm: NgForm;

  schedule: ActivationSchedule;

  replace4g5gOpts: string[];
  subjectOpts: string[];
  requirementTemplateName: string;
  activationIssueCount: number = 0;
  service: Service;

  editingScheduleDetails: boolean = false;
  editingTestResults: boolean = false;
  editingTtuNotes: boolean = false;
  editingCloseNotes: boolean = false;
  editingRequirements: boolean = false;
  editingCompleteDates: boolean = false;
  editingMilestoneDates: boolean = false;

  showActivationIssues: boolean = false;

  isLoading: boolean = false;

  public activationEditEnabled$ = this.store.pipe(select(activationAttemptEditEnabled(this.attempt.scheduledAttemptStatus)));


  constructor(
    public createEmailDialog: MatDialog,
    public surchargesDialog: MatDialog,
    private activationAttemptService: ActivationAttemptService,
    private activationIssueService: ActivationIssueService,
    public securityUtils: SecurityUtilService,
    private lookupValueService: LookupValueService,
    private requirementTemplateService: RequirementTemplateService,
    private store: Store,
    public oes: OrderEditService,
    private subjectService: SubjectService,
    private activationScheduleService: ActivationScheduleService,
    private dialog: NewMatDialog
  ) {
    this.store.pipe(select(getIsInventory)).subscribe(res => {
      this.isInventory = res;
    });
  }

  ngOnInit(): void {
    this.store.dispatch(loadUserStatuses());
    this.lookupValueService.getValues('ACTIVATION_REPLACE_4G5G', this.companyId).subscribe((res: string[]) => {
      this.replace4g5gOpts = res;
    });
    this.subjectService.getSubjects(this.oes.order?.id).subscribe((res: SubjectInterface[]) => {
      this.subjectOpts = res.map((subject: SubjectInterface) => subject.displayName);
    });
    if (this.requirementTemplateId) {
      this.requirementTemplateService.retrieve(this.requirementTemplateId).subscribe((res: RequirementTemplate) => {
        this.requirementTemplateName = res.name;
      });
    }
    this.activationScheduleService.retrieve(this.attempt.activationScheduleId).subscribe((res: ActivationSchedule) => {
      this.schedule = res;
    });

    if (!this.isInventory) {
      this.store.dispatch(loadInventoryLocation({ serviceId: this.attempt.serviceId }));
      this.inventoryLocation$ = this.store.pipe(select(getInventoryLocation));
      this.selectedLocation$ = this.store.pipe(select(getSelectedLocation));
    }
  }

  ngOnChanges(): void {
    this.setActivationIssueCount();
  }

  onSaveClicked(): void {
    this.setAllFlags(false);
    this.activationAttemptService.save(this.attempt).subscribe((res: ActivationAttempt) => {
      this.attempt = plainToClass(ActivationAttempt, res);
      this.activationSaved.emit();
    });
  }

  onPushClicked(status: string): void {
    this.isLoading = true;
    const oldStatus = this.attempt.scheduledAttemptStatus;
    this.attempt.scheduledAttemptStatus = status;
    this.setAllFlags(false);
    if (status === 'Complete') {
      this.inventoryLocation$.pipe(take(1)).subscribe(inventoryLocation => {
        if (inventoryLocation != null) {
          this.selectedLocation$.pipe(take(1)).subscribe(selectedLocation => {
            let serviceFormattedAddress = addressToString(selectedLocation.address1, selectedLocation.address2, selectedLocation.city, selectedLocation.state, selectedLocation.postalCode, selectedLocation.country);
            let inventoryFormattedAddress = addressToString(inventoryLocation.address1, inventoryLocation.address2, inventoryLocation.city, inventoryLocation.state, inventoryLocation.postalCode, inventoryLocation.country);
            if (serviceFormattedAddress != inventoryFormattedAddress) {
              let message = `The service order you are completing for Client Location ID "${selectedLocation.clientLocationId}" has a different address in inventory from your order.  Please select which address to keep for this inventory record.`;
              const dialogRef = this.dialog.open(OptionsDialogComponent, {
                data: {
                  title: 'Attention', message: message,
                  options: [
                    {header: 'Your Address', content: serviceFormattedAddress},
                    {header: 'Inventory Address', content: inventoryFormattedAddress}
                  ]
                },
                maxWidth: '600px'
              });

              dialogRef.afterClosed().subscribe(result => {
                if (result) {
                  this.attempt.useExistingInventoryLocationAddress = result.header == 'Inventory Address';
                  this.saveActivationAttempt(oldStatus);
                } else {
                  this.attempt.scheduledAttemptStatus = oldStatus;
                  this.isLoading = false;
                  return;
                }
              });
              return;
            }

          });
        } else {
          this.saveActivationAttempt(oldStatus);
        }
      });
    } else {
      this.saveActivationAttempt(oldStatus);
    }
  }

  saveActivationAttempt(oldStatus : string): void {
    this.activationAttemptService.save(this.attempt).subscribe(
      (res: ActivationAttempt) => {
        this.attempt = plainToClass(ActivationAttempt, res);
        this.activationSaved.emit();
        this.isLoading = false;
        //TODO: open email dialog
      },
      err => {
        this.isLoading = false;
        this.attempt.scheduledAttemptStatus = oldStatus;
        throw err;
      }
    );
  }

  toggleComplete(requirement: ActivationAttemptRequirement) {
    requirement.complete = !requirement.complete;
  }

  toggleEdit(editFlag: string): void {
    if (this.isEditing() && this.activationForm.dirty) {
      const reset = confirm('You have unsaved changes. Revert these changes and proceed?');
      if (reset) {
        this.activationForm.resetForm();
      } else {
        return;
      }
    }
    this.setAllFlags(false);
    switch (editFlag) {
      case 'scheduleDetails':
        this.editingScheduleDetails = true;
        break;
      case 'testResults':
        this.editingTestResults = true;
        break;
      case 'ttuNotes':
        this.editingTtuNotes = true;
        break;
      case 'closeNotes':
        this.editingCloseNotes = true;
        break;
      case 'requirements':
        this.editingRequirements = true;
        break;
      case 'completeDates':
        this.editingCompleteDates = true;
        break;
      case 'milestoneDates':
        this.editingMilestoneDates = true;
        break;
      default:
        console.error('Activations: ' + editFlag + 'is not a supported edit flag');
    }
  }

  isEditing(): boolean {
    return this.editingScheduleDetails || this.editingTestResults || this.editingTtuNotes || this.editingCloseNotes
        || this.editingRequirements || this.editingCompleteDates || this.editingMilestoneDates;
  }

  setAllFlags(value: boolean): void {
    this.editingScheduleDetails = value;
    this.editingTestResults = value;
    this.editingTtuNotes = value;
    this.editingCloseNotes = value;
    this.editingRequirements = value;
    this.editingCompleteDates = value;
    this.editingMilestoneDates = value;
  }

  onActivationIssuesRecordClicked(): void {
    this.showActivationIssues = true;
  }

  onIssuesDialogClosed(event: number | void): void {
    this.showActivationIssues = false;
    if (event) {
      this.activationIssueCount = event;
    } else {
      this.setActivationIssueCount();
    }
  }

  onCreateEmailClicked(): void {
    const component: ComponentType<EmailTemplateDialogComponent> = EmailTemplateDialogComponent;

    const dialogRef = this.createEmailDialog.open(component, {
      data: {
        id: this.attempt.id,
        level: 'ACTIVATION'
      },
      width: '800px',
      panelClass: 'modal-dialog',
    });


    dialogRef.afterClosed().pipe(take(1)).subscribe(result => {
      console.log(result);
      // I don't think we want to do anything here, but catching for now
    });
  }


  onServiceSurchargesClicked(serviceId: number, companyId: number): void {
    const component: ComponentType<SurchargeDialogComponent> = SurchargeDialogComponent;

    const dialogRef = this.surchargesDialog.open(component, {
      data: {
        id: serviceId,
        companyId: companyId
      },
      width: '1000px',
      panelClass: 'modal-dialog'
    });

    dialogRef.afterClosed().pipe(take(1)).subscribe(result => {
      console.log(result);
      // I don't think we want to do anything here, but catching for now
    });
  }

  setActivationIssueCount(): void {
    this.activationIssueService.findByActivationAttemptId(this.attempt.id).subscribe((res: ActivationIssue[]) => {
      this.activationIssueCount = res.length;
    });
  }

  onCancelDispatchClicked(): void {
    this.cancelDispatch.emit(this.schedule);
  }

   onRollbackPush(): void {
    this.rollbackPush.emit(this.schedule);
  }

  canCancelDispatch(): boolean {
    let isReadOnly = !(this.securityUtils.getIsOrderWriteUser() || this.securityUtils.getIsOrderWriteTerminalUser());
    if (isReadOnly) { //user is readonly
      return false;
    }
    if (this.attempt.cancelledDate) { //attempt is already cancelled
      return false;
    }
    let isEndeavor = this.schedule && this.schedule.vendor == 'Endeavor';
    if (!isEndeavor) { //not endeavor, not cancelled yet
      return true; //can cancel
    }
    if (isEndeavor && (!this.schedule.dispatches || this.schedule.dispatches.length == 0)) { //endeavor, no dispatches
      return false;
    }
    if (isEndeavor && this.schedule.dispatches[0].vendorDispatchId) { //endeavor, dispatches, dispatch has id
      return true;
    }
    return false;
  }
}
