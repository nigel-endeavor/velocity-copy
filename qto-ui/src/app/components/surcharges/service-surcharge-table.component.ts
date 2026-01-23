import { Component } from '@angular/core';
import { Store } from "@ngrx/store";
import { AbstractTableComponent } from "../../features/abstract-table/abstract-table.component";
import { SecurityUtilService } from "../../services/security-util.service";
import { ServiceSurchargeService } from "../../services/service-surcharge.service";
import { ServiceSurchargeSearchCriteria } from '../../models/service-surcharge-search-criteria.model';
import { SURCHARGE_COLUMNS } from './data/surcharge-table-columns.consts';
import { CommonColumn } from '../../interfaces/columns.interface';
import { ServiceSurcharge } from '../../models/service-surcharge-model';
@Component({
  selector: 'app-service-surcharge-table',
  templateUrl: '../../features/abstract-table/abstract-table.component.html',
  styleUrls: ['../../features/abstract-table/abstract-table.component.scss']
})
export class ServiceSurchargeTableComponent extends AbstractTableComponent<ServiceSurcharge> {
  override searchCriteria: ServiceSurchargeSearchCriteria = new ServiceSurchargeSearchCriteria();

  override persistFilters = true;
  constructor(
    private serviceSurchargeService: ServiceSurchargeService,
    public override store: Store,
    public override securityUtils: SecurityUtilService,
  ) {
    super(store, securityUtils);
  }
  
  override columns: CommonColumn[] = SURCHARGE_COLUMNS;


  override getModelService() {
    return this.serviceSurchargeService;
  }

  override getStatusColor(row: any) {
    return '#FFFFFF';
  }
}