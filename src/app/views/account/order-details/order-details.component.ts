import { Component, OnInit } from '@angular/core';
import { Order } from 'src/app/models/order';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'freshfood-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.scss'],
})
export class OrderDetailsComponent implements OnInit {
  orders: Order[];
  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    this.orderService.getOrders().subscribe((orders) => {
      if (orders) {
        this.orders = orders;
        this.orders.sort((a, b) => {
          return <any>new Date(b.orderDate) - <any>new Date(a.orderDate);
        });
      }
      // console.log(orders);
    });
  }
}
