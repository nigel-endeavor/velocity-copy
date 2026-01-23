import { Injectable } from "@angular/core";
import { Dispute } from "../models/dispute.model";
import { AbstractModelService } from "./abstract-model.service";
import { DisputeSearchCriteria } from "../models/dispute-search-criteria.model";
import { Observable } from "rxjs";
import { PaginatedResult } from "../models/paginated-result.model";
import { DisputeMeta } from "../components/disputes/store/disputes.reducer";

@Injectable({
  providedIn: 'root'
})
export class DisputesService extends AbstractModelService<Dispute> {
  override path = '/disputes';

  override search(criteria: DisputeSearchCriteria) {
    if (!criteria.serviceId && !criteria.locationId) {
      return new Observable<PaginatedResult<Dispute>>();
    }
    return super.search(criteria);
  }

  findByServiceId(serviceId: number): Observable<Dispute[]> {
    if (!serviceId) {
      return new Observable<Dispute[]>();
    }
    const url = `${this.getUrl()}/service/${serviceId}`;
    return this.http.get<Dispute[]>(url);
  }

  getDisputeMeta(criteria: any): Observable<DisputeMeta> {
    let params = this.getParams(criteria);
    return this.http.get<DisputeMeta>(this.getUrl() + '/meta', { params: params });
  }

  submitMultiDispute(serviceIds: number[], dispute: Dispute): Observable<any> {
    const url = `${this.getUrl()}/multiDispute`;
    return this.http.post(url, { serviceIds, dispute });
  }
}