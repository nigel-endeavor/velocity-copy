import { Component, Inject } from "@angular/core";
import { AbstractTableComponent } from "../abstract-table/abstract-table.component";
import { ServiceCyberView } from "../../models/service-cyber-view.model";
import { ServiceCyberViewService } from "../../services/service-cyber-view.service";
import { Store } from "@ngrx/store";
import { SecurityUtilService } from "../../services/security-util.service";
import { LocalStoreService } from "../local-store/local-store.service";
import { SharedTableEffectsConfigToken } from "../shared-table/tokens";
import { SharedTableConfig } from "../shared-table/interfaces";
import { getStatusColor } from '../../utilities';

@Component({
  selector: 'app-service-cyber-table',
  templateUrl: '../abstract-table/abstract-table.component.html',
  styleUrls: ['../abstract-table/abstract-table.component.scss']
})
export class ServiceCyberTableComponent extends AbstractTableComponent<ServiceCyberView> {
  override persistFilters = true;
  override cardPageCount = 2;
  override allowLimitToggle = true;

  constructor(
    private serviceCyberViewService: ServiceCyberViewService,
    public override store: Store,
    public override securityUtils: SecurityUtilService,
    private localStoreService: LocalStoreService,
    @Inject(SharedTableEffectsConfigToken) private config: SharedTableConfig
  ) {
    super(store, securityUtils);

  }

override getModelService() {
    return this.serviceCyberViewService;
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

  override showLinkedIcon(row: any): boolean {
    return row.linked;
  }

  override showBundledIcon(row: any): boolean {
    return row.bundled;
  }

  override showDisconnectIcon(row: any): boolean {
    return row.showOpenDisconnectIcon;
  }

  override showMacIcon(row: any): boolean {
    return row.showOpenMacIcon;
  }
}
