import { ComponentType } from '@angular/cdk/portal';
import { CommonModule } from '@angular/common';
import { Component, ElementRef, Inject, QueryList, ViewChild, ViewChildren, TemplateRef, EventEmitter, Output} from '@angular/core';
import { FormsModule, NgModel } from '@angular/forms';
import { MAT_LEGACY_DIALOG_DATA, MatLegacyDialog, MatLegacyDialogModule, MatLegacyDialogRef } from '@angular/material/legacy-dialog';
import { Router } from '@angular/router';
import { Observable, Subscription, catchError, forkJoin, map, throwError } from 'rxjs';
import { Address } from 'src/app/models/address.model';
import { CompanySearchCriteria } from 'src/app/models/company-search-criteria';
import { Company } from 'src/app/models/company.model';
import { LookupValue } from 'src/app/models/lookup-value.model';
import {
  CustomFieldDto,
  OrderCreateDto,
  OrderCreateDtoWrapper,
  OrderCreateLocation,
  OrderCreateService
} from 'src/app/models/order-create-dto.model';
import { PaginatedResult } from 'src/app/models/paginated-result.model';
import { SubjectInterface } from 'src/app/models/subject.model';
import { CompanyService } from 'src/app/services/company.service';
import { LookupValueService } from 'src/app/services/lookup-value.service';
import { OrderService } from 'src/app/services/order.service';
import { SecurityUtilService } from 'src/app/services/security-util.service';
import { ServiceFileAttachmentSearchCriteria, ServiceFileAttachmentService } from 'src/app/services/service-file-attachment.service';
import { SubjectService } from 'src/app/services/subject.service';
import { CANADIAN_PROVINCES, getBaseUrl } from 'src/app/utilities';
import { VtkSelectComponent } from '../../vtk-select/vtk-select.component';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { CompanyConfigPropertyService } from 'src/app/services/company-config-property.service';
import { ImportActivityService } from 'src/app/services/import-activity.service';
import { HttpResponse } from '@angular/common/http';
import { ImportActivity } from 'src/app/models/import-activity.model';
import { AbstractFileAttachmentService } from 'src/app/services/abstract-file-attachment.service';
import { LoadingSpinnerComponent } from '../../loading-spinner/loading-spinner.component';
import { plainToInstance } from 'class-transformer';
import { FileAttachment } from 'src/app/models/file-attachment';
import { Contact, ContactType } from 'src/app/models/contact.model';
import { ContactService } from 'src/app/services/contact.service';
import { DatepickerModule } from "../../datepicker/datepicker.module";
import { DatepickerComponent } from "../../datepicker/datepicker.component";
import { CustomField, CustomFieldTabValue } from "../../../models/custom-field.model";
import { CustomFieldsComponent } from "../../custom-fields/custom-fields.component";
import { MatDialog } from '@angular/material/dialog';
@Component({
  selector: 'app-new-order-wizard',
  templateUrl: './new-order-wizard.component.html',
  styleUrls: ['./new-order-wizard.component.scss']
})
export class NewOrderWizardComponent {

  @ViewChildren('serviceField') serviceFields!: QueryList<NgModel>
  @ViewChildren('brokerageField') brokerageFields!: QueryList<NgModel>
  @ViewChildren('serviceDate') serviceDates!: QueryList<DatepickerComponent>;
  @ViewChildren('brokerageDate') brokerageDates!: QueryList<DatepickerComponent>;
  @ViewChild('cf') cf!: CustomFieldsComponent;
  @ViewChild('addAddressDialogContent') addAddressDialogContent!: TemplateRef<any>;
  @Output() addressSelectCancelled = new EventEmitter<void>();

  public customFieldTabValue = CustomFieldTabValue.NEW_ORDER_BROKERAGE;

  orders: OrderCreateDto[] = [new OrderCreateDto()];

  selectedOrder: OrderCreateDto = this.orders[0];
  selectedLocation: OrderCreateLocation | null = null;
  selectedService: OrderCreateService | null = null;
  tabSelected: string;
  attachments = new Map<string, any[]>(); //key is service uuid, value is array of attachment info
  telecomClient: boolean = false;
  //MC opts
  masterCustomers: Company[];
  mcOpts: string[];
  mcLoading: boolean = false;
  mcParams: CompanySearchCriteria = new CompanySearchCriteria();
  //EC opts
  endCustomers: Company[];
  ecOpts: string[];
  ecLoading: boolean = false;
  ecParams: CompanySearchCriteria = new CompanySearchCriteria();

  //location & service dropdowns
  serviceTypes: LookupValue[];
  stateOpts: LookupValue[];
  countryOpts: string[];
  canadianProvinces = CANADIAN_PROVINCES;
  // levelOfEffortOpts: string[];
  projectNameOpts: string[];
  providerOpts: string[];
  subProductTypeOpts: string[];
  serviceBilledToOpts: string[];
  contractTermOpts: string[];
  speedOpts: string[];
  mediaTypeOpts: string[];
  noticePeriodOpts: string[];
  brokerageOpts: string[];
  additionalIpBlockOpts: string[];
  agencyNameLvs: LookupValue[];
  agentOpts: string[];
  agencyRepLvs: LookupValue[];
  commissionPaymentTypeOpts: string[];
  fieldServicesProviderOpts: string[];

  //assignment dialog dropdowns
  subjects: SubjectInterface[];
  subjectOpts: string[];
  clientPmOpts: string[];
  orderJeopOpts: string[];
  jeopResponsibilityOpts: string[];

  //contacts
  ContactType = ContactType;
  showContactDialog = false;
  selectedContact: any;

  //
  showAddressDialog: boolean = false;
  showAddAddressDialog: boolean = false; // used if billing address is empty
  editedAddressType: string; //used to determine which address we are editing
  isLoading: boolean = false;
  ordersCreated: boolean = false;
  lockOneLocationPerOrder: boolean = false;
  autoCreateClientServiceId: boolean = false;
  showBrokerageFields: boolean = false;
  userHasBeenWarned: boolean = false; //used in tandem with the lockOneLocationPerOrder flag to prevent multiple warnings
  changeCustomerConfirmText = 'Changing the Master or End Customer for this order will delete any location addresses you have already associated to the order.  Do you wish to continue?';
  tenant: string;

  constructor(
    private orderService: OrderService,
    private companyService: CompanyService,
    private lookupValueService: LookupValueService,
    public securityUtils: SecurityUtilService,
    private router: Router,
    private serviceFileAttachmentService: ServiceFileAttachmentService,
    private subjectService: SubjectService,
    private dialogService: MatLegacyDialog,
    private companyConfigService: CompanyConfigPropertyService,
    private contactService: ContactService,
    public dialog: MatDialog
  ) { }

  ngOnInit(): void {
    //get the users current tenant
    let companySearchCriteria = new CompanySearchCriteria();
    this.telecomClient = localStorage.getItem('TELECOM_CLIENT') === 'true';
    companySearchCriteria.type = 'Vertek Client';
    this.companyService.search(companySearchCriteria).subscribe((res: PaginatedResult<Company>) => {
      this.tenant = res.collection[0].name;
      //retrieve saved order from local storage
      let savedOrderString = localStorage.getItem('orderCreateDto');
      if (savedOrderString) {
        let savedOrder = JSON.parse(savedOrderString);
        if (savedOrder.tenant == this.tenant) {
          const component: ComponentType<SavedOrderDialogComponent> = SavedOrderDialogComponent;
          const dialogRef = this.dialogService.open(component,{
            data: {
              timeStamp: new Date(savedOrder.timeStamp)
            },
            disableClose: true
          });

          dialogRef.afterClosed().subscribe((res) => {
            if (res) {
              //if the user clicked 'yes' on the dialog, load the saved order
              this.orders = [plainToInstance(OrderCreateDto, this.deserializeOrder(savedOrder.order))];
              this.selectedOrder = this.orders[0];
            } else {
              //if the user clicked 'no', clear the saved order
              localStorage.removeItem('orderCreateDto');
            }
          });
        } else {
          localStorage.removeItem('orderCreateDto');
        }
      }
    });

    //load master customers
    this.mcParams.type = "Master Customer";
    this.mcParams.limit = 10000;
    this.mcParams.name = '';
    this.mcParams.active = true;
    this.loadMasterCustomers();
    //load end customers
    this.ecParams.type = "End Customer";
    this.ecParams.limit = 10000;
    this.ecParams.name = '';
    this.ecParams.active = true;
    this.loadEndCustomers();

    //load config properties
    this.companyConfigService.getValue('LOCK_ONE_LOCATION_PER_ORDER').subscribe((res) => { if (res && res.value) { this.lockOneLocationPerOrder = JSON.parse(res.value.toLowerCase()); }});
    this.companyConfigService.getValue('SHOW_BROKERAGE_FIELDS').subscribe((res) => { if (res && res.value) { this.showBrokerageFields = JSON.parse(res.value.toLowerCase()); }});
    this.companyConfigService.getValue('AUTO_CREATE_CLIENT_SERVICE_ID').subscribe((res) => { if (res && res.value) { this.autoCreateClientServiceId = JSON.parse(res.value.toLowerCase()); }});
    //load lookup values
    this.lookupValueService.find('TENANT_SERVICE_TYPES').subscribe((values: LookupValue[]) => { this.serviceTypes = values });
    this.lookupValueService.find('STATE_PROVINCE').subscribe((values: LookupValue[]) => { this.stateOpts = values });
    this.lookupValueService.find('COUNTRY').subscribe((values: LookupValue[]) => { this.countryOpts = values.map(v => v.value); });
    this.lookupValueService.find('PROJECT_NAME').subscribe((values: LookupValue[]) => { this.projectNameOpts = values.map(v => v.value); });
    this.lookupValueService.find('PROVIDER').subscribe((values: LookupValue[]) => { this.providerOpts = values.map(v => v.value); });
    this.lookupValueService.find('SUB_PRODUCT_TYPE').subscribe((values: LookupValue[]) => { this.subProductTypeOpts = values.map(v => v.value); });
    this.lookupValueService.find('SERVICE_BILLED_TO').subscribe((values: LookupValue[]) => { this.serviceBilledToOpts = values.map(v => v.value); });
    this.lookupValueService.find('CONTRACT_TERM').subscribe((values: LookupValue[]) => { this.contractTermOpts = values.map(v => v.value); });
    this.lookupValueService.find('SPEED').subscribe((values: LookupValue[]) => { this.speedOpts = values.map(v => v.value); });
    this.lookupValueService.find('MEDIA_TYPE').subscribe((values: LookupValue[]) => { this.mediaTypeOpts = values.map(v => v.value); });
    this.lookupValueService.find('CLIENT_PROJECT_MANAGER').subscribe((values: LookupValue[]) => { this.clientPmOpts = values.map(v => v.value); });
    this.lookupValueService.find('ORDER_JEOPARDY').subscribe((values: LookupValue[]) => { this.orderJeopOpts = values.map(v => v.value); });
    this.lookupValueService.find('JEOPARDY_RESPONSIBILITY').subscribe((values: LookupValue[]) => { this.jeopResponsibilityOpts = values.map(v => v.value); });
    this.lookupValueService.find('NOTICE_PERIOD').subscribe((values: LookupValue[]) => { this.noticePeriodOpts = values.map(v => v.value); });
    this.lookupValueService.find('PARENT_TSD').subscribe((values: LookupValue[]) => { this.brokerageOpts = values.map(v => v.value); });
    this.lookupValueService.find('ADDITIONAL_IP_BLOCK').subscribe((values: LookupValue[]) => { this.additionalIpBlockOpts = values.map(v => v.value); });
    this.lookupValueService.find('COMMISSIONS_PAYMENT_TYPE').subscribe((values: LookupValue[]) => { this.commissionPaymentTypeOpts = values.map(v => v.value); });
    this.lookupValueService.find('FIELD_SERVICES_PROVIDER').subscribe((values: LookupValue[]) => { this.fieldServicesProviderOpts = values.map(v => v.value); });
    this.lookupValueService.find('AGENCY_NAME').subscribe((values: LookupValue[]) => { this.agentOpts = values.map(v => v.value); });
    this.lookupValueService.find('AGENCY_NAME').subscribe((values: LookupValue[]) => {
      this.agencyNameLvs = values;
      this.agentOpts = values.map(lv => lv.value);
    });
    this.lookupValueService.find('AGENCY_REP').subscribe((values: LookupValue[]) => { this.agencyRepLvs = values; });
    //
    // this.levelOfEffortService.findByCompanyId(null).subscribe((res: LevelOfEffort[]) => { this.levelOfEffortOpts = res.map((loe: LevelOfEffort) => loe.levelOfEffort); });
    this.subjectService.getSubjects().subscribe((subjects: SubjectInterface[]) => { this.subjects = subjects; this.subjectOpts = subjects.map(s => s.displayName);});
    // causes ngOnDestroy hook to fire when page is destroyed outside of the angular workflow (e.g. browser refresh or close tab)
    window.onbeforeunload = () => this.ngOnDestroy();
  }

  //if the user has entered data into the form, but hasn't submitted it, save it to local storage
  ngOnDestroy(): void {
    if (this.orders.length == 1 && !this.ordersCreated) {
      let order = this.orders[0];
      if (order.masterCustomer || order.endCustomer || order.clientOrderId || order.locations.length > 0) {
        localStorage.setItem('orderCreateDto', JSON.stringify({
          order,
          timeStamp: new Date(),
          tenant: this.tenant
        }));
      }
    }
  }

  onCreateOrderClicked() {
    //validation
    this.orders.forEach(o => {
      if (!o.masterCustomer) {
        throw new Error('Master Customer is required');
      }
      if (!o.endCustomer) {
        throw new Error('End Customer is required');
      }
      if (o.locations.length === 0) {
        throw new Error('At least one location is required');
      }
      if (!o.locations.every(s => s.clientLocationId)) {
        throw new Error('Client Location ID is required for all locations & services');
      }
      // validation to ensure clientLocationId is unique within locations
      if (o.locations.length > 1) {
        const locationIds = o.locations.map(l => l.clientLocationId);
        const uniqueLocationIds = new Set(locationIds);
        if (uniqueLocationIds.size !== locationIds.length) {
          throw new Error('Client Location ID must be unique for each location');
        }
      }
      if (!this.autoCreateClientServiceId) {
        if (!o.locations.flat().map(l => l.services).flat().every(s => s.clientServiceId)) {
          throw new Error('Client Service ID is required for all services');
        }
      }
      if (!o.locations.flat().map(l => l.services).flat().every(s => s.serviceType)) {
        throw new Error('Service Type is required for all services');
      }
      o.locations.forEach(l => {
        l.services.forEach(s => {
          if (s.linkedOrBundled != 'none' && !s.linkedBundledClientServiceId) {
            throw new Error('Parent Client Service ID is required for all Bundled/Linked Services');
          }
          if (s.linkedBundledClientServiceId && s.linkedOrBundled == 'none') {
            throw new Error('Parent Client Service ID is only allowed for Bundled/Linked Services');
          }
          if (s.linkedOrBundled != 'none') {
            let found = false;
            l.services.forEach(s2 => {
              if (s.linkedBundledClientServiceId == s2.clientServiceId) {
                found = true;
                return;
              }
            });
            if (!found) {
              throw new Error('For Bundled/Linked Services the Parent Client Service ID must be a Client Service ID from one of the Service within the same Location');
            }
          }
          l.services.filter(s2 => s2.clientServiceId == s.linkedBundledClientServiceId).forEach(s3 => {
            if (s.linkedOrBundled != s3.linkedOrBundled) {
              throw new Error("All Services with the same Parent Client Service ID must have the same value for 'Linked' or 'Bundled'");
            }
          });
        });
      });
    });

    const dialogRef = this.openAssignmentDialog();
    //result is the enriched order
    dialogRef.afterClosed().subscribe((result: OrderCreateDto) => this.afterAssignmentDialogClose(result));
  }

  openAssignmentDialog(): any {
    const component: ComponentType<NewOrderAssignmentDialogComponent> = NewOrderAssignmentDialogComponent;
    if (this.selectedOrder.masterCustomer?.provisioner) {
      this.selectedOrder.provisioner = this.subjects.find(s => s.id === this.selectedOrder.masterCustomer?.provisioner)?.displayName;
    }
    if (this.selectedOrder.masterCustomer?.i90ProjectManager) {
      this.selectedOrder.vertekProjectManager = this.subjects.find(s => s.id === this.selectedOrder.masterCustomer?.i90ProjectManager)?.displayName;
    }
    return this.dialogService.open(component, {
      data: {
        order: this.selectedOrder,
        locationCount: this.orders.map(o => o.locations).flat().length,
        serviceCount: this.orders.map(o => o.locations).flat().map(l => l.services).flat().length,
        subjects: this.subjectOpts,
        clientPmOpts: this.clientPmOpts,
        orderJeopOpts: this.orderJeopOpts,
        jeopResponsibilityOpts: this.jeopResponsibilityOpts
      }
    });
  }

  afterAssignmentDialogClose(result: OrderCreateDto) {
    if (result) {
      //validate assignment dialog form
      if (result.holdProvisioning && (!result.jeopDescription || !result.jeopResponsibility || !result.vertekProjectManager)) {
        const dialogRef = this.openAssignmentDialog();
        dialogRef.afterClosed().subscribe((result2: OrderCreateDto) => this.afterAssignmentDialogClose(result2));
        throw new Error('Hold reason and i90 Project Manager are required when holding provisioning');
      }
      let provisioner = this.subjects.find(s => s.displayName === result.provisioner);
      let activationEngineer = this.subjects.find(s => s.displayName === result.activationEngineer);
      let vertekProjectManager = this.subjects.find(s => s.displayName === result.vertekProjectManager);
      let qaManager = this.subjects.find(s => s.displayName === result.qaManager);
      this.orders.forEach(o => {
        //map display names to subject ids
        o.provisioner = provisioner?.id;
        o.activationEngineer = activationEngineer?.id;
        o.vertekProjectManager = vertekProjectManager?.id;
        o.qaManager = qaManager?.id;
        o.clientProjectManager = result.clientProjectManager;
        o.holdProvisioning = result.holdProvisioning;
        o.jeopDescription = result.jeopDescription;
        o.jeopResponsibility = result.jeopResponsibility;
      });
      this.save();
      localStorage.removeItem('orderCreateDto');
    }
  }

  getAgencyRepOpts(event:any): string[] {
    return this.agencyRepLvs?.filter(lv => lv.parentId == this.agencyNameLvs?.find(lv => lv.value == event)?.id).map(lv => lv.value);
  }

  save(): void {
    this.isLoading = true;
    //create orders
    this.orderService.create(this.orders).pipe(
      catchError((createError) => {
        this.isLoading = false;
        //map subject ids back to display names
        this.orders.forEach(o => {
          o.provisioner = this.subjects.find(s => s.id === o.provisioner)?.displayName;
          o.activationEngineer = this.subjects.find(s => s.id === o.activationEngineer)?.displayName;
          o.vertekProjectManager = this.subjects.find(s => s.id === o.vertekProjectManager)?.displayName;
          o.qaManager = this.subjects.find(s => s.id === o.qaManager)?.displayName;
        });
        return throwError(() => createError);
      })
    ).subscribe((res: OrderCreateDtoWrapper) => {
      let orders = plainToInstance(OrderCreateDto, res.dtoList);
      orders.forEach(o => {
        o.locations = plainToInstance(OrderCreateLocation, o.locations);
        o.locations.forEach(l => l.address = plainToInstance(Address, l.address));
        o.locations.forEach((l: OrderCreateLocation) => {
          l.services = plainToInstance(OrderCreateService, l.services);
          l.services.forEach(s => s.zAddress = plainToInstance(Address, s.zAddress));
        });
      });
      this.orders = orders;
      //upload attachments
      let attachmentErrorString = 'Your Order(s) have been created, but errors occurred while uploading attachments:\n';
      let attachmentsProcessed = 0;
      let attachmentCount = [...this.attachments.values()].reduce((acc, currentValue) => acc + currentValue.length, 0);
      if (attachmentCount === 0) {
        this.isLoading = false;
        this.ordersCreated = true;
      }
      res.dtoList.forEach(o => {
        o.locations.flat().map(l => l.services).flat().forEach(s => {
          const attachments = this.attachments.get(s.uuid);
          if (attachments) {
            this.selectedService = s;
            attachments.forEach(att => {
              let criteria = new ServiceFileAttachmentSearchCriteria();
              criteria.serviceId = s.serviceId;
              if (att.content) {
                let attachment = new FileAttachment();
                attachment.content.data = att.content;
                attachment.description = att.description;
                this.serviceFileAttachmentService.upload([attachment], criteria).pipe(
                  catchError((attachmentError) => {
                    attachmentErrorString += attachmentError.error + '\n';
                    attachmentsProcessed++;
                    if (attachmentsProcessed === attachmentCount) {
                      this.isLoading = false;
                      this.ordersCreated = true;
                      if (attachmentErrorString.length > 82) {
                        throw new Error(attachmentErrorString);
                      }
                    }
                    return new Observable();
                  })
                ).subscribe(() => {
                  attachmentsProcessed++;
                  if (attachmentsProcessed === attachmentCount) {
                    this.isLoading = false;
                    this.ordersCreated = true;
                    if (attachmentErrorString.length > 82) {
                      throw new Error(attachmentErrorString);
                    }
                  }
                });
              } else {
                attachmentsProcessed++;
              }
            });
          }
        });
      });
    });
  }

  //set the selected order, lcoation, and service
  onRowClick(serviceUuid: string) {
    this.selectedService = this.orders.map(l => l.locations).flat().flatMap(o => o.services).find(s => s.uuid === serviceUuid)!;
    this.selectedLocation = this.orders.map(l => l.locations).flat().find(l => l.services.includes(this.selectedService!))!;
    this.selectedOrder = this.orders.find(o => o.locations.includes(this.selectedLocation!))!;
    this.tabSelected = 'service';
  }

  //open the newly created service in a new tab
  onRowDblClick(link: string) {
    if (link) {
      const url = getBaseUrl() + '/' + link;
      window.open(url);
    }
  }

  onAddLocationClicked() {
    this.selectedOrder.locations.push(new OrderCreateLocation());
    this.selectedLocation = this.selectedOrder.locations[this.selectedOrder.locations.length - 1];
    this.selectedLocation.services.push(new OrderCreateService());
    this.selectedService = this.selectedLocation.services[this.selectedLocation.services.length - 1];
    this.tabSelected = 'location'

    //warn the user if tenant configuration only allows one location per order
    if (this.selectedOrder.locations.length > 1 && this.lockOneLocationPerOrder && !this.userHasBeenWarned) {
      this.userHasBeenWarned = true;
      throw new Error('Tenant configuration only allows one location per order.  Additional locations will be split into their own order.');
    }
  }


  onAddServiceClicked() {
    this.selectedLocation!.services.push(new OrderCreateService());
    this.selectedService = this.selectedLocation!.services[this.selectedLocation!.services.length - 1];
    this.tabSelected = 'service';
  }

  onRemoveServiceClicked() {
    this.selectedLocation!.services = this.selectedLocation!.services.filter(s => s !== this.selectedService);
    if (this.selectedLocation!.services.length == 0) {
      this.selectedOrder.locations = this.selectedOrder.locations.filter(l => l !== this.selectedLocation);
      this.selectedLocation = null;
      this.selectedService = null;
    } else {
      this.selectedService = this.selectedLocation!.services[0];
    }
  }

  onLocationClearEntryClicked() {
    let index = this.selectedOrder.locations.indexOf(this.selectedLocation!);
    let services = this.selectedOrder.locations[index].services;
    this.selectedOrder.locations[index] = new OrderCreateLocation();
    this.selectedOrder.locations[index].services = services;
    this.selectedLocation = this.selectedOrder.locations[index];
  }

  onFocusOut(event: FocusEvent) {
    //this gets the custom fields when the custom fields on the  brokerage tab lose focus and puts them onto the service dto.
    let customFields: CustomFieldDto[] = [];
    this.cf.fields.forEach(field => {
      if (this.cf.getValue(field) !== null && this.cf.getValue(field) !== '') {
        customFields.push({ customFieldId: field.id, value: this.cf.getValue(field) });
      }
    });
    // @ts-ignore
    this.selectedService.customFields = customFields;
    console.log(customFields);
  }

  loadCustomFields() {
    setTimeout(() => {
      this.selectedService?.customFields.forEach(field => {
        let fld: CustomField | undefined = this.cf.fields.find(f => f.id === field.customFieldId);
        if (fld) {
          this.cf.setValue(fld, field.value);
        }
      });
    }, 500);
  }

  onServiceClearEntryClicked(fields: string, dates: string) {
    if (fields === 'serviceField') {
      this.serviceFields.forEach(field => field.reset());
    } else if (fields === 'brokerageField') {
       this.brokerageFields.forEach(field => field.reset());
       this.cf.fields.forEach(field => this.cf.setValue(field, ''));
    }
    if (dates === 'serviceDate') {
      this.serviceDates.forEach(field => field.date = null);
    } else if (dates === 'brokerageDate') {
      this.brokerageFields.forEach(field => field.reset());
    }
  }

  onClosePreviewClicked() {
    this.selectedLocation = null;
    this.selectedService = null;
  }

  // ADD ADDRESS
  onAddAddressClicked(): void {
    const dialogRef = this.dialog.open(this.addAddressDialogContent, {
      width: '500px',
    });

    dialogRef.afterClosed().subscribe(() => {
      console.log('The dialog was closed');
    });
  }
  onCancelClicked(): void {
    this.dialog.closeAll();
  }

  saveNewAddress(): void {
    this.showAddAddressDialog = false;
    this.dialog.closeAll();
  }

  //CONTACTS
  onContactClicked(contactType: ContactType): void {
    let companyId = this.selectedOrder.endCustomer?.id || null;
    switch(contactType) {
      case ContactType.SALES:
        if (!this.selectedOrder.salesContact) {
          this.selectedContact = new Contact(companyId, ContactType.SALES);
        } else {
          this.selectedContact = plainToInstance(Contact, {...this.selectedOrder.salesContact});
        }
        break;
      case ContactType.TECH:
        if (!this.selectedOrder.techContact) {
          this.selectedContact = new Contact(companyId, ContactType.TECH);
        } else {
          this.selectedContact = plainToInstance(Contact, {...this.selectedOrder.techContact});
        }
        break;
      case ContactType.AUTH:
        if (!this.selectedOrder.authContact) {
          this.selectedContact = new Contact(companyId, ContactType.AUTH);
        } else {
          this.selectedContact = plainToInstance(Contact, {...this.selectedOrder.authContact});
        }
        break;
    }
    this.showContactDialog = true;
  }

  onContactSaved(contact: Contact): void {
    switch(this.selectedContact?.type) {
      case ContactType.SALES:
        this.selectedOrder.salesContact = contact;
        break;
      case ContactType.TECH:
        this.selectedOrder.techContact = contact;
        break;
      case ContactType.AUTH:
        this.selectedOrder.authContact = contact;
        break;
    }
    this.showContactDialog = false;
  }

  onContactDeleted(contact: Contact): void {
    switch(this.selectedContact?.type) {
      case ContactType.SALES:
        this.selectedOrder.salesContact = null;
        break;
      case ContactType.TECH:
        this.selectedOrder.techContact = null;
        break;
      case ContactType.AUTH:
        this.selectedOrder.authContact = null;
        break;
    }
    this.showContactDialog = false;
  }

  //END CONTACTS

  //MASTER CUSTOMER & END CUSTOMER DROPDOWNS
  loadMasterCustomers() {
    this.mcLoading = true;
    this.companyService.search(this.mcParams).subscribe((res: PaginatedResult<Company>) => {
      this.masterCustomers = res.collection;
      this.mcOpts = this.masterCustomers.map(mc => mc.name);
      this.mcLoading = false;
    });
  }

  ecSearchSubscription: Subscription;
  loadEndCustomers() {
    this.ecLoading = true;
    if (this.ecSearchSubscription) {
      this.ecSearchSubscription.unsubscribe();
    }
    this.ecSearchSubscription = this.companyService.search(this.ecParams).subscribe((res: PaginatedResult<Company>) => {
      this.endCustomers = res.collection;
      this.ecOpts = this.endCustomers.map(ec => ec.name);
      this.ecLoading = false;
    });
  }

  onMcSelected(mc: string, dropdown: any) {
    if (this.selectedOrder.masterCustomer && !confirm(this.changeCustomerConfirmText)) {
      dropdown.selected = this.selectedOrder.masterCustomer.name;
      return;
    }
    this.selectedOrder.masterCustomer = this.masterCustomers.find(c => c.name === mc)!;
    this.selectedOrder.endCustomer = null;
    if (mc) {
      this.ecParams.masterCustomers = [mc];
    } else {
      this.ecParams.masterCustomers = [];
    }
    this.loadEndCustomers();
  }

  onEcSelected(ec: string, dropdown: any) {
    if (this.selectedOrder.endCustomer && !confirm(this.changeCustomerConfirmText)) {
      dropdown.selected = this.selectedOrder.endCustomer.name;
      return;
    }
    this.selectedOrder.endCustomer = this.endCustomers.find(c => c.name === ec)!;
    if (this.selectedOrder.endCustomer) {
      this.selectedOrder.masterCustomer = this.selectedOrder.endCustomer.parentCompany;
    }
    this.selectedOrder.locations.forEach(l => {
      if (l.address.id && l.address.id != this.selectedOrder.endCustomer?.id) {
        l.address = new Address();
      }
    });
    if (this.selectedOrder.endCustomer) {
      this.isLoading = true;
      this.selectedOrder.billingAddress = new Address();
      this.selectedOrder.billingAddress.address1 = this.selectedOrder.endCustomer.address1;
      this.selectedOrder.billingAddress.address2 = this.selectedOrder.endCustomer.address2;
      this.selectedOrder.billingAddress.city = this.selectedOrder.endCustomer.city;
      this.selectedOrder.billingAddress.state = this.selectedOrder.endCustomer.state;
      this.selectedOrder.billingAddress.postalCode = this.selectedOrder.endCustomer.postalCode;
      this.selectedOrder.billingAddress.country = this.selectedOrder.endCustomer.country;
      //checks if billingAddress contains meaningful data
      if(!this.selectedOrder.billingAddress.address1 && !this.selectedOrder.billingAddress.address2 && !this.selectedOrder.billingAddress.city && !this.selectedOrder.billingAddress.state && !this.selectedOrder.billingAddress.postalCode && !this.selectedOrder.billingAddress.country) {
        // if not, allows the user to enter a new address
        this.showAddAddressDialog = true;
      }
      forkJoin([
        this.contactService.findByCompanyIdAndType(this.selectedOrder.endCustomer.id, ContactType.SALES),
        this.contactService.findByCompanyIdAndType(this.selectedOrder.endCustomer.id, ContactType.TECH),
        this.contactService.findByCompanyIdAndType(this.selectedOrder.endCustomer.id, ContactType.AUTH)
      ]).pipe(
        map(([sales, tech, auth]) => {
          this.selectedOrder.salesContact = plainToInstance(Contact, sales);
          this.selectedOrder.techContact = plainToInstance(Contact, tech);
          this.selectedOrder.authContact = plainToInstance(Contact, auth);
          this.isLoading = false;
        })
      ).subscribe();
    } else {
      this.selectedOrder.billingAddress = null;
      this.selectedOrder.salesContact = null;
      this.selectedOrder.techContact = null;
      this.selectedOrder.authContact = null;
    }
  }

  onMcSearch(value: string) {
    this.mcParams.name = value;
    this.loadMasterCustomers();
  }

  onEcSearch(value: string) {
    this.ecParams.name = value;
    this.loadEndCustomers();
  }
  //END MASTER CUSTOMER & END CUSTOMER DROPDOWNS

  //ADDRESS DIALOG
  onStateChanged(value: string, address: Address): void {
    let country = '';
    if (this.canadianProvinces.includes(value)) {
      country = 'Canada';
    } else {
      country = 'US';
    }
    address.country = country;
    address.state = value;
  }

  onSelectAddressClicked(addressType: string): void {
    if (this.ordersCreated) {
      return;
    }
    if (this.selectedOrder.endCustomer) {
      this.editedAddressType = addressType;
      this.showAddressDialog = true;
    }
  }

  onAddressSelected(address: Address): void {
    let add = plainToInstance(Address, address);
    if (this.editedAddressType == 'location') {
      this.selectedLocation!.clientLocationId = add.clientLocationId;
      this.selectedLocation!.address = add;
    } else if (this.editedAddressType == 'billing') {
      this.selectedOrder!.billingAddress = add;
    } else if (this.editedAddressType == 'z') {
      this.selectedService!.zAddress = add;
    }
    this.editedAddressType = '';
    this.showAddressDialog = false;
    if (this.showAddAddressDialog) {
      this.showAddAddressDialog = false;
      this.dialog.closeAll();
    }
  }
  //END ADDRESS DIALOG

  //ATTACHMENTS
  onAddAttachmentClicked(service: OrderCreateService): void {
    if (this.ordersCreated) {
      return;
    }
    if (!this.attachments.has(service.uuid)) {
      this.attachments.set(service.uuid, []);
    }
    this.attachments.get(service.uuid)!.push({ inputId: Math.random().toString(36).substring(3,9), description: '' });
  }

  onAttachmentFileSelected(event: any, attachmentInputId: string): void {
    const attachment = this.attachments.get(this.selectedService!.uuid)!.find(att => att.inputId === attachmentInputId);
    attachment.content = event.target.files[0];
  }

  onRemoveAttachmentClicked(service: OrderCreateService, attachment: any): void {
    if (this.ordersCreated) {
      return;
    }
    this.attachments.set(service.uuid, this.attachments.get(service.uuid)!.filter(att => att !== attachment));
  }
  //END ATTACHMENTS

  onBulkImportClicked(): void {
    const component: ComponentType<BulkImportDialogComponent> = BulkImportDialogComponent;
    const dialogRef = this.dialogService.open(component, {
      data: {},
    });
    dialogRef.afterClosed().subscribe((result: OrderCreateDto[]) => {
      if (result) {
        let typedOrders = plainToInstance(OrderCreateDto, result);
        typedOrders.forEach(o => {
          this.deserializeOrder(o);
        });
        this.orders = typedOrders.reverse();
        this.selectedOrder = this.orders[0];
        this.selectedLocation = null;
        this.selectedService = null;
      }
    });
  }

  onClearFormClicked(): void {
    this.orders = [new OrderCreateDto()];
    this.selectedOrder = this.orders[0];
    this.selectedLocation = null;
    this.selectedService = null;
    this.attachments = new Map<string, any[]>();
    localStorage.removeItem('orderCreateDto');
  }

  onImportActivityClicked(): void {
    this.router.navigate(['/import'], { queryParams: { importType: 'Order' } });
  }

  deserializeOrder(o: OrderCreateDto): OrderCreateDto {
    o.locations = plainToInstance(OrderCreateLocation, o.locations);
    o.locations.forEach((l) => {
      l.address = plainToInstance(Address, l.address);
      l.services = plainToInstance(OrderCreateService, l.services);
      l.services.forEach((s: OrderCreateService) => {
        s.zAddress = s.zAddress ? plainToInstance(Address, s.zAddress) : new Address();
        s.uuid = Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);
      });
    });
    o.billingAddress = plainToInstance(Address, o.billingAddress);
    o.salesContact = o.salesContact ? plainToInstance(Contact, o.salesContact) : null;
    o.techContact = o.techContact ? plainToInstance(Contact, o.techContact) : null;
    o.authContact = o.authContact ? plainToInstance(Contact, o.authContact) : null;
    return o;
  }

}

@Component({
  selector: 'new-order-assignment-dialog',
  templateUrl: './new-order-assignment-dialog.component.html',
  styleUrls: ['./new-order-wizard.component.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule, MatLegacyDialogModule, VtkSelectComponent, MatCheckboxModule]
})
export class NewOrderAssignmentDialogComponent {
  constructor(
    public dialogRef: MatLegacyDialogRef<NewOrderAssignmentDialogComponent>,
    @Inject(MAT_LEGACY_DIALOG_DATA) public data: {
      order: OrderCreateDto,
      locationCount: number,
      serviceCount: number,
      subjects: string[],
      clientPmOpts: string[],
      orderJeopOpts: string[],
      jeopResponsibilityOpts: string[]
    }
  ) { }

  onClose() {
    this.dialogRef.close();
  }
}

@Component({
  selector: 'bulk-import-dialog',
  templateUrl: './bulk-import-dialog.component.html',
  styleUrls: ['./new-order-wizard.component.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule, MatLegacyDialogModule, LoadingSpinnerComponent]
})
export class BulkImportDialogComponent {

  selectedFile: File;
  isLoading: boolean = false;

  constructor(
    public dialogRef: MatLegacyDialogRef<BulkImportDialogComponent>,
    @Inject(MAT_LEGACY_DIALOG_DATA) public data: any,
    private importActivityService: ImportActivityService,
    private attachmentService: AbstractFileAttachmentService
  ) { }

  onClose() {
    this.dialogRef.close();
  }

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
  }

  onUploadClicked(): void {
    this.isLoading = true;
    this.importActivityService.uploadFile('Order', this.selectedFile).subscribe((res: any) => {
        //if there are errors, download the error file
        if (res.errorFileAttachment) {
          this.attachmentService.download(res.errorFileAttachment.id).subscribe((att: any) => {
            var a = document.createElement('a');
            var url = window.URL.createObjectURL(att);
            a.href = url;
            a.download = res.errorFileAttachment.name;
            document.body.append(a);
            a.click();
            a.remove();
            window.URL.revokeObjectURL(url);
            this.isLoading = false;
            this.dialogRef.close();
            throw new Error('File contains errors.  Please correct and re-import.');
          });
        } else if (res.status == 'Upload error') {
          let err = res.status + ': ' + res.statusDetails;
          this.isLoading = false;
          this.dialogRef.close();
          throw new Error(err)
        } else {
          this.isLoading = false;
          this.dialogRef.close(res.dtoList);
        }
      }
    );
  }

  onDownloadTemplateClicked(): void {
    this.importActivityService.downloadTemplate('Order').subscribe((res: HttpResponse<Blob>) => {
      var a = document.createElement('a');
      var url = window.URL.createObjectURL(res.body!);
      a.href = url;
      a.download = res.headers.get('Content-Disposition')!.split('=')[1];
      document.body.append(a);
      a.click();
      a.remove();
      window.URL.revokeObjectURL(url);
    }, (err) => {
      let reader = new FileReader();
      reader.onload = () => {
        throw Error(reader.result as string);
      };
      reader.readAsText(err.error);
    });
  }

}

@Component({
  selector: 'saved-order-dialog',
  templateUrl: './saved-order-dialog.component.html',
  styleUrls: ['./new-order-wizard.component.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule, MatLegacyDialogModule]
})
export class SavedOrderDialogComponent {
  constructor(
    public dialogRef: MatLegacyDialogRef<SavedOrderDialogComponent>,
    @Inject(MAT_LEGACY_DIALOG_DATA) public data: {
      timeStamp: Date,
    }
  ) { }
}
