import { Observable } from "rxjs";
import { HttpClient, HttpResponse } from "@angular/common/http";
import { environment } from "../../environments/environment";
import { Injectable } from "@angular/core";


@Injectable({
  providedIn: 'root'
})
export class ReportService {
    path = '/reports/';

    constructor(protected http: HttpClient) { }

  downloadReport(type: string): Observable<HttpResponse<Blob>> {
    const url = environment.appUrl + this.path + type;
    return this.http.get(url, { responseType: 'blob', observe: 'response' });
  }
}
