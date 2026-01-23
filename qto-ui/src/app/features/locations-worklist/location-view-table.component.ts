import { Component, Inject } from '@angular/core';
import { AbstractTableComponent } from '../abstract-table/abstract-table.component';
import { LocationView } from '../../models/location-view.model';
import { LocationViewService } from '../../services/location-view.service';
import { getStatusColor } from '../../utilities';
import { Store } from '@ngrx/store';
import { SecurityUtilService } from '../../services/security-util.service';
import { LocalStoreService } from '../local-store/local-store.service';
import { SharedTableEffectsConfigToken } from '../shared-table/tokens';
import { SharedTableConfig } from '../shared-table/interfaces';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-location-view-table',
  templateUrl: '../abstract-table/abstract-table.component.html',
  styleUrls: ['../abstract-table/abstract-table.component.scss']
})
export class LocationViewTableComponent extends AbstractTableComponent<LocationView> {
  override persistFilters = true;
  override cardPageCount = 2;
  override allowLimitToggle = true;

  isInventory: boolean;

  constructor(
    private locationViewService: LocationViewService,
    public override store: Store,
    public override securityUtils: SecurityUtilService,
    private localStoreService: LocalStoreService,
    @Inject(SharedTableEffectsConfigToken) private config: SharedTableConfig,
    private route: ActivatedRoute
  ) {
    super(store, securityUtils);
    this.isInventory = !!this.route.snapshot.data['inventory'];
  }

  override getModelService() {
    return this.locationViewService;
  }

  override getStatusColor(row: any) {
    if (this.isInventory) {
      if (row.active) {
        return '#2A9041';//green
      } else {
        return '#565656'; //gray
      }
    } else {
      return getStatusColor(row.locationStatus);
    }
  }

  override showJeopIcon(row: any) {
    return row.showJeopIcon;
  }

  override showDisconnectIcon(row: any): boolean {
    return row.showOpenDisconnectIcon;
  }

  override showMacIcon(row: any): boolean {
    return row.showOpenMacIcon;
  }

  override showDisputeIcon(row: any): boolean {
    return row.showOpenDisputeIcon;
  }

  override showLinkedIcon(row: any): boolean {
    return row.showLinkedIcon;
  }

  override showBundledIcon(row: any): boolean {
    return row.showBundledIcon;
  }
}
