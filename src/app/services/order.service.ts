import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, ReplaySubject } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Order } from '../models/order';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  BACKEND_URL = environment.BACKEND_URL;

  _orders: Order[];
  $orders = new ReplaySubject<Order[]>();

  constructor(private httpClient: HttpClient) {}

  placeOrder(order): Observable<{ message: String }> {
    console.log(order);

    return this.httpClient.post<{ message: String }>(
      `${this.BACKEND_URL}/api/order`,
      order
    );
  }

  getOrders() {
    return this.httpClient
      .get<{ message: String; orders: Order[] }>(
        `${this.BACKEND_URL}/api/order`
      )
      .pipe(
        map((response) => {
          console.log(response);
          this._orders = response.orders;
          this.$orders.next(this._orders);
          return response.orders;
        })
      );
  }
}
