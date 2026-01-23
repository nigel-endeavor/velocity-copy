import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { AbstractBaseModel } from '../models/abstract-base-model';
import { BaseSearchCriteria } from '../models/base-search-criteria.model';
import { PaginatedResult } from '../models/paginated-result.model';
import { getUserTimeZoneAbbreviation } from '../utilities';
import { DashboardSearchCriteria } from '../features/dashboards/models/dashboard-search-criteria.model';

@Injectable({
  providedIn: 'root'
})
export class AbstractModelService<T extends AbstractBaseModel> {

  //Resource path, subclasses should implement
  path: string;

  constructor(protected http: HttpClient) { }

  search(criteria: BaseSearchCriteria) {
    return this.http.get<PaginatedResult<T>>(this.getUrl(), { params: this.getParams(criteria) });
  }

  retrieve(id: number) {
    return this.http.get<T>(this.getUrl() + '/' + id);
  }

  save(entity: T) {
    if (entity.id) {
      const url = this.getUrl() + '/' + entity.id;
      return this.http.put<T>(url, entity);
    } else {
      return this.http.post<T>(this.getUrl(), entity);
    }
  }

  pullFromCrm(entity: T) {
    const url = this.getUrl() + '/pullFromCrm';
    return this.http.put<T>(url, entity);
  }

  cloneAndCancel(entity: T) {
    const url = this.getUrl() + '/cloneAndCancel';
    return this.http.post<T>(url, entity);
  }

  delete(entity: T) {
    const url = this.getUrl() + '/' + entity.id;
    return this.http.delete<T>(url);
  }

  export(criteria: BaseSearchCriteria) {
    const headers = new HttpHeaders({'accept': 'application/vnd.ms-excel'});
    return this.http.get(this.getUrl(), { params: this.getParams(criteria), headers: headers, responseType: 'blob' });
  }

  getUrl(): string {
    return environment.appUrl + this.path;
  }

  // Create query params based on given search criteria
  getParams(criteria: BaseSearchCriteria | DashboardSearchCriteria): HttpParams {
    let params = new HttpParams();
    for (const key in criteria) {
      let val: any = criteria[key as keyof (BaseSearchCriteria | DashboardSearchCriteria)];
      if (val && (val.filter?.propertyName || val.condition)) {
        params = params.append(key, JSON.stringify(val));
      } else if (val?.isEmpty) {
        params = params.append(key + '-comparison', 'ISEMPTY');
      } else if (val?.dateRange) {
        if (this.getDateParam(val.dateRange.startDate) === this.getDateParam(val.dateRange.endDate)) {
          const abbr = getUserTimeZoneAbbreviation();
          params = params.append(key, this.getDateParam(val.dateRange.startDate) + ' 00:00:00 ' + abbr);
          params = params.append(key, this.getDateParam(val.dateRange.startDate) + ' 23:59:59 ' + abbr);
          params = params.append(key + '-comparison', 'AFTER');
          params = params.append(key + '-comparison', 'BEFORE');

        } else {
          params = params.append(key, this.getDateParam(val.dateRange.startDate));
          params = params.append(key, this.getDateParam(val.dateRange.endDate));
          params = params.append(key + '-comparison', 'AFTER');
          params = params.append(key + '-comparison', 'BEFORE');
        }
      } else if (val?.comparison && val?.date) {
        params = params.append(key, this.getDateParam(val.date));
        params = params.append(key + '-comparison', val.comparison);
      } else if ((typeof val == 'string' && val.includes(',')) || val === 'Empty') {
        val = val.replace('Empty', '<NULL>');
        //todo: this is kludgey, the app-dropdown component (or a new component) should handle arrays of strings
        val = val.split(', ');
        for (const item of val) {
          if (item === '<NULL>') {
            params = params.append(key, 'ISEMPTY');
          } else {
            params = params.append(key, item);
          }
        }
      } else if ((typeof val == "boolean" || val) && typeof val != "object") {
        if (val === '<NULLNUMBER>') {
          params = params.append(key, '0');
          params = params.append(key + '-comparison', 'ISEMPTY');
        } else {
          params = params.append(key, val);
        }
      } else if (Array.isArray(val) && val.length) {
        for (const item of val) {
          if (item === '<NULL>' || item === 'Empty') {
            params = params.append(key, 'ISEMPTY');
          } else {
            params = params.append(key, item);
          }
        }
      }
    }
    return params;
  }

  getDateParam(dateString: string): string {
    let d = new Date(dateString);
    const offset = d.getTimezoneOffset() * 60 * 1000;
    d.setTime(d.getTime() + offset);
    return d.toLocaleDateString('en-us', { month: 'short', day: '2-digit', 'year': 'numeric',  }).replace(',', '');
  }
}
