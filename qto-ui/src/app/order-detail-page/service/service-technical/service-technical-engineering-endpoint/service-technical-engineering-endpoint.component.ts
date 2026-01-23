import { Component, EventEmitter, Input, OnDestroy, OnInit, Output, SimpleChanges, ViewChild } from "@angular/core";
import { select, Store } from "@ngrx/store";
import {
  editEnabled,
  getFlagByName,
  getIsInventory,
  getIsLoading,
  getIsReadOnly
} from "../../../ngrx/order-details.selectors";
import { Service } from "../../../../models/service.model";
import { NgForm } from "@angular/forms";
import { OrderEditService } from "../../../order-edit.service";
import { LookupValueService } from "../../../../services/lookup-value.service";
import { plainToClass } from "class-transformer";
import { EngineeringEndpointProtectionService } from "../../../../models/engineering-endpoint-service.model";
import { ServiceTechnicalTooltipsService} from "../service-technical-tooltips.service";
@Component({
  selector: 'app-service-technical-engineering-endpoint',
  templateUrl: './service-technical-engineering-endpoint.component.html',
  styleUrls: ['../../../form-styles.scss']
})

export class ServiceTechnicalEngineeringEndpointComponent implements OnInit, OnDestroy {

  public editEnabled$ = this.store.pipe(select(editEnabled));
  public isReadOnly$ = this.store.pipe(select(getIsReadOnly))
  public isLoading$ = this.store.pipe(select(getIsLoading));
  public isInventory$ = this.store.pipe(select(getIsInventory));

  @Input() service: EngineeringEndpointProtectionService;
  @Input() companyId: number;
  @Output() save = new EventEmitter<Service>();
  @ViewChild('serviceForm') serviceForm: NgForm;
  providerOpts: string[];
  public editingServiceProvider = false;
  public editingEngineeringEndpointProtectionDetails = false;

  constructor(
    public oes: OrderEditService,
    private lookupValueService: LookupValueService,
    private store: Store,
    public tooltips: ServiceTechnicalTooltipsService
  ) { }

  ngOnInit(): void {
    this.store.pipe(select(getFlagByName('editingServiceProvider'))).subscribe(res => {
      this.editingServiceProvider = res;
    });
    this.store.pipe(select(getFlagByName('editingEngineeringEndpointProtectionDetails'))).subscribe(res => {
      this.editingEngineeringEndpointProtectionDetails = res;
    });
    this.lookupValueService.getValues('PROVIDER', this.companyId).subscribe((values: string[]) => { this.providerOpts = values; });

  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['service']) {
      this.setService(this.service);
    }
  }

  setService(service: EngineeringEndpointProtectionService): void {
    this.service = plainToClass(EngineeringEndpointProtectionService, service);
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

}
