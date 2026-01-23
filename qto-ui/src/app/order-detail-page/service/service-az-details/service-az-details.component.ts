import { Component, OnInit } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { filter, map } from 'rxjs';
import { ServiceType } from 'src/app/models/constants/service-type';
import { Service } from 'src/app/models/service.model';
import { saveLocation, saveService } from '../../ngrx/order-details.actions';
import { getIsLoading, getSelectedLocation, getSelectedLocationOrService } from '../../ngrx/order-details.selectors';
import { OrderEditService } from '../../order-edit.service';
import { EthernetService } from 'src/app/models/ethernet-service.model';
import { Location } from 'src/app/models/location.model';
import { LocationService } from 'src/app/services/location.service';

@Component({
  selector: 'app-service-az-details',
  templateUrl: './service-az-details.component.html',
  styleUrls: ['./service-az-details.component.scss']
})
export class ServiceAzDetailsComponent implements OnInit {
  public isLoading$ = this.store.pipe(select(getIsLoading));
  public selectedLocationOrService$ = this.store.pipe(
    select(getSelectedLocationOrService),
    filter(item => !!item),
    map(item => JSON.parse(JSON.stringify(item)))
  );
  public selectedLocation$ = this.store.pipe(
    select(getSelectedLocation),
    filter(item => !!item),
    map(item => JSON.parse(JSON.stringify(item)))
  );

  companyId: number;

  readonly ServiceType = ServiceType;

  constructor(
    public oes: OrderEditService,
    private store: Store,
    private locationService: LocationService
  ) { }

  ngOnInit(): void {
    this.companyId = this.oes.order!.company.id;
  }

  saveService(event: {service: Service, location: Location}) {
    this.locationService.save(event.location).subscribe(res => {
      this.store.dispatch(saveService({ service: event.service }));
    });
  }

  getSelectedService(service: Service): any {
    switch (service.type) {
      case ServiceType.ETHERNET:
        return service as EthernetService;
      default:
        console.error('selectedService type is not supported. type: ' + service.type);
    }
  }
}
