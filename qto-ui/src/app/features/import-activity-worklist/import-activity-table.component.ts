import { Component } from '@angular/core';
import { AbstractTableComponent } from '../abstract-table/abstract-table.component';
import { ImportActivitySearchCriteria } from 'src/app/models/import-activity-search-criteria.model';
import { ImportActivity } from 'src/app/models/import-activity.model';
import { Store } from '@ngrx/store';
import { ImportActivityService } from 'src/app/services/import-activity.service';
import { SecurityUtilService } from 'src/app/services/security-util.service';
import { CommonColumn } from 'src/app/interfaces/columns.interface';
import { IMPORT_WORKLIST_COLUMNS } from './data/import-worklist-columns.consts';

@Component({
  selector: 'app-import-activity-table',
  templateUrl: '../abstract-table/abstract-table.component.html',
  styleUrls: ['../abstract-table/abstract-table.component.scss']
})
export class ImportActivityTableComponent extends AbstractTableComponent<ImportActivity> {
  override cardViewSelected = false;
  
  override searchCriteria: ImportActivitySearchCriteria = new ImportActivitySearchCriteria();

  constructor(
    private importActivityService: ImportActivityService,
    public override store: Store,
    public override securityUtils: SecurityUtilService,
  ) {
    super(store, securityUtils);
  }

  override columns: CommonColumn[] = IMPORT_WORKLIST_COLUMNS;

  override getModelService() {
    return this.importActivityService;
  }

  override getStatusColor(row: any) {
    return '#FFFFFF';
  }
}
