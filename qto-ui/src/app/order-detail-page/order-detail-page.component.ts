import { Component, ElementRef, HostListener, OnDestroy, OnInit, ViewChild} from '@angular/core';
import { EmailValidator, NgForm } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { CompanySearchCriteria } from '../models/company-search-criteria';
import { Company } from '../models/company.model';
import { Location } from '../models/location.model';
import { PaginatedResult } from '../models/paginated-result.model';
import { Service } from '../models/service.model';
import { CompanyService } from '../services/company.service';
import { addressToString, getBaseUrl, getStatusColor } from '../utilities';
import { OrderEditService } from './order-edit.service';
import { CompanyType } from '../models/constants/company-type';
import { TerminalOrderStatuses } from '../models/constants/terminal-order-statuses';
import { select, Store } from '@ngrx/store';
import { clearStore, loadCompanyConfig, loadUserTenantAccess, onReadParams, saveOrder, setIsInventory, toggleEdit } from './ngrx/order-details.actions';
import { editEnabled, getClientManagersAsOptions, getFlagByName, getIsEditing, getIsInventory, getIsLoading, getIsReadOnly,
  getOrder, getProvisioners, getSelectedisLocation, getSelectedLocation, getSelectedLocationOrService, getSelectedMasterCompany } from './ngrx/order-details.selectors';
import { Observable, combineLatest, map, take } from 'rxjs';
import { Order } from '../models/order.model';
import { ErrorDialogComponent } from '../components/error-dialog/error-dialog.component';
import { ErrorDialogService } from '../components/error-dialog/error-dialog.service';

@Component({
  selector: 'app-order-detail-page',
  templateUrl: './order-detail-page.component.html',
  styleUrls: ['./order-detail-page.component.scss'],
})
export class OrderDetailPageComponent implements OnInit, OnDestroy {

  public editingBillingAddress$ = this.store.pipe(select(getFlagByName('editingBillingAddress')));
  public isLoading$ = this.store.pipe(select(getIsLoading));
  public isInventory$ = this.store.pipe(select(getIsInventory));
  public selectedisLocation$ = this.store.pipe(select(getSelectedisLocation));
  public order$ = this.store.pipe(select(getOrder)).pipe(map((order) => {
    if (order) {
      this.oes.setOrder({ ...order } as Order);
    this.originalEcValue = this.oes.order?.company;
    this.originalMcValue = this.oes.order?.company.parentCompany;
      this.provisioners$.subscribe(provisioners => {
        if (provisioners) {
          this.accountManager = provisioners.find(p => p.id === this.oes.order?.company.parentCompany.accountManager)?.displayName;
        }
      });
    }
    return order
  }));

  public isReadOnly$ = this.store.pipe(select(getIsReadOnly)).pipe(map(isReadonly => {
    this.oes.isReadOnly = isReadonly;
    return isReadonly
  }));

  public editEnabled$ = this.store.pipe(select(editEnabled));
  public selectedLocationOrService$ = this.store.pipe(select(getSelectedLocationOrService));
  public selectedLocation$ = this.store.pipe(select(getSelectedLocation));
  public orderDetailsCollapsed = false
  public editingRequirementsTemplate = false;
  public editingClientOrderId = false;
  public editingMasterCustomer = false;
  public editingAssignmentCard = false;
  public editingSalesContact = false;
  public editingTechContact = false;
  public editingAuthContact = false;
  public masterCompany: Company | null = null;
  public ecParams: CompanySearchCriteria = new CompanySearchCriteria();
  public masterCustomerCriteria = new CompanySearchCriteria();
  public accountManager: string | undefined = undefined;
  public ecLoading = false;
  public validationError = false;
  public originalMcValue: any;
  public originalEcValue: any;

  endCustomers: Company[];
  masterCustomers: Company[];
  endCustomerNames: string[];
  masterCustomerNames: string[];
  selectedEndCompany: string;
  public provisioners$ = this.store.pipe(select(getProvisioners));
  public clientProjectManagers$ = this.store.pipe(select(getClientManagersAsOptions));
  readonly CompanyType = CompanyType;
  terminalOrderStatuses: string[] = Object.values(TerminalOrderStatuses);
  selectedTab: string = 'billing';
  @ViewChild('orderForm') orderForm: NgForm;
  @ViewChild('salesContactEmail') salesContactEmail: ElementRef;
  @ViewChild('salesContactPhone') salesContactPhone: ElementRef;
  @ViewChild('techContactEmail') techContactEmail: ElementRef;
  @ViewChild('techContactPhone') techContactPhone: ElementRef;
  @ViewChild('authContactEmail') authContactEmail: ElementRef;
  @ViewChild('authContactPhone') authContactPhone: ElementRef

  worklist: string;
  public cyberClient: boolean = false;
  public telecomClient: boolean = false;

  constructor(private route: ActivatedRoute,
              private companyService: CompanyService,
              public oes: OrderEditService,
              public router: Router,
              private titleService: Title,
              private store: Store,
              private errorDialogService: ErrorDialogService,
  ) {
    this.store.pipe(select(getFlagByName('editingClientOrderId'))).subscribe(res => {
      this.editingClientOrderId = res;
    });
    this.store.pipe(select(getFlagByName('editingMasterCustomer'))).subscribe(res => {
      this.editingMasterCustomer = res;
    });
    this.store.pipe(select(getFlagByName('editingSalesContact'))).subscribe(res => {
      this.editingSalesContact = res;
    });
    this.store.pipe(select(getFlagByName('editingTechContact'))).subscribe(res => {
      this.editingTechContact = res;
    });
    this.store.pipe(select(getFlagByName('editingAuthContact'))).subscribe(res => {
      this.editingAuthContact = res;
    });
    this.store.pipe(select(getFlagByName('editingAssignmentCard'))).subscribe(res => {
      this.editingAssignmentCard = res;
    });
    this.store.pipe(select(getFlagByName('editingRequirementsTemplate'))).subscribe(res => {
      this.editingRequirementsTemplate = res;
    });

    this.store.pipe(select(getSelectedMasterCompany)).subscribe(res => {
      this.masterCompany = res;
    });

    this.store.pipe(select(getIsEditing)).subscribe(isEditing => {
      this.oes.isEditing = isEditing;
    });
  }

  ngOnInit(): void {
    this.cyberClient = localStorage.getItem('CYBER_SECURITY_CLIENT') === 'true';
    this.telecomClient = localStorage.getItem('TELECOM_CLIENT') === 'true';
    let isInventory = !!this.route.snapshot.data['inventory'];
    this.store.dispatch(setIsInventory({isInventory}));
    this.store.dispatch(loadCompanyConfig());
    this.store.dispatch(loadUserTenantAccess());
    if (isInventory) {
      this.worklist = (localStorage.getItem('inventory-worklist') || 'locations');
    } else {
      this.worklist = (localStorage.getItem('worklist') || 'locations');
    }

    // this.initMasterCustomerDropdowns();
    this.route.params.subscribe(params => {
      this.store.dispatch(onReadParams(params));
    });

    setTimeout(() => {
      if (this.orderForm) {
        this.oes.addForm(this.orderForm);
      }
    }, 1);

    this.isReadOnly$.subscribe();
  }

  ngOnDestroy(): void {
    this.titleService.setTitle('i90');
    this.store.dispatch(clearStore());
  }

  initMasterCustomerDropdowns(): void {
    this.masterCustomerCriteria.limit = 1000;
    this.masterCustomerCriteria.active = true;
    this.masterCustomerCriteria.type = CompanyType.MASTER_CUSTOMER;
    this.companyService.search(this.masterCustomerCriteria).subscribe((res: PaginatedResult<Company>) => {
      this.masterCustomers = res.collection;
      if (this.oes.order?.company.parentCompany.name) {
        this.onMasterCustomerSelected(this.oes.order?.company.parentCompany);
      }
    });
  }

  onRequirementTemplateDialogClosed(): void {
    this.store.dispatch(toggleEdit({ key: 'editingRequirementsTemplate' }));
  }

  @ViewChild('shareTooltip') tooltip: ElementRef;
  onShareClicked(): void {
    navigator.clipboard.writeText(window.location.href);
    this.tooltip.nativeElement.style.display = 'block';
    //fade out tooltip
    setTimeout(() => {
      this.tooltip.nativeElement.style.opacity = 0;
      this.tooltip.nativeElement.style['-webkit-transition'] = 'opacity 2s ease-in-out';
    }, 1000);
    //set tooltip element back to its original state
    setTimeout(() => {
      this.tooltip.nativeElement.style.opacity = 100;
      this.tooltip.nativeElement.style.display = 'none';
    }, 3000);
  }

  //listens for page click events
  //used to toggle the master customer and clientOrderId edit flags
  //also used to save the order when the user clicks outside of the edit fields
  @ViewChild('clientOrderIdInput') clientOrderIdInput: ElementRef;
  @ViewChild('clientOrderIdLabel') clientOrderIdLabel: ElementRef;
  @ViewChild('masterCustomerInput') masterCustomerInput: ElementRef;
  @ViewChild('masterCustomerLabel') masterCustomerLabel: ElementRef;
  @ViewChild('errorDialog') errorDialogRef: ErrorDialogComponent;

  errorRef: any = null;
  @HostListener('document:click', ['$event']) toggleEditingClientOrderId(event: Event) {
    if (this.oes.isReadOnly) {
      return;
    }
    //if the user clicks on the clientOrderId label, toggle the edit flag
    if (this.clientOrderIdLabel && this.clientOrderIdLabel.nativeElement.contains(event.target) && !this.editingClientOrderId) {
      this.oes.toggleEditFlag('editingClientOrderId')
    }
    //if one of the the edit flags is true and the user clicks outside of the edit fields, save the order
    if ((this.editingClientOrderId || (this.editingMasterCustomer && this.oes.order?.company.parentCompany))
      && !this.clientOrderIdInput?.nativeElement.contains(event.target)
      && !this.clientOrderIdLabel?.nativeElement.contains(event.target)
      && (!this.errorRef || !this.errorRef.contains(event.target))) {
      //validate the order before saving
      //@ts-ignore
      if ([...this.oes.forms.values()].some(form => form.invalid)) {
        throw new Error('Validation Error: Please correct the highlighted fields before saving');
      }
      // if (this.ecSelected && (this.ecSelected != this.originalEcValue)) {
      //   this.ecDropdown = false;
      //   this.errorRef = this.errorDialogService.openDialog('Save end customer before making further changes.');
      //   return;
      // }
      // if (this.ecSelected = this.originalEcValue) {
      //   this.ecDropdown = false;
      // }
      // this.store.dispatch(saveOrder());
    }
  }

  onToggleIsEditFlag (key: string): void {
    this.store.dispatch(toggleEdit({
      key
    }))
  }

  getStatusColor(): Observable<string> {
    return combineLatest([this.isInventory$, this.selectedLocation$]).pipe(
      take(1),
      map(([isInventory, selectedLocation]) => {
        if (isInventory) {
          if (selectedLocation?.active) {
            return '#2A9041';//green
          } else {
            return '#565656'; //gray
          }
        } else {
          return getStatusColor(this.oes.order!.status);
        }
      })
    );
  }

  onSaveClicked(): void {
    //@ts-ignore
    if ([...this.oes.forms.values()].some(form => form.invalid)) {
      throw new Error('Validation Error: Please correct the highlighted fields before saving');
    }
    if (!this.oes.order!.company) {
      alert('Please select an End Customer for this order.');
      return;
    }
    if ((this.editingSalesContact && (this.salesContactEmail.nativeElement.classList.contains('ng-invalid') || this.salesContactPhone.nativeElement.classList.contains('ng-invalid')))
        || (this.editingTechContact && (this.techContactEmail.nativeElement.classList.contains('ng-invalid') || this.techContactPhone.nativeElement.classList.contains('ng-invalid')))
        || (this.editingAuthContact && (this.authContactEmail.nativeElement.classList.contains('ng-invalid') || this.authContactPhone.nativeElement.classList.contains('ng-invalid')))) {
      throw new Error('Validation Error: Please correct the highlighted fields before saving');
    }
    this.store.dispatch(saveOrder());
  }

  onWorklistClicked(): void {
    this.isInventory$.pipe(take(1)).subscribe(isInventory => {
      if (isInventory) {
        this.router.navigate(['inventory', this.worklist]);
      } else {
        this.router.navigate([this.worklist]);
      }
    });
  }

  navigateToOrder(): void {
    this.isInventory$.pipe(take(1)).subscribe(isInventory => {
      if (isInventory) {
        this.selectedLocationOrService$.pipe(take(1)).subscribe(selected => {
          if (!selected) {
            return;
          }
          if (selected instanceof Location) {
            this.router.navigate(['order', selected.orderId, 'location', selected.id]);
          } else {
            this.router.navigate(['order', selected.orderId, 'location', (selected as Service).locationId, 'service', selected.id]);
          }
        });
      }
    });
  }

  companyAddressToString(): string {
    if (this.oes.order?.company) {
      return addressToString(this.oes.order?.company.address1, this.oes.order?.company.address2, this.oes.order?.company.city, this.oes.order?.company.state, this.oes.order?.company.postalCode, this.oes.order?.company.country);
    }
    return '';
  }

  onMasterCustomerSelected(selectedCustomer: any) {
    if (selectedCustomer) {
      if (typeof (selectedCustomer) === 'string') {
        this.ecParams.masterCustomers = [selectedCustomer];
      } else {
        this.ecParams.masterCustomers = selectedCustomer.name;
      }
      this.loadEndCustomers();
      this.selectedEndCompany = '';
    } else {
      this.ecParams.masterCustomers = [];
    }
  }

  loadEndCustomers() {
    this.ecLoading = true; // Set loading flag
    this.ecParams.limit = 1000;
    this.ecParams.active = true;
    this.ecParams.type = CompanyType.END_CUSTOMER;
    this.companyService.search(this.ecParams).subscribe((res: PaginatedResult<Company>) => {
      this.endCustomers = res.collection;
      if (this.endCustomers.length === 0) {
        //@ts-ignore
        this.oes.order.company.parentCompany = this.originalMcValue;
        throw new Error('Master customer ' + this.ecParams.masterCustomers + ' has no associated end customers.')
      }
      // @ts-ignore
      if (!this.endCustomers.find(c => c.name === this.oes.order.company.name)) {
        this.onEcClick(this.endCustomers[0].name);
      } else {
        // @ts-ignore
        this.selectedEndCompany = this.oes.order.company.name;
      }
      this.ecLoading = false; // Clear loading flag when data is loaded
    });
  }

  toggleMC() {
    this.oes.toggleEditFlag('editingMasterCustomer')
    this.initMasterCustomerDropdowns();
    if (this.orderForm) {
      setTimeout(() => {
        if (this.orderForm) {
          this.oes.addForm(this.orderForm);
        }
      }, 1);
    }
  }

  navToMc() {
    let custId = this.oes.order?.company.parentCompany.id;
    const url = `${getBaseUrl()}customers/masterCustomers/${custId}`;
    window.open(url);
  }

  navToEc() {
    let custId = this.oes.order?.company.id;
    const url = `${getBaseUrl()}customers/endCustomers/${custId}`;
    window.open(url);
  }

  onEcClick(selectedDropDownEC: any) {
    const endCustomer = this.endCustomers.find(c => c.name === selectedDropDownEC);
    // @ts-ignore
    this.selectedEndCompany = this.oes.order.company.name;
    // @ts-ignore
    this.oes.order.company = endCustomer;
  }

  onOrderSave(selectedDropDownEC: any) {
    // @ts-ignore
    if (this.selectedEndCompany === '') {
      throw new Error('Validation Error: End Customer is required.');
    }

    // @ts-ignore
    if (this.oes.order.inventoryOrderId) {
      const save = confirm('Some items on this Order have already moved into inventory and will have to be updated there.  Do you wish to proceed?');
      if (save) {
        this.store.dispatch(saveOrder());
      } else {
        this.oes.resetForms();
      }
    } else {
      this.store.dispatch(saveOrder());
    }
  }
}
