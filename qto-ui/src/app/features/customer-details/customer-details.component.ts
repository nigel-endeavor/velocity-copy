import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Store, select } from '@ngrx/store';
import * as actions from './ngrx/customer-details.actions';
import { SecurityUtilService, Permissions } from 'src/app/services/security-util.service';
import { Company } from 'src/app/models/company.model';
import { plainToClass } from 'class-transformer';
import { filter, map, merge, Observable, take } from 'rxjs';
import { getAuthContact, getBillingContact, getIsLoading, getProvisioners, getSalesContact, getSelectedCustomer, getTaskGroups, getTaskGroupValues, getTechContact } from './ngrx/customer-details.selectors';
import { Contact } from 'src/app/models/contact.model';
import { ContactService } from 'src/app/services/contact.service';
import { LookupValueService } from 'src/app/services/lookup-value.service';
import { LookupValue } from 'src/app/models/lookup-value.model';
import { CANADIAN_PROVINCES } from 'src/app/utilities';
import { CompanyService } from 'src/app/services/company.service';
import { NgForm } from '@angular/forms';
import { CompanyType } from 'src/app/models/constants/company-type';
import { CompanySearchCriteria } from 'src/app/models/company-search-criteria';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from '../../components/confirmation-dialog/confirmation-dialog.component';
import { TaskManagerComponent } from '../task-manager/task-manager.component';
import { ComponentType } from '@angular/cdk/portal';
import { TaskGroup } from '../../models/task-group.model';
import { CustomerDetailsFilterKeys, CustomerDetailsOptionsKeys } from './data/customer-details.consts';
import { CompanyTask } from '../../models/company-task.model';
import { AutomatedEmailComponent } from 'src/app/components/automated-email/automated-email.component';
import { getAppliedFilters } from "../end-customers-worklist/ngrx/end-customers-worklist.selectors";

@Component({
  selector: 'app-company-details',
  templateUrl: './customer-details.component.html',
  styleUrls: ['../../order-detail-page/form-styles.scss', './customer-details.component.scss', "../../order-detail-page/form-styles.scss"]
})
export class CustomerDetailsComponent {

  company: Company;
  public billingContact$ = this.store.select(getBillingContact);
  public techContact$ = this.store.select(getTechContact);
  public salesContact$ = this.store.select(getSalesContact);
  public authContact$ = this.store.select(getAuthContact);
  public isLoading$ = this.store.select(getIsLoading);
  public customerTasks: CompanyTask[] = [];
  public taskGroups$ = this.store.select(getTaskGroups);
  public taskGroup: TaskGroup | undefined = undefined;

  billingContact: Contact | null;
  techContact: Contact | null;
  salesContact: Contact | null;
  authContact: Contact | null;

  editEnabledActive: boolean = this.securityUtils.userHasPermission(Permissions.TENANT_ADMIN);
  editEnabled: boolean = this.securityUtils.userHasPermission(Permissions.ORDER_WRITE) || this.securityUtils.userHasPermission(Permissions.INVENTORY_WRITE);

  editingCustomerInfo: boolean = false;
  editingAddress: boolean = false;
  editingPermanentAccontNotes: boolean = false;
  showAddressDialog: boolean = false;
  editingContacts: boolean = false;
  // editingBillingContact: boolean = false;
  editingTasks: boolean = false;
  // editingTechContact: boolean = false;
  // editingSalesContact: boolean = false;
  // editingAuthContact: boolean = false;
  editingMasterCustomer: boolean = false;
  editingAutomatedEmails: boolean = false;
  automatedEmailsEnabled: boolean = false;

  stateOpts: LookupValue[];
  countryOpts: string[];
  canadianProvinces = CANADIAN_PROVINCES;
  masterCustomers: Company[];
  mcOpts: string[];
  mcParams: CompanySearchCriteria = new CompanySearchCriteria();
  intialProvisioner: number | undefined = undefined;

  masterCustomerTooltip = 'Master Customer Name';
  endCustomerTooltip = 'End Customer Name';
  masterCustClientIdTooltip = 'Unique ID assigned to the Master Customer';
  endCustClientIdTooltip = 'Unique ID assigned to the End Customer';
  accountManagerTooltip = 'Account manager assignement carries to all orders and interactions for the customer';
  provisionerTooltip = 'Enter a name here if you want all new orders for this Master customer to be auto assigned to this user as the order provisioner upon each order creation.';
  i90ProjMgrTooltip = 'Enter a name here if you want all new orders for this Master customer to be auto assigned to this user as the order Project manager upon each new order creation.';
  taskGroupTooltip = 'If the customer onboarding feature is utilized, use this dropdown to select which onboarding task group will be used';
  editTasksTooltip = 'Enter the account onboarding task manager';
  deleteMastCustTooltip = 'Deletes the Master Customer record.  Only available if there are no End Customers attached to this Master Customer';
  changeMastCustTooltip = 'Allows you to reassign this end customer to a different parent master customer for your organization';
  deleteEndCustTooltip = 'Delete the end customer record.  Only available if there are no active orders or inventory assigned to this End Customer';

  public selectedCustomer$ = this.store.pipe(select(getSelectedCustomer)).pipe(
    filter(selectedCustomer => !!selectedCustomer),
    map(selectedCustomer => {
      this.company = plainToClass(Company, selectedCustomer as Company);
      //load dropdown values
      if (!this.stateOpts) {
        this.lookupValueService.find('STATE_PROVINCE', this.company.id).subscribe((values: LookupValue[]) => { this.stateOpts = values });
      }
      if (!this.countryOpts) {
        this.lookupValueService.getValues('COUNTRY', this.company.id).subscribe((values: string[]) => { this.countryOpts = values });
      }
      if (this.company.type !== CompanyType.END_CUSTOMER) {
        this.intialProvisioner = this.company.provisioner;
      }
      this.taskGroups$.subscribe(taskGroups => {
        if (taskGroups && taskGroups.length > 0) {
          this.taskGroup = taskGroups.find(tg => tg.id === this.company.taskGroupId);
        }
      });
      return selectedCustomer;
    })
  ).subscribe();

  public provisioners$ = this.store.pipe(select(getProvisioners));

  constructor(
    private store: Store,
    private route: ActivatedRoute,
    private router: Router,
    private securityUtils: SecurityUtilService,
    private companyService: CompanyService,
    private contactService: ContactService,
    private lookupValueService: LookupValueService,
    private dialog: MatDialog,
    private taskManagerDialog: MatDialog
  ) {  }

  ngOnInit(): void {
    this.store.dispatch(actions.setSelectedTab({ tab: 'Details' }));
    //load master customers
    this.mcParams.type = CompanyType.MASTER_CUSTOMER;
    this.mcParams.limit = 10000;
    this.mcParams.name = '';
    this.loadMcOpts();
    this.store.dispatch(actions.loadProvisioners());
    this.billingContact$.subscribe(contact => {
      if (contact) {
        this.billingContact = plainToClass(Contact, contact);
      }
    });
    this.techContact$.subscribe(contact => {
      if (contact) {
        this.techContact = plainToClass(Contact, contact);
      }
    });
    this.salesContact$.subscribe(contact => {
      if (contact) {
        this.salesContact = plainToClass(Contact, contact);
      }
    });
    this.authContact$.subscribe(contact => {
      if (contact) {
        this.authContact = plainToClass(Contact, contact);
      }
    });
    //load selected customer if customerId is present
    this.route.params.subscribe(params => {
      if (params['customerId'] || this.router.url.endsWith('new')) {
        setTimeout(() => {
          this.editingCustomerInfo = false;
          this.editingAddress = false;
          this.editingPermanentAccontNotes = false;
          this.editingContacts = false;
          this.editingTasks = false;
          if (this.router.url.endsWith('new')) {
            let newCompany = new Company();
            newCompany.active = true;
            if (this.company && this.company.id) {
              newCompany.type = CompanyType.END_CUSTOMER;
              newCompany.parentCompany = this.company;
            } else {
              newCompany.type = CompanyType.MASTER_CUSTOMER;
            }
            this.store.dispatch(actions.setSelectedCustomer({ customer: newCompany }));
            this.editingCustomerInfo = true;
          } else {
            this.store.dispatch(actions.loadSelectedCustomer({ customerId: params['customerId'] }));
          }
        });
      }
    });
  }

  ngOnDestroy(): void {
    this.editingCustomerInfo = false;
    this.editingAddress = false;
    this.editingPermanentAccontNotes = false;
    this.editingContacts = false;
    this.editingTasks = false;
    this.editingMasterCustomer = false;
    this.store.dispatch(actions.pageDestroyed());
    // if (this.company.type == 'End Customer' && this.company.parentCompany) {
    //   this.store.dispatch(actions.setSelectedCustomer({ customer: this.company.parentCompany }));
    //   this.store.dispatch(actions.setSelectedTab({ tab: 'End Customers' }));
    // }
  }

  onSaveCompanyClicked(): void {
    if (!this.company.name || this.company.name === '') {
      throw new Error('Company Name is required');
    }
    if (this.company.type === CompanyType.MASTER_CUSTOMER && this.company.provisioner && ((this.intialProvisioner !== this.company.provisioner))) {
      const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
        data: { message: 'You are changing the default provisioner. Doing so will update all associated orders and inventory items to use this provisioner. Do you wish to continue?' },
        maxWidth: '400px' // Specify the maximum width here
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          this.onSaveCompany();
        } else {
          this.company.provisioner = this.intialProvisioner;
        }
      });
    } else if (this.company.type === CompanyType.END_CUSTOMER && this.company.id && this.company.duplicatedMasterCustomerDetails) {
      const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
        data: { message: 'This will overwrite all existing contacts and addresses with the master customer values. Do you wish to continue?' },
        maxWidth: '400px' // Specify the maximum width here
      });
      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          this.onSaveCompany();
        }
      });
    } else {
      this.onSaveCompany();
    }
  }

  onSaveCompany(): void {
    this.editingCustomerInfo = false;
    this.editingAddress = false;
    this.editingPermanentAccontNotes = false;
    this.editingMasterCustomer = false;
    this.companyService.save(this.company).subscribe(res => {
      this.store.dispatch(actions.loadSelectedCustomer({ customerId: res.id }));
      if (this.company.type === CompanyType.MASTER_CUSTOMER) {
        this.router.navigate(['/customers/masterCustomers', res.id]);
      } else {
        this.router.navigate(['/customers/endCustomers', res.id]);
      }
    });
  }

  onSaveContactClicked(billingContact: Contact, techContact: Contact | null, salesContact: Contact | null, authContact: Contact | null,
                       form: NgForm): void {

    if (!form.valid) {
      throw new Error('Contact Info invalid, please correct the highlighted fields');
    }
    const contactsToSave = [billingContact, techContact, salesContact, authContact]
      .filter(contact => contact && (contact.name?.trim() || contact.email || contact.phone || contact.id));

    if (contactsToSave.length === 0) {
      // If no contacts have info to save, reset flag and call loadSelectedCustomer immediately
      this.editingContacts = false;
      this.store.dispatch(actions.loadSelectedCustomer({customerId: this.company.id}));
      return;
    }

    let saveCount = 0;
    contactsToSave.forEach(contact => {
      this.contactService.save(<Contact>contact).subscribe(() => {
        saveCount++;
        if (saveCount === contactsToSave.length) {
          // Reset the flag and call loadSelectedCustomer after all contacts are saved
          this.editingContacts = false;
          this.store.dispatch(actions.loadSelectedCustomer({customerId: this.company.id}));
        }
      });
    });
  }

  onTaskGroupChange(event: any): void {
    console.log('Implement me');
    //if the company has a task group and it is changed to null or a different task group, verify that user
    //wants to remove the existing tasks
    if (this.company.taskGroupId
      && (!event || this.company.taskGroupId !== event)) {
      const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
        data: { message: 'Changing the task group will remove all existing tasks and immediately save changes to this customer. Do you wish to continue?' },
        maxWidth: '400px' // Specify the maximum width here
      });
      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          this.company.taskGroupId = event;
          this.onSaveCompanyClicked();
        }
      });
    } else {
      this.company.taskGroupId = event;
    }
  }

  onSaveTasksClicked(): void {
    this.editingTasks = false;
    this.onSaveCompanyClicked();
  }

  onEditTasksClicked(): void {
    const component: ComponentType<TaskManagerComponent> = TaskManagerComponent;
    const dialogRef = this.taskManagerDialog.open(component, {
      width: '1000px',
      panelClass: 'modal-dialog'
    });
    merge(dialogRef.componentInstance.close, dialogRef.backdropClick()).subscribe(() => dialogRef.close());
    dialogRef.afterClosed().pipe(take(1)).subscribe(result => {
      // I don't think we want to do anything here, but catching for now
      //maybe reload the company's tasks
      this.store.dispatch(actions.loadSelectedCustomer({ customerId: this.company.id }));
    });
  }

  onStateChanged(value: string): void {
    if (this.canadianProvinces.includes(value)) {
      this.company.country = 'Canada';
    } else {
      this.company.country = 'US';
    }
    this.company.state = value;
  }

  onChangeMcClicked(): void {
    this.editingMasterCustomer = !this.editingMasterCustomer;
    if (!this.editingMasterCustomer) {
      this.store.dispatch(actions.setLoading({ loading: true }));
      this.onSaveCompanyClicked();
    }
  }

  loadMcOpts(): void {
    this.companyService.search(this.mcParams).subscribe(res => {
      this.masterCustomers = res.collection;
      this.mcOpts = this.masterCustomers.map(mc => mc.name);
    });
  }

  onMasterCustomerSearch(value: string): void {
    this.mcParams.name = value;
    this.loadMcOpts();
  }

  onMcSelected(value: string): void {
    this.company.parentCompany = this.masterCustomers.find(mc => mc.name === value)!;
  }

  onUpdateParams(value: string | number, filterKey: CustomerDetailsFilterKeys, key: string, optionKey: CustomerDetailsOptionsKeys): void {
    this.store.dispatch(actions.updateParams({ key, value, filterKey, optionKey }));
  }

  onFilterChanged(value: string | boolean | number, key: string): void {
    this.store.dispatch(actions.updateFilters({ key, value }));
  }

  onSaveCompanyTasksClicked(task: CompanyTask): void {
    // this.companyTaskService.save(task).subscribe(res =>
    this.store.dispatch(actions.saveCustomerTask({ customerTask: task }));
  }

  getMaxDate(includeTime: boolean): string {
    return (includeTime) ? '9999-12-31T00:00' : '9999-12-31';
  }

  openCustomerTasks(event: any) {
    const component: ComponentType<TaskManagerComponent> = TaskManagerComponent;
    const dialogRef = this.taskManagerDialog.open(component, {
      width: '500px',
      panelClass: 'modal-dialog'
    });
    merge(dialogRef.componentInstance.close, dialogRef.backdropClick()).subscribe(() => dialogRef.close());
    dialogRef.afterClosed().pipe(take(1)).subscribe(result => {
      // I don't think we want to do anything here, but catching for now
      //maybe reload the company's tasks
    });
  }

  onDeleteClicked() {
    this.companyService.delete(this.company).subscribe(() => {
      if (this.company.type == CompanyType.MASTER_CUSTOMER) {
        this.router.navigate(['/customers/masterCustomers']);
      } else {
        this.store.dispatch(actions.setSelectedTab({ tab: 'Details' }));
        this.router.navigate(['/customers/masterCustomers', this.company.parentCompany.id])
      }
    });
  }
}
