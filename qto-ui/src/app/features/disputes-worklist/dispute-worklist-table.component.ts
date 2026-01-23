import { Component, Inject } from '@angular/core';
import { AbstractTableComponent } from '../abstract-table/abstract-table.component';
import { DisputeView } from '../../models/dispute-view.model';
import { DisputeViewService } from '../../services/dispute-view.service';
import { getStatusColor } from '../../utilities';
import { Store } from '@ngrx/store';
import { SecurityUtilService } from '../../services/security-util.service';
import { LocalStoreService } from '../local-store/local-store.service';
import { SharedTableEffectsConfigToken } from '../shared-table/tokens';
import { SharedTableConfig } from '../shared-table/interfaces';

@Component({
  selector: 'app-dispute-view-table',
  templateUrl: '../abstract-table/abstract-table.component.html',
  styleUrls: ['../abstract-table/abstract-table.component.scss']
})
export class DisputeWorklistTableComponent extends AbstractTableComponent<DisputeView> {

  override persistFilters = true;
  override allowLimitToggle = true;

  constructor(
    private disputeViewService: DisputeViewService,
    public override store: Store,
    public override securityUtils: SecurityUtilService,
    private localStoreService: LocalStoreService,
    @Inject(SharedTableEffectsConfigToken) private config: SharedTableConfig
  ) {
    super(store, securityUtils);

  }

  override getModelService() {
    return this.disputeViewService;
  }

  override getStatusColor(row: any) {
    return getStatusColor(row.disputeStatus)
  }

  override showDisputeFollowUpIcon(row: any): boolean {
      return row.showDisputeFollowUpIcon;
    }
}
