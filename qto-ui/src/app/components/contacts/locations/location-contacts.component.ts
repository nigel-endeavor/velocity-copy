import { Component, OnInit, ViewChild } from '@angular/core';
import { Location} from '../../../models/location.model';
import { LocationContact } from '../../../models/location-contact.model';
import { LocationContactService } from '../../../services/location-contact.service';
import { LocationContactsTableComponent } from './location-contacts-table.component';
import { Contact, ContactType } from '../../../models/contact.model';
import { plainToInstance } from 'class-transformer';
import { ContactComponent } from '../contact.component';
import { CommonModule } from '@angular/common';
import { OrderEditService } from '../../../order-detail-page/order-edit.service';
import { select, Store } from '@ngrx/store';
import { reloadOrder } from '../../../order-detail-page/ngrx/order-details.actions';
import { editEnabled, getSelectedLocationOrService } from '../../../order-detail-page/ngrx/order-details.selectors';
import { map } from 'rxjs';

@Component({
  standalone: true,
  selector: 'app-location-contacts',
  templateUrl: './location-contacts.component.html',
  styleUrls: ['./location-contacts.component.scss'],
  imports: [
    CommonModule,
    LocationContactsTableComponent,
    ContactComponent
  ]
})
export class LocationContactsComponent implements OnInit {
  public editEnabled$ = this.store.pipe(select(editEnabled));
  public selectedLocationOrService$ = this.store.pipe(
    select(getSelectedLocationOrService),
    map(item => item as Location)
  );

  companyId: number;
  @ViewChild('table') table: LocationContactsTableComponent;
  openContactDialog: boolean = false;
  contact: LocationContact;
  editEnabled: boolean;

  constructor(
    public oes: OrderEditService,
    private locationContactService : LocationContactService,
    private store : Store
  ) { }

  ngOnInit(): void {
    // this.store.dispatch(loadUserStatuses());
    this.companyId = this.oes.order!.company.id;
    this.editEnabled$.subscribe((res) => {
      this.editEnabled = res;
    });
  }

  onAddContactClicked(location: Location): void {
    //open dialog to edit contact
      this.contact = new LocationContact(this.companyId, ContactType.SALES, location.id);
      this.openContactDialog = true; 
  }

  onContactDblClicked(locationContact: LocationContact): void {
    //open dialog to edit contact
    if (this.editEnabled) {
    this.contact = plainToInstance(LocationContact, structuredClone(locationContact));
    this.openContactDialog = true;
  }
}

  onContactSaved(locationContact: Contact): void {
    // save the contact
    this.locationContactService.save(locationContact as LocationContact)
      .subscribe((res: LocationContact) => {
        this.table.fetch();
        this.openContactDialog = false;
        this.store.dispatch(reloadOrder());
      });
  }

  onContactDeleted(locationContact: Contact): void {
    // delete the contact
    this.locationContactService.delete(locationContact as LocationContact)
      .subscribe((res: Object) => {
        this.table.fetch();
        this.openContactDialog = false;
      });
  }

  onContactCancelled(): void {
    this.openContactDialog = false;
  }
}
