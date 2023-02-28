import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, ReplaySubject } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Order } from '../models/order';
import { SnackbarService } from './snackbar.service';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  BACKEND_URL = environment.BACKEND_URL;

  _orders: Order[];
  $orders = new ReplaySubject<Order[]>();

  constructor(
    private httpClient: HttpClient,
    private snackBarService: SnackbarService
  ) {}

  placeOrder(order): Observable<{ message: String }> {
    console.log(order);

    return this.httpClient
      .post<{ message: String }>(`${this.BACKEND_URL}/api/order`, order)
      .pipe(
        tap((message) => {
          this.snackBarService.displaySnackBar(
            'Order Placedt Successfully',
            'Done'
          );
        })
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
          this.snackBarService.displaySnackBar(
            'Fetching Previous Orders',
            'Done'
          );
          return response.orders;
        })
      );
  }
}
