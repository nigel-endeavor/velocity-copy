import { Component, Inject, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
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
import { SurchargeDialogComponent } from '../../../components/surcharges/surcharge-dialog.component';
import { ComponentType } from '@angular/cdk/overlay';
import { MAT_LEGACY_DIALOG_DATA as MAT_DIALOG_DATA, MatLegacyDialog as MatDialog, MatLegacyDialogModule as MatDialogModule, MatLegacyDialogRef as MatDialogRef } from '@angular/material/legacy-dialog';
import { Observable, filter, take } from 'rxjs';
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
  getSelectedLocationOrService,
  getAutoCreateClientServiceId
} from '../../ngrx/order-details.selectors';
import {
  loadUserStatuses,
  saveService,
  loadInventoryLocation,
  loadRelatedMacds,
  clearRelatedMacds,
  reloadOrder,
  pullFromCrm, deleteService, deleteLocation, toggleEdit
} from '../../ngrx/order-details.actions';
import { ServiceHistoryComponent } from 'src/app/components/service-history/service-history.component';
import { CostHistoryComponent } from 'src/app/components/cost-history/cost-history.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { CommonModule } from '@angular/common';
import { SecurityUtilService } from 'src/app/services/security-util.service';
import { NewMacdComponent } from '../../../features/macds/new-macd/new-macd.component';
import { LinkServiceComponent } from 'src/app/components/link-service/link-service.component';
import { CANADIAN_PROVINCES, addressToString, getBaseUrl } from 'src/app/utilities';
import { CrossConnectService } from '../../../models/cross-connect-service.model';
import { EthernetService } from 'src/app/models/ethernet-service.model';
import { RelocateRecordComponent } from 'src/app/features/relocate-record/relocate-record.component';
import { TelevisionService } from 'src/app/models/television-service.model';
import { Address } from 'src/app/models/address.model';
import { LookupValue } from 'src/app/models/lookup-value.model';
import { MplsService } from 'src/app/models/mpls-service.model';
import { RelocateInventoryRecordComponent } from '../../../features/relocate-inventory-record/relocate-inventory-record.component';
import { get } from "lodash";
import { ThreatMDRService } from 'src/app/models/threatMdr-service.model';
import { RansomMDRService } from 'src/app/models/ransomMdr-service.model';
import { RiskMDRService } from 'src/app/models/riskMdr-service.model';
import { EngineeringIAMService } from 'src/app/models/engineering-IAM-service.model';
import { EngineeringMDMService } from "../../../models/engineering-MDM-service.model";
import { EngineeringEndpointProtectionService } from "../../../models/engineering-endpoint-service.model";
import { EngineeringInfoProtectionService } from "../../../models/engineering-Info-protection-service.model";
import { EngineeringEmailMessagingService } from 'src/app/models/engineering-Email-messaging-service.model';
import { Cyber360MXDRService } from 'src/app/models/cyber360MXDR-service.model';
import { TerminalServiceStatuses } from "../../../models/constants/terminal-service-statuses";
import { MicrosoftLicenses } from 'src/app/models/microsoft-licenses.model';

@Component({
  selector: 'app-service-general',
  templateUrl: './service-general.component.html',
  styleUrls: ['../../form-styles.scss']
})
export class ServiceGeneralComponent implements OnInit, OnDestroy {
  public editEnabled$ = this.store.pipe(select(editEnabled));
  public isReadOnly$ = this.store.pipe(select(getIsReadOnly))
  public terminalstatus$ = this.store.pipe(select(getSelectedIsInTerminalStatus));
  public isLoading$ = this.store.pipe(select(getIsLoading));
  public selectedLocationOrService$ = this.store.pipe(select(getSelectedLocationOrService));
  public costChanged = false;
  public inventoryLocation$: Observable<any>;
  public relatedMacds$: Observable<any>;

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

  contractTermOpts: string[];
  noticePeriodOpts: string[];
  providerOpts: string[];
  subProductTypeOpts: string[];
  filteredSubProductTypeOpts: string[];
  underlyingProviderOpts: string[];
  fieldServicesProviderOpts: string[];
  serviceBilledToOpts: string[];
  costChangeReasonOpts: string[];
  projectNameOpts: string[];
  canadianProvinces = CANADIAN_PROVINCES;
  countryOpts: string[];
  stateOpts: LookupValue[];
  serviceSubStatusOpts: string[];
  customerBillingInstructionsOpts: string[];

  public editingServiceBilling = false;
  public editingServiceInfo = false;
  public editingServiceBillingAddress = false;
  public editingServiceAssociations = false;
  public linked = false;
  public bundled = false;
  public autoCreateClientServiceId = false;
  showAddressDialog = false;
  public cyberClient: boolean = false;

  newMacdTitleText = 'Requires the service to be Active, Billable, and not have existing MACD orders open against it.';

  orderTypeTip = 'Identifies this order as a Move, Add, Change, Or Disconnect.';
  externalOrderReferenceTip = 'External reference number for this service.';
  subOrderTypeTip = 'Identifies the description type of a MACD.';
  clientServiceIdTip = 'Your Custom ID for this service record.';
  alternateIdTip = 'A custom alternate ID for the service record.';
  opportunityNumTip = 'Unique ID from a CRM platform'
  quoteIdTip = 'Unique Id from the Provider when the service was quoted in pre-order stage.';
  projectNameTip = 'Your project grouping that this service order belongs to.';
  serviceStatusTip = 'i90 Status that is determined by order progression of service milestones.';
  serviceSubStatusTip = 'Custom sub status you can set for the service.';
  serviceBilledToTip = 'Entity or group that you want the provider\'s service invoice to allocate to.';
  customerBillingInstructionsTip = 'Custom billing instructions for the provider.';
  providerTip = 'The organization which will be supplying this service and invoicing.';
  underlyingProviderTip = 'The service provider that the main provider sources underlying network, infrastructure, equipment, or professional services through.';
  fieldServicesProviderTip = 'Provier of on-site equipment installation, maintenance, and repair.'
  subProductTypeTip = 'Custom product or service identifier for your organization.';
  followUpDateTip = 'Custom date you can set for when you wish the next follow up or action to be taken.';
  serviceInfoTip = 'Custom grouping identifier for this service.';
  serviceTypeTip = 'Secondary Custom grouping identifier for this service.';
  sourceTip = 'Displays how this record was created ( Manual, Import, API, etc).';
  descriptionTip = 'Custom text field to provide a brief, high level statement of this service\'s role, special conditions, etc.';
  contractTermTip = 'Term length in Months of the service contract.';
  contractSignedDateTip = 'Date that the service term begins.';
  contractTermEndDateTip = 'Date the service term ends.';
  ignoreForRenewalsTip = 'If checked/true, this service will be ignored for future renewal reports & considerations.';
  poNumberTip = 'Purchase Order Number.';
  jobNumberTip = 'Job Number.';
  mrcTip = 'Monthly Recurring Cost.';
  nrcTip = 'Non-Recurring Cost.';
  mrrTip = 'Monthly Recurring Revenue.';
  nrrTip = 'Non-Recurring Revenue.';
  annualRecurringCostTip = 'Cost incurred on a once per year billing cycle.';
  icbTip = 'Indicates service has attributes of Individual Case Basis or non contract billing.';
  icbUnitCostTip = 'Cost associated to Individual Case Basis Component.';
  ospConstructionCostTip = 'Cost to be billed associated with Outside Plat Construction indicator.';
  linkedTip = 'Indicates this service is part of a billing bundle with other services at this location.';
  bundledTip = 'Indicates this service is related as a parent, or dependent, of another service.';
  surchargesTip = 'Enter surcharges for End Customer invoicing.';
  serviceHistoryTip = 'Views the full history of service changes that occurred in the platform.';
  deleteServiceTip = 'This will delete this service record.';
  linkInventoryTip = 'Associate this service to a different parent order record that previously exists in inventory.';
  cloneAndCancelTip = 'This action allows you to cancel this existing service order and open a new one.';
  relocateTip = 'Associate this service to a different parent order record.';
  linkServicesTip = 'Create a parent/child relationship for 2 or more services in this order.';
  bundleTip = 'Create a billing bundle from multiple services in this order.';
  managedServiceTip = 'Indicates if the service is actively being managed.';
  productionImpactingTip = 'Indicates if the addition/change of this service will impact in production services.';
  autoRenewalTip = 'Indicates if the service automatically renews.';
  coTerminusTip = 'Indicates if the service contract is co-terminus with the larger account.';
  renewalCancelNoticePeriodTip = 'Context on the renewal/cancel timeline to the provider.';
  contractInfoTip = 'Contract parameters for this service.';
  billToLocationTip = 'Check this box if you want to default sending the service invoice to the physical location of the order record.';
  billingAddressTip = 'Location you wish to have an individual service bill/invoice sent to, if the customer is not being billed on a master account.';
  billingEmailTip = 'Email address you wish to have an individual service bill/invoice  sent to, if the customer is not being billed on a master account.';

  constructor(
    public oes: OrderEditService,
    private lookupValueService: LookupValueService,
    private router: Router,
    private route: ActivatedRoute,
    private store: Store,
    public securityUtils: SecurityUtilService,
    public surchargesDialog: MatDialog,
    public serviceHistoryDialog: MatDialog,
    public costHistoryDialog: MatDialog,
    public newMacdDialog: MatDialog,
    public linkToParentDialog: MatDialog,
    public relocateRecordDialog: MatDialog,
    public relocateInventoryRecordDialog: MatDialog
  ) {
    this.store.pipe(select(getFlagByName('editingServiceBilling'))).subscribe(res => {
      this.editingServiceBilling = res;
    });
    this.store.pipe(select(getFlagByName('editingServiceInfo'))).subscribe(res => {
      this.editingServiceInfo = res;
    });
    this.store.pipe(select(getFlagByName('editingServiceAssociations'))).subscribe(res => {
      this.editingServiceAssociations = res;
    });
    this.store.pipe(select(getFlagByName('editingServiceBillingAddress'))).subscribe(res => {
      this.editingServiceBillingAddress = res;
    });
    this.store.pipe(select(getIsInventory)).subscribe(res => {
      this.isInventory = res;
    });
    this.store.pipe(select(getAutoCreateClientServiceId)).subscribe(res => {
      this.autoCreateClientServiceId = res;
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
    this.cyberClient = localStorage.getItem('CYBER_SECURITY_CLIENT') === 'true';
    setTimeout(() => {
      // This is a hack to prevent the screen from 'flickering' when general tab is navigated to
      this.showForm = true;
    }, 0);
    this.store.dispatch(loadUserStatuses());
    this.companyId = this.oes.order!.company.id;
    this.isReadOnly$.subscribe();
    this.lookupValueService.getValues('CONTRACT_TERM', this.companyId).subscribe((values: string[]) => { this.contractTermOpts = values; });
    this.lookupValueService.getValues('NOTICE_PERIOD', this.companyId).subscribe((values: string[]) => { this.noticePeriodOpts = values; });
    this.lookupValueService.getValues('PROVIDER', this.companyId).subscribe((values: string[]) => { this.providerOpts = values; });
    this.lookupValueService.getValues('SUB_PRODUCT_TYPE', this.companyId).subscribe((values: string[]) => { this.subProductTypeOpts = values; });
    this.lookupValueService.getValues('UNDERLYING_PROVIDER', this.companyId).subscribe((values: string[]) => { this.underlyingProviderOpts = values; });
    this.lookupValueService.getValues('FIELD_SERVICES_PROVIDER', this.companyId).subscribe((values: string[]) => { this.fieldServicesProviderOpts = values; });
    this.lookupValueService.getValues('SERVICE_BILLED_TO', this.companyId).subscribe((values: string[]) => { this.serviceBilledToOpts = values; });
    this.lookupValueService.getValues('COST_CHANGE_REASON', this.companyId).subscribe((values: string[]) => { this.costChangeReasonOpts = values; });
    this.lookupValueService.getValues('PROJECT_NAME', this.companyId).subscribe((values: string[]) => { this.projectNameOpts = values; });
    this.lookupValueService.find('STATE_PROVINCE', this.companyId).subscribe((values: LookupValue[]) => { this.stateOpts = values });
    this.lookupValueService.getValues('COUNTRY', this.companyId).subscribe((values: string[]) => { this.countryOpts = values });
    this.lookupValueService.getValues('SERVICE_SUB_STATUS', this.companyId).subscribe((values: string[]) => { this.serviceSubStatusOpts = values });
    this.lookupValueService.getValues('CUSTOMER_BILLING_INSTRUCTIONS', this.companyId).subscribe((values: string[]) => { this.customerBillingInstructionsOpts = values });

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
          this.linked = service.linked;
          this.bundled = service.bundled;
        }
      });

    if (this.isInventory) {
      this.relatedMacds$ = this.store.pipe(select(getRelatedMacds));
    } else {
      this.inventoryLocation$ = this.store.pipe(select(getInventoryLocation));
    }
  }

  setService(service: Service): void {
    this.lookupValueService.find('SUB_PRODUCT_TYPE', this.companyId)
    .subscribe(values => {
      this.subProductTypeOpts = values
        .filter((sp: any) => sp.parentId === service.typeId) // Filter by parentId
        .map((sp: any) => sp.display); // Extract display field
    });
    this.isLoading$.subscribe(isLoading => console.log('isLoading:', isLoading));
    if (this.editingServiceInfo) {
      this.store.dispatch(toggleEdit({ key: 'editingServiceInfo' }));
    }
    if (this.editingServiceBilling) {
      this.store.dispatch(toggleEdit({ key: 'editingServiceBilling' }));
    }
    if (this.editingServiceAssociations) {
      this.store.dispatch(toggleEdit({ key: 'editingServiceAssociations' }));
    }
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
    if (!this.service.clientServiceId == undefined || (this.service.clientServiceId == '' && this.isOrderTypeNew)) {
      // checks for client service id and resets to original  value if empty
      if (this.editingServiceInfo && !this.service.clientServiceId) {
        this.service.clientServiceId = this.originalCSID;
        throw Error('Validation Error: Client Service ID is required.')
      }

      // checks for client service ids that only contain whitespaces.
      if (this.service.clientServiceId) {
        let whitespace = /^\s*$/.test(this.service.clientServiceId);
        if (whitespace) {
          throw Error('Client Service ID cannot be empty or contain whitespaces.');
        }
      }
    }

    if (!this.serviceForm.valid) {
      this.service.clientServiceId = this.originalCSID;
      throw Error('Validation Error: Please correct the highlighted fields before saving')
    }
    this.isOrderTypeNew = !this.isOrderTypeNew
    console.log("after onSave clicked, new = ", this.isOrderTypeNew)
    this.editEnabled$.subscribe((editEnabled) => {
      let terminalstatus: any;
      terminalstatus = ["Service Cancelled", "Service Complete", "Disconnect Complete", "Change In Assignment"].includes(this.service.status);
      if (terminalstatus && !editEnabled) {
        throw new Error('Service is in terminal status and cannot be updated');
      }

      if (this.isInventory) {
        if (this.service.id == undefined) {
          this.service.currentInventory = true;
        }
        this.relatedMacds$.pipe(take(1)).subscribe(macds => {
          if (macds.length > 0) {
            const continueSave = confirm('This service has an Open MACD. Any Edits to the inventory record will be overwritten by the MACD once it completes. Do you wish to continue?');
            if (!continueSave) {
              return;
            }
          }

          if (this.costChanged) {
            const component: ComponentType<CostChangePromptDialog> = CostChangePromptDialog;
            const dialogRef = this.costHistoryDialog.open(component, {
              data: {
                changeReason: undefined,
                changeReasonOpts: this.costChangeReasonOpts
              },
              panelClass: 'modal-dialog'
            });

            dialogRef.afterClosed().pipe(take(1)).subscribe(result => {
              if (result) {
                this.service.costChangeReason = result;
                this.costChanged = false;
                this.store.dispatch(saveService({ service: this.service }));
              }
            });
          } else {
            this.store.dispatch(saveService({ service: this.service }));
          }
        });
      } else {
        this.store.dispatch(saveService({ service: this.service }));
      }
    });
  }

  onServiceSurchargesClicked(serviceId: number, companyId: number): void {
    const component: ComponentType<SurchargeDialogComponent> = SurchargeDialogComponent;
    const dialogRef = this.surchargesDialog.open(component, {
      data: {
        id: serviceId,
        companyId: companyId,
        isInventory: this.isInventory
      },
      width: '1000px',
      panelClass: 'modal-dialog'
    });
  }

  onNewMacdClicked(service: Service, companyId: number): void {
    const component: ComponentType<NewMacdComponent> = NewMacdComponent;
    const linkedOrBundled = service.linked || service.bundled;
    const dialogRef = this.newMacdDialog.open(component, {
      data: {
        serviceIds: [service.id],
        companyId: companyId,
        multiMacd: false,
        linkedOrBundled
      },
      width: '1000px',
      panelClass: 'modal-dialog'
    });

    dialogRef.afterClosed().pipe(take(1)).subscribe(result => {
      // I don't think we want to do anything here, but catching for now
    });
  }

  onServiceHistoryClicked(serviceId: number): void {
    const component: ComponentType<ServiceHistoryComponent> = ServiceHistoryComponent;
    const dialogRef = this.serviceHistoryDialog.open(component, {
      data: {
        id: serviceId
      },
      width: '1500px',
      panelClass: 'modal-dialog'
    });
  }

  onCostHistoryClicked(serviceId: number): void {
    const component: ComponentType<CostHistoryComponent> = CostHistoryComponent;
    const dialogRef = this.costHistoryDialog.open(component, {
      data: {
        id: serviceId,
        type: 'service'
      },
      width: '1500px'
    });
  }

  onLinkToParentClicked(): void {
    this.openDialog('Inventory');
  }

  onLinkToServicesClicked(): void {
    this.openDialog('Link');
  }

  onBundleClicked(): void {
    this.openDialog('Bundle');
  }

  openDialog(type: String): void {
    const component: ComponentType<LinkServiceComponent> = LinkServiceComponent;
    const callFrom = this.isInventory ? 'inventory' : 'ordering';
    const dialogRef = this.linkToParentDialog.open(component, {
      data: {
        companyId: this.companyId,
        locationId: this.service.locationId,
        type: type,
        from: callFrom,
        incomingServiceId: this.service.id,
        selectedParentServiceId: this.service.id
      },
      width: '1500px',
      panelClass: 'modal-dialog'
    });

    dialogRef.afterClosed().pipe(take(1)).subscribe(result => {
      // I don't think we want to do anything here, but catching for now
    });
  }

  onViewInventoryClicked(inventoryLocation: Location): void {
    let serviceId = this.service.inventoryServiceId || this.service.parentServiceId;
    const url = `${getBaseUrl()}inventory/order/${inventoryLocation.orderId}/location/${inventoryLocation.id}/service/${serviceId}`;
    window.open(url);
  }

  onOrderServiceClicked(service: Service): void {
    const url = `${getBaseUrl()}order/${service.orderId}/location/${service.locationId}/service/${service.id}`;
    window.open(url);
  }

  relocateServiceRecord(): void {
    if (!this.oes.order?.company.parentCompany) {
      throw Error('A Master Customer is required for this action')
    }
    let component: ComponentType<RelocateRecordComponent> | ComponentType<RelocateInventoryRecordComponent> | null = null;
    let dialogRef = null;
    if (this.service.currentInventory) {
      component = RelocateInventoryRecordComponent;
      dialogRef = this.relocateInventoryRecordDialog.open(component, {
        data: {
          locationId: this.service.locationId,
          companyId: this.oes.order?.company.parentCompany.id,
          type: 'service'
        },
        panelClass: 'modal-dialog'
      });
    } else {
      component = RelocateRecordComponent;
      dialogRef = this.relocateRecordDialog.open(component, {
        data: {
          locationId: this.service.locationId,
          companyId: this.oes.order?.company.parentCompany.id,
          type: 'service'
        },
        panelClass: 'modal-dialog'
      });
    }
    dialogRef.afterClosed().pipe(take(1)).subscribe(result => {
      if (result && result.locationId && result.orderId) {
        let locationId = this.service.locationId;
        let orderId = this.service.orderId;
        this.service.locationId = result.locationId;
        this.oes.getServiceForServiceType(this.service.type)!.save(this.service).subscribe(res => {
          this.store.dispatch(reloadOrder());
          let url = null;
          if (this.service.currentInventory) {
            this.router.navigate(['inventory', 'order', orderId, 'location', locationId]);
            url = `${getBaseUrl()}/inventory/order/${res.orderId}/location/${res.locationId}/service/${res.id}`;
          } else {
            this.router.navigate(['order', orderId, 'location', locationId]);
            url = `${getBaseUrl()}/order/${res.orderId}/location/${res.locationId}/service/${res.id}`;
          }
          window.open(url);
        })
      }
    });
  }

  onPullFromCrmClicked(): void {
    this.store.dispatch(pullFromCrm({ service: this.service }));
  }

  onAddressClicked(): void {
    if (!this.editingServiceBillingAddress) {
      return;
    }
    this.showAddressDialog = true;
  }

  onAddressSelected(address: Address): void {
    this.service.address1 = address.address1;
    this.service.address2 = address.address2;
    this.service.city = address.city;
    this.service.state = address.state;
    this.service.postalCode = address.postalCode;
    this.service.country = address.country;
    this.showAddressDialog = false;
  }

  onAddressSelectCancelled(): void {
    this.showAddressDialog = false;
  }

  onStateChanged(value: string): void {
    if (this.canadianProvinces.includes(value)) {
      this.service.country = 'Canada';
    } else {
      this.service.country = 'US';
    }
    this.service.state = value;
  }

  hasMacds(macds: any): boolean {
    return macds.filter((macd: any) => macd.orderType !== 'New').length > 0;
  }

  serviceAddressToString(): string {
    return addressToString(this.service.address1, this.service.address2, this.service.city, this.service.state, this.service.postalCode, this.service.country);
  }

  locationAddressToString(): string {
    return addressToString(this.location.address1, this.location.address2, this.location.city, this.location.state, this.location.postalCode, this.location.country);
  }

  onDeleteService(serviceId: number) {
    if (this.service.inventoryServiceId != null && this.service.orderType === 'New') {
      alert('This service is in inventory. Please delete the inventory service first.');
      return;
    }

    if (!this.service.isTerminal && this.service.currentInventory) {
      alert('This service is still open in ordering and must be cancelled first.');
      return;
    }

    let msg = ""
    if (this.service.currentInventory) {
      msg = " This action will also remove any closed/inactive dispute records."
    }
    if (confirm("Are you sure you want to delete this Service?" + msg + " Click OK to Continue.")) {
      if (this.location.services.length == 1) {
        if (confirm("This service is the only item assigned to this location. Click Ok if you want to delete the location as well. Click Cancel if you only want to delete the Service")) {
          this.store.dispatch(deleteLocation({ location: this.location, order: this.oes.order! }));
        } else {
          this.store.dispatch(deleteService({ service: this.service, location: this.location }));
        }
      } else {
        this.store.dispatch(deleteService({ service: this.service, location: this.location }));
      }
    }
  }
}

@Component({
  selector: 'cost-change-prompt-dialog',
  templateUrl: './cost-change-prompt-dialog.html',
  styleUrls: ['../../form-styles.scss'],
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, MatSelectModule, CommonModule, FormsModule, MatDialogModule]
})
export class CostChangePromptDialog {
  constructor(
    public dialogRef: MatDialogRef<CostChangePromptDialog>,
    @Inject(MAT_DIALOG_DATA) public data: {
      changeReason: string | undefined,
      changeReasonOpts: string[]
    }
  ) { }

  onClose() {
    this.dialogRef.close();
  }
}
