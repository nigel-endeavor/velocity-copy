import { Component, OnInit } from '@angular/core';
import { Service } from '../../../models/service.model';
import { ServiceType } from '../../../models/constants/service-type';
import { OrderEditService } from '../../order-edit.service';
import { select, Store } from '@ngrx/store';
import { saveService } from '../../ngrx/order-details.actions';
import { getIsLoading, getSelectedLocationOrService } from '../../ngrx/order-details.selectors';
import { map } from 'rxjs';
import { DiaService } from '../../../models/dia-service.model';
import { BroadbandService } from '../../../models/broadband-service.model';
import { UcaasService } from '../../../models/ucaas-service.model';
import { GService } from '../../../models/g-service.model';
import { filter } from 'rxjs/operators';
import { CrossConnectService } from '../../../models/cross-connect-service.model';
import { EthernetService } from 'src/app/models/ethernet-service.model';
import { TelevisionService } from 'src/app/models/television-service.model';
import { MplsService } from 'src/app/models/mpls-service.model';
import { ThreatMDRService } from 'src/app/models/threatMdr-service.model';
import { RansomMDRService } from 'src/app/models/ransomMdr-service.model';
import { RiskMDRService } from 'src/app/models/riskMdr-service.model';
import { EngineeringIAMService } from 'src/app/models/engineering-IAM-service.model';
import { EngineeringMDMService } from "../../../models/engineering-MDM-service.model";
import { EngineeringEndpointProtectionService } from "../../../models/engineering-endpoint-service.model";
import { EngineeringInfoProtectionService } from "../../../models/engineering-Info-protection-service.model";
import { EngineeringEmailMessagingService } from 'src/app/models/engineering-Email-messaging-service.model';
import { Cyber360MXDRService } from 'src/app/models/cyber360MXDR-service.model';
import { MicrosoftLicenses } from 'src/app/models/microsoft-licenses.model';

@Component({
  selector: 'app-service-technical',
  templateUrl: './service-technical.component.html',
  styleUrls: ['./service-technical.component.scss']
})
export class ServiceTechnicalComponent implements OnInit {
  public isLoading$ = this.store.pipe(select(getIsLoading));
  public selectedLocationOrService$ = this.store.pipe(
    select(getSelectedLocationOrService),
    filter(item => !!item),
    map(item => JSON.parse(JSON.stringify(item)))
  );

  companyId: number;
  showForm: boolean = false;

  readonly ServiceType = ServiceType;

  constructor(
    public oes: OrderEditService,
    private store: Store
  ) { }

  ngOnInit(): void {
    setTimeout(() => {
      // This is a hack to prevent the screen from 'flickering' when technical tab is navigated to
      this.showForm = true;
    }, 0);
    this.companyId = this.oes.order!.company.id;
  }

  saveService(service: Service) {
    this.store.dispatch(saveService({ service}));
  }

  getSelectedService(service: Service): any {
    switch (service.type) {
      case ServiceType.DIA:
        return service as DiaService;
      case ServiceType.BROADBAND:
        return service as BroadbandService;
      case ServiceType.UCAAS:
        return service as UcaasService;
      case ServiceType.G:
        return service as GService;
      case ServiceType.CROSSCONNECT:
        return service as CrossConnectService;
      case ServiceType.ETHERNET:
        return service as EthernetService;
      case ServiceType.TELEVISION:
        return service as TelevisionService;
      case ServiceType.MPLS:
        return service as MplsService;
      case ServiceType.THREATMDR:
        return service as ThreatMDRService;
      case ServiceType.RANSOMMDR:
        return service as RansomMDRService;
      case ServiceType.RISKMDR:
        return service as RiskMDRService;
      case ServiceType.ENGINEERING_IAM:
        return service as EngineeringIAMService;
      case ServiceType.ENGINEERING_MDM:
        return service as EngineeringMDMService;
      case ServiceType.ENGINEERING_ENDPOINT:
        return service as EngineeringEndpointProtectionService;
      case ServiceType.ENGINEERING_INFO_PROTECTION:
        return service as EngineeringInfoProtectionService;
      case ServiceType.ENGINEERING_EMAIL_MESSAGING:
        return service as EngineeringEmailMessagingService;
      case ServiceType.CYBER360MXDR:
        return service as Cyber360MXDRService;
      case ServiceType.MICROSOFTLICENSES:
        return service as MicrosoftLicenses;
      default:
        console.error('selectedService type is not supported. type: ' + service.type);
    }
  }
}
