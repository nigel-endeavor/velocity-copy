import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import {
  ActivationSchedule,
  ActivationScheduleCustom,
  ActivationScheduleEquipment
} from 'src/app/models/activation-schedule.model';
import { FtdiCustomField, FtdiEquipmentType, FtdiOrderType } from 'src/app/models/ftdi-order-type.model';
import { editEnabled } from 'src/app/order-detail-page/ngrx/order-details.selectors';
import { select, Store } from "@ngrx/store";
import { FtdiOrderTypeService } from 'src/app/services/ftdi-order-type.service';
import { LookupValueService } from 'src/app/services/lookup-value.service';
import { loadUserStatuses } from 'src/app/order-detail-page/ngrx/order-details.actions';

@Component({
  selector: 'app-activation-schedule',
  templateUrl: './activation-schedule.component.html',
  styleUrls: ['../../../form-styles.scss', './activation-schedule.component.scss']
})
export class ActivationScheduleComponent implements OnInit, OnChanges {
  public editEnabled$ = this.store.pipe(select(editEnabled));
  editEnabled: boolean;

  @Input() schedule: ActivationSchedule;
  @Input() companyId: number;
  @Output() cancel = new EventEmitter<void>();
  @Output() save = new EventEmitter<void>();
  @Output() pushToFtdi = new EventEmitter<void>();

  @ViewChild('scheduleForm') scheduleForm: NgForm;

  dispatchVendorOpts: string[];
  sort1Opts: string[];
  sort2Opts: string[];
  orderTypeOpts: FtdiOrderType[];
  dayOpts = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];


  orderTypes: FtdiOrderType[];
  // create a copy of the schedule to avoid changing the original schedule until the save button is clicked

  orderTypeSort1: string | null = null;
  orderTypeSort2: string | null = null;
  equipmentType: FtdiEquipmentType | null = null;
  equipmentQuantity: number | null = null;

  editingRequestedDate: boolean = false;

  isLoading: boolean = false;

  constructor(
    private ftdiOrderTypeService: FtdiOrderTypeService,
    private lookupValueService: LookupValueService,
    private store: Store<any>
  ) { }

  ngOnInit(): void {
    this.store.dispatch(loadUserStatuses());
    this.lookupValueService.getValues('DISPATCH_VENDOR', this.companyId).subscribe((res: string[]) => {
      this.dispatchVendorOpts = res;
    });
    this.editEnabled$.subscribe((res) => {
      this.editEnabled = res;
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['schedule']) {
      this.loadOrderType();

      //support for "If Hard ETA needed, please specify ETA time" field
      let cf = this.schedule.customFields.find(cf => cf.fieldName == 'If Hard ETA needed, please specify ETA time');
      if (cf && cf.fieldValue) {
        const [hour, minute, ampm] = cf.fieldValue.split(/:|\s/);
        this.hour = hour;
        this.minute = minute;
        this.ampm = ampm;
      }
    }
  }

  loadOrderType(): void {
    this.isLoading = true;
    if (this.isEndeavor()) {
      this.ftdiOrderTypeService.retrieve(this.schedule.ftdiOrderTypeId!).subscribe((res: FtdiOrderType) => {
        this.orderTypeSort1 = res.sort1;
        this.orderTypeSort2 = res.sort2;
        if (this.isReadOnly()) {
          this.sort1Opts = [res.sort1];
          this.sort2Opts = [res.sort2];
          this.ftdiOrderTypeService.findOrderType(this.orderTypeSort1, this.orderTypeSort2).subscribe((res2: FtdiOrderType[]) => {
            this.isLoading = true;
            this.orderTypeOpts = res2;
            this.orderTypes = res2;
            this.isLoading = false;
          });
        } else {
          this.fillSort1('Endeavor');
          this.fillsort2(this.orderTypeSort1);
          this.ftdiOrderTypeService.findOrderType(this.orderTypeSort1, this.orderTypeSort2).subscribe((res2: FtdiOrderType[]) => {
            this.isLoading = true;
            this.orderTypeOpts = res2;
            this.orderTypes = res2;
            this.isLoading = false;
          });
        }
      });
    }
  }

  isEndeavor(): boolean {
    return this.schedule.vendor == 'Endeavor';
  }

  isCts(): boolean {
    return this.schedule.vendor == 'CTS';
  }

  isReadOnly(): boolean {
    if (!this.schedule.id) {
      return false;
    } else if (this.isEndeavor() &&
      (!this.schedule.dispatches || this.schedule.dispatches.length == 0)) {
      return false
    } else if ((this.isEndeavor() && this.schedule.dispatches[0].vendorDispatchId)
      || (!this.isEndeavor() && this.schedule.id)) {
      return true;
    }
    return false;
  }

  onDispatchVendorSelected(value: string): void {
    this.schedule.vendor = value;
    this.schedule.ftdiOrderTypeId = null;
    this.orderTypeSort1 = null;
    this.schedule.customFields = [];
    this.schedule.equipment = [];
    this.isLoading = true;
    this.fillSort1(value).then(() => {
      this.isLoading = false;
    });
    this.orderTypeSort2 = null;
  }

  fillSort1(value: string): Promise<string[]> {
    return new Promise<string[]>((resolve) => {
      this.ftdiOrderTypeService.findSort1(value).subscribe(
        (res: string[]) => {
          this.sort1Opts = res;
          resolve(res);
        }
      );
    });
  }

  onSort1Selected(value: string): void {
    this.schedule.ftdiOrderTypeId = null;
    this.schedule.customFields = [];
    this.schedule.equipment = [];
    this.orderTypeSort1 = value;
    this.orderTypeSort2 = null;
    this.fillsort2(value);

  }

  fillsort2(value: string): void {
    this.ftdiOrderTypeService.findSort2(value).subscribe((res: string[]) => {
      this.sort2Opts = res;
    });
  }

  onSort2Selected(value: string): void {
    this.schedule.ftdiOrderTypeId = null;
    this.schedule.customFields = [];
    this.schedule.equipment = [];
    this.orderTypeSort2 = value;
    this.ftdiOrderTypeService.findOrderType(this.orderTypeSort1, value).subscribe((res: FtdiOrderType[]) => {
      this.orderTypeOpts = res;
      this.orderTypes = res;
      this.isLoading = false;
    });
  }


  onOrderTypeSelected(value: number): void {
    this.schedule.ftdiOrderTypeId = value;
    this.schedule.customFields = [];
    this.schedule.equipment = [];
  }

  onCustomFieldInput(event: any, customField: FtdiCustomField) {
    let value;
    if (customField.dataType == 'Boolean') {
      value = event.target.checked;
    } else {
      value = event.target.value;
    }
    let cf = this.schedule.customFields.find(c => c.fieldName == customField.fieldName);
    if (!cf) {
      cf = new ActivationScheduleCustom();
      cf.fieldName = customField.fieldName;
      cf.fieldValue = value;
      this.schedule.customFields.push(cf);
    } else {
      cf.fieldValue = value;
    }
  }

  onAddEquipmentClicked(): void {
    if (!this.equipmentType || !this.equipmentQuantity) {
      return;
    }
    let equipment = this.schedule.equipment.find(e => e.equipmentType == this.equipmentType!.equipmentType);
    if (!equipment) {
      equipment = new ActivationScheduleEquipment();
      equipment.equipmentType = this.equipmentType.equipmentType;
      equipment.itemNumber = this.equipmentType.itemNumber;
      equipment.quantity = this.equipmentQuantity;
      this.schedule.equipment.push(equipment);
    } else {
      equipment.quantity = this.equipmentQuantity;
    }
    this.equipmentType = null;
    this.equipmentQuantity = null;
  }

  onDeleteEquipmentClicked(index: number): void {
    this.schedule.equipment.splice(index, 1);
  }

  onCancelClicked(): void {
    this.cancel.emit();
  }

  onSaveClicked(): void {
    this.save.emit();
  }

  onPushClicked(): void {
    this.pushToFtdi.emit();
  }

  getEquipmentOpts(): FtdiEquipmentType[] {
    if (this.schedule.ftdiOrderTypeId && this.orderTypes) {
      const orderType = this.orderTypes.find(ot => ot.id == this.schedule.ftdiOrderTypeId);
      if (orderType) {
        return orderType.equipmentTypes;
      }
    }
    return [];
  }

  getCustomFields(): FtdiCustomField[] {
    if (this.schedule.ftdiOrderTypeId && this.orderTypes) {
      const orderType = this.orderTypes.find(ot => ot.id == this.schedule.ftdiOrderTypeId);
      if (orderType) {
        return orderType.customFields;
      }
    }
    return [];
  }

  getCustomFieldValue(customField: FtdiCustomField): string {
    let cf = this.schedule.customFields.find(cf => cf.fieldName == customField.fieldName);
    if (cf) {
      return cf.fieldValue;
    }
    return '';
  }

  getOrderTypeTitleText(): string {
    if (this.schedule.ftdiOrderTypeId && this.orderTypes) {
      let orderType = this.orderTypes.find(ot => ot.id == this.schedule.ftdiOrderTypeId);
      if (orderType) {
        return orderType.orderType;
      }
    }
    return '';
  }

  requestedDateIsFuture(): boolean {
    return !!this.schedule.id && new Date(this.schedule.requestedDate!) > new Date();
  }

  //support for "If Hard ETA needed, please specify ETA time" field
  hour: string | null = null;
  minute: string | null = null;
  ampm: string | null = null;

  hourOpts = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'];
  minOpts = ['00', '15', '30', '45'];
  amPmOpts = ['AM', 'PM'];

  onTimeChange(event: any, dropdownName: string, customField: FtdiCustomField) {
    console.log(event, dropdownName, customField)
    //@ts-ignore
    this[dropdownName] = event;
    if (this.hour) {
      if (!this.minute) {
        this.minute = this.minOpts[0];
      }
      if (!this.ampm) {
        this.ampm = this.amPmOpts[0];
      }
      let value = this.hour + ':' + this.minute + ' ' + this.ampm;
      this.onCustomFieldInput({ target: { value: value } }, customField);
    }
  }
}
