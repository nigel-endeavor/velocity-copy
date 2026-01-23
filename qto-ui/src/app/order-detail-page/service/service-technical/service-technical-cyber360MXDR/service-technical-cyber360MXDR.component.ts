import { Component, EventEmitter, Input, OnDestroy, OnInit, Output, SimpleChanges, ViewChild } from "@angular/core";
import { select, Store } from "@ngrx/store";
import {
  editEnabled,
  getFlagByName,
  getIsInventory,
  getIsLoading,
  getIsReadOnly
} from "../../../ngrx/order-details.selectors";
import { Cyber360MXDRService } from "../../../../models/cyber360MXDR-service.model";
import { Service } from "../../../../models/service.model";
import { NgForm } from "@angular/forms";
import { OrderEditService } from "../../../order-edit.service";
import { LookupValueService } from "../../../../services/lookup-value.service";
import { plainToClass } from "class-transformer";
import { ServiceTechnicalTooltipsService} from "../service-technical-tooltips.service";

@Component({
  selector: 'app-service-technical-cyber360MXDR',
  templateUrl: './service-technical-cyber360MXDR.component.html',
  styleUrls: ['../../../form-styles.scss']
})

export class ServiceTechnicalCyber360MXDRComponent implements OnInit, OnDestroy {

  public editEnabled$ = this.store.pipe(select(editEnabled));
  public isReadOnly$ = this.store.pipe(select(getIsReadOnly))
  public isLoading$ = this.store.pipe(select(getIsLoading));
  public isInventory$ = this.store.pipe(select(getIsInventory));

  @Input() service: Cyber360MXDRService;
  @Input() companyId: number;
  @Output() save = new EventEmitter<Service>();
  @ViewChild('serviceForm') serviceForm: NgForm;
  providerOpts: string[];
  public editingServiceProvider = false;
  public editingCyber360MXDRDetails = false;

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
    this.store.pipe(select(getFlagByName('editingCyber360MXDRDetails'))).subscribe(res => {
      this.editingCyber360MXDRDetails = res;
    });
    this.lookupValueService.getValues('PROVIDER', this.companyId).subscribe((values: string[]) => { this.providerOpts = values; });

  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['service']) {
      this.setService(this.service);
    }
  }

  setService(service: Cyber360MXDRService): void {
    this.service = plainToClass(Cyber360MXDRService, service);
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
