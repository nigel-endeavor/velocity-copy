import { Injectable } from "@angular/core";
import { CompanyView } from "../models/company-view.model";
import { AbstractModelService } from "./abstract-model.service";
import { Observable } from "rxjs";
import { BaseSearchCriteria } from "../models/base-search-criteria.model";
import { PaginatedResult } from "../models/paginated-result.model";
import { CustomersMeta } from "../features/master-customers-worklist/ngrx/master-customers-worklist.reducer";


@Injectable({
  providedIn: 'root'
})
export class CompanyViewService extends AbstractModelService<CompanyView> {
  override path = '/companyViews';
  
  override search(criteria: BaseSearchCriteria) {
    return this.http.get<PaginatedResult<CompanyView>>(this.getUrl(), { params: this.getParams(criteria) });
  }

  getCustomerWorklistMeta(criteria: any): Observable<CustomersMeta> {
    let params = this.getParams(criteria);
    return this.http.get<CustomersMeta>(this.getUrl() + '/meta', { params: params });
  }
}