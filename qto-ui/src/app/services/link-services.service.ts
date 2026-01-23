import { Injectable } from '@angular/core';
import { AbstractModelService } from "./abstract-model.service";
import { ServiceView } from "../models/service-view.model";
import { Observable } from "rxjs";
import { environment } from "../../environments/environment";
import { HttpParams } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class LinkServicesService extends AbstractModelService<ServiceView> {

  override path = '/serviceViews';


  getLinkServices(criteria: any): Observable<any> {
    if (criteria.linkBundleFrom) {
      let params = this.getParams(criteria);
      if (criteria.linkBundleFrom === 'inventory') {
        this.path = '/serviceInventoryViews';
      }
      switch (criteria.linkBundleType) {
        case 'Inventory':
          return this.http.get<any>(environment.appUrl + '/serviceInventoryViews/inventory', {params: params});
          break;
        case 'Link':
          return this.http.get<any>(this.getUrl() + '/link', {params: params});
          break;
        case 'Bundle':
          return this.http.get<any>(this.getUrl() + '/bundle', {params: params});
          break;
        default:
          console.log('Invalid Link/Bundle/Inventory type.');
      }
      return this.http.get<any>(this.getUrl() + '/inventory', {params: params});
    } else {
      return new Observable<any>();
    }
  }

  saveLink(incomingServiceId: number, selectedItems: any[], linkType: string): Observable<any> {
        return this.http.put<any>(this.getUrl() + '/link?incomingServiceId=' + incomingServiceId
          + '&selectedItems=' + selectedItems + '&linkType=' + linkType, null);
  }

  saveLinkInventory(incomingServiceId: number, selectedItem: any, linkType: string): Observable<any> {
        return this.http.put<any>(environment.appUrl + '/serviceInventoryViews/inventory/link?incomingServiceId=' + incomingServiceId
          + '&selectedItems=' + selectedItem + '&linkType=' + linkType, null);
  }

}

