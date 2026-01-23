import { Component, EventEmitter, Input, OnDestroy, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { GService } from '../../../../models/g-service.model';
import { LookupValueService } from '../../../../services/lookup-value.service';
import { plainToClass } from 'class-transformer';
import { OrderEditService } from '../../../order-edit.service';
import { select, Store } from '@ngrx/store';
import { editEnabled, getFlagByName, getIsInventory, getIsLoading, getIsReadOnly } from '../../../ngrx/order-details.selectors';
import { ServiceTechnicalTooltipsService} from "../service-technical-tooltips.service";


@Component({
  selector: 'app-service-technical-g',
  templateUrl: './service-technical-g.component.html',
  styleUrls: ['../../../form-styles.scss']
})
export class ServiceTechnicalGComponent implements OnInit, OnDestroy {
  public editEnabled$ = this.store.pipe(select(editEnabled));
  public isLoading$ = this.store.pipe(select(getIsLoading));
  public isReadOnly$ = this.store.pipe(select(getIsReadOnly));
  public isInventory$ = this.store.pipe(select(getIsInventory));

  @Input() service: GService;
  @Input() companyId: number;
  @Output() save = new EventEmitter<GService>();

  @ViewChild('serviceForm') serviceForm: any;

  providerOpts: string[];
  replace4g5gOpts: string[];
  circuitPriorityOpts: string[];
  public editingServiceEquipment = false;
  public editingServiceCircuit = false;
  public editingServiceProvider = false;

  constructor(
    public oes: OrderEditService,
    private lookupValueService: LookupValueService,
    private store: Store,
    public tooltips: ServiceTechnicalTooltipsService
  ) {
    this.store.pipe(select(getFlagByName('editingServiceEquipment'))).subscribe(res => {
      this.editingServiceEquipment = res;
    });
    this.store.pipe(select(getFlagByName('editingServiceCircuit'))).subscribe(res => {
      this.editingServiceCircuit = res;
    });
    this.store.pipe(select(getFlagByName('editingServiceProvider'))).subscribe(res => {
      this.editingServiceProvider = res;
    });
  }

  ngOnInit(): void {
    this.isReadOnly$.subscribe();
    this.lookupValueService.getValues('PROVIDER', this.companyId).subscribe((values: string[]) => { this.providerOpts = values; });
    this.lookupValueService.getValues('ACTIVATION_REPLACE_4G5G', this.companyId).subscribe((values: string[]) => { this.replace4g5gOpts = values; });
    this.lookupValueService.getValues('CIRCUIT_PRIORITY', this.companyId).subscribe((values: string[]) => { this.circuitPriorityOpts = values; });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['service']) {
      this.setService(this.service);
    }
  }

  setService(service: GService): void {
    this.service = plainToClass(GService, service);
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
