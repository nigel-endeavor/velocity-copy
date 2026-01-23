import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { plainToClass } from 'class-transformer';
import { LookupValueService } from '../../../../services/lookup-value.service';
import { CIDR_SUBNET_MAPPING } from '../service-technical-broadband/service-technical-broadband.component';
import { DiaService } from '../../../../models/dia-service.model';
import { Service } from '../../../../models/service.model';
import { OrderEditService } from '../../../order-edit.service';
import { select, Store } from '@ngrx/store';
import {editEnabled, getFlagByName, getIsInventory, getIsLoading, getIsReadOnly} from '../../../ngrx/order-details.selectors';
import { toggleEdit } from "../../../ngrx/order-details.actions";
import { ServiceTechnicalTooltipsService} from "../service-technical-tooltips.service";


@Component({
  selector: 'app-service-technical-dia',
  templateUrl: './service-technical-dia.component.html',
  styleUrls: ['../../../form-styles.scss']
})
export class ServiceTechnicalDiaComponent implements OnInit {
  public editEnabled$ = this.store.pipe(select(editEnabled));
  public isReadOnly$ = this.store.pipe(select(getIsReadOnly))
  public isInventory$ = this.store.pipe(select(getIsInventory));
  public isLoading$ = this.store.pipe(select(getIsLoading));

  @Input() service: DiaService;
  @Input() companyId: number;
  @Output() save = new EventEmitter<Service>();

  @ViewChild('serviceForm') serviceForm: NgForm;

  providerOpts: string[];
  constructionIntervalOpts: string[];
  speedOpts: string[];
  mediaTypeOpts: string[];
  netStatusOpts: string[];
  buildingStatusOpts: string[];
  yesNoOpts: string[] = ['Yes', 'No'];
  installIntervalOpts: string[];
  networkProtocolOpts: string[];
  ipBlockOpts: string[];
  interfaceConnector: string[];
  providerActivationMethod: string[];

  subnetMapping: Map<string, string> = CIDR_SUBNET_MAPPING;


  public editingServiceNetwork = false;
  public editingServiceEquipment = false;
  public editingServiceCircuit = false;
  public editingServiceProvider = false;
  public editingServicePrioritization = false;
  constructor(
    public oes: OrderEditService,
    private lookupValueService: LookupValueService,
    private store: Store,
    public tooltips: ServiceTechnicalTooltipsService
  ) { }


  ngOnInit(): void {
    this.isReadOnly$.subscribe();
    this.store.pipe(select(getFlagByName('editingServiceNetwork'))).subscribe(res => {
      this.editingServiceNetwork = res;
    });
    this.store.pipe(select(getFlagByName('editingServiceEquipment'))).subscribe(res => {
      this.editingServiceEquipment = res;
    });
    this.store.pipe(select(getFlagByName('editingServiceCircuit'))).subscribe(res => {
      this.editingServiceCircuit = res;
    });
    this.store.pipe(select(getFlagByName('editingServiceProvider'))).subscribe(res => {
      this.editingServiceProvider = res;
    });
    this.store.pipe(select(getFlagByName('editingServicePrioritization'))).subscribe(res => {
      this.editingServicePrioritization = res;
    });

    this.lookupValueService.getValues('PROVIDER', this.companyId).subscribe((values: string[]) => { this.providerOpts = values; });
    this.lookupValueService.getValues('OSP_CONS_INTERVAL', this.companyId).subscribe((values: string[]) => { this.constructionIntervalOpts = values; });
    this.lookupValueService.getValues('SPEED', this.companyId).subscribe((values: string[]) => { this.speedOpts = values; });
    this.lookupValueService.getValues('MEDIA_TYPE', this.companyId).subscribe((values: string[]) => { this.mediaTypeOpts = values; });
    this.lookupValueService.getValues('NET_STATUS', this.companyId).subscribe((values: string[]) => { this.netStatusOpts = values; });
    this.lookupValueService.getValues('BUILDING_STATUS', this.companyId).subscribe((values: string[]) => { this.buildingStatusOpts = values; });
    this.lookupValueService.getValues('PRODUCT_INSTALL_INTERVAL', this.companyId).subscribe((values: string[]) => { this.installIntervalOpts = values; });
    this.lookupValueService.getValues('NETWORK_PROTOCOL', this.companyId).subscribe((values: string[]) => { this.networkProtocolOpts = values; });
    this.lookupValueService.getValues('ADDITIONAL_IP_BLOCK', this.companyId).subscribe((values: string[]) => { this.ipBlockOpts = values; });
    this.lookupValueService.getValues('INTERFACE_CONNECTOR', this.companyId).subscribe((values: string[]) => { this.interfaceConnector = values; });
    this.lookupValueService.getValues('PROVIDER_ACTIVATION_METHOD', this.companyId).subscribe((values: string[]) => { this.providerActivationMethod = values; });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['service']) {
      this.setService(this.service);
    }
  }

  setService(service: DiaService): void {
    this.service = plainToClass(DiaService, service);
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

  onWanBlockChanged(value: string): void {
    this.service.additionalIpBlock = value;
    if (this.service.ipFormat == 'IPv4') {
      let subnet = this.subnetMapping.get(value);
      if (subnet) {
        this.service.wanSubnet = subnet;
      }
    }
    if (this.editingServiceNetwork) {
      //toggling oes.editingServiceNetwork when IP format changes forces the IP address validator to reset
      this.store.dispatch(toggleEdit({ key: 'editingServiceNetwork' }));
      setTimeout(() => {
        this.store.dispatch(toggleEdit({ key: 'editingServiceNetwork' }));
      }, 1);
    }
  }

  onLanBlockChanged(value: string): void {
    this.service.lanBlock = value;
    if (this.service.ipFormat == 'IPv4') {
      let subnet = this.subnetMapping.get(value);
      if (subnet) {
        this.service.lanSubnet = subnet;
      }
    }
    if (this.editingServiceNetwork) {
      //toggling oes.editingServiceNetwork when IP format changes forces the IP address validator to reset
      this.store.dispatch(toggleEdit({ key: 'editingServiceNetwork' }));
      setTimeout(() => {
        this.store.dispatch(toggleEdit({ key: 'editingServiceNetwork' }));
      }, 1);
    }
  }
}
