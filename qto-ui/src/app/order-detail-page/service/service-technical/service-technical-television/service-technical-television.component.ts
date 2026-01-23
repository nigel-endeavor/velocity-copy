import { Component, EventEmitter, Input, OnDestroy, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { plainToClass } from 'class-transformer';
import { TelevisionService } from 'src/app/models/television-service.model';
import { Service } from '../../../../models/service.model';
import { OrderEditService } from '../../../order-edit.service';
import { LookupValueService } from '../../../../services/lookup-value.service';
import { select, Store } from '@ngrx/store';
import {editEnabled, getFlagByName, getIsInventory, getIsLoading, getIsReadOnly} from '../../../ngrx/order-details.selectors';
import { ServiceTechnicalTooltipsService} from "../service-technical-tooltips.service";



@Component({
  selector: 'app-service-technical-television',
  templateUrl: './service-technical-television.component.html',
  styleUrls: ['../../../form-styles.scss']
})
export class ServiceTechnicalTelevisionComponent implements OnInit, OnDestroy {
  public editEnabled$ = this.store.pipe(select(editEnabled));
  public isReadOnly$ = this.store.pipe(select(getIsReadOnly))
  public isLoading$ = this.store.pipe(select(getIsLoading));
  public isInventory$ = this.store.pipe(select(getIsInventory));

  @Input() service: TelevisionService;
  @Input() companyId: number;
  @Output() save = new EventEmitter<Service>();

  @ViewChild('serviceForm') serviceForm: NgForm;
  providerOpts: string[];
  mediaTypeOpts: string[];
  yesNoOpts: string[] = ['Yes', 'No'];
  installIntervalOpts: string[];
  constructionIntervalOpts: string[];
  public editingServiceProvider = false;
  public editingPlanDetails = false;
  public editingServiceEquipment = false;

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
    this.lookupValueService.getValues('PROVIDER', this.companyId).subscribe((values: string[]) => { this.providerOpts = values; });
    this.store.pipe(select(getFlagByName('editingPlanDetails'))).subscribe(res => {
      this.editingPlanDetails = res;
    });
    this.lookupValueService.getValues('MEDIA_TYPE', this.companyId).subscribe((values: string[]) => { this.mediaTypeOpts = values; });
    this.lookupValueService.getValues('PRODUCT_INSTALL_INTERVAL', this.companyId).subscribe((values: string[]) => { this.installIntervalOpts = values; });
    this.lookupValueService.getValues('OSP_CONS_INTERVAL', this.companyId).subscribe((values: string[]) => { this.constructionIntervalOpts = values; });
    this.store.pipe(select(getFlagByName('editingServiceEquipment'))).subscribe(res => {
      this.editingServiceEquipment = res;
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['service']) {
      this.setService(this.service);
    }
  }

  setService(service: TelevisionService): void {
    this.service = plainToClass(TelevisionService, service);
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
