import { Component, EventEmitter, Input, OnDestroy, OnInit, Output, SimpleChanges, ViewChild } from "@angular/core";
import { NgForm } from "@angular/forms";
import { select, Store } from '@ngrx/store';
import { plainToClass } from "class-transformer";
import { MicrosoftLicenses } from "src/app/models/microsoft-licenses.model";
import { Service } from "src/app/models/service.model";
import { editEnabled, getFlagByName, getIsInventory, getIsLoading, getIsReadOnly } from "src/app/order-detail-page/ngrx/order-details.selectors";
import { OrderEditService } from "src/app/order-detail-page/order-edit.service";
import { LookupValueService } from "src/app/services/lookup-value.service";
import { ServiceTechnicalTooltipsService} from "../service-technical-tooltips.service";

@Component({
  selector: 'app-service-technical-microsoft-licenses',
  templateUrl: './service-technical-microsoft-licenses.component.html',
  styleUrls: ['../../../form-styles.scss']
})

export class ServiceTechnicalMicrosoftLicensesComponent implements OnInit, OnDestroy {

  public editEnabled$ = this.store.pipe(select(editEnabled));
  public isReadOnly$ = this.store.pipe(select(getIsReadOnly))
  public isLoading$ = this.store.pipe(select(getIsLoading));
  public isInventory$ = this.store.pipe(select(getIsInventory));

  @Input() service: MicrosoftLicenses;
  @Input() companyId: number;
  @Output() save = new EventEmitter<Service>();
  @ViewChild('serviceForm') serviceForm: NgForm;
  providerOpts: string[];
  public editingServiceProvider = false;
  public editingLicenseOptions = false;

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
    this.store.pipe(select(getFlagByName('editingLicenseOptions'))).subscribe(res => {
      this.editingLicenseOptions = res;
    });
    this.lookupValueService.getValues('PROVIDER', this.companyId).subscribe((values: string[]) => { this.providerOpts = values; });

  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['service']) {
      this.setService(this.service);
    }
  }

  setService(service: MicrosoftLicenses): void {
    this.service = plainToClass(MicrosoftLicenses, service);
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
