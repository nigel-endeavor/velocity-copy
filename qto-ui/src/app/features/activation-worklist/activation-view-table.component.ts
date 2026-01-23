import { Component } from '@angular/core';
import { AbstractTableComponent } from '../abstract-table/abstract-table.component';
import { ActivationViewSearchCriteria } from '../../models/activation-view-search-criteria.model';
import { ActivationView } from '../../models/activation-view.model';
import { ActivationViewService } from '../../services/activation-view.service';
import { Store } from '@ngrx/store';
import { SecurityUtilService } from '../../services/security-util.service';
import { ACTIVATION_WORKLIST_COMPONENTS } from './data/activation-worklist-columns.consts';
import { CommonColumn } from '../../interfaces/columns.interface';
import { getStatusColor } from '../../utilities';

@Component({
  selector: 'app-activation-view-table',
  templateUrl: '../abstract-table/abstract-table.component.html',
  styleUrls: ['../abstract-table/abstract-table.component.scss']
})
export class ActivationViewTableComponent extends AbstractTableComponent<ActivationView> {
  override cardViewSelected = false;
  override persistFilters = true;
  override allowLimitToggle = true;

  override searchCriteria: ActivationViewSearchCriteria = new ActivationViewSearchCriteria();

  constructor(
    private activationViewService: ActivationViewService,
    public override store: Store,
    public override securityUtils: SecurityUtilService,
  ) {
    super(store, securityUtils);
  }

  override columns: CommonColumn[] = ACTIVATION_WORKLIST_COMPONENTS;

  override getModelService() {
    return this.activationViewService;
  }

  override getStatusColor(row: any) {
    return getStatusColor(row.scheduledAttemptStatus);
  }
}
