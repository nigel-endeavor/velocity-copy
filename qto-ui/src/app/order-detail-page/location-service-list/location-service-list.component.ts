import { AfterContentInit, Component, ElementRef, Renderer2, ViewChild } from '@angular/core';
import { ServiceType } from '../../models/constants/service-type';
import { Location } from '../../models/location.model';
import { Service } from '../../models/service.model';
import { addressToString, getStatusColor } from '../../utilities';
import { OrderEditService } from '../order-edit.service';
import { ActivatedRoute, Router } from '@angular/router';
import { select, Store } from '@ngrx/store';
import {getIsInventory, getSelectedLocation, getSelectedLocationOrService, editEnabled, getLockOneLocationPerOrder} from '../ngrx/order-details.selectors';
import { removeNewService, setActiveTab, setSelectedLocationOrService, toggleLocationDisplay } from '../ngrx/order-details.actions';
import { Observable, map, take } from 'rxjs';
import { add } from 'lodash';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from '../../components/confirmation-dialog/confirmation-dialog.component';
import { LookupValue } from "../../models/lookup-value.model";
import { LookupValueService } from "../../services/lookup-value.service";

@Component({
  selector: 'app-location-service-list',
  templateUrl: './location-service-list.component.html',
  styleUrls: ['./location-service-list.component.scss']
})
export class LocationServiceListComponent implements AfterContentInit {
  // public isReadOnly$ = this.store.pipe(select(getIsReadOnly));
  public isInventory$ = this.store.pipe(select(getIsInventory));

  serviceTypes: LookupValue[];
  public selectedLocationOrService: Location | Service;

  //used by inventory view
  public selectedLocation$ = this.store.pipe(select(getSelectedLocation));

  public editEnabled$ = this.store.pipe(select(editEnabled));

  lockOneLocationPerOrder: boolean;

  constructor(
    private renderer: Renderer2,
    public oes: OrderEditService,
    private router: Router,
    private store: Store,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private lookupValueService: LookupValueService
    ) {
    this.store.pipe(select(getSelectedLocationOrService)).subscribe(res => {
      if (res) {
        this.selectedLocationOrService = res;
      }
    });
    this.store.pipe(select(getLockOneLocationPerOrder)).subscribe(res => {
      this.lockOneLocationPerOrder = res;
    });
  }

  ngAfterContentInit(): void {
    let selectedListElement = document.getElementsByClassName('selected')[0];
    if (selectedListElement && selectedListElement.parentElement) {
      selectedListElement.parentElement.scrollIntoView(true);
    }
  }

  onItemClicked(item: Location | Service): void {
    if (this.selectedLocationOrService && this.selectedLocationOrService.id == null) {
      if (item.id == null) {
        return;
      }
      let selectedType = 'location';
      if (this.selectedLocationOrService instanceof Service) {
        selectedType = 'service';
      }
      const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
        data: { title: 'Warning', message: `If you continue, you\'ll lose your work on this ${selectedType}.` },
        maxWidth: '400px'
      });
      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          //remove the this.selectedLocationOrService from the list
          if (this.selectedLocationOrService instanceof Service) {
            const locationId = this.selectedLocationOrService.locationId;
            const index = this.oes.order?.locations.findIndex(l => l.id == locationId);
            if (index !== undefined && index > -1) {
              const location = this.oes.order?.locations[index];

              let services: Service[] = [];
              this.isInventory$.pipe(take(1)).subscribe(isInventory => {
                if (isInventory) {
                  services = [...(location?.inventoryServices ?? [])];
                } else {
                  services = [...(location?.services ?? [])];
                }
              });
              const serviceIndex = services.findIndex(s => s.id == this.selectedLocationOrService.id);
              if (location && serviceIndex !== undefined && serviceIndex > -1) {
                this.store.dispatch(removeNewService({service: services[serviceIndex]}));
              }
            }
          } else {
            const index = this.oes.order?.locations.findIndex(l => l.id == this.selectedLocationOrService.id);
            if (index !== undefined && index > -1) {
              this.oes.order?.locations.splice(index, 1);
            }
          }
          this.processTabChange(item);
        } else {
          return;
        }
      });
    } else {
      this.processTabChange(item);
    }
  }

  processTabChange(item: Location | Service): void {
    if (item instanceof Location) {
      let tab = 'General';
      if (this.oes.locationTabMemory.has(item.id)) {
        tab = this.oes.locationTabMemory.get(item.id)!;
      }
      this.store.dispatch(setActiveTab({ tab, host: 'location' }));
      this.isInventory$.pipe(take(1)).subscribe(isInventory => {
        console.log('isInventory', isInventory);
        if (isInventory) {
          this.router.navigate(['inventory', 'order', item.orderId, 'location', item.id, tab.toLowerCase()]);
        } else {
          this.router.navigate(['order', item.orderId, 'location', item.id, tab.toLowerCase()]);
        }
      });
    } else {
      let tab = 'General';

      if (this.oes.serviceTabMemory.has(item.id)) {
        tab = this.oes.serviceTabMemory.get(item.id)!;
      }
      this.store.dispatch(setActiveTab({ tab, host: 'service' }));
      this.isInventory$.pipe(take(1)).subscribe(isInventory => {
        if (isInventory) {
          this.router.navigate(['inventory', 'order', this.oes.order!.id, 'location', item.locationId, 'service', item.id, tab.toLowerCase()]);
        } else {
          this.router.navigate(['order', item.orderId, 'location', (item as Service).locationId, 'service', item.id, tab.toLowerCase()]);
        }
      });
    }
  }

  expandClicked(event: Event, location: Location): void {
    event.stopPropagation(); //stops click action from triggering onItemClicked
    this.store.dispatch(toggleLocationDisplay({ location }))
  }

  isSelected(item: Location | Service): boolean {
    if (!this.selectedLocationOrService || this.selectedLocationOrService.constructor != item.constructor) {
      return false;
    }

    if (item.id) {
      return item.id == this.selectedLocationOrService.id;
    } else {
      return item === this.selectedLocationOrService;
    }
  }

  isSelectedlocation(item: Location): boolean {
    if (item.id && this?.selectedLocationOrService) {
      // @ts-ignore
      return item.id == this.selectedLocationOrService['locationId'];
    }
    return false
  }

  getStatusColor(item: Location | Service): Observable<string> {
    return this.isInventory$.pipe(
      take(1),
      map(isInventory => {
        if (isInventory) {
          if (item instanceof Service) {
            if (item.orderType == 'Disconnect') {
              return '#D73F49';
            }
            if (item.status == 'Service Complete' && !item.active) {
              return '#565656'; //gray
            }
          }
          if (item instanceof Location) {
            if (item.active) {
              return '#2A9041';//green
            } else {
              return '#565656'; //gray
            }
          }
        }
        return getStatusColor(item.status);
      })
    );
  }

  @ViewChild('newSelector') newSelectorRef: ElementRef;
  newSelectorStyle = {};
  onAddClicked(event: any): void {
    event.stopPropagation();
    this.newSelectorStyle = {
      'display': 'block',
      'position': 'absolute',
      'left.px': event.pageX,
      'top.px': event.pageY
    }
    let listenerFn = this.renderer.listen('window', 'click',  (e: any) => {
      if (!this.newSelectorRef.nativeElement.contains(e.target)) {
        this.newSelectorStyle = { 'display': 'none' };
        listenerFn();
      }
    });
  }

  onMapIconClicked(event: Event, location: Location): void {
    event.stopPropagation();
    const addressString = addressToString(location.address1, location.address2, location.city, location.state, location.postalCode, location.country);
    const url = 'https://www.google.com/maps/search/?api=1&query=' + encodeURIComponent(addressString);
    window.open(url);
  }

  onNewLocationClicked(): void {
    this.isInventory$.pipe(take(1)).subscribe(isInventory => {
      if (!isInventory) {
        if (['Order Complete', 'Order Cancelled', 'Change In Assignment'].includes(this.oes.order!.status)) {
          throw Error('This order has already been completed. You must create a new order, or enter a MAC-D.');
        }
        if (this.oes.order!.locations.length >= 1 && this.lockOneLocationPerOrder) {
          throw Error('Tenant configuration only allows one location per order');
        }
      }
    });
    if (this.oes.order!.locations.findIndex(l => !l.id) != -1) {
      throw Error('Please save the existing New Location before creating another');
    }
    this.newSelectorStyle = { 'display': 'none' };
    this.router.navigate(['location', 'new'], { relativeTo: this.route });
  }

  //New Service Dialog
  @ViewChild('newServiceDialog') dialogRef: ElementRef;
  selectedServiceType: string | undefined;

  onNewServiceClicked(): void {
    this.selectedServiceType = undefined;
    this.lookupValueService.find('TENANT_SERVICE_TYPES').subscribe((values: LookupValue[]) => {
      this.serviceTypes = values
    });
    if (!this.selectedLocationOrService || !(this.selectedLocationOrService instanceof Location)) {
      throw Error('You must select a location to add a service to.');
    }
    let throwError = false;
    this.isInventory$.pipe(take(1)).subscribe(isInventory => {
      if (!isInventory) {
        if (['Location Complete', 'Location Cancelled', 'Change In Assignment'].includes(this.selectedLocationOrService.status)) {
          throwError = true;
        }
      }
    });
    if (throwError) {
      throw Error('This location has already been completed. You must create a new location, or enter a MAC-D.');
    }
    this.newSelectorStyle = {'display': 'none'};
    setTimeout(() => {
      this.dialogRef.nativeElement.showModal();
    }, 0);
  }

  onDialogSelectClicked(): void {
    this.router.navigate(['location', this.selectedLocationOrService.id, 'service', 'new'], { relativeTo: this.route, queryParams: { serviceType: this.selectedServiceType } });
    this.selectedServiceType = undefined;
    this.dialogRef.nativeElement.close();
  }

  onDialogCancelClicked(): void {
    this.selectedServiceType = undefined;
    this.dialogRef.nativeElement.close();
  }

  onCancelClicked(item: Location): void {
      const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
        data: { title: 'Warning', message: `If you continue, you\'ll lose your work on this Location` },
        maxWidth: '400px'
      });
      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          //remove the this.selectedLocationOrService from the list
          const index = this.oes.order?.locations.findIndex(l => l.id == this.selectedLocationOrService.id);
          if (index !== undefined && index > -1) {
            this.oes.order?.locations.splice(index, 1);
          }
          this.processTabChange(this.oes.order?.locations[0]!);
        } else {
          return;
        }
      });
  }
}
