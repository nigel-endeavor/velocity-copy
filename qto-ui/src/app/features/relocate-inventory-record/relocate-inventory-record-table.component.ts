import { Component } from '@angular/core';
import { AbstractTableComponent } from '../abstract-table/abstract-table.component';
import { LocationView } from 'src/app/models/location-view.model';
import { Store } from '@ngrx/store';
import { CommonColumn } from 'src/app/interfaces/columns.interface';
import { LocationViewSearchCriteria } from 'src/app/models/location-view-search-criteria.model';
import { LocationInventoryViewService } from 'src/app/services/location-inventory-view.service';
import { SecurityUtilService } from 'src/app/services/security-util.service';
import { RELOCATE_INVENTORY_RECORD_COLUMNS } from './data/relocate-inventory-record-columns.const';

@Component({
  selector: 'app-relocate-inventory-record-table',
  templateUrl: '../../features/abstract-table/abstract-table.component.html',
  styleUrls: ['../../features/abstract-table/abstract-table.component.scss']
})
export class RelocateInventoryRecordTableComponent extends AbstractTableComponent<LocationView> {
  override searchCriteria: LocationViewSearchCriteria = new LocationViewSearchCriteria();

  constructor(
    private locationInventoryViewService: LocationInventoryViewService,
    public override store: Store,
    public override securityUtils: SecurityUtilService
  ) {
    super(store, securityUtils);
  }

  override columns: CommonColumn[] = RELOCATE_INVENTORY_RECORD_COLUMNS;

  override getModelService() {
    return this.locationInventoryViewService;
  }

  override getStatusColor(row: any): string {
    return '#FFFFFF';  
  }
}
