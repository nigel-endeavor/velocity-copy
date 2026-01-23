import { Inject, Injectable } from '@angular/core';
import { Notification } from '../models/notifications.model';
import { AbstractModelService } from './abstract-model.service';
import { HttpClient } from '@angular/common/http';
import { TOASTR_TOKEN, Toastr } from './toastr.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class NotificationService extends AbstractModelService<Notification> {
  override path = '/notifications';
  
  private ws: WebSocket;
  private token: string;
  notifications: Notification[];
  count: number;

  constructor(@Inject(TOASTR_TOKEN) private toastr: Toastr, http: HttpClient) {
    super(http);
  }

  connect(token: string): void {
    let url = environment.wsUrl + this.path;
    this.ws = new WebSocket(url);
    this.token = token;

    this.ws.onopen = () => {
      console.log('WebSocket connection established.');
      this.poll();
    };

    this.ws.onmessage = (event) => {
      if (event.data) {
        if (event.data == 'pong') {
          return;
        }
        let data = JSON.parse(event.data);
        //array of notifications was received, replace all notifications
        if (data instanceof Array) {
          this.notifications = JSON.parse(event.data);
        } 
        //single notification was received, add it to the list and create toast
        else {
          this.notifications.unshift(data);
          this.toastr.info(data.body + '<a hidden>notificationId='+ data.id +'</a>', data.header);
        }
        this.count = this.notifications.filter(n => !n.dismissed).length;
      }
    };

    this.ws.onerror = (error) => {
      console.error('NotificationWebsocket error:', error);
    };

    setInterval(() => {
      this.ws.send(JSON.stringify('ping'));
    }, 180000);
  }

  poll(showDismissed?: boolean): void {
    const message = {
      token: this.token,
      showDismissed: showDismissed
    }
    this.ws.send(JSON.stringify(message));
  }

  dismissNotification(notification: Notification): void {
    this.http.post<Notification>(this.getUrl() + '/' + notification.id + '/dismiss', null).subscribe(() => {});
  }

  dismissAllNotifications(): void {
    this.http.post<Notification>(this.getUrl() + '/dismissAll', null).subscribe(() => {});
  }
}
