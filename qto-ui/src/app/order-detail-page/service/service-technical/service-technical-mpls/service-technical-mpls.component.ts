import { Component, EventEmitter, Input, Output, SimpleChanges, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { select, Store } from '@ngrx/store';
import { MplsService } from 'src/app/models/mpls-service.model';
import { Service } from 'src/app/models/service.model';
import { editEnabled, getIsReadOnly, getIsInventory, getIsLoading, getFlagByName } from 'src/app/order-detail-page/ngrx/order-details.selectors';
import { OrderEditService } from 'src/app/order-detail-page/order-edit.service';
import { LookupValueService } from 'src/app/services/lookup-value.service';
import { CIDR_SUBNET_MAPPING } from '../service-technical-broadband/service-technical-broadband.component';
import { plainToClass } from 'class-transformer';
import { toggleEdit } from 'src/app/order-detail-page/ngrx/order-details.actions';
import { ServiceTechnicalTooltipsService} from "../service-technical-tooltips.service";

@Component({
  selector: 'app-service-technical-mpls',
  templateUrl: './service-technical-mpls.component.html',
  styleUrls: ['../../../form-styles.scss']
})
export class ServiceTechnicalMplsComponent {
  public editEnabled$ = this.store.pipe(select(editEnabled));
  public isReadOnly$ = this.store.pipe(select(getIsReadOnly))
  public isInventory$ = this.store.pipe(select(getIsInventory));
  public isLoading$ = this.store.pipe(select(getIsLoading));

  @Input() service: MplsService;
  @Input() companyId: number;
  @Output() save = new EventEmitter<Service>();

  @ViewChild('serviceForm') serviceForm: NgForm;

  providerOpts: string[];
  mplsTypeOpts: string[];
  constructionIntervalOpts: string[];
  speedOpts: string[];
  mediaTypeOpts: string[];
  interfaceConnector: string[];
  providerActivationMethod: string[];
  yesNoOpts: string[] = ['Yes', 'No'];
  installIntervalOpts: string[];
  ipBlockOpts: string[];

  subnetMapping: Map<string, string> = CIDR_SUBNET_MAPPING;

  public editingServiceProvider = false;
  public editingServiceCircuit = false;
  public editingServiceRouting = false;
  public editingServiceNetwork = false;
  public editingServicePrioritization = false;

  constructor(
    public oes: OrderEditService,
    private lookupValueService: LookupValueService,
    private store: Store,
    public tooltips: ServiceTechnicalTooltipsService
  ) { }

  ngOnInit(): void {
    this.isReadOnly$.subscribe();
    this.store.pipe(select(getFlagByName('editingServiceProvider'))).subscribe(res => {
      this.editingServiceProvider = res;
    });
    this.store.pipe(select(getFlagByName('editingServiceCircuit'))).subscribe(res => {
      this.editingServiceCircuit = res;
    });
    this.store.pipe(select(getFlagByName('editingServiceRouting'))).subscribe(res => {
      this.editingServiceRouting = res;
    });
    this.store.pipe(select(getFlagByName('editingServiceNetwork'))).subscribe(res => {
      this.editingServiceNetwork = res;
    });
    this.store.pipe(select(getFlagByName('editingServicePrioritization'))).subscribe(res => {
      this.editingServicePrioritization = res;
    });

    this.lookupValueService.getValues('PROVIDER', this.companyId).subscribe((values: string[]) => { this.providerOpts = values; });
    this.lookupValueService.getValues('MPLS_TYPE', this.companyId).subscribe((values: string[]) => { this.mplsTypeOpts = values; });
    this.lookupValueService.getValues('OSP_CONS_INTERVAL', this.companyId).subscribe((values: string[]) => { this.constructionIntervalOpts = values; });
    this.lookupValueService.getValues('SPEED', this.companyId).subscribe((values: string[]) => { this.speedOpts = values; });
    this.lookupValueService.getValues('MEDIA_TYPE', this.companyId).subscribe((values: string[]) => { this.mediaTypeOpts = values; });
    this.lookupValueService.getValues('INTERFACE_CONNECTOR', this.companyId).subscribe((values: string[]) => { this.interfaceConnector = values; });
    this.lookupValueService.getValues('PROVIDER_ACTIVATION_METHOD', this.companyId).subscribe((values: string[]) => { this.providerActivationMethod = values; });
    this.lookupValueService.getValues('PRODUCT_INSTALL_INTERVAL', this.companyId).subscribe((values: string[]) => { this.installIntervalOpts = values; });
    this.lookupValueService.getValues('ADDITIONAL_IP_BLOCK', this.companyId).subscribe((values: string[]) => { this.ipBlockOpts = values; });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['service']) {
      this.setService(this.service);
    }
  }

  setService(service: MplsService): void {
    this.service = plainToClass(MplsService, service);
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
