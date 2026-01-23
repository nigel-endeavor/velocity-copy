import { Component } from '@angular/core';
import { Store, select } from '@ngrx/store';
import { plainToClass } from 'class-transformer';
import { filter, map } from 'rxjs';
import { getSelectedLocationOrService } from '../../ngrx/order-details.selectors';
import { Location } from 'src/app/models/location.model';
import { Router, ActivatedRoute } from '@angular/router';
import { CompanyConfigPropertyService } from 'src/app/services/company-config-property.service';

@Component({
  selector: 'app-location-brokerage',
  templateUrl: './location-brokerage.component.html',
  styleUrls: ['../../form-styles.scss']
})
export class LocationBrokerageComponent {
  public selectedLocationOrService$ = this.store.pipe(
    select(getSelectedLocationOrService),
    filter(item => !!item),
    map(item => plainToClass(Location, JSON.parse(JSON.stringify(item))))
  );

  constructor(
    public store: Store,
    private companyConfigService: CompanyConfigPropertyService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    // If the company config is set to hide brokerage fields, navigate back to the order details page
    this.companyConfigService.getValue('SHOW_BROKERAGE_FIELDS').subscribe((res) => {
      if (!res || !res.value || res.value == 'false') {
        this.router.navigate(['../'], { relativeTo: this.route });
      }
    });
  }
}

