import { Component } from '@angular/core';
import { OrderEditService } from '../../order-edit.service';
import { select, Store } from '@ngrx/store';
import { editEnabled, getFlagByName, getIsInventory, getIsLoading, getSelectedLocationOrService, selectServiceBrokerage } from '../../ngrx/order-details.selectors';
// import { selectServiceBrokerage } from './store/service-brokerage.selectors';
import { Service } from 'src/app/models/service.model';
import { LookupValueService } from 'src/app/services/lookup-value.service';
import { loadLookupValuesByKey, loadServiceBrokerage, saveServiceBrokerage } from '../../ngrx/order-details.actions';
import { plainToClass } from 'class-transformer';
import { filter, map } from 'rxjs';
import { CustomFieldTabValue } from 'src/app/models/custom-field.model';
import { ServiceBrokerage } from '../../../models/service-brokerage-model';
import { LookupValue } from '../../../models/lookup-value.model';
import { CompanyConfigPropertyService } from 'src/app/services/company-config-property.service';
import { ActivatedRoute, Router } from '@angular/router';
// import { loadServiceBrokerage, saveServiceBrokerage } from './store/service-brokerage.actions';

@Component({
  selector: 'app-service-brokerage',
  templateUrl: './service-brokerage.component.html',
  styleUrls: ['../../form-styles.scss']
})
export class ServiceBrokerageComponent {
  public editEnabled$ = this.store.pipe(select(editEnabled));
  public serviceBrokerage$ = this.store.pipe(
    select(selectServiceBrokerage),
    filter(item => !!item),
    map(item => plainToClass(ServiceBrokerage, JSON.parse(JSON.stringify(item)))));
  public selectedLocationOrService$ = this.store.pipe(
    select(getSelectedLocationOrService),
    filter(item => !!item),
    map(item => {
      if (item) {
        this.store.dispatch(loadServiceBrokerage({ serviceId: item.id }));
      }
      return plainToClass(Service, JSON.parse(JSON.stringify(item)))
    })
  );
  public isLoading$ = this.store.pipe(select(getIsLoading));
  public customFieldTabValue = CustomFieldTabValue.SERVICE_BROKERAGE;

  editingServiceBrokerageOrderDetails: boolean = false;
  editingServiceBrokerageCommissions: boolean = false;
  editingServiceBrokerageAgency: boolean = false;
  editingServiceBrokerageProfitMonitoring: boolean = false;
  editingCustomFields: boolean = false;
  isInventory: boolean = false;
  companyId: number;
  parentTsdOpts: string[];
  agencyNameLvs: LookupValue[];
  agencyNameOpts: string[];
  agencyRepLvs: LookupValue[];
  agencyRepOpts: string[];
  commissionPaymentTypeOpts: string[];
  commissionsSupplierOpts: string[];
  showForm: boolean = false;

  cyberClient: boolean = false;
  telecomClient: boolean = false;

  constructor(
    public oes: OrderEditService,
    private store: Store,
    private lookupValueService: LookupValueService,
    private companyConfigService: CompanyConfigPropertyService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.store.pipe(select(getFlagByName('editingServiceBrokerageOrderDetails'))).subscribe(res => {
      this.editingServiceBrokerageOrderDetails = res;
    });
    this.store.pipe(select(getFlagByName('editingServiceBrokerageCommissions'))).subscribe(res => {
      this.editingServiceBrokerageCommissions = res;
    });
    this.store.pipe(select(getFlagByName('editingServiceBrokerageAgency'))).subscribe(res => {
      this.editingServiceBrokerageAgency = res;
    });
    this.store.pipe(select(getFlagByName('editingServiceBrokerageProfitMonitoring'))).subscribe(res => {
      this.editingServiceBrokerageProfitMonitoring = res;
    });
    this.store.pipe(select(getIsInventory)).subscribe(res => {
      this.isInventory = res;
    });
  }

  ngOnInit(): void {
    this.cyberClient = localStorage.getItem('CYBER_SECURITY_CLIENT') === 'true';
    this.telecomClient = localStorage.getItem('TELECOM_CLIENT') === 'true';
    setTimeout(() => {
      // This is a hack to prevent the screen from 'flickering' when brokerage tab is navigated to
      this.showForm = true;
    }, 0);
    // If the company config is set to hide brokerage fields, navigate back to the order details page
    this.companyConfigService.getValue('SHOW_BROKERAGE_FIELDS').subscribe((res) => {
      if (!res || !res.value || res.value == 'false') {
        this.router.navigate(['../'], { relativeTo: this.route });
      }
    });
    this.companyId = this.oes.order!.company.id;
    this.lookupValueService.getValues('PARENT_TSD', this.companyId).subscribe((values: string[]) => { this.parentTsdOpts = values; });
    this.lookupValueService.find('AGENCY_NAME', this.companyId).subscribe((values: LookupValue[]) =>
      {
        this.agencyNameLvs = values;
        this.agencyNameOpts = values.map(lv => lv.value);
      });
    this.lookupValueService.find('AGENCY_REP', this.companyId).subscribe((values: LookupValue[]) => { this.agencyRepLvs = values; });
    this.lookupValueService.getValues('COMMISSIONS_PAYMENT_TYPE', this.companyId).subscribe((values: string[]) => { this.commissionPaymentTypeOpts = values; });
    this.lookupValueService.getValues('COMMISSIONS_SUPPLIER', this.companyId).subscribe((values: string[]) => { this.commissionsSupplierOpts = values; });
  }

  getAgencyRepOpts(event:any): string[] {
    return this.agencyRepLvs?.filter(lv => lv.parentId == this.agencyNameLvs?.find(lv => lv.value == event)?.id).map(lv => lv.value);
  }

  onSaveClicked(service: ServiceBrokerage) {
    if (service.agentPercent > 100 || service.subAgentPercent > 100) {
      throw Error("Agent percentages must be under 100%.");
    }
    this.store.dispatch(saveServiceBrokerage({ serviceBrokerage: service }));
  }
  referralChanged(event: any, serviceBrokerage: ServiceBrokerage) {
    if (!event) {
      serviceBrokerage.referralName = undefined;
      serviceBrokerage.referralPercent = undefined;
    }
  }
  engineerChanged(event: any, serviceBrokerage: ServiceBrokerage) {
    if (!event) {
      serviceBrokerage.engineerResourceAllocation = undefined;
    }
  }
}
