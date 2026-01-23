import { Component } from "@angular/core";
import { Store } from "@ngrx/store";
import { AbstractTableComponent } from "src/app/features/abstract-table/abstract-table.component";
import { CommonColumn } from "src/app/interfaces/columns.interface";
import { CostHistorySearchCriteria } from "src/app/models/cost-history-search-criteria.model";
import { CostHistory } from "src/app/models/cost-history.model";
import { CostHistoryService } from "src/app/services/cost-history.service";
import { SecurityUtilService } from "src/app/services/security-util.service";
import { COST_HISTORY_COLUMNS } from "./data/cost-history-columns.const";

@Component({
  selector: 'app-cost-history-table',
  templateUrl: '../../features/abstract-table/abstract-table.component.html',
  styleUrls: ['../../features/abstract-table/abstract-table.component.scss']
})
export class CostHistoryTableComponent extends AbstractTableComponent<CostHistory> {
  override searchCriteria: CostHistorySearchCriteria = new CostHistorySearchCriteria();

  constructor(
    private CostHistoryService: CostHistoryService,
    public override store: Store,
    public override securityUtils: SecurityUtilService
  ) {
    super(store, securityUtils);
  }

  override columns: CommonColumn[] = COST_HISTORY_COLUMNS;

  override getModelService() {
    return this.CostHistoryService;
  }

  override getStatusColor(row: any) {
    return '#FFFFFF';
  }
}