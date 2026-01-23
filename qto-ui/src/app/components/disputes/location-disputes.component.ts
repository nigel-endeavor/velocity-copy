import { Component } from "@angular/core";
import { DisputesComponent } from "./disputes.component";
import { Service } from 'src/app/models/service.model';
import { Location } from 'src/app/models/location.model';
import * as actions from './store/disputes.actions';
import { editEnabled} from 'src/app/order-detail-page/ngrx/order-details.selectors';
import { loadUserStatuses } from "src/app/order-detail-page/ngrx/order-details.actions";
import { Store, select } from '@ngrx/store';
import { getMetaData } from "./store/disputes.selectors";

@Component({
  selector: 'app-location-disputes',
  templateUrl: './disputes.component.html',
  styleUrls: ['./disputes.component.scss', '../../order-detail-page/form-styles.scss']
})
export class LocationDisputesComponents extends DisputesComponent {
  public override editEnabled$ = this.store.pipe(select(editEnabled));
  override editDisabled = true;

  locationId: number;

  override ngOnInit(): void {
    this.store.dispatch(loadUserStatuses());
    this.selectedLocationOrService$.subscribe((location: Service | Location | null) => {
      if (location instanceof Location) {
        this.locationId = location.id;
        this.companyId = this.oes.order!.company.id;
      }
      this.store.dispatch(actions.updateFilters({ key: 'locationId', value: this.locationId }));
    });
    this.setInputDisabled();
    this.store.dispatch(actions.loadMetaData());
    this.metaData$ = this.store.pipe(select(getMetaData));
  }
}