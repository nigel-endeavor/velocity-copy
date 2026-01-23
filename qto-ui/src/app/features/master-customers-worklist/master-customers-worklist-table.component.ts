import { Component, Inject } from '@angular/core';
import { AbstractTableComponent } from '../abstract-table/abstract-table.component';
import { getStateColor } from '../../utilities';
import { Store } from '@ngrx/store';
import { SecurityUtilService } from '../../services/security-util.service';
import { LocalStoreService } from '../local-store/local-store.service';
import { SharedTableEffectsConfigToken } from '../shared-table/tokens';
import { SharedTableConfig } from '../shared-table/interfaces';
import { CompanyView } from '../../models/company-view.model';
import { CompanyViewService } from '../../services/company-view.service';

@Component({
  selector: 'master-customers-table',
  templateUrl: '../abstract-table/abstract-table.component.html',
  styleUrls: ['../abstract-table/abstract-table.component.scss']
})
export class MasterCustomersWorklistTableComponent extends AbstractTableComponent<CompanyView> {
  override persistFilters = true;
  override cardPageCount = 2;
  override allowLimitToggle = true;

  constructor(
    private companyViewService: CompanyViewService,
    public override store: Store,
    public override securityUtils: SecurityUtilService,
    private localStoreService: LocalStoreService,
    @Inject(SharedTableEffectsConfigToken) private config: SharedTableConfig
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
