import { Injectable } from '@angular/core';

import { Actions, createEffect, ofType } from '@ngrx/effects';
import { select, Store } from '@ngrx/store';

import * as actions from './order-details.actions';
import { OrderDetailsState, selectedIsLocation } from './order-details.reducer';
import { EMPTY, forkJoin, map, of, throwError } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderEditService } from '../order-edit.service';
import { catchError, filter, mergeMap, switchMap, tap, withLatestFrom } from 'rxjs/operators';
import { LookupValue } from '../../models/lookup-value.model';
import { LookupValueService } from '../../services/lookup-value.service';
import { SubjectInterface } from '../../models/subject.model';
import { SubjectService } from '../../services/subject.service';
import { Order } from '../../models/order.model';
import {
  loadLookupValuesByKey,
  loadOrderSuccess,
  loadProvisioners, setSelectedLocationOrService,
  setUserStatuses, loadServiceBrokerage, loadServiceBrokerageSuccess
} from './order-details.actions';
import { OrderService } from '../../services/order.service';
import { Service } from '../../models/service.model';
import { Title } from '@angular/platform-browser';
import { getOrder, getSelectedActivationId, getSelectedLocationOrService } from './order-details.selectors';
import { SecurityUtilService } from '../../services/security-util.service';
import { RequirementTemplate } from '../../models/requirement-template.model';
import { RequirementTemplateService } from '../../services/requirement-template.service';
import { plainToClass } from 'class-transformer';
import { Location } from '../../models/location.model';
import { ServiceType } from '../../models/constants/service-type';
import { DiaService } from '../../models/dia-service.model';
import { BroadbandService } from '../../models/broadband-service.model';
import { UcaasService } from '../../models/ucaas-service.model';
import { GService } from '../../models/g-service.model';
import { DiaServiceService } from '../../services/dia-service.service';
import { UcaasServiceService } from '../../services/ucaas-service.service';
import { GServiceService } from '../../services/g-service.service';
import { BroadbandServiceService } from '../../services/broadband-service.service';
import { ServiceService } from '../../services/service.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivationAttemptService } from 'src/app/services/activation-attempt.service';
import { DisputesService } from 'src/app/services/disputes.service';
import { ActivationAttempt } from 'src/app/models/activation-attempt.model';
import { Dispute } from 'src/app/models/dispute.model';
import { DisputeSearchCriteria } from 'src/app/models/dispute-search-criteria.model';
import { PaginatedResult } from 'src/app/models/paginated-result.model';
import { LocationService } from 'src/app/services/location.service';
import { CrossConnectServiceService } from '../../services/cross-connect-service.service';
import { CrossConnectService } from '../../models/cross-connect-service.model';
import { EthernetServiceService } from 'src/app/services/ethernet-service.service';
import { EthernetService } from 'src/app/models/ethernet-service.model';
import { CompanyConfigPropertyService } from 'src/app/services/company-config-property.service';
import { TelevisionService } from 'src/app/models/television-service.model';
import { TelevisionServiceService } from 'src/app/services/television-service.service';
import { MplsService } from 'src/app/models/mpls-service.model';
import { MplsServiceService } from 'src/app/services/mpls-service.service';
import { ContactService } from 'src/app/services/contact.service';
import { ServiceBrokerageService } from '../../services/service-brokerage.service';
import { ServiceBrokerage } from '../../models/service-brokerage-model';
import { ThreatMDRServiceService } from 'src/app/services/threatMDR-service.service';
import { ThreatMDRService } from 'src/app/models/threatMdr-service.model';
import { RansomMDRService } from 'src/app/models/ransomMdr-service.model';
import { RansomMDRServiceService } from 'src/app/services/ransomMDR-service.service';
import { RiskMDRService } from 'src/app/models/riskMdr-service.model';
import { RiskMDRServiceService } from 'src/app/services/riskMDR-service.service';
import { EngineeringIAMService } from 'src/app/models/engineering-IAM-service.model';
import { EngineeringIAMServiceService } from 'src/app/services/engineering-IAM-service.service';
import { EngineeringMDMServiceService } from "../../services/engineering-MDM-service.service";
import { EngineeringMDMService } from "../../models/engineering-MDM-service.model";
import { EngineeringEndpointProtectionServiceService } from "../../services/engineering-endpoint-protection-service.service";
import { EngineeringEndpointProtectionService } from "../../models/engineering-endpoint-service.model";
import { EngineeringInfoProtectionService } from "../../models/engineering-Info-protection-service.model";
import { EngineeringEmailMessagingServiceService } from 'src/app/services/engineering-email-messaging-service.service';
import { EngineeringEmailMessagingService } from 'src/app/models/engineering-Email-messaging-service.model';
import { Cyber360MXDRService } from 'src/app/models/cyber360MXDR-service.model';
import { Cyber360MXDRServiceService } from 'src/app/services/cyber360MXDR-service.service';
import { MicrosoftLicensesService } from 'src/app/services/microsoft-licenses.service';
import { MicrosoftLicenses } from 'src/app/models/microsoft-licenses.model';
import { EngineeringInfoProtectionServiceService } from 'src/app/services/engineering-info-protection-service.service';


@Injectable()
export class OrderDetailsEffects {

  constructor(
    private actions$: Actions,
    private store: Store<OrderDetailsState>,
    private router: Router,
    private route: ActivatedRoute,
    private oes: OrderEditService,
    private subjectService: SubjectService,
    private lookupValueService: LookupValueService,
    private orderService: OrderService,
    private securityUtils: SecurityUtilService,
    private titleService: Title,
    private requirementTemplateService: RequirementTemplateService,
    private diaServiceService: DiaServiceService,
    private broadbandServiceService: BroadbandServiceService,
    private ucaasServiceService: UcaasServiceService,
    private crossConnectServiceService: CrossConnectServiceService,
    private ethernetServiceService: EthernetServiceService,
    private gServiceService: GServiceService,
    private televisionServiceService: TelevisionServiceService,
    private mplsServiceService: MplsServiceService,
    private serviceService: ServiceService,
    private activationAttemptService: ActivationAttemptService,
    private disputesService: DisputesService,
    private locationService: LocationService,
    private companyConfigService: CompanyConfigPropertyService,
    private contactService: ContactService,
    private serviceBrokerageService: ServiceBrokerageService,
    private threatMDRServiceService: ThreatMDRServiceService,
    private ransomMDRServiceService: RansomMDRServiceService,
    private riskMDRServiceService: RiskMDRServiceService,
    private engineeringIAMServiceService: EngineeringIAMServiceService,
    private engineeringMDMServiceService: EngineeringMDMServiceService,
    private engineeringEndpointProtectionServiceService: EngineeringEndpointProtectionServiceService,
    private cyber360MXDRServiceService: Cyber360MXDRServiceService,
    private engineeringInfoProtectionServiceService: EngineeringInfoProtectionServiceService,
    private engineeringEMSServiceService: EngineeringEmailMessagingServiceService,
    private microsoftLicensesService: MicrosoftLicensesService
  ) {
  }

  public reloadOrder$ = createEffect(() => this.actions$.pipe(
    ofType(actions.reloadOrder),
    switchMap(action => {
      return forkJoin([
        this.orderService.retrieve(this.oes.order!.id),
        this.contactService.findByOrderId(this.oes.order!.id)
      ]).pipe(
        map(([order, contacts]) => {
          order.contacts = contacts;
          //retain which locations are expanded in the locations/services list
          order.locations.forEach(loc => {
            const orderLoc = this.oes.order!.locations.find(l => l.id == loc.id);
            loc.displayServices = (orderLoc) ? orderLoc.displayServices : false;
          });
          return loadOrderSuccess({order})
        })
      )
    }))
  );

  public onOrderLoadSuccess = createEffect(() => this.actions$.pipe(
    ofType(actions.loadOrderSuccess),
    withLatestFrom(
      this.store.pipe(select(getSelectedLocationOrService)),
      this.store.pipe(select(getSelectedActivationId))
    ),
    map(([action, selected, activationId]) => {
        let inventory = this.router.url.includes('/inventory');
        if (selected instanceof Service) {
          let route = ((this.router.url.includes('?')) ? this.router.url.substring(this.router.url.lastIndexOf('/') + 1, this.router.url.lastIndexOf('?')) : this.router.url.substring(this.router.url.lastIndexOf('/') + 1)) || 'general';
          let queryParams = {};
          if (route == 'activation' && activationId) {
            queryParams = {activationId};
          }
          let orderLoc = action.order.locations.find(l => l.id == (selected as Service).locationId);
          let service = orderLoc!.services.find(s => s.id == selected.id || (!selected.id && s.clientServiceId == (selected as Service).clientServiceId))!;
          if (inventory) {
            this.router.navigate(['inventory', 'order', action.order.id, 'location', service.locationId, 'service', service.id, route], {queryParams}).then(() => {
              this.store.dispatch(setSelectedLocationOrService({selectedLocationOrService: service}));
            });
          } else {
            this.router.navigate(['order', service.orderId, 'location', service.locationId, 'service', service.id, route], {queryParams}).then(() => {
              this.store.dispatch(setSelectedLocationOrService({selectedLocationOrService: service}));
            });
          }
        } else if (selected instanceof Location) {
          let loc = this.router.url.includes('/location/new/general') ? action.order.locations[0] :
            action.order.locations.find(l => l.id == selected.id ||
              (!selected.id && l.clientLocationId == (selected as Location).clientLocationId))!;
          let route = this.router.url.substring(this.router.url.lastIndexOf('/') + 1) || 'general';
          if (inventory) {
            this.router.navigate(['inventory', 'order', loc.orderId, 'location', loc.id, route]).then(() => {
              this.store.dispatch(setSelectedLocationOrService({selectedLocationOrService: loc}));
            });
          } else {
            if (loc) {
              this.router.navigate(['order', loc.orderId, 'location', loc.id, route]).then(() => {
                this.store.dispatch(setSelectedLocationOrService({selectedLocationOrService: loc}));
              });
            }
          }
        }
      }
    )), {dispatch: false}
  );

  public onOrderSave$ = createEffect(() => this.actions$.pipe(
    ofType(actions.saveOrder),
    switchMap(action => {
      //clean up order model
      this.oes.order!.contacts = this.oes.order!.contacts.filter(c => (c.firstName != null && c.firstName != '') || (c.email != null && c.email != '') || c.id != null); //remove contacts w/o first name

      if (!this.oes.order!.company.billingContact?.firstName) {
        this.oes.order!.company.billingContact = undefined; //remove billing contact if first name not set//remove contact info w/o data
      }

      return this.orderService.save(this.oes.order!).pipe(
        map((res: Order) => {
          //retain which locations are expanded in the locations/services list
          res.locations.forEach(loc => {
            const orderLoc = this.oes.order!.locations.find(l => l.id == loc.id);
            loc.displayServices = (orderLoc) ? orderLoc.displayServices : false;
          });
          return actions.reloadOrder();
        }),
        catchError((error) => {
          setTimeout(() => {
            throw error;
          }, 100);
          return of(actions.saveOrderFailure(error))
        }))
    }))
  );

  public onCloneService$ = createEffect(() => this.actions$.pipe(
    ofType(actions.cloneService),
    switchMap((action) => {
      switch (action.serviceType) {
        case ServiceType.DIA:
          return this.diaServiceService.cloneAndCancel(action.service as DiaService).pipe(
            switchMap(service => {
              this.router.routeReuseStrategy.shouldReuseRoute = () => {
                return false;
              };
              this.router.navigate(['/order', service.orderId, 'location', service.locationId, 'service', service.id], {
                relativeTo: this.route,
                replaceUrl: true
              });
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                actions.reloadOrder()
              ]
            }),
            catchError(error => of(actions.cloneServiceFailure(error)))
          );
        case ServiceType.BROADBAND:
          return this.broadbandServiceService.cloneAndCancel(action.service as BroadbandService).pipe(
            switchMap(service => {
              this.router.routeReuseStrategy.shouldReuseRoute = () => {
                return false;
              };
              this.router.navigate(['/order', service.orderId, 'location', service.locationId, 'service', service.id], {
                relativeTo: this.route,
                replaceUrl: true
              });
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                actions.reloadOrder()
              ]
            }),
            catchError(error => of(actions.cloneServiceFailure(error)))
          );
        case ServiceType.UCAAS:
          return this.ucaasServiceService.cloneAndCancel(action.service as UcaasService).pipe(
            switchMap(service => {
              this.router.routeReuseStrategy.shouldReuseRoute = () => {
                return false;
              };
              this.router.navigate(['/order', service.orderId, 'location', service.locationId, 'service', service.id], {
                relativeTo: this.route,
                replaceUrl: true
              });
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                actions.reloadOrder()
              ]
            }),
            catchError(error => of(actions.cloneServiceFailure(error)))
          );
        case ServiceType.G:
          return this.gServiceService.cloneAndCancel(action.service as GService).pipe(
            switchMap(service => {
              this.router.routeReuseStrategy.shouldReuseRoute = () => {
                return false;
              };
              this.router.navigate(['/order', service.orderId, 'location', service.locationId, 'service', service.id], {
                relativeTo: this.route,
                replaceUrl: true
              });
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                actions.reloadOrder()
              ]
            }),
            catchError(error => of(actions.cloneServiceFailure(error)))
          );
        case ServiceType.CROSSCONNECT:
          return this.crossConnectServiceService.cloneAndCancel(action.service as CrossConnectService).pipe(
            switchMap(service => {
              this.router.routeReuseStrategy.shouldReuseRoute = () => {
                return false;
              };
              this.router.navigate(['/order', service.orderId, 'location', service.locationId, 'service', service.id], {
                relativeTo: this.route,
                replaceUrl: true
              });
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                actions.reloadOrder()
              ]
            }),
            catchError(error => of(actions.cloneServiceFailure(error)))
          );
        case ServiceType.ETHERNET:
          return this.ethernetServiceService.cloneAndCancel(action.service as EthernetService).pipe(
            switchMap(service => {
              this.router.routeReuseStrategy.shouldReuseRoute = () => {
                return false;
              };
              this.router.navigate(['/order', service.orderId, 'location', service.locationId, 'service', service.id], {
                relativeTo: this.route,
                replaceUrl: true
              });
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                actions.reloadOrder()
              ]
            }),
            catchError(error => of(actions.cloneServiceFailure(error)))
          );
        case ServiceType.TELEVISION:
          return this.televisionServiceService.cloneAndCancel(action.service as TelevisionService).pipe(
            switchMap(service => {
              this.router.routeReuseStrategy.shouldReuseRoute = () => {
                return false;
              };
              this.router.navigate(['/order', service.orderId, 'location', service.locationId, 'service', service.id], {
                relativeTo: this.route,
                replaceUrl: true
              });
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                actions.reloadOrder()
              ]
            }),
            catchError(error => of(actions.cloneServiceFailure(error)))
          );
        case ServiceType.MPLS:
          return this.mplsServiceService.cloneAndCancel(action.service as MplsService).pipe(
            switchMap(service => {
              this.router.routeReuseStrategy.shouldReuseRoute = () => {
                return false;
              };
              this.router.navigate(['/order', service.orderId, 'location', service.locationId, 'service', service.id], {
                relativeTo: this.route,
                replaceUrl: true
              });
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                actions.reloadOrder()
              ]
            }),
            catchError(error => of(actions.cloneServiceFailure(error)))
          );
        default:
          return of(actions.cloneServiceFailure('can\'t save service of type: ' + action.service.type))
      }
    }))
  );

  public loadTemplates$ = createEffect(() => this.actions$.pipe(
    ofType(actions.loadOrderSuccess),
    switchMap(action => {
        return this.requirementTemplateService.getTemplates(action.order.company.id, null)
          .pipe(map((requirementTemplates: RequirementTemplate[]) =>
              actions.loadRequirementTemplatesSuccess({requirementTemplates})
            ),
            catchError(error => of(actions.loadRequirementTemplatesFailure(error)))
          );
      }
    ))
  );

  public onSetSelectedLocationOrService$ = createEffect(() => this.actions$.pipe(
    ofType(actions.setSelectedLocationOrService),
    withLatestFrom(
      this.store.select(getOrder)
    ),
    mergeMap(([action, order]) => {

        // @ts-ignore

        if (action.selectedLocationOrService && !selectedIsLocation(action.selectedLocationOrService)) {
          this.titleService.setTitle('i90 - ' + order?.company.name + ' - ' + (action.selectedLocationOrService as Service).type);
          return [
            actions.loadActivationCount({serviceId: action.selectedLocationOrService.id}),
            actions.loadDisputesCount({serviceId: action.selectedLocationOrService.id})
          ];
        } else {
          this.titleService.setTitle('i90 - ' + order?.company.name);
          return [
            actions.loadDisputesCount({locationId: action.selectedLocationOrService?.id})
          ];
        }
      }
    ))
  );

  public onLoadUserStatuses = createEffect(() => this.actions$.pipe(
    ofType(actions.loadUserStatuses),
    switchMap(action => {
      return [
        setUserStatuses({
          isAdmin: this.securityUtils.getIsAdminUser(),
          isOrderRead: this.securityUtils.getIsOrderReadUser(),
          isOrderWrite: this.securityUtils.getIsOrderWriteUser(),
          isOrderWriteTerminal: this.securityUtils.getIsOrderWriteTerminalUser(),
          isInventoryRead: this.securityUtils.getIsInventoryReadUser(),
          isInventoryWrite: this.securityUtils.getIsInventoryWriteUser(),
        })
      ]
    })
  ));

  public onLoadInventoryLocation = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadInventoryLocation
    ),
    switchMap((action) => {
      return this.serviceService.getInventoryLocation(action.serviceId).pipe(
        map((inventoryLocation: Location) => actions.loadInventoryLocationSuccess({inventoryLocation: inventoryLocation})),
        catchError((error: HttpErrorResponse) => {
          return of(actions.loadInventoryLocationFailure({errorMessage: error.message}));
        })
      );
    })
  ));

  public onLoadRelatedMacds = createEffect(() => this.actions$.pipe(
    ofType(
      actions.loadRelatedMacds
    ),
    switchMap((action) => {
      return this.serviceService.getRelatedMacds(action.serviceId).pipe(
        map((macds: Service[]) => actions.loadRelatedMacdsSuccess({relatedMacds: macds})),
        catchError((error: HttpErrorResponse) => {
          return of(actions.loadRelatedMacdsFailure({errorMessage: error.message}));
        })
      );
    })
  ));

  public onReadParams = createEffect(() => this.actions$.pipe(
    ofType(actions.onReadParams),
    mergeMap((action) => {
      return forkJoin([
        this.orderService.retrieve(+action['id']),
        this.contactService.findByOrderId(+action['id'])
      ]).pipe(
        mergeMap(([res, contacts]) => {
          res.contacts = contacts;
          const location = res.locations.find(l => l.id == +action['id']);
          return [
            loadOrderSuccess({order: res}),
            loadLookupValuesByKey({
              key: 'clientManagers',
              lookupKey: 'CLIENT_PROJECT_MANAGER',
              companyId: res.company.id
            }),
            loadProvisioners({orderId: res.id}),
            // @ts-ignore
            setSelectedLocationOrService({selectedLocationOrService: plainToClass(Location, location)}),
            setUserStatuses({
              isAdmin: this.securityUtils.getIsAdminUser(),
              isOrderRead: this.securityUtils.getIsOrderReadUser(),
              isOrderWrite: this.securityUtils.getIsOrderWriteUser(),
              isOrderWriteTerminal: this.securityUtils.getIsOrderWriteTerminalUser(),
              isInventoryRead: this.securityUtils.getIsInventoryReadUser(),
              isInventoryWrite: this.securityUtils.getIsInventoryWriteUser(),
            })
          ];
        })
      );
    })
  ));

  public onLoadSubjects$ = createEffect(() => this.actions$.pipe(
      ofType(actions.loadProvisioners),
      switchMap((action) => {
        return this.subjectService.getSubjects(action.orderId).pipe(
          map((subjects: SubjectInterface[]) => actions.loadProvisionersSuccess({subjects})),
          catchError(error => of(actions.loadProvisionersFailure(error)))
        )
      })
    )
  );

  public onSetMissingServiceField$ = createEffect(() => this.actions$.pipe(
    ofType(
      actions.setMissingServiceField
    ),
    withLatestFrom(
      this.store.pipe(select(getSelectedLocationOrService))
    ),
    switchMap(([item, selectedLocationOrService]) => {
      if (item) {
        setTimeout(() => {
          // @ts-ignore
          this.router.navigate(['/order', this.oes.order?.id || 'new', 'location', selectedLocationOrService?.locationId, 'service', selectedLocationOrService?.id, 'general']);
        }, 5);
        setTimeout(() => {
          setTimeout(() => {
            const formArray = Array.from(this.oes.forms.keys());
            for (let i = 0; i < formArray.length; i++) {
              if (formArray[i].form.controls[item.missingFieldName]) {
                formArray[i].form.controls[item.missingFieldName].setErrors({'missing': true});
              }
            }
          }, 15);
          throw Error(item.missingFieldDisplayName + ' must be set before ' + item.milestoneName + ' milestone can be saved');
        }, 10);
      }
      return [
        actions.toggleEdit({key: 'editingServiceBilling'}),
        actions.setActiveTab({
          tab: 'General',
          host: 'service'
        })
      ]
    })
  ));

  public onLoadLookupValue$ = createEffect(() => this.actions$.pipe(
      ofType(actions.loadLookupValuesByKey),
      mergeMap((action) => {
        return this.lookupValueService.find(action.lookupKey, action.companyId).pipe(
          map(item => {
            return item
          }),
          map((result: LookupValue[]) => actions.loadLookupValuesByKeySuccess({
            key: action.key,
            values: result
          })),
          catchError(error => of(actions.loadLookupValuesByKeyFailure(error)))
        )
      })
    )
  );

  public onSaveLocation = createEffect(() => this.actions$.pipe(
    ofType(actions.saveLocation),
    switchMap((action) => {
      const newLocation = action.location.id;
      return this.locationService.save(action.location).pipe(
        switchMap((location) => {
          if (newLocation == undefined && action.location.currentInventory) {
            this.router.navigate(['inventory', 'order', location.orderId, 'location', location.id, 'general']);
            this.store.dispatch(setSelectedLocationOrService({selectedLocationOrService: location}));
            return [
              actions.setSelectedLocationOrService({selectedLocationOrService: location}),
            ];
          } else {
            return [
              actions.setSelectedLocationOrService({selectedLocationOrService: location}),
              actions.reloadOrder()
            ];
          }
        }),
        catchError((error) => {
          setTimeout(() => {
            throw error;
          }, 100);
          return [
            actions.saveLocationFailure(error),
            actions.setSelectedLocationOrService({selectedLocationOrService: action.location})
          ];
        })
      );
    })
  ));

  public onSaveService = createEffect(() => this.actions$.pipe(
      ofType(actions.saveService),
      switchMap((action) => {
        switch (action.service.type) {
          case ServiceType.DIA:
            return this.diaServiceService.save(action.service as DiaService).pipe(
              switchMap((service) => {
                return [
                  actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                  actions.reloadOrder()
                ]
              }),
              catchError((error) => {
                setTimeout(() => {
                  throw error;
                }, 100);
                return of(actions.saveServiceFailure(error))
              })
            );
          case ServiceType.BROADBAND:
            return this.broadbandServiceService.save(action.service as BroadbandService).pipe(
              switchMap((service) => {
                return [
                  actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                  actions.reloadOrder()
                ]
              }),
              catchError((error) => {
                setTimeout(() => {
                  throw error;
                }, 100);
                return of(actions.saveServiceFailure(error))
              })
            );
          case ServiceType.UCAAS:
            return this.ucaasServiceService.save(action.service as UcaasService).pipe(
              switchMap((service) => {
                return [
                  actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                  actions.reloadOrder()
                ]
              }),
              catchError((error) => {
                setTimeout(() => {
                  throw error;
                }, 100);
                return of(actions.saveServiceFailure(error))
              })
            );
          case ServiceType.G:
            return this.gServiceService.save(action.service as GService).pipe(
              switchMap((service) => {
                return [
                  actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                  actions.reloadOrder()
                ]
              }),
              catchError((error) => {
                setTimeout(() => {
                  throw error;
                }, 100);
                return of(actions.saveServiceFailure(error))
              })
            );
          case ServiceType.CROSSCONNECT:
            return this.crossConnectServiceService.save(action.service as CrossConnectService).pipe(
              switchMap((service) => {
                return [
                  actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                  actions.reloadOrder()
                ]
              }),
              catchError((error) => {
                setTimeout(() => {
                  throw error;
                }, 100);
                return of(actions.saveServiceFailure(error))
              })
            );
          case ServiceType.ETHERNET:
            return this.ethernetServiceService.save(action.service as EthernetService).pipe(
              switchMap((service) => {
                return [
                  actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                  actions.reloadOrder()
                ]
              }),
              catchError((error) => {
                setTimeout(() => {
                  throw error;
                }, 100);
                return of(actions.saveServiceFailure(error))
              })
            );
          case ServiceType.TELEVISION:
            return this.televisionServiceService.save(action.service as TelevisionService).pipe(
              switchMap((service) => {
                return [
                  actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                  actions.reloadOrder()
                ]
              }),
              catchError((error) => {
                setTimeout(() => {
                  throw error;
                }, 100);
                return of(actions.saveServiceFailure(error))
              })
            );
          case ServiceType.MPLS:
            return this.mplsServiceService.save(action.service as MplsService).pipe(
              switchMap((service) => {
                return [
                  actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                  actions.reloadOrder()
                ]
              }),
              catchError((error) => {
                setTimeout(() => {
                  throw error;
                }, 100);
                return of(actions.saveServiceFailure(error))
              })
            );
          case ServiceType.THREATMDR:
            return this.threatMDRServiceService.save(action.service as ThreatMDRService).pipe(
              switchMap((service) => {
                return [
                  actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                  actions.reloadOrder()
                ]
              }),
              catchError((error) => {
                setTimeout(() => {
                  throw error;
                }, 100);
                return of(actions.saveServiceFailure(error))
              })
            );
          case ServiceType.RANSOMMDR:
            return this.ransomMDRServiceService.save(action.service as RansomMDRService).pipe(
              switchMap((service) => {
                return [
                  actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                  actions.reloadOrder()
                ]
              }),
              catchError((error) => {
                setTimeout(() => {
                  throw error;
                }, 100);
                return of(actions.saveServiceFailure(error))
              })
            );
          case ServiceType.RISKMDR:
            return this.riskMDRServiceService.save(action.service as RiskMDRService).pipe(
              switchMap((service) => {
                return [
                  actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                  actions.reloadOrder()
                ]
              }),
              catchError((error) => {
                setTimeout(() => {
                  throw error;
                }, 100);
                return of(actions.saveServiceFailure(error))
              })
            );
          case ServiceType.ENGINEERING_IAM:
            return this.engineeringIAMServiceService.save(action.service as EngineeringIAMService).pipe(
              switchMap((service) => {
                return [
                  actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                  actions.reloadOrder()
                ]
              }),
              catchError((error) => {
                setTimeout(() => {
                  throw error;
                }, 100);
                return of(actions.saveServiceFailure(error))
              })
            );
          case ServiceType.ENGINEERING_MDM:
            return this.engineeringMDMServiceService.save(action.service as EngineeringMDMService).pipe(
              switchMap((service) => {
                return [
                  actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                  actions.reloadOrder()
                ]
              }),
              catchError((error) => {
                setTimeout(() => {
                  throw error;
                }, 100);
                return of(actions.saveServiceFailure(error))
              })
            );
          case ServiceType.ENGINEERING_ENDPOINT:
            return this.engineeringEndpointProtectionServiceService.save(action.service as EngineeringEndpointProtectionService).pipe(
              switchMap((service) => {
                return [
                  actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                  actions.reloadOrder()
                ]
              }),
              catchError((error) => {
                setTimeout(() => {
                  throw error;
                }, 100);
                return of(actions.saveServiceFailure(error))
              })
            );
          case ServiceType.ENGINEERING_INFO_PROTECTION:
            return this.engineeringInfoProtectionServiceService.save(action.service as EngineeringInfoProtectionService).pipe(
              switchMap((service) => {
                return [
                  actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                  actions.reloadOrder()
                ]
              }),
              catchError((error) => {
                setTimeout(() => {
                  throw error;
                }, 100);
                return of(actions.saveServiceFailure(error))
              })
            );
          case ServiceType.ENGINEERING_EMAIL_MESSAGING:
            return this.engineeringEMSServiceService.save(action.service as EngineeringEmailMessagingService).pipe(
              switchMap((service) => {
                return [
                  actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                  actions.reloadOrder()
                ]
              }),
              catchError((error) => {
                setTimeout(() => {
                  throw error;
                }, 100);
                return of(actions.saveServiceFailure(error))
              })
            );
          case ServiceType.CYBER360MXDR:
            return this.cyber360MXDRServiceService.save(action.service as Cyber360MXDRService).pipe(
              switchMap((service) => {
                return [
                  actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                  actions.reloadOrder()
                ]
              }),
              catchError((error) => {
                setTimeout(() => {
                  throw error;
                }, 100);
                return of(actions.saveServiceFailure(error))
              })
            );
          case ServiceType.MICROSOFTLICENSES:
            return this.microsoftLicensesService.save(action.service as MicrosoftLicenses).pipe(
              switchMap((service) => {
                return [
                  actions.setSelectedLocationOrService({ selectedLocationOrService: service }),
                  actions.reloadOrder()
                ]
              }),
              catchError((error) => {
                setTimeout(() => {
                  throw error;
                }, 100);
                return of(actions.saveServiceFailure(error))
              })
            );
          default:
            return of(actions.saveServiceFailure('can\'t save service of type: ' + action.service.type))
        }
      })
    )
  );

  public onLoadActivationCount$ = createEffect(() => this.actions$.pipe(
      ofType(actions.loadActivationCount),
      filter(action => action.serviceId !== undefined),
      switchMap((action) => {
        return this.activationAttemptService.findByServiceId(action.serviceId).pipe(
          map((attempts: ActivationAttempt[]) => {
            let openAttempts = attempts.filter(a => a.scheduledAttemptStatus == 'Schedule Date Confirmed' || a.scheduledAttemptStatus == 'In Progress');
            return actions.loadActivationCountSuccess({activationCount: openAttempts.length})
          }),
        )
      })
    )
  );

  public onLoadDisputesCount$ = createEffect(() => this.actions$.pipe(
      ofType(actions.loadDisputesCount),
      filter(action => action.serviceId !== undefined),
      switchMap((action) => {
        if (action.serviceId) {
          return this.disputesService.findByServiceId(action.serviceId).pipe(
            map((disputes: Dispute[]) => {
              let openDisputes = disputes.filter(d => d.disputeClosedDate == null);
              return actions.loadDisputesCountSuccess({disputesCount: openDisputes.length})
            }),
          )
        } else if (action.locationId) {
          let criteria = new DisputeSearchCriteria();
          criteria.locationId = action.locationId;
          return this.disputesService.search(criteria).pipe(
            map((disputes: PaginatedResult<Dispute>) => {
              let openDisputes = disputes.collection.filter(d => d.disputeClosedDate == null);
              return actions.loadDisputesCountSuccess({disputesCount: openDisputes.length})
            })
          )
        } else {
          return of(actions.loadDisputesCountSuccess({disputesCount: 0}));
        }
      })
    )
  );

  onLoadCompanyConfig$ = createEffect(() => this.actions$.pipe(
    ofType(actions.loadCompanyConfig),
    switchMap((action) => {
      return forkJoin([
        this.companyConfigService.getValue('LOCK_ONE_LOCATION_PER_ORDER'),
        this.companyConfigService.getValue('SHOW_BROKERAGE_FIELDS'),
        this.companyConfigService.getValue('AUTO_CREATE_CLIENT_SERVICE_ID')
      ]).pipe(
        map(([value1, value2, value3]) => {
          const lockOneLocationPerOrder = value1 && value1.value ? JSON.parse(value1.value.toLowerCase()) : false;
          const showBrokerageFields = value2 && value2.value ? JSON.parse(value2.value.toLowerCase()) : false;
          const autoCreateClientServiceId = value3 && value3.value ? JSON.parse(value3.value.toLowerCase()) : false;
          return actions.loadCompanyConfigSuccess({
            lockOneLocationPerOrder,
            showBrokerageFields,
            autoCreateClientServiceId
          });
        })
      );
    })
  ));

  onLoadUserTenantAccess$ = createEffect(() => this.actions$.pipe(
    ofType(actions.loadUserTenantAccess),
    switchMap((action) => {
      return this.subjectService.getUserTenantAccess().pipe(
        map((userHasTenantAccess: boolean) => {
          return actions.loadUserTenantAccessSuccess({userHasTenantAccess});
        })
      );
    })
  ));

  saveServiceBrokerage = createEffect(() => this.actions$.pipe(
    ofType(
      actions.saveServiceBrokerage
    ),
    switchMap((action) => {
      return this.serviceBrokerageService.save(action.serviceBrokerage).pipe(
        switchMap((serviceBrokerage: any) => [actions.saveServiceBrokerageSuccess({serviceBrokerage}),
          actions.reloadOrder()]),
      );
    })
  ))

  loadServiceBrokerage = createEffect(() => this.actions$.pipe(
    ofType(
      loadServiceBrokerage
    ),
    switchMap((action) => {
      return this.serviceBrokerageService.retrieve(action.serviceId).pipe(
        map((serviceBrokerage: any) => {
          if (!serviceBrokerage) {
            serviceBrokerage = new ServiceBrokerage();
            serviceBrokerage.serviceId = action.serviceId;
          }
          return loadServiceBrokerageSuccess(serviceBrokerage)
        }),
        catchError((error: HttpErrorResponse) => {
          if (error.status === 404) {
            // If the error is a 404, create a new ServiceBrokerage
            const newServiceBrokerage = new ServiceBrokerage();
            newServiceBrokerage.serviceId = action.serviceId;
            return of(loadServiceBrokerageSuccess(newServiceBrokerage));
          } else {
            // Rethrow other errors
            return throwError(error);
          }
        })
      );
    })
  ));

  public onPullFromCrm$ = createEffect(() => this.actions$.pipe(
    ofType(actions.pullFromCrm),
    switchMap(action => {
      switch (action.service.type) {
        case ServiceType.DIA:
          return this.diaServiceService.pullFromCrm(action.service as DiaService).pipe(
            switchMap((service) => {
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                actions.reloadOrder()
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.pullFromCrmFailure(error))
            })
          );
        case ServiceType.BROADBAND:
          return this.broadbandServiceService.pullFromCrm(action.service as BroadbandService).pipe(
            switchMap((service) => {
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                actions.reloadOrder()
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.pullFromCrmFailure(error))
            })
          );
        case ServiceType.UCAAS:
          return this.ucaasServiceService.pullFromCrm(action.service as UcaasService).pipe(
            switchMap((service) => {
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                actions.reloadOrder()
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.pullFromCrmFailure(error))
            })
          );
        case ServiceType.G:
          return this.gServiceService.pullFromCrm(action.service as GService).pipe(
            switchMap((service) => {
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                actions.reloadOrder()
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.pullFromCrmFailure(error))
            })
          );
        case ServiceType.CROSSCONNECT:
          return this.crossConnectServiceService.pullFromCrm(action.service as CrossConnectService).pipe(
            switchMap((service) => {
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                actions.reloadOrder()
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.pullFromCrmFailure(error))
            })
          );
        case ServiceType.ETHERNET:
          return this.ethernetServiceService.pullFromCrm(action.service as EthernetService).pipe(
            switchMap((service) => {
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                actions.reloadOrder()
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.pullFromCrmFailure(error))
            })
          );
        case ServiceType.TELEVISION:
          return this.televisionServiceService.pullFromCrm(action.service as TelevisionService).pipe(
            switchMap((service) => {
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                actions.reloadOrder()
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.pullFromCrmFailure(error))
            })
          );
        case ServiceType.MPLS:
          return this.mplsServiceService.pullFromCrm(action.service as MplsService).pipe(
            switchMap((service) => {
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                actions.reloadOrder()
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.pullFromCrmFailure(error))
            })
          );
        case ServiceType.THREATMDR:
          return this.threatMDRServiceService.pullFromCrm(action.service as ThreatMDRService).pipe(
            switchMap((service) => {
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                actions.reloadOrder()
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.pullFromCrmFailure(error))
            })
          );
        case ServiceType.RANSOMMDR:
          return this.ransomMDRServiceService.pullFromCrm(action.service as RansomMDRService).pipe(
            switchMap((service) => {
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                actions.reloadOrder()
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.pullFromCrmFailure(error))
            })
          );
        case ServiceType.RISKMDR:
          return this.riskMDRServiceService.pullFromCrm(action.service as RiskMDRService).pipe(
            switchMap((service) => {
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                actions.reloadOrder()
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.pullFromCrmFailure(error))
            })
          );
        case ServiceType.ENGINEERING_IAM:
          return this.engineeringIAMServiceService.pullFromCrm(action.service as EngineeringIAMService).pipe(
            switchMap((service) => {
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                actions.reloadOrder()
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.pullFromCrmFailure(error))
            })
          );
        case ServiceType.ENGINEERING_MDM:
          return this.engineeringMDMServiceService.pullFromCrm(action.service as EngineeringMDMService).pipe(
            switchMap((service) => {
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                actions.reloadOrder()
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.pullFromCrmFailure(error))
            })
          );
        case ServiceType.ENGINEERING_ENDPOINT:
          return this.engineeringEndpointProtectionServiceService.pullFromCrm(action.service as EngineeringEndpointProtectionService).pipe(
            switchMap((service) => {
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                actions.reloadOrder()
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.pullFromCrmFailure(error))
            })
          );
        case ServiceType.ENGINEERING_INFO_PROTECTION:
          return this.engineeringInfoProtectionServiceService.pullFromCrm(action.service as EngineeringInfoProtectionService).pipe(
            switchMap((service) => {
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                actions.reloadOrder()
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.pullFromCrmFailure(error))
            })
          );
        case ServiceType.ENGINEERING_EMAIL_MESSAGING:
          return this.engineeringEMSServiceService.pullFromCrm(action.service as EngineeringEmailMessagingService).pipe(
            switchMap((service) => {
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                actions.reloadOrder()
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.pullFromCrmFailure(error))
            })
          );
        case ServiceType.CYBER360MXDR:
          return this.cyber360MXDRServiceService.pullFromCrm(action.service as Cyber360MXDRService).pipe(
            switchMap((service) => {
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: service}),
                actions.reloadOrder()
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.pullFromCrmFailure(error))
            })
          );
        default:
          return of(actions.pullFromCrmFailure('can\'t pull CRM data for service of type: ' + action.service.type))
      }
    })
  ));


  public onDeleteService$ = createEffect(() => this.actions$.pipe(
    ofType(actions.deleteService),
    switchMap(action => {
      switch (action.service.type) {
        case ServiceType.DIA:
          return this.diaServiceService.delete(action.service as DiaService).pipe(
            switchMap((service) => {
              if (action.location.currentInventory) {
                this.router.navigate(['inventory', 'order', action.location.orderId, 'location', action.location.id, 'general']);
              } else {
                this.router.navigate(['/order', action.location.orderId, 'location', action.location.id, 'general']);
              }
              this.store.dispatch(setSelectedLocationOrService({selectedLocationOrService: action.location}));
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: action.location}),
                actions.reloadOrder(),
                actions.deleteServiceSuccess({location: action.location})
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.deleteServiceFailure(error))
            })
          );
        case ServiceType.BROADBAND:
          return this.broadbandServiceService.delete(action.service as BroadbandService).pipe(
            switchMap((service) => {
              if (action.location.currentInventory) {
                this.router.navigate(['inventory', 'order', action.location.orderId, 'location', action.location.id, 'general']);
              } else {
                this.router.navigate(['/order', action.location.orderId, 'location', action.location.id, 'general']);
              }
              this.store.dispatch(setSelectedLocationOrService({selectedLocationOrService: action.location}));
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: action.location}),
                actions.reloadOrder(),
                actions.deleteServiceSuccess({location: action.location})
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.deleteServiceFailure(error))
            })
          );
        case ServiceType.UCAAS:
          return this.ucaasServiceService.delete(action.service as UcaasService).pipe(
            switchMap((service) => {
              if (action.location.currentInventory) {
                this.router.navigate(['inventory', 'order', action.location.orderId, 'location', action.location.id, 'general']);
              } else {
                this.router.navigate(['/order', action.location.orderId, 'location', action.location.id, 'general']);
              }
              this.store.dispatch(setSelectedLocationOrService({selectedLocationOrService: action.location}));
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: action.location}),
                actions.reloadOrder(),
                actions.deleteServiceSuccess({location: action.location})
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.deleteServiceFailure(error))
            })
          );
        case ServiceType.G:
          return this.gServiceService.delete(action.service as GService).pipe(
            switchMap((service) => {
              if (action.location.currentInventory) {
                this.router.navigate(['inventory', 'order', action.location.orderId, 'location', action.location.id, 'general']);
              } else {
                this.router.navigate(['/order', action.location.orderId, 'location', action.location.id, 'general']);
              }
              this.store.dispatch(setSelectedLocationOrService({selectedLocationOrService: action.location}));
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: action.location}),
                actions.reloadOrder(),
                actions.deleteServiceSuccess({location: action.location})
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.deleteServiceFailure(error))
            })
          );
        case ServiceType.CROSSCONNECT:
          return this.crossConnectServiceService.delete(action.service as CrossConnectService).pipe(
            switchMap((service) => {
              if (action.location.currentInventory) {
                this.router.navigate(['inventory', 'order', action.location.orderId, 'location', action.location.id, 'general']);
              } else {
                this.router.navigate(['/order', action.location.orderId, 'location', action.location.id, 'general']);
              }
              this.store.dispatch(setSelectedLocationOrService({selectedLocationOrService: action.location}));
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: action.location}),
                actions.reloadOrder(),
                actions.deleteServiceSuccess({location: action.location})
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.deleteServiceFailure(error))
            })
          );
        case ServiceType.ETHERNET:
          return this.ethernetServiceService.delete(action.service as EthernetService).pipe(
            switchMap((service) => {
              if (action.location.currentInventory) {
                this.router.navigate(['inventory', 'order', action.location.orderId, 'location', action.location.id, 'general']);
              } else {
                this.router.navigate(['/order', action.location.orderId, 'location', action.location.id, 'general']);
              }
              this.store.dispatch(setSelectedLocationOrService({selectedLocationOrService: action.location}));
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: action.location}),
                actions.reloadOrder(),
                actions.deleteServiceSuccess({location: action.location})
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.deleteServiceFailure(error))
            })
          );
        case ServiceType.TELEVISION:
          return this.televisionServiceService.delete(action.service as TelevisionService).pipe(
            switchMap((service) => {
              if (action.location.currentInventory) {
                this.router.navigate(['inventory', 'order', action.location.orderId, 'location', action.location.id, 'general']);
              } else {
                this.router.navigate(['/order', action.location.orderId, 'location', action.location.id, 'general']);
              }
              this.store.dispatch(setSelectedLocationOrService({selectedLocationOrService: action.location}));
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: action.location}),
                actions.reloadOrder(),
                actions.deleteServiceSuccess({location: action.location})
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.deleteServiceFailure(error))
            })
          );
        case ServiceType.MPLS:
          return this.mplsServiceService.delete(action.service as MplsService).pipe(
            switchMap((service) => {
              if (action.location.currentInventory) {
                this.router.navigate(['inventory', 'order', action.location.orderId, 'location', action.location.id, 'general']);
              } else {
                this.router.navigate(['/order', action.location.orderId, 'location', action.location.id, 'general']);
              }
              this.store.dispatch(setSelectedLocationOrService({selectedLocationOrService: action.location}));
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: action.location}),
                actions.reloadOrder(),
                actions.deleteServiceSuccess({location: action.location})
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.deleteServiceFailure(error))
            })
          );
        case ServiceType.THREATMDR:
          return this.threatMDRServiceService.delete(action.service as ThreatMDRService).pipe(
            switchMap((service) => {
              if (action.location.currentInventory) {
                this.router.navigate(['inventory', 'order', action.location.orderId, 'location', action.location.id, 'general']);
              } else {
                this.router.navigate(['/order', action.location.orderId, 'location', action.location.id, 'general']);
              }
              this.store.dispatch(setSelectedLocationOrService({selectedLocationOrService: action.location}));
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: action.location}),
                actions.reloadOrder(),
                actions.deleteServiceSuccess({location: action.location})
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.deleteServiceFailure(error))
            })
          );
        case ServiceType.RANSOMMDR:
          return this.ransomMDRServiceService.delete(action.service as RansomMDRService).pipe(
            switchMap((service) => {
              if (action.location.currentInventory) {
                this.router.navigate(['inventory', 'order', action.location.orderId, 'location', action.location.id, 'general']);
              } else {
                this.router.navigate(['/order', action.location.orderId, 'location', action.location.id, 'general']);
              }
              this.store.dispatch(setSelectedLocationOrService({selectedLocationOrService: action.location}));
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: action.location}),
                actions.reloadOrder(),
                actions.deleteServiceSuccess({location: action.location})
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.deleteServiceFailure(error))
            })
          );
        case ServiceType.RISKMDR:
          return this.riskMDRServiceService.delete(action.service as RiskMDRService).pipe(
            switchMap((service) => {
              if (action.location.currentInventory) {
                this.router.navigate(['inventory', 'order', action.location.orderId, 'location', action.location.id, 'general']);
              } else {
                this.router.navigate(['/order', action.location.orderId, 'location', action.location.id, 'general']);
              }
              this.store.dispatch(setSelectedLocationOrService({selectedLocationOrService: action.location}));
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: action.location}),
                actions.reloadOrder(),
                actions.deleteServiceSuccess({location: action.location})
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.deleteServiceFailure(error))
            })
          );
        case ServiceType.ENGINEERING_IAM:
          return this.engineeringIAMServiceService.delete(action.service as EngineeringIAMService).pipe(
            switchMap((service) => {
              if (action.location.currentInventory) {
                this.router.navigate(['inventory', 'order', action.location.orderId, 'location', action.location.id, 'general']);
              } else {
                this.router.navigate(['/order', action.location.orderId, 'location', action.location.id, 'general']);
              }
              this.store.dispatch(setSelectedLocationOrService({selectedLocationOrService: action.location}));
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: action.location}),
                actions.reloadOrder(),
                actions.deleteServiceSuccess({location: action.location})
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.deleteServiceFailure(error))
            })
          );
        case ServiceType.ENGINEERING_MDM:
          return this.engineeringMDMServiceService.delete(action.service as EngineeringMDMService).pipe(
            switchMap((service) => {
              if (action.location.currentInventory) {
                this.router.navigate(['inventory', 'order', action.location.orderId, 'location', action.location.id, 'general']);
              } else {
                this.router.navigate(['/order', action.location.orderId, 'location', action.location.id, 'general']);
              }
              this.store.dispatch(setSelectedLocationOrService({selectedLocationOrService: action.location}));
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: action.location}),
                actions.reloadOrder(),
                actions.deleteServiceSuccess({location: action.location})
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.deleteServiceFailure(error))
            })
          );
        case ServiceType.ENGINEERING_ENDPOINT:
          return this.engineeringEndpointProtectionServiceService.delete(action.service as EngineeringEndpointProtectionService).pipe(
            switchMap((service) => {
              if (action.location.currentInventory) {
                this.router.navigate(['inventory', 'order', action.location.orderId, 'location', action.location.id, 'general']);
              } else {
                this.router.navigate(['/order', action.location.orderId, 'location', action.location.id, 'general']);
              }
              this.store.dispatch(setSelectedLocationOrService({selectedLocationOrService: action.location}));
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: action.location}),
                actions.reloadOrder(),
                actions.deleteServiceSuccess({location: action.location})
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.deleteServiceFailure(error))
            })
          );
        case ServiceType.ENGINEERING_INFO_PROTECTION:
          return this.engineeringInfoProtectionServiceService.delete(action.service as EngineeringInfoProtectionService).pipe(
            switchMap((service) => {
              if (action.location.currentInventory) {
                this.router.navigate(['inventory', 'order', action.location.orderId, 'location', action.location.id, 'general']);
              } else {
                this.router.navigate(['/order', action.location.orderId, 'location', action.location.id, 'general']);
              }
              this.store.dispatch(setSelectedLocationOrService({selectedLocationOrService: action.location}));
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: action.location}),
                actions.reloadOrder(),
                actions.deleteServiceSuccess({location: action.location})
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.deleteServiceFailure(error))
            })
          );
        case ServiceType.ENGINEERING_EMAIL_MESSAGING:
          return this.engineeringEMSServiceService.delete(action.service as EngineeringEmailMessagingService).pipe(
            switchMap((service) => {
              if (action.location.currentInventory) {
                this.router.navigate(['inventory', 'order', action.location.orderId, 'location', action.location.id, 'general']);
              } else {
                this.router.navigate(['/order', action.location.orderId, 'location', action.location.id, 'general']);
              }
              this.store.dispatch(setSelectedLocationOrService({selectedLocationOrService: action.location}));
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: action.location}),
                actions.reloadOrder(),
                actions.deleteServiceSuccess({location: action.location})
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.deleteServiceFailure(error))
            })
          );
        case ServiceType.CYBER360MXDR:
          return this.cyber360MXDRServiceService.delete(action.service as Cyber360MXDRService).pipe(
            switchMap((service) => {
              if (action.location.currentInventory) {
                this.router.navigate(['inventory', 'order', action.location.orderId, 'location', action.location.id, 'general']);
              } else {
                this.router.navigate(['/order', action.location.orderId, 'location', action.location.id, 'general']);
              }
              this.store.dispatch(setSelectedLocationOrService({selectedLocationOrService: action.location}));
              return [
                actions.setSelectedLocationOrService({selectedLocationOrService: action.location}),
                actions.reloadOrder(),
                actions.deleteServiceSuccess({location: action.location})
              ]
            }),
            catchError((error) => {
              setTimeout(() => {
                throw error;
              }, 100);
              return of(actions.deleteServiceFailure(error))
            })
          );
        default:
          return of(actions.deleteServiceFailure('can\'t delete Service for service of type: ' + action.service.type))
      }
    })
  ));

  onDeleteServiceSuccess$ = createEffect(() =>
      this.actions$.pipe(
        ofType(actions.deleteServiceSuccess),
        tap(() => {
          alert('The Service has been marked for deletion!');
        })
      ),
    {dispatch: false} // Prevents another action from being dispatched
  );

  public onDeleteLocation$ = createEffect(() => this.actions$.pipe(
    ofType(actions.deleteLocation),
    switchMap(action => {
      return this.locationService.delete(action.location).pipe(
        map(() => {
          return actions.deleteLocationSuccess({order: action.order, isInventory: action.location.currentInventory});
        }),
        catchError((error) => {
          setTimeout(() => {
            throw error;
          }, 100);
          return of(actions.deleteLocationFailure({error}));
        })
      );
    })
  ));


  onDeleteLocationSuccess$ = createEffect(() =>
    this.actions$.pipe(
      ofType(actions.deleteLocationSuccess),
      mergeMap(({order, isInventory}) => {
        alert('The Location has been marked for deletion!');
        if (order.locations.length === 1 || isInventory) {
          const worklist = localStorage.getItem(isInventory ? 'inventory-worklist' : 'worklist') ?? 'locations';
          this.router.navigate([isInventory ? `inventory/${worklist}` : `/${worklist}`]);
          return EMPTY; // No action is dispatched
        } else {
          this.router.navigate(['/order', order.id]);
          return of(actions.reloadOrder()); // Dispatch reloadOrder
        }
      })
    )
  );
}

