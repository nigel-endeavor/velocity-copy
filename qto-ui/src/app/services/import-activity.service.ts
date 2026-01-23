import { Injectable } from "@angular/core";
import { ImportActivity } from "../models/import-activity.model";
import { AbstractModelService } from "./abstract-model.service";
import { environment } from '../../environments/environment';
import { Observable, Subject } from "rxjs";
import { HttpResponse } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ImportActivityService extends AbstractModelService<ImportActivity> {
  override path = '/importActivities';

  public refreshSubject: Subject<void> = new Subject<void>();
  private ws: WebSocket;

  uploadFile(type: string, file: File): Observable<ImportActivity> {
    const url = environment.appUrl + '/import/' + type;
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<ImportActivity>(url, formData);
  }

  downloadTemplate(type: string): Observable<HttpResponse<Blob>> {
    const url = environment.appUrl + '/import/template/' + type;
    return this.http.get(url, { responseType: 'blob', observe: 'response' });
  }

  connect(): void {
    let url = environment.wsUrl + this.path;
    this.ws = new WebSocket(url);

    this.ws.onmessage = (event) => {
      if (event.data) {
        if (event.data == 'pong') {
          return;
        }
        if (event.data == 'refresh') {
          console.log('refresh received')
          this.refreshSubject.next();
        }
      }
    }

    setInterval(() => {
      this.ws.send(JSON.stringify('ping'));
    }, 180000);
  }

  disconnect(): void {
    this.ws.close();
  }
}