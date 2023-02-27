import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  BACKEND_URL = environment.BACKEND_URL;

  constructor(private httpClient: HttpClient) {}

  placeOrder(order) {
    console.log(order);

    this.httpClient
      .post(`${this.BACKEND_URL}/api/placeOrder`, order)
      .subscribe((result) => console.log(result));
  }
}
