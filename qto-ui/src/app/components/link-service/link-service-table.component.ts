import { Component } from "@angular/core";
import { AbstractTableComponent } from "src/app/features/abstract-table/abstract-table.component";
import { CommonColumn } from "src/app/interfaces/columns.interface";
import { ServiceViewSearchCriteria } from "src/app/models/service-view-search-criteria.model";
import { ServiceView } from "src/app/models/service-view.model";
import { SecurityUtilService } from "src/app/services/security-util.service";
import { LINK_SERVICE_COLUMNS } from "./data/link-service-columns.const";
import { Store } from "@ngrx/store";
import { LinkServicesService } from "../../services/link-services.service";

@Component({
  selector: 'app-link-service-table',
  templateUrl: '../../features/abstract-table/abstract-table.component.html',
  styleUrls: ['../../features/abstract-table/abstract-table.component.scss']
})
export class LinkServiceTableComponent extends AbstractTableComponent<ServiceView> {
  override searchCriteria: ServiceViewSearchCriteria = new ServiceViewSearchCriteria();

  constructor(
    private linkServicesService: LinkServicesService,
    public override store: Store,
    public override securityUtils: SecurityUtilService
  ) {
    super(store, securityUtils);
  }

  override columns: CommonColumn[] = LINK_SERVICE_COLUMNS;

  override getModelService() {
    return this.linkServicesService;
  }

  override getStatusColor(row: any): string {
    return '#FFFFFF';
  }
}
