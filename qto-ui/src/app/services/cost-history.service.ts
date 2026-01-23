import { Injectable } from "@angular/core";
import { CostHistory, CostHistoryMeta } from "../models/cost-history.model";
import { AbstractModelService } from "./abstract-model.service";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CostHistoryService extends AbstractModelService<CostHistory> {
  override path = '/costHistory';

  getCostHistoryMeta(criteria: any): Observable<CostHistoryMeta> {
    let params = this.getParams(criteria);
    return this.http.get<CostHistoryMeta>(this.getUrl() + '/meta', { params: params });
  }
}