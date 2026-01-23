import { Component } from '@angular/core';
import { AbstractTableComponent } from '../abstract-table/abstract-table.component';
import { LocationView } from 'src/app/models/location-view.model';
import { Store } from '@ngrx/store';
import { CommonColumn } from 'src/app/interfaces/columns.interface';
import { LocationViewSearchCriteria } from 'src/app/models/location-view-search-criteria.model';
import { SecurityUtilService } from 'src/app/services/security-util.service';
import { RELOCATE_RECORD_COLUMNS } from './data/relocate-record-columns.const';
import { LocationViewService } from '../../services/location-view.service';

@Component({
  selector: 'app-relocate-record-table',
  templateUrl: '../../features/abstract-table/abstract-table.component.html',
  styleUrls: ['../../features/abstract-table/abstract-table.component.scss']
})
export class RelocateRecordTableComponent extends AbstractTableComponent<LocationView> {
  override searchCriteria: LocationViewSearchCriteria = new LocationViewSearchCriteria();

  constructor(
    private locationViewService: LocationViewService,
    public override store: Store,
    public override securityUtils: SecurityUtilService
  ) {
    super(store, securityUtils);
  }

  override columns: CommonColumn[] = RELOCATE_RECORD_COLUMNS;

  override getModelService() {
    return this.locationViewService;
  }

  override getStatusColor(row: any): string {
    return '#FFFFFF';  
  }
}
