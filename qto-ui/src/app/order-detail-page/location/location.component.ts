import { Component, OnInit } from '@angular/core';
import { OrderEditService } from '../order-edit.service';
import { ActivatedRoute } from '@angular/router';
import { Location } from 'src/app/models/location.model';
import { Title } from '@angular/platform-browser';
import { select, Store } from '@ngrx/store';
import {
  getActiveLocationTab,
  getDisputesCount,
  getIsInventory,
  getSelectedisLocation,
  getSelectedLocationOrService,
  getShowBrokerageFields,
  getUserHasTenantAccess
} from '../ngrx/order-details.selectors';
import { setActiveTab, setSelectedLocationOrService, toggleEdit } from '../ngrx/order-details.actions';
import { combineLatest, map, take } from 'rxjs';

@Component({
  selector: 'app-location',
  templateUrl: './location.component.html',
  styleUrls: ['../order-detail-page.component.scss']
})
export class LocationComponent implements OnInit {
  public locationTabs = ['General', 'Brokerage', 'Milestones', 'Contacts', 'Communication', 'Disputes'];

  public showOutlet = false;
  public selectedTab$ = this.store.pipe(select(getActiveLocationTab));
  public selectedLocationOrService$ = this.store.pipe(select(getSelectedLocationOrService));
  public selectedIsLocation$ = this.store.pipe(select(getSelectedisLocation));
  public isInventory$ = this.store.pipe(select(getIsInventory));
  public disputesCount$ = this.store.pipe(select(getDisputesCount));
  public showBrokerageFields$ = this.store.pipe(select(getShowBrokerageFields));
  public userHasTenantAccess$ = this.store.pipe(select(getUserHasTenantAccess));
  public cyberClient: boolean = false;
  public telecomClient: boolean = false;

  constructor(
    public oes: OrderEditService,
    private route: ActivatedRoute,
    private titleService: Title,
    private store: Store
  ) { }

  ngOnInit(): void {
    this.telecomClient = localStorage.getItem('TELECOM_CLIENT') === 'true';
    this.cyberClient = localStorage.getItem('CYBER_SECURITY_CLIENT') === 'true';
    const urlTab = this.locationTabs.find(t => t.toLowerCase() == this.route.children[0].snapshot.url[0].path);
    if (urlTab) {
      this.store.dispatch(setActiveTab({
        tab: urlTab,
        host: 'location'
      }));
    }

    this.route.params.subscribe(params => {
      const id = params['id'];
      if (id) {
        if (id == 'new') {
          this.showOutlet = false;
          this.locationTabs = ['General'];
          let newLocation = new Location();
          newLocation.orderId = this.oes.order!.id;
          newLocation.status = 'Pending Assignment';
          setTimeout(() => {
            this.store.dispatch(setSelectedLocationOrService({ selectedLocationOrService: newLocation }));
            this.oes.order!.locations.unshift(newLocation);
            this.store.dispatch(toggleEdit({ key: 'editingLocInfo' }));
            this.showOutlet = true;
          }, 1);
        } else {
          setTimeout(() => {
            let loc = this.oes.order!.locations.find(l => l.id == id);
            if (loc) {
              this.store.dispatch(setSelectedLocationOrService({ selectedLocationOrService: loc ? loc : this.oes.order!.locations[0] }));
            }
            this.showOutlet = true;
          }, 0);
        }
      } else {
        setTimeout(() => {
          this.store.dispatch(setSelectedLocationOrService({ selectedLocationOrService: this.oes.order!.locations[0] }));
          this.showOutlet = true;
        }, 0);
      }
    });
    this.showOutlet = true;
    this.titleService.setTitle('i90 - ' + this.oes.order?.company.name);

    this.selectedLocationOrService$.subscribe((selected) => {
      if (selected instanceof Location) {
        combineLatest([this.isInventory$, this.disputesCount$, this.showBrokerageFields$, this.userHasTenantAccess$]).pipe(
          take(1),
          map(([isInventory, disputesCount, showBrokerageFields, userHasTenantAccess]) => {
            this.locationTabs = ['General'];
            if (showBrokerageFields && userHasTenantAccess && this.telecomClient) {
              this.locationTabs.push('Brokerage');
            }
            if (this.telecomClient) {
              this.locationTabs.push('Milestones');
            }
            this.locationTabs.push('Contacts');
            if (this.telecomClient) {
              this.locationTabs.push('Communication');
            }
            if (isInventory) {
              let disputesTab = disputesCount > 0 ? 'Disputes (' + disputesCount + ')' : 'Disputes';
              this.locationTabs.push(disputesTab);
              this.disputesCount$.subscribe((disputesCount) => {
                let index = this.locationTabs.findIndex(t => t.includes('Disputes'));
                if (index > -1) {
                  if (disputesCount > 0) {
                    this.locationTabs[index] = 'Disputes (' + disputesCount + ')';
                  } else {
                    this.locationTabs[index] = 'Disputes';
                  }
                }
              });
              }
          })
        ).subscribe();
        this.titleService.setTitle('i90 - ' + this.oes.order?.company.name);
      }
    });
  }

  ngOnDestroy(): void {
    this.oes.locationTabMemory.clear();
    this.oes.serviceTabMemory.clear();
  }

  changeSelectedTab(tab: string) {
    this.selectedLocationOrService$.pipe(take(1)).subscribe(selected => {
      if (selected && selected instanceof Location) {
        this.oes.locationTabMemory.set(selected.id, tab);
      }
    });
    this.store.dispatch(setActiveTab({
      tab,
      host: 'location'
    }));
  }

  // Scroll to top of page when tab is activated
  onActivate(event: any) {
    setTimeout(() => {
      window.scrollTo(0, 0);
    }, 0);
  }
}
