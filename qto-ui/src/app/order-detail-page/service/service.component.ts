import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderEditService } from '../order-edit.service';
import { Service } from '../../models/service.model';
import { Title } from '@angular/platform-browser';
import {
  addNewService,
  openLocationDisplay,
  setActiveTab,
  setSelectedLocationOrService, toggleEdit
} from '../ngrx/order-details.actions';
import { select, Store } from '@ngrx/store';
import {
  getActivationCount,
  getActiveServiceTab,
  getDisputesCount,
  getIsInventory,
  getSelectedisLocation,
  getSelectedLocation,
  getSelectedLocationOrService,
  getShowBrokerageFields,
  getUserHasTenantAccess
} from '../ngrx/order-details.selectors';
import { combineLatest, map, Observable, take } from 'rxjs';
import { ServiceType } from 'src/app/models/constants/service-type';
import { ServiceBrokerage } from '../../models/service-brokerage-model';

@Component({
  selector: 'app-service',
  templateUrl: './service.component.html',
  styleUrls: ['../order-detail-page.component.scss']
})
export class ServiceComponent implements OnInit {

  public serviceTabs: { display: string, route: string }[] = [
    { display: 'General', route: 'general' },
    { display: 'Brokerage', route: 'brokerage' },
    { display: 'Milestones', route: 'milestones' },
    { display: 'Technical', route: 'technical' },
    { display: 'A-Z Location', route: 'azDetails' },
    { display: 'Equipment', route: 'equipment' },
    { display: 'Schedule', route: 'schedule' },
    { display: 'Activation', route: 'activation' },
    { display: 'Disputes', route: 'disputes' }
  ];
  public selectedTab$: Observable<string>;
  public selectedLocationOrService$ = this.store.pipe(select(getSelectedLocationOrService));
  public selectedLocation$ = this.store.pipe(select(getSelectedLocation));
  public selectedIsLocation$ = this.store.pipe(select(getSelectedisLocation));
  public isInventory$ = this.store.pipe(select(getIsInventory));
  public activationCount$ = this.store.pipe(select(getActivationCount));
  public disputesCount$ = this.store.pipe(select(getDisputesCount));
  public showBrokerageFields$ = this.store.pipe(select(getShowBrokerageFields));
  public userHasTenantAccess$ = this.store.pipe(select(getUserHasTenantAccess));
  selectedTab: string;
  public cyberClient: boolean = false;
  public telecomClient: boolean = false;

  constructor(
    public oes: OrderEditService,
    private route: ActivatedRoute,
    private titleService: Title,
    private store: Store,
    private router: Router
  ) {
    this.selectedTab$ = this.store.pipe(select(getActiveServiceTab));
  }


  ngOnInit(): void {
    this.telecomClient = localStorage.getItem('TELECOM_CLIENT') === 'true';
    this.cyberClient = localStorage.getItem('CYBER_SECURITY_CLIENT') === 'true';
    this.route.params.subscribe(params => {
      const locationId = this.route.parent?.snapshot.params['id'];
      const location = this.oes.order!.locations.find(l => l.id == locationId);
      const id = params['id'];
      let locationIndex = this.oes.order!.locations.findIndex(l => l.id == locationId);
      this.store.dispatch(openLocationDisplay({ location: location! }))
      if (id == 'new') {
        this.route.queryParams.subscribe(qp => {
          if (qp['serviceType']) {
            this.serviceTabs = [{ display: 'General', route: 'general' }];
            setTimeout(() => {
              const service = this.oes.createService(this.oes.order!.locations[locationIndex].id, qp['serviceType'], this.oes.order!.id);
              this.store.dispatch(setSelectedLocationOrService({ selectedLocationOrService: service! }));
              this.store.dispatch(addNewService({ service: service }));
              this.store.dispatch(toggleEdit({ key: 'editingServiceInfo' }));
              this.titleService.setTitle('i90 - ' + this.oes.order?.company.name + ' - ' + (service as Service).type);
            }, 0);
          }
        });
      } else {
        setTimeout(() => {
          this.isInventory$.pipe(take(1)).subscribe(isInventory => {
            let service;
            if (isInventory) {
              service = this.oes.order!.locations[locationIndex].inventoryServices.find(s => s.id == id);
            } else {
              service = this.oes.order!.locations[locationIndex].services.find(s => s.id == id);
            }
            if (service) {
              this.store.dispatch(setSelectedLocationOrService({ selectedLocationOrService: service }));
              this.titleService.setTitle('i90 - ' + this.oes.order?.company.name + ' - ' + (service as Service).type);
            }
            else {
              this.router.navigate(['order', this.oes.order!.id, 'location', locationId]);
            }
          });
        }, 0);
      }
    });

    this.selectedLocationOrService$.subscribe((selected) => {
      if (selected instanceof Service) {
        const urlTab = this.serviceTabs.find(t => t.route == this.route.children[0].snapshot?.url[0].path);
        this.store.dispatch(setActiveTab({
          tab: urlTab?.display || 'General',
          host: 'service'
        }));
        if (selected.id) {
          combineLatest([this.isInventory$, this.activationCount$, this.disputesCount$, this.showBrokerageFields$, this.userHasTenantAccess$]).pipe(
            take(1),
            map(([isInventory, activationCount, disputesCount, showBrokerageFields, userHasTenantAccess]) => {
              let disputesTab = disputesCount > 0 ? 'Disputes (' + disputesCount + ')' : 'Disputes';
              let activationTab = activationCount > 0 ? 'Activation (' + activationCount + ')' : 'Activation';
              this.serviceTabs = [
                { display: 'General', route: 'general' },
                { display: 'Milestones', route: 'milestones' },
                { display: 'Technical', route: 'technical' },
                { display: 'Equipment', route: 'equipment' }
              ];
              if (selected.type == ServiceType.ETHERNET) {
                this.serviceTabs.push({ display: 'A-Z Location', route: 'azDetails' });
              }
              if (selected.orderType != 'Disconnect' && this.telecomClient) {
                this.serviceTabs.push({ display: 'Schedule', route: 'schedule' });
                this.serviceTabs.push({ display: activationTab, route: 'activation' });
              }
              if (isInventory) {
                this.serviceTabs.push({ display: disputesTab, route: 'disputes' });
              }
              if (showBrokerageFields && userHasTenantAccess) {
                this.serviceTabs.splice(1, 0, { display: 'Brokerage', route: 'brokerage' });
              }

              this.activationCount$.subscribe(activationCount => {
                let index = this.serviceTabs.findIndex(t => t.display.includes('Activation'));
                if (index > -1) {
                  if (activationCount > 0) {
                    this.serviceTabs[index].display = 'Activation (' + activationCount + ')';
                  } else {
                    this.serviceTabs[index].display = 'Activation';
                  }
                }
              });
              this.disputesCount$.subscribe(disputesCount => {
                this.selectedTab$.subscribe(res => {
                  this.selectedTab = res
                if (this.selectedTab.includes('Disputes')) {
                  this.selectedTab = 'Disputes (' + disputesCount + ')';
                }
              });
                let index = this.serviceTabs.findIndex(t => t.display.includes('Disputes'));
                if (index > -1) {
                  if (disputesCount > 0) {
                    this.serviceTabs[index].display = 'Disputes (' + disputesCount + ')';
                  } else {
                    this.serviceTabs[index].display = 'Disputes';
                  }
                }
              });
            })
          ).subscribe();
        } else {
          this.serviceTabs = [{ display: 'General', route: 'general' }];
        }
        this.titleService.setTitle('i90 - ' + this.oes.order?.company.name + ' - ' + selected.type);
      }
    });
  }

  ngOnDestroy(): void {
    this.selectedLocationOrService$.pipe(take(1)).subscribe(selected => {
      if (selected instanceof Service) {
        this.selectedLocation$.pipe(take(1)).subscribe(location => {
          this.store.dispatch(setSelectedLocationOrService({ selectedLocationOrService: location! }));
        });
      }
    });
  }

  changeSelectedTab(tab: string) {
    this.selectedLocationOrService$.pipe(take(1)).subscribe(selected => {
      if (selected && selected instanceof Service) {
        let t = tab;
        if (tab.includes('(')) {
          t = tab.split('(')[0].trim();
        }
        this.oes.serviceTabMemory.set(selected.id, t);
      }
    });
    this.store.dispatch(setActiveTab({
      tab,
      host: 'service'
    }));
  }

  // Scroll to top of page when tab is activated
  onActivate(event: any) {
    setTimeout(() => {
      window.scrollTo(0, 0);
    }, 0);
  }
}
