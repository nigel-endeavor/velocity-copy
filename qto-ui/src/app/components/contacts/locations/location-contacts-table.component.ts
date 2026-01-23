import { Component, Input, OnInit } from '@angular/core';
import { plainToInstance } from 'class-transformer';
import { ContactSearchCriteria } from 'src/app/models/contact-search-criteria.model';
import { Location } from 'src/app/models/location.model';
import { LocationContact } from 'src/app/models/location-contact.model';
import { LocationContactService } from 'src/app/services/location-contact.service';
import { TableDeprecatedComponent, Column } from '../../abstract-table/table-deprecated.component';
import { Store } from '@ngrx/store';
import { MatLegacyDialog as MatDialog } from '@angular/material/legacy-dialog';
import { MatLegacyProgressSpinnerModule as MatProgressSpinnerModule } from '@angular/material/legacy-progress-spinner';
import { DropdownModule} from '../../dropdown/dropdown.module';
import { FormsModule } from '@angular/forms';
import { MatLegacyCheckboxModule as MatCheckboxModule } from '@angular/material/legacy-checkbox';
import { CommonModule } from '@angular/common';
import { SecurityUtilService } from '../../../services/security-util.service';
import { MatIconModule } from '@angular/material/icon';

@Component({
  standalone: true,
  selector: 'app-location-contacts-table',
  templateUrl: '../../abstract-table/table-deprecated.component.html',
  styleUrls: ['../../abstract-table/table-deprecated.component.scss'],
  imports: [
    CommonModule,
    DropdownModule,
    FormsModule,
    MatCheckboxModule,
    MatIconModule,
    MatProgressSpinnerModule
  ]
})
export class LocationContactsTableComponent extends TableDeprecatedComponent<LocationContact> implements OnInit {
  @Input() location: Location;
  type: string = 'location';

  override showFilter: boolean = false;
  override cardViewSelected: boolean = false;

  override searchCriteria = new ContactSearchCriteria();

  constructor(
    private locationContactService: LocationContactService,
    public override store: Store,
    public override dialog: MatDialog,
    public override securityUtils: SecurityUtilService,
  ) {
    super(store, dialog, securityUtils);
  }

  override ngOnInit() {
    this.searchCriteria[this.getType() + 'Id'] = this.getParent();
    super.ngOnInit();
  }

  override columns: Column[] = [
    { name: 'Role', propertyName: 'role', type: 'text' },
    { name: 'Name', propertyName: 'name', type: 'text' },
    { name: 'Phone', propertyName: 'phone', type: 'text' },
    { name: 'Email', propertyName: 'email', type: 'text' },
    { name: 'Notes', propertyName: 'notes', type: 'text' },
    { name: 'Last Updated', propertyName: 'lastUpdateDate', type: 'date' }
  ]

  override getModelService() {
    return this.locationContactService;
  }

  getType(): string {
    return this.type;
  }

  getParent(): number {
    return this.location.id;
  }

  //retrieves the value of a cell
  override getCellValue(row: any, col: Column): string {
    let locationContact = plainToInstance(LocationContact, row);
    // @ts-ignore
    return locationContact[col.propertyName];
  }
}
