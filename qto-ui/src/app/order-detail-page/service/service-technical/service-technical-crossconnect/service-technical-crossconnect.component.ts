import { Component, EventEmitter, Input, OnDestroy, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Service } from '../../../../models/service.model';
import { LookupValueService } from '../../../../services/lookup-value.service';
import { plainToClass } from 'class-transformer';
import { OrderEditService } from '../../../order-edit.service';
import { select, Store } from '@ngrx/store';
import { editEnabled, getFlagByName, getIsInventory, getIsLoading, getIsReadOnly } from '../../../ngrx/order-details.selectors';
import { CrossConnectService } from '../../../../models/cross-connect-service.model';
import { ServiceTechnicalTooltipsService} from "../service-technical-tooltips.service";

@Component({
  selector: 'app-service-technical-crossconnect',
  templateUrl: './service-technical-crossconnect.component.html',
  styleUrls: ['../../../form-styles.scss']
})
export class ServiceTechnicalCrossConnectComponent implements OnInit, OnDestroy {
  public editEnabled$ = this.store.pipe(select(editEnabled));
  public isLoading$ = this.store.pipe(select(getIsLoading));
  public isReadOnly$ = this.store.pipe(select(getIsReadOnly))
  public isInventory$ = this.store.pipe(select(getIsInventory));
  public editingServiceCircuit = false;
  public editingServiceProvider = false;
  @Input() service: CrossConnectService;
  @Input() companyId: number;
  @Output() save = new EventEmitter<Service>();

  @ViewChild('serviceForm') serviceForm: NgForm;

  providerOpts: string[];
  crossConnectTypeOpts: string[];

  constructor(
    public oes: OrderEditService,
    private lookupValueService: LookupValueService,
    private store: Store,
    public tooltips: ServiceTechnicalTooltipsService
  ) {
    this.store.pipe(select(getFlagByName('editingServiceCircuit'))).subscribe(res => {
      this.editingServiceCircuit = res;
    });
    this.store.pipe(select(getFlagByName('editingServiceProvider'))).subscribe(res => {
      this.editingServiceProvider = res;
    })
  }

  ngOnInit(): void {
    this.isReadOnly$.subscribe();
    this.lookupValueService.getValues('PROVIDER', this.companyId).subscribe((values: string[]) => { this.providerOpts = values; });
    this.lookupValueService.getValues('CROSS_CONNECT_TYPE', this.companyId).subscribe((values: string[]) => { this.crossConnectTypeOpts = values; });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['service']) {
      this.setService(this.service);
    }
  }

  setService(service: CrossConnectService): void {
    this.service = plainToClass(CrossConnectService, service);
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
