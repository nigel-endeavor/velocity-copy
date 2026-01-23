import { Injectable } from '@angular/core';
import { AbstractModelService } from './abstract-model.service';
import { Message, MessageThread } from '../models/message-thread.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MessageThreadService extends AbstractModelService<MessageThread> {
  override path = '/messageThreads';

  findByLocationId(locationId: number): Observable<MessageThread[]> {
    const url = this.getUrl() + '?locationId=' + locationId;
    return this.http.get<MessageThread[]>(url);
  }

  createMessage(messageThreadId: number, message: Message): Observable<MessageThread> {
    const url = this.getUrl() + '/' + messageThreadId;
    return this.http.put<MessageThread>(url, message);
  }

  setSubjects(messageThreadId: number, subjectIds: number[]): Observable<MessageThread> {
    const url = this.getUrl() + '/' + messageThreadId + '/subjects';
    return this.http.put<MessageThread>(url, subjectIds);
  }
}
