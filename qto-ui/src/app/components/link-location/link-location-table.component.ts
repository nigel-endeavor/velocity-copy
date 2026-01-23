import { Component } from "@angular/core";
import { Store } from "@ngrx/store";
import { AbstractTableComponent } from "src/app/features/abstract-table/abstract-table.component";
import { CommonColumn } from "src/app/interfaces/columns.interface";
import { LocationViewSearchCriteria } from "src/app/models/location-view-search-criteria.model";
import { LocationView } from "src/app/models/location-view.model";
import { LocationInventoryViewService } from "src/app/services/location-inventory-view.service";
import { SecurityUtilService } from "src/app/services/security-util.service";
import { LINK_LOCATION_COLUMNS } from "./data/link-location-columns.const";

@Component({
  selector: 'app-link-location-table',
  templateUrl: '../../features/abstract-table/abstract-table.component.html',
  styleUrls: ['../../features/abstract-table/abstract-table.component.scss']
})
export class LinkLocationTableComponent extends AbstractTableComponent<LocationView> {
  override searchCriteria: LocationViewSearchCriteria = new LocationViewSearchCriteria();

  constructor(
    private locationInventoryViewService: LocationInventoryViewService,
    public override store: Store,
    public override securityUtils: SecurityUtilService
  ) {
    super(store, securityUtils);
  }

  override columns: CommonColumn[] = LINK_LOCATION_COLUMNS;

  override getModelService() {
    return this.locationInventoryViewService;
  }

  override getStatusColor(row: any): string {
    return '#FFFFFF';  
  }
}