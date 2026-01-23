import { Component } from '@angular/core';
import { AbstractTableComponent } from '../abstract-table/abstract-table.component';
import { ServiceView } from '../../models/service-view.model';
import { ServiceViewService } from '../../services/service-view.service';
import { getStatusColor } from '../../utilities';
import { Store } from '@ngrx/store';
import { SecurityUtilService } from '../../services/security-util.service';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-service-view-table',
  templateUrl: '../abstract-table/abstract-table.component.html',
  styleUrls: ['../abstract-table/abstract-table.component.scss']
})
export class ServiceViewTableComponent extends AbstractTableComponent<ServiceView> {
  override persistFilters = true;
  override cardPageCount = 2;
  override allowLimitToggle = true;

  isInventory: boolean;

  constructor(
    private serviceViewService: ServiceViewService,
    public override store: Store,
    public override securityUtils: SecurityUtilService,
    private route: ActivatedRoute
  ) {
    super(store, securityUtils);
    this.isInventory = !!this.route.snapshot.data['inventory'];
  }

  override getModelService() {
    return this.serviceViewService;
  }

  override getStatusColor(row: any) {
    if (this.isInventory) {
      if (row.active) {
        return '#2A9041';//green
      } else if (row.status == 'Disconnect Complete') {
        return '#D73F49'; //red
      } else {
        return '#565656'; //gray
      }
    } else {
      return getStatusColor(row.status)
    }
  }

  override showJeopIcon(row: any): boolean {
    return row.showJeopIcon;
  }

  override showNoteIcon(row: any): boolean {
    return row.showNoteIcon;
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
    return row.linked;
  }

  override showBundledIcon(row: any): boolean {
    return row.bundled;
  }
}
