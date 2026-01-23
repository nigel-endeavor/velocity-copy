import { Component, Input } from '@angular/core';
import { TableDeprecatedComponent, Column } from '../../abstract-table/table-deprecated.component';
import { Jeop } from '../../../models/jeop.model';
import { JeopService } from '../../../services/jeop.service';
import { JeopSearchCriteria } from '../../../models/jeop-search-criteria.model';
import { Store } from '@ngrx/store';
import { MatLegacyDialog as MatDialog } from '@angular/material/legacy-dialog';
import { MatLegacyProgressSpinnerModule as MatProgressSpinnerModule } from '@angular/material/legacy-progress-spinner';
import { DropdownModule } from '../../dropdown/dropdown.module';
import { FormsModule } from '@angular/forms';
import { MatLegacyCheckboxModule as MatCheckboxModule } from '@angular/material/legacy-checkbox';
import { CommonModule } from '@angular/common';
import { SecurityUtilService } from '../../../services/security-util.service';
import { MatIconModule } from '@angular/material/icon';

@Component({
  standalone: true,
  selector: 'app-jeops-table',
  templateUrl: '../../abstract-table/table-deprecated.component.html',
  styleUrls: ['../../abstract-table/table-deprecated.component.scss'],
  imports: [
    CommonModule,
    FormsModule,
    MatCheckboxModule,
    DropdownModule,
    MatProgressSpinnerModule,
    MatIconModule
  ]
})
export class JeopsTableComponent extends TableDeprecatedComponent<Jeop> {

  @Input() type: string = '';
  @Input() parentId: number;

  override showFilter: boolean = false;

  override searchCriteria = new JeopSearchCriteria();
  override cardViewSelected = false;

  override jeopIconColor: string = '#5DCADE';

  constructor(
    private jeopService: JeopService,
    public override store: Store,
    public override dialog: MatDialog,
    public override securityUtils: SecurityUtilService,
  ) {
    super(store, dialog, securityUtils);
  }

  override ngOnInit() {
    this.jeopService.setType(this.type);
    this.searchCriteria[this.type + 'Id'] = this.parentId;
    super.ngOnInit();
  }

  override columns: Column[] = [
    { name: 'Description', propertyName: 'description', type: 'text', width: '300px' },
    { name: 'Level', propertyName: 'level', type: 'text' },
    { name: 'Start Date', propertyName: 'startDate', type: 'dateutc' },
    { name: 'End Date', propertyName: 'endDate', type: 'dateutc' },
    { name: 'Originator', propertyName: 'originator', type: 'text' },
    { name: 'Responsibility', propertyName: 'responsibility', type: 'text' },
    { name: 'Assigned To', propertyName: 'assignedTo', type: 'text' },
    { name: 'Note', propertyName: 'note', type: 'text' },
    { name: 'Business Days Open', propertyName: 'businessDaysOpen', type: 'count', width: '150px' },
  ]

  override getModelService() {
    return this.jeopService;
  }

  override showJeopIcon(row: any): boolean {
    return !row.endDate;
  }
}
