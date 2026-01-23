import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { plainToClass } from 'class-transformer';
import { BroadbandService } from '../../../models/broadband-service.model';
import { DiaService } from '../../../models/dia-service.model';
import { Location } from '../../../models/location.model';
import { Service } from '../../../models/service.model';
import { LookupValueService } from 'src/app/services/lookup-value.service';
import { OrderEditService } from '../../order-edit.service';
import { UcaasService } from '../../../models/ucaas-service.model';
import { ServiceType } from '../../../models/constants/service-type';
import { GService } from '../../../models/g-service.model';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, combineLatest, filter, take, tap } from 'rxjs';
import { select, Store } from '@ngrx/store';
import {
  editEnabled,
  getFlagByName,
  getInventoryLocation,
  getIsInventory,
  getIsLoading,
  getIsReadOnly,
  getRelatedMacds,
  getSelectedIsInTerminalStatus,
  getSelectedLocationOrService
} from '../../ngrx/order-details.selectors';
import { loadUserStatuses, saveService, loadInventoryLocation, loadRelatedMacds, clearRelatedMacds } from '../../ngrx/order-details.actions';
import { SecurityUtilService } from 'src/app/services/security-util.service';
import { CANADIAN_PROVINCES, addressToString, getBaseUrl } from 'src/app/utilities';
import { CrossConnectService } from '../../../models/cross-connect-service.model';
import { EthernetService } from 'src/app/models/ethernet-service.model';
import { TelevisionService } from 'src/app/models/television-service.model';
import { Address } from 'src/app/models/address.model';
import { LookupValue } from 'src/app/models/lookup-value.model';
import { MplsService } from 'src/app/models/mpls-service.model';
import { NgForm } from '@angular/forms';
import { equipmentTableSelectors, equipmentTableActions } from './configs/table.config';
import { getCardViewSelected, getColumns, getFilters, getSelectedServiceEquipment } from './ngrx/service-equipment.selectors';
import { reorderColumns, saveServiceEquipment, setSelectedServiceEquipment, updateFilters, updateSort } from './ngrx/service-equipment.actions';
import { CommonColumn } from '../../../interfaces/columns.interface';
import { ServiceEquipment } from '../../../models/service-equipment-model';
import { EquipmentComponent } from './equipment.component';
import { ComponentType } from '@angular/cdk/portal';
import { MatDialog } from '@angular/material/dialog';
import { ThreatMDRService } from "../../../models/threatMdr-service.model";
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
  selector: 'app-service-equipment',
  templateUrl: './service-equipment.component.html',
  styleUrls: ['../../form-styles.scss', './service-equipment.component.scss']
})
export class ServiceEquipmentComponent implements OnInit, OnDestroy {
  public editEnabled$ = this.store.pipe(select(editEnabled));
  public isReadOnly$ = this.store.pipe(select(getIsReadOnly))
  public terminalstatus$ = this.store.pipe(select(getSelectedIsInTerminalStatus));
  public isLoading$ = this.store.pipe(select(getIsLoading));
  public selectedLocationOrService$ = this.store.pipe(select(getSelectedLocationOrService));
  public inventoryLocation$: Observable<any>;
  public relatedMacds$: Observable<any>;

  //table
  public tableData$: Observable<any[]> = this.store.pipe(select(equipmentTableSelectors.getTableData));
  public tableTotal$: Observable<number> = this.store.pipe(select(equipmentTableSelectors.getTableTotal));
  public columns$ = this.store.pipe(select(getColumns));
  public searchCriteria$: Observable<any> = this.store.pipe(select(getFilters), filter(item => !!item));
  public cardViewOn$ = this.store.pipe(select(getCardViewSelected));
  public selectedServiceEquipment$ = this.store.pipe(select(getSelectedServiceEquipment), tap(equipment => {
    if (equipment) {
      this.selectedServiceEquipment = plainToClass(ServiceEquipment, equipment);
    } else {
      this.selectedServiceEquipment = null;
    }
  }));
  public selectedServiceEquipment: ServiceEquipment | null = null;

  public shipToServiceAddressTooltip = 'Autopopulates the service address as the ship to location.';
  public shipToMasterCustomerAddressTooltip = 'Autopopulates the Master Customer address as the ship to location.';
  public otherTooltip = 'Freehand enter the shipping address.';
  public equipmentTypeTooltip = 'Category of equipment/device.';
  public subEquipmentTypeTooltip = 'Sub category of equipment/device, based on the parent equipment type.';
  public makeTooltip = 'Brand or common name of the equipment/device.';
  public modelTooltip = 'Model Number of equipment/device.';
  public serialNumberTooltip = 'Serial # specific to this piece of equipment/device.';
  public macAddressTooltip = 'Primary MAC address of the device or primary port you wish to identify.';
  public ownershipTooltip = 'Entity financially resposible for the equipment';
  public decommissionTooltip = 'Checking this box marks the equipment a inactive with the current date.';
  public decommissionCardTooltip = 'The date the equipment was decommissioned.';

  service: any;
  location: Location;
  companyId: number;
  public isReadOnly: boolean;
  public isInventory: boolean;
  public isMacd: boolean;
  public isDisconnect: boolean;
  public isOrderTypeNew: boolean;
  showForm: boolean = false;
  public originalCSID: any;

  @ViewChild('serviceForm') serviceForm: NgForm;

  canadianProvinces = CANADIAN_PROVINCES;
  countryOpts: string[];
  stateOpts: LookupValue[];

  public editingShipping = false;
  showAddressDialog = false;

  constructor(
    public oes: OrderEditService,
    private lookupValueService: LookupValueService,
    private store: Store,
    public securityUtils: SecurityUtilService,
    private equipmentDialog: MatDialog
  ) {
    this.store.pipe(select(getFlagByName('editingShipping'))).subscribe(res => {
      this.editingShipping = res;
    });
    this.store.pipe(select(getIsInventory)).subscribe(res => {
      this.isInventory = res;
    });
    this.store.pipe(select(getSelectedLocationOrService)).subscribe(res => {
      if (res instanceof Service) {
        this.isMacd = res.orderType ? ['Move', 'Add', 'Change', 'Disconnect'].some(type => res.orderType.includes(type)) : false;
        this.isDisconnect = res.orderType === 'Disconnect';
        this.isOrderTypeNew = res.orderType === 'New';
      }
    });
  }

  ngOnInit(): void {
    setTimeout(() => {
      // This is a hack to prevent the screen from 'flickering' when general tab is navigated to
      this.showForm = true;
    }, 0);
    this.store.dispatch(loadUserStatuses());
    this.companyId = this.oes.order!.company.id;
    this.isReadOnly$.subscribe();
    this.lookupValueService.find('STATE_PROVINCE', this.companyId).subscribe((values: LookupValue[]) => { this.stateOpts = values });
    this.lookupValueService.getValues('COUNTRY', this.companyId).subscribe((values: string[]) => { this.countryOpts = values });

    // from service general, we only care about the service
    // if the service changes, we need to update the selected service and update supporting data (location, inventory, macds, etc.)
    this.selectedLocationOrService$
      .pipe(filter(service => service !== null && service instanceof Service && (this.service == null || service.id != this.service.id || service.version != this.service.version)))
      .subscribe((service: Service | Location | null) => {
        if (service instanceof Service) {
          this.setService(service);
          this.originalCSID = service.clientServiceId;
          if (service.clientServiceId == undefined) {
            this.isOrderTypeNew = true;
          } else {
            this.isOrderTypeNew = false;
          }

          this.location = this.oes.order!.locations.find(l => l.id === service.locationId)!;
          if (!this.isInventory) {
            this.store.dispatch(loadInventoryLocation({ serviceId: service.id }));
          } else if (this.isInventory && service.billable && service.active) {
            this.store.dispatch(loadRelatedMacds({ serviceId: service.id }));
          } else if (this.isInventory && (!service.billable || !service.active)) {
            // billable and active clauses above and clearRelatedMacds method below exist to reduce unnecessary API calls
            this.store.dispatch(clearRelatedMacds());
          }

          this.store.dispatch(updateFilters({ key: 'serviceId', value: service.id }));
        }
      });

    if (this.isInventory) {
      this.relatedMacds$ = this.store.pipe(select(getRelatedMacds));
    } else {
      this.inventoryLocation$ = this.store.pipe(select(getInventoryLocation));
    }
  }

  setService(service: Service): void {
    switch (service.type) {
      case ServiceType.DIA:
        this.service = plainToClass(DiaService, service);
        break;
      case ServiceType.BROADBAND:
        this.service = plainToClass(BroadbandService, service);
        break;
      case ServiceType.UCAAS:
        this.service = plainToClass(UcaasService, service);
        break;
      case ServiceType.G:
        this.service = plainToClass(GService, service);
        break;
      case ServiceType.CROSSCONNECT:
        this.service = plainToClass(CrossConnectService, service);
        break;
      case ServiceType.ETHERNET:
        this.service = plainToClass(EthernetService, service);
        break;
      case ServiceType.TELEVISION:
        this.service = plainToClass(TelevisionService, service);
        break;
      case ServiceType.MPLS:
        this.service = plainToClass(MplsService, service);
        break;
      case ServiceType.THREATMDR:
        this.service = plainToClass(ThreatMDRService, service);
        break;
      case ServiceType.RANSOMMDR:
        this.service = plainToClass(RansomMDRService, service);
        break;
      case ServiceType.RISKMDR:
        this.service = plainToClass(RiskMDRService, service);
        break;
      case ServiceType.ENGINEERING_IAM:
        this.service = plainToClass(EngineeringIAMService, service);
        break;
      case ServiceType.ENGINEERING_MDM:
        this.service = plainToClass(EngineeringMDMService, service);
        break;
      case ServiceType.ENGINEERING_ENDPOINT:
        this.service = plainToClass(EngineeringEndpointProtectionService, service);
        break;
      case ServiceType.ENGINEERING_INFO_PROTECTION:
        this.service = plainToClass(EngineeringInfoProtectionService, service);
        break;
      case ServiceType.ENGINEERING_EMAIL_MESSAGING:
        this.service = plainToClass(EngineeringEmailMessagingService, service);
        break;
      case ServiceType.CYBER360MXDR:
        this.service = plainToClass(Cyber360MXDRService, service);
        break;
      case ServiceType.MICROSOFTLICENSES:
        this.service = plainToClass(MicrosoftLicenses, service);
        break;
    }
    if (this.service.billingAddress) {
      this.service.billingAddress = plainToClass(Address, this.service.billingAddress);
    }
    setTimeout(() => {
      if (this.serviceForm) {
        this.oes.addForm(this.serviceForm);
      }
    }, 1);
  }

  ngOnDestroy(): void {
    this.oes.removeForm(this.serviceForm);
  }

  onSaveClicked(): void {
    if (!this.serviceForm.valid) {
      this.service.clientServiceId = this.originalCSID;
      throw Error('Validation Error: Please correct the highlighted fields before saving')
    }
    this.isOrderTypeNew = !this.isOrderTypeNew
    combineLatest([this.terminalstatus$, this.editEnabled$]).subscribe(([terminalstatus, editEnabled]) => {
      if (terminalstatus && !editEnabled) {
        throw new Error('Service is in terminal status and cannot be updated');
      }

      if (this.isInventory) {
      } else {
        this.store.dispatch(saveService({ service: this.service }));
      }
    });
  }

  onOrderServiceClicked(service: Service): void {
    const url = `${getBaseUrl()}order/${service.orderId}/location/${service.locationId}/service/${service.id}`;
    window.open(url);
  }

  onAddressClicked(): void {
    if (!this.editingShipping) {
      return;
    }
    this.showAddressDialog = true;
  }

  onAddressSelected(address: Address): void {
    this.service.shippingAddress1 = address.address1;
    this.service.shippingAddress2 = address.address2;
    this.service.shippingCity = address.city;
    this.service.shippingState = address.state;
    this.service.shippingPostalCode = address.postalCode;
    this.service.shippingCountry = address.country;
    this.showAddressDialog = false;
  }

  onAddressSelectCancelled(): void {
    this.showAddressDialog = false;
  }

  onStateChanged(value: string): void {
    if (this.canadianProvinces.includes(value)) {
      this.service.shippingCountry = 'Canada';
    } else {
      this.service.shippingCountry = 'US';
    }
    this.service.shippingCtate = value;
  }

  shippingAddressToString(): string {
    return addressToString(this.service.shippingAddress1, this.service.shippingAddress2, this.service.shippingCity, this.service.shippingState, this.service.shippingPostalCode, this.service.shippingCountry);
  }

  serviceAddressToString(): string {
    return addressToString(this.service.address1, this.service.address2, this.service.city, this.service.state, this.service.postalCode, this.service.country);
  }

  locationAddressToString(): string {
    return addressToString(this.location.address1, this.location.address2, this.location.city, this.location.state, this.location.postalCode, this.location.country);
  }

  masterCustomerAddressToString(): string {
    let masterCompany = this.oes.order?.company.parentCompany;
    if (!masterCompany) {
      return '';
    } else {
      return addressToString(masterCompany.address1, masterCompany.address2, masterCompany.city, masterCompany.state, masterCompany.postalCode, masterCompany.country);
    }
  }

  onLimitChanged(value: number): void {
    this.onFilterChanged(value, 'limit');
    this.onFilterChanged(0, 'offset');
  }

  onFilterChanged(value: string | boolean | number, key: string): void {
    this.store.dispatch(updateFilters({ key, value }));
  }

  reorderColumns(event: { columns: CommonColumn[] }): void {
    this.store.dispatch(reorderColumns({ columns: event.columns }));
  }

  onChangeSortClicked(event: any) {
    this.store.dispatch(updateSort({ sort: event }));
  }

  onAddEquipmentClicked(): void {
    let newServiceEquipment = new ServiceEquipment();
    newServiceEquipment.serviceId = this.service.id;
    newServiceEquipment.decommission = false;
    this.onSelectedServiceEquipmentChanged(newServiceEquipment );

  }

  onSelectedServiceEquipmentChanged(equipment: ServiceEquipment): void {
    this.editEnabled$.pipe(take(1)).subscribe(editEnabled => {
      if (!editEnabled) {
        return;
      }
      this.store.dispatch(setSelectedServiceEquipment({ serviceEquipment: equipment }));
      const component: ComponentType<EquipmentComponent> = EquipmentComponent;
      const dialogRef = this.equipmentDialog.open(component, {
        data: { equipment },
        maxWidth: '1000px',
        panelClass: 'modal-dialog'
      });

      dialogRef.afterClosed().subscribe(result => {
        // Handle the result when the dialog closes
      });
    });
  }

}
