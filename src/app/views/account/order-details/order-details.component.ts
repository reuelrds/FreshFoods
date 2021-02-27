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
  order: Order;
  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    this.orderService.getOrders().subscribe((orders) => {
      this.orders = orders;
      this.order = orders[2];

      const items = this.order.orderItems;

      console.log(orders);
    });
  }
}
