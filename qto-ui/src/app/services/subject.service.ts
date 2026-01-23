import { Injectable } from '@angular/core';
import { AbstractModelService } from './abstract-model.service';
import { SubjectInterface } from "../models/subject.model";
import { Observable } from "rxjs";
import {CommonDropdownSearchCriteria} from "../interfaces/commonSearch.interface";

@Injectable({
  providedIn: 'root'
})
export class SubjectService extends AbstractModelService<SubjectInterface> {
  override path = '/subjects';

  getSubjects(orderId?: number | null, filters?: CommonDropdownSearchCriteria | null, isInventoryWrite? : boolean): Observable<SubjectInterface[]> {
    let url = this.getUrl();
    if (orderId) {
      url += '?orderId=' + orderId;
    }

    if (isInventoryWrite) {
      url += ((url.includes('?')) ? '&' : '?') + 'isInventoryWrite=true';
    }

    if (filters) {
      const searchParams = Object.keys(filters);
      const stringifiedParams: string[] = [];
      searchParams.forEach((key: string, index) => {
        // @ts-ignore
        if (filters[key]) {
          // @ts-ignore
          stringifiedParams.push(`${key}=${filters[key]}`);
        }
      });
      url+= ((url.includes('?')) ? '&' : '?') + stringifiedParams.join('&');
    }

    return this.http.get<SubjectInterface[]>(url);
  }

  getUserSubject(): Observable<SubjectInterface> {
    let url = this.getUrl() + '/me';
    return this.http.get<SubjectInterface>(url);
  }

  getUserTenantAccess(): Observable<boolean> {
    let url = this.getUrl() + '/me/tenantAccess';
    return this.http.get<boolean>(url);
  }

  updateSelectedTenant(tenantName: string): Observable<any> {
    let url = this.getUrl() + '/setTenant/' + tenantName;
    return this.http.post(url, null);
  }

  getTenantNames(): Observable<string[]> {
    let url = this.getUrl() + '/tenants';
    return this.http.get<string[]>(url);
  }
}
