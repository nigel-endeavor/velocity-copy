import { Component, EventEmitter, Input, OnDestroy, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { select, Store } from '@ngrx/store';
import { plainToClass } from 'class-transformer';
import { EthernetService } from 'src/app/models/ethernet-service.model';
import { Service } from 'src/app/models/service.model';
import { toggleEdit } from 'src/app/order-detail-page/ngrx/order-details.actions';
import { editEnabled, getFlagByName, getIsInventory, getIsLoading, getIsReadOnly } from 'src/app/order-detail-page/ngrx/order-details.selectors';
import { OrderEditService } from 'src/app/order-detail-page/order-edit.service';
import { LookupValueService } from 'src/app/services/lookup-value.service';
import { ServiceTechnicalTooltipsService} from "../service-technical-tooltips.service";

@Component({
  selector: 'app-service-technical-ethernet',
  templateUrl: './service-technical-ethernet.component.html',
  styleUrls: ['../../../form-styles.scss']
})
export class ServiceTechnicalEthernetComponent implements OnInit, OnDestroy {
  public editEnabled$ = this.store.pipe(select(editEnabled));
  public isLoading$ = this.store.pipe(select(getIsLoading));
  public isReadOnly$ = this.store.pipe(select(getIsReadOnly));
  public isInventory$ = this.store.pipe(select(getIsInventory));

  public editingServiceProvider = false;
  public editingServiceCircuit = false;
  public editingServiceNetwork = false;
  public editingServicePrioritization = false;
  @Input() service: EthernetService;
  @Input() companyId: number;
  @Output() save = new EventEmitter<Service>();

  @ViewChild('serviceForm') serviceForm: NgForm;

  providerOpts: string[];
  productTypeOpts: string[];
  speedOpts: string[];
  mtuOpts: string[];
  muxOpts: string[];
  cableCategoryOpts: string[];
  yesNoOpts: string[] = ['Yes', 'No'];

  constructor(
    public oes: OrderEditService,
    private lookupValueService: LookupValueService,
    private store: Store,
    public tooltips: ServiceTechnicalTooltipsService
  ) {
    this.store.pipe(select(getFlagByName('editingServiceProvider'))).subscribe(res => {
      this.editingServiceProvider = res;
    });
    this.store.pipe(select(getFlagByName('editingServiceCircuit'))).subscribe(res => {
      this.editingServiceCircuit = res;
    });
    this.store.pipe(select(getFlagByName('editingServiceNetwork'))).subscribe(res => {
      this.editingServiceNetwork = res;
    });
    this.store.pipe(select(getFlagByName('editingServicePrioritization'))).subscribe(res => {
      this.editingServicePrioritization = res;
    });
  }

  ngOnInit(): void {
    this.isReadOnly$.subscribe();
    this.lookupValueService.getValues('PROVIDER', this.companyId).subscribe((values: string[]) => { this.providerOpts = values; });
    this.lookupValueService.getValues('ETHERNET_PRODUCT_TYPE', this.companyId).subscribe((values: string[]) => { this.productTypeOpts = values; });
    this.lookupValueService.getValues('SPEED', this.companyId).subscribe((values: string[]) => { this.speedOpts = values; });
    this.lookupValueService.getValues('MTU', this.companyId).subscribe((values: string[]) => { this.mtuOpts = values; });
    this.lookupValueService.getValues('MUX', this.companyId).subscribe((values: string[]) => { this.muxOpts = values; });
    this.lookupValueService.getValues('CABLE_CATEGORY', this.companyId).subscribe((values: string[]) => { this.cableCategoryOpts = values; });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['service']) {
      this.setService(this.service);
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
    this.save.emit(this.service);
  }

  onIpFormatChanged(value: string): void {
    this.service.ipFormat = value;
    if (this.editingServiceNetwork) {
      //toggling oes.editingServiceNetwork when IP format changes forces the IP address validator to reset
      this.store.dispatch(toggleEdit({ key: 'editingServiceNetwork' }));
      setTimeout(() => {
        this.store.dispatch(toggleEdit({ key: 'editingServiceNetwork' }));
      }, 1);
    }
  }

}
