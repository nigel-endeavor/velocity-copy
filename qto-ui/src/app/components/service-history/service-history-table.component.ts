import { Component } from "@angular/core";
import { Store } from "@ngrx/store";
import { AbstractTableComponent } from "src/app/features/abstract-table/abstract-table.component";
import { CommonColumn } from "src/app/interfaces/columns.interface";
import { SecurityUtilService } from "src/app/services/security-util.service";
import { SERVICE_HISTORY_COLUMNS } from "./data/service-history-columns.const";
import { ServiceHistoryViewService } from "src/app/services/service-history-view.service";
import { ServiceHistoryViewSearchCriteria } from "src/app/models/service-history-view-search-critiera.model";
import { ServiceHistoryView } from "src/app/models/service-history-view.model";
import { getStatusColor } from "src/app/utilities";


@Component({
  selector: 'app-service-history-table',
  templateUrl: '../../features/abstract-table/abstract-table.component.html',
  styleUrls: ['../../features/abstract-table/abstract-table.component.scss']
})
export class ServiceHistoryTableComponent extends AbstractTableComponent<ServiceHistoryView> {
  override searchCriteria: ServiceHistoryViewSearchCriteria = new ServiceHistoryViewSearchCriteria();

  constructor(
    private serviceHistoryService: ServiceHistoryViewService,
    public override store: Store,
    public override securityUtils: SecurityUtilService,
  ) {
    super(store, securityUtils);
  }

  override columns: CommonColumn[] = SERVICE_HISTORY_COLUMNS;

  override getModelService() {
    return this.serviceHistoryService;
  }

  override getStatusColor(row: any) {
    return getStatusColor(row.serviceStatus);
  }
}