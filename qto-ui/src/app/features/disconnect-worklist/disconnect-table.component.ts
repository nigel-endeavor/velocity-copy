import { Component, Inject } from '@angular/core';
import { AbstractTableComponent } from '../abstract-table/abstract-table.component';
import { DisconnectViewService } from '../../services/disconnect-view.service';
import { getStatusColor } from '../../utilities';
import { Store } from '@ngrx/store';
import { SecurityUtilService } from '../../services/security-util.service';
import { LocalStoreService } from '../local-store/local-store.service';
import { SharedTableEffectsConfigToken } from '../shared-table/tokens';
import { SharedTableConfig } from '../shared-table/interfaces';
import { DisconnectView } from '../../models/disconnect-view.model';

@Component({
  selector: 'app-disconnect-view-table',
  templateUrl: '../abstract-table/abstract-table.component.html',
  styleUrls: ['../abstract-table/abstract-table.component.scss']
})
export class DisconnectTableComponent extends AbstractTableComponent<DisconnectView> {
  override persistFilters = true;
  override cardPageCount = 2;
  override allowLimitToggle = true;

  constructor(
    private disconnectViewService: DisconnectViewService,
    public override store: Store,
    public override securityUtils: SecurityUtilService,
    private localStoreService: LocalStoreService,
    @Inject(SharedTableEffectsConfigToken) private config: SharedTableConfig
  ) {
    super(store, securityUtils);

  }

  override getModelService() {
    return this.disconnectViewService;
  }

  override getStatusColor(row: any) {
    return getStatusColor(row.status)
  }

  override showNoteIcon(row: any): boolean {
    return row.showNoteIcon;
  }

  override showJeopIcon(row: any): boolean {
    return row.showJeopIcon;
  }

  override showDisconnectIcon(row: any): boolean {
    return row.showOpenDisconnectIcon;
  }

  override showLinkedIcon(row: any): boolean {
    return row.linked;
  }

  override showBundledIcon(row: any): boolean {
    return row.bundled;
  }
}
