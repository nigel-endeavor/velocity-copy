import { Injectable } from "@angular/core";
import { AbstractModelService } from "./abstract-model.service";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CompanyConfigPropertyService extends AbstractModelService<any> {
  override path = '/companyConfigProperties';

  getValue(key: string): Observable<any> {
    let url = this.getUrl() + '/' + key;
    return this.http.get(url);
  }

  getModifiableProperties(id: number): Observable<any[]> {
    let url = this.getUrl() + '/modifiable/' + id;
    return this.http.get<any[]>(url);
  }

}
