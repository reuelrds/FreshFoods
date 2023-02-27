import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ReplaySubject } from 'rxjs';
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

  placeOrder(order) {
    console.log(order);

    this.httpClient
      .post(`${this.BACKEND_URL}/api/order`, order)
      .subscribe((result) => console.log(result));
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
