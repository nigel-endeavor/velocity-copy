import { Injectable } from '@angular/core';
import { NgForm } from '@angular/forms';
import { BroadbandService } from '../models/broadband-service.model';
import { DiaService } from '../models/dia-service.model';
import { Order } from '../models/order.model';
import { Service } from '../models/service.model';
import { UcaasService } from '../models/ucaas-service.model';
import { ServiceType } from '../models/constants/service-type';
import { GService } from '../models/g-service.model';
import { plainToClass } from 'class-transformer';
import { Store } from '@ngrx/store';
import { toggleEdit } from './ngrx/order-details.actions';
import { CrossConnectService } from '../models/cross-connect-service.model';
import { EthernetService } from '../models/ethernet-service.model';
import { AbstractModelService } from '../services/abstract-model.service';
import { BroadbandServiceService } from '../services/broadband-service.service';
import { CrossConnectServiceService } from '../services/cross-connect-service.service';
import { DiaServiceService } from '../services/dia-service.service';
import { EthernetServiceService } from '../services/ethernet-service.service';
import { GServiceService } from '../services/g-service.service';
import { UcaasServiceService } from '../services/ucaas-service.service';
import { TelevisionService } from '../models/television-service.model';
import { TelevisionServiceService } from '../services/television-service.service';
import { MplsService } from '../models/mpls-service.model';
import { MplsServiceService } from '../services/mpls-service.service';
import { ThreatMDRService } from '../models/threatMdr-service.model';
import { ThreatMDRServiceService } from '../services/threatMDR-service.service';
import { RansomMDRService } from '../models/ransomMdr-service.model';
import { RansomMDRServiceService } from '../services/ransomMDR-service.service';
import { RiskMDRServiceService } from '../services/riskMDR-service.service';
import { RiskMDRService } from '../models/riskMdr-service.model';
import { EngineeringIAMServiceService } from '../services/engineering-IAM-service.service';
import { EngineeringIAMService } from '../models/engineering-IAM-service.model';
import { EngineeringMDMServiceService } from "../services/engineering-MDM-service.service";
import { EngineeringMDMService } from "../models/engineering-MDM-service.model";
import { EngineeringEndpointProtectionServiceService } from "../services/engineering-endpoint-protection-service.service";
import { EngineeringEndpointProtectionService } from "../models/engineering-endpoint-service.model";
import { EngineeringInfoProtectionService } from "../models/engineering-Info-protection-service.model";
import { EngineeringEmailMessagingServiceService } from '../services/engineering-email-messaging-service.service';
import { EngineeringEmailMessagingService } from '../models/engineering-Email-messaging-service.model';
import { Cyber360MXDRService } from '../models/cyber360MXDR-service.model';
import { Cyber360MXDRServiceService } from '../services/cyber360MXDR-service.service';
import { MicrosoftLicensesService } from '../services/microsoft-licenses.service';
import { MicrosoftLicenses } from '../models/microsoft-licenses.model';
import { EngineeringInfoProtectionServiceService } from '../services/engineering-info-protection-service.service';

@Injectable({
  providedIn: 'root'
})
export class OrderEditService {

  public forms: Map<NgForm, {}> = new Map();

  public order: Order | null = null;

  public locationTabMemory: Map<number, string> = new Map();
  public serviceTabMemory: Map<number, string> = new Map();

  public isReadOnly: boolean = false;
  public isEditing: boolean = false;

  constructor(
    private store: Store,
    private diaServiceService: DiaServiceService,
    private broadbandServiceService: BroadbandServiceService,
    private ucaasServiceService: UcaasServiceService,
    private crossConnectServiceService: CrossConnectServiceService,
    private ethernetServiceService: EthernetServiceService,
    private gServiceService: GServiceService,
    private televisionServiceService: TelevisionServiceService,
    private mplsServiceService: MplsServiceService,
    private threatMDRServiceService: ThreatMDRServiceService,
    private ransomMDRServiceService: RansomMDRServiceService,
    private riskMDRServiceService: RiskMDRServiceService,
    private engineeringIAMServiceService: EngineeringIAMServiceService,
    private engineeringServiceService: EngineeringMDMServiceService,
    private engineeringEndpointProtectionServiceService: EngineeringEndpointProtectionServiceService,
    private engineeringEMSServiceService: EngineeringEmailMessagingServiceService,
    private cyber360MXDRServiceService: Cyber360MXDRServiceService,
    private engineeringInfoProtectionServiceService: EngineeringInfoProtectionServiceService,
    private microsoftLicensesService: MicrosoftLicensesService
  ) { }

  toggleEditFlag(editFlag: string): void {
    if (this.isReadOnly) {
      return;
    }
    if (this.isEditing && this.hasDirtyForm()) {
      const reset = confirm('You have unsaved changes. Revert these changes and proceed?');
      if (reset) {
        this.resetForms();
      } else {
        return;
      }
    }
    this.store.dispatch(toggleEdit({ key: editFlag }))
  }

  resetForms(): void {
    for (const [form, initialState] of this.forms.entries()) {
      form.resetForm(initialState);
    }
  }

  hasDirtyForm(): boolean {
    for (const form of this.forms.keys()) {
      if (form.dirty) {
        return true;
      }
    }
    return false;
  }

  addForm(form: NgForm): void {
    //captures the initial state of the form, in case we need to roll back
    let initialState: any = {};
    for (const fc in form.form.controls) {
      initialState[fc] = form.controls[fc].value;
    }
    this.forms.set(form, initialState);
  }

  removeForm(form: NgForm): void {
    this.forms.delete(form);
  }

  setOrder(order: Order): void {
    const deepOrder = JSON.parse(JSON.stringify(order));
    this.order = plainToClass(Order, deepOrder);
  }

  createService(locationId: number, serviceType: string, orderId: number): Service {
    // let thing = new ServiceBrokerage();
    let newService;
    switch (serviceType) {
      case ServiceType.BROADBAND:
        newService = new BroadbandService();
        break;
      case ServiceType.DIA:
        newService = new DiaService();
        break;
      case ServiceType.UCAAS:
        newService = new UcaasService();
        break;
      case ServiceType.G:
        newService = new GService();
        break;
      case ServiceType.CROSSCONNECT:
        newService = new CrossConnectService();
        break;
      case ServiceType.ETHERNET:
        newService = new EthernetService();
        break;
      case ServiceType.TELEVISION:
        newService = new TelevisionService();
        break;
      case ServiceType.MPLS:
        newService = new MplsService();
        break;
      case ServiceType.THREATMDR:
        newService = new ThreatMDRService();
        break;
      case ServiceType.RANSOMMDR:
        newService = new RansomMDRService();
        break;
      case ServiceType.RISKMDR:
        newService = new RiskMDRService();
        break;
      case ServiceType.ENGINEERING_IAM:
        newService = new EngineeringIAMService();
        break;
      case ServiceType.ENGINEERING_MDM:
        newService = new EngineeringMDMService();
        break;
      case ServiceType.ENGINEERING_ENDPOINT:
        newService = new EngineeringEndpointProtectionService();
        break;
      case ServiceType.ENGINEERING_INFO_PROTECTION:
        newService = new EngineeringInfoProtectionService();
        break;
      case ServiceType.ENGINEERING_EMAIL_MESSAGING:
        newService = new EngineeringEmailMessagingService();
        break;
      case ServiceType.CYBER360MXDR:
        newService = new Cyber360MXDRService();
        break;
      case ServiceType.MICROSOFTLICENSES:
        newService = new MicrosoftLicenses();
        break;
      default:
        console.log('Error: ' + serviceType + ' is not a supported Service type');
        newService = new Service();
    }
    newService.type = serviceType;
    newService.locationId = locationId;
    newService.orderId = orderId;
    newService.status = 'Pending Assignment';
    return newService;
  }

  getServiceForServiceType(serviceType: string): AbstractModelService<Service> | null {
    switch (serviceType) {
      case ServiceType.BROADBAND:
        return this.broadbandServiceService;
      case ServiceType.DIA:
        return this.diaServiceService;
      case ServiceType.UCAAS:
        return this.ucaasServiceService;
      case ServiceType.G:
        return this.gServiceService;
      case ServiceType.CROSSCONNECT:
        return this.crossConnectServiceService;
      case ServiceType.ETHERNET:
        return this.ethernetServiceService;
      case ServiceType.TELEVISION:
        return this.televisionServiceService;
      case ServiceType.MPLS:
        return this.mplsServiceService;
      case ServiceType.THREATMDR:
        return this.threatMDRServiceService;
      case ServiceType.RANSOMMDR:
        return this.ransomMDRServiceService;
      case ServiceType.RISKMDR:
        return this.riskMDRServiceService;
      case ServiceType.ENGINEERING_IAM:
        return this.engineeringIAMServiceService;
      case ServiceType.ENGINEERING_MDM:
        return this.engineeringServiceService;
      case ServiceType.ENGINEERING_ENDPOINT:
        return this.engineeringEndpointProtectionServiceService;
      case ServiceType.ENGINEERING_INFO_PROTECTION:
        return this.engineeringInfoProtectionServiceService
      case ServiceType.ENGINEERING_EMAIL_MESSAGING:
        return this.engineeringEMSServiceService;
      case ServiceType.CYBER360MXDR:
        return this.cyber360MXDRServiceService;
      case ServiceType.MICROSOFTLICENSES:
        return this.microsoftLicensesService;

      default:
        console.log('Error: ' + serviceType + ' is not a supported Service type');
        return null;
    }
  }
}
