import { Injectable } from '@angular/core';
import { Order } from '../models/order.model';
import { AbstractModelService } from './abstract-model.service';
import { OrderCreateDto, OrderCreateDtoWrapper } from '../models/order-create-dto.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService extends AbstractModelService<Order> {
  override path = '/orders';

  create(dtoList: OrderCreateDto[]): Observable<OrderCreateDtoWrapper> {
    const wrapper = { dtoList: dtoList };
    return this.http.post<OrderCreateDtoWrapper>(this.getUrl(), wrapper);
  }
}
