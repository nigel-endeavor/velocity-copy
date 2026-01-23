import { Component } from "@angular/core";
import { AbstractTableComponent } from "../abstract-table/abstract-table.component";
import { CompanyView } from "src/app/models/company-view.model";
import { CompanyViewSearchCriteria } from "src/app/models/company-view-search-criteria.model";
import { SecurityUtilService } from "src/app/services/security-util.service";
import { Store } from "@ngrx/store";
import { CommonColumn } from "src/app/interfaces/columns.interface";
import { CompanyViewService } from "src/app/services/company-view.service";
import { COMPANY_VIEW_COLUMNS } from "./data/customer-details-columns.const";
import { getStateColor } from "../../utilities";

@Component({
  selector: 'app-company-view-table',
  templateUrl: '../abstract-table/abstract-table.component.html',
  styleUrls: ['../abstract-table/abstract-table.component.scss']
})
export class CompanyViewTableComponent extends AbstractTableComponent<CompanyView> {
  override persistFilters = true;
  override searchCriteria: CompanyViewSearchCriteria = new CompanyViewSearchCriteria();
  override allowLimitToggle = true;

  constructor(
    private companyViewService: CompanyViewService,
    public override store: Store,
    public override securityUtils: SecurityUtilService
  ) {
    super(store, securityUtils);
  }

  override getModelService() {
    return this.companyViewService;
  }

  override getStatusColor(row: any): string {
    switch (row.status) {
      case 'active':
        return '#2A9041'; //green
      case 'onboarding':
        return '#5DCADE'; //light blue
      default:
        return '#565656'; //gray
    }
  }
}
