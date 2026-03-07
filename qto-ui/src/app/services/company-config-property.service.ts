import { Injectable } from "@angular/core";
import { AbstractModelService } from "./abstract-model.service";
import { EMPTY, Observable } from "rxjs";
import { catchError } from "rxjs/operators";
import { HttpErrorResponse } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CompanyConfigPropertyService extends AbstractModelService<any> {
  override path = '/companyConfigProperties';

  /**
   * Fetches a config value by key.
   * Returns EMPTY (no value, no error) for 404/401/403 responses so that
   * callers don't need individual error handlers when no tenant is associated
   * with the current user.  Server errors (5xx) are still propagated.
   */
  getValue(key: string): Observable<any> {
    let url = this.getUrl() + '/' + key;
    return this.http.get(url).pipe(
      catchError((err: HttpErrorResponse) => {
        if (err.status === 404 || err.status === 401 || err.status === 403) {
          return EMPTY;
        }
        throw err;
      })
    );
  }

  getModifiableProperties(id: number): Observable<any[]> {
    let url = this.getUrl() + '/modifiable/' + id;
    return this.http.get<any[]>(url);
  }

}

