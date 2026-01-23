import { EventEmitter, Injectable } from '@angular/core';
import { AbstractModelService } from './abstract-model.service';
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DisconnectMultiEditService extends AbstractModelService<any> {
  override path = '/disconnectViews/multiEdit';

  public onMultiEdit = new EventEmitter<any>();

  sendMultiEdit(payload: any): Observable<any> {
    let url = this.getUrl();
    return this.http.post<any>(url, payload);
  }

}
