import { Component, EventEmitter, Input, OnDestroy, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { select, Store } from '@ngrx/store';
import { plainToClass } from 'class-transformer';
import { EthernetService } from 'src/app/models/ethernet-service.model';
import { LookupValue } from 'src/app/models/lookup-value.model';
import { Service } from 'src/app/models/service.model';
import { editEnabled, getIsLoading, getIsReadOnly, getFlagByName } from 'src/app/order-detail-page/ngrx/order-details.selectors';
import { OrderEditService } from 'src/app/order-detail-page/order-edit.service';
import { LookupValueService } from 'src/app/services/lookup-value.service';
import { CANADIAN_PROVINCES } from 'src/app/utilities';
import { Location } from 'src/app/models/location.model';
import { Address } from 'src/app/models/address.model';

@Component({
  selector: 'app-service-az-ethernet',
  templateUrl: './service-az-ethernet.component.html',
  styleUrls: ['../../../form-styles.scss', '../service-az-details.component.scss']
})
export class ServiceAzEthernetComponent implements OnInit, OnDestroy {

  public editEnabled$ = this.store.pipe(select(editEnabled));
  public isLoading$ = this.store.pipe(select(getIsLoading));
  public isReadOnly$ = this.store.pipe(select(getIsReadOnly));

  public editingALocation = false;
  public editingZLocation = false;
  @Input() service: EthernetService;
  @Input() location: Location;
  @Input() companyId: number;
  @Output() save = new EventEmitter<{service: Service, location: Location}>();

  @ViewChild('serviceForm') serviceForm: NgForm;

  countryOpts: string[];
  stateOpts: LookupValue[];
  buildingStatusOpts: string[];
  accessTypeOpts: string[];
  interfaceConnectorOpts: string[];
  mediaTypeOpts: string[];
  yesNoOpts: string[] = ['Yes', 'No'];
  handoffFiberModeOpts: string[];

  constructor(
    public oes: OrderEditService,
    private lookupValueService: LookupValueService,
    private store: Store
  ) {
    this.store.pipe(select(getFlagByName('editingALocation'))).subscribe(res => {
      this.editingALocation = res;
    });
    this.store.pipe(select(getFlagByName('editingZLocation'))).subscribe(res => {
      this.editingZLocation = res;
    });
  }

  ngOnInit(): void {
    this.setService(this.service);
    this.isReadOnly$.subscribe();
    this.lookupValueService.getValues('COUNTRY', this.companyId).subscribe((values: string[]) => { this.countryOpts = values });
    this.lookupValueService.find('STATE_PROVINCE', this.companyId).subscribe((values: LookupValue[]) => { this.stateOpts = values });
    this.lookupValueService.getValues('ACCESS_TYPE', this.companyId).subscribe((values: string[]) => { this.accessTypeOpts = values });
    this.lookupValueService.getValues('BUILDING_STATUS', this.companyId).subscribe((values: string[]) => { this.buildingStatusOpts = values; });
    this.lookupValueService.getValues('INTERFACE_CONNECTOR', this.companyId).subscribe((values: string[]) => { this.interfaceConnectorOpts = values; });
    this.lookupValueService.getValues('MEDIA_TYPE', this.companyId).subscribe((values: string[]) => { this.mediaTypeOpts = values; });
    this.lookupValueService.getValues('HANDOFF_FIBER_MODE', this.companyId).subscribe((values: string[]) => { this.handoffFiberModeOpts = values });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['service']) {
      this.setService(changes['service'].currentValue);
    }
  }

  setService(service: EthernetService): void {
    this.service = plainToClass(EthernetService, service);
    setTimeout(() => {
      this.oes.addForm(this.serviceForm);
    }, 1);
  }

  ngOnDestroy(): void {
    this.oes.removeForm(this.serviceForm);
  }

  onSaveClicked(): void {
    if (this.serviceForm.invalid) {
      throw new Error('Validation Error: Please correct the highlighted fields before saving');
    }
    this.save.emit({service: this.service, location: this.location});
  }

  onAStateChanged(value: string): void {
    if (CANADIAN_PROVINCES.includes(value)) {
      this.location.country = 'Canada';
    } else {
      this.location.country = 'US';
    }
    this.location.state = value;
  }

  onZStateChanged(value: string): void {
    if (CANADIAN_PROVINCES.includes(value)) {
      this.service.zCountry = 'Canada';
    } else {
      this.service.zCountry = 'US';
    }
    this.service.zState = value;
  }

}
