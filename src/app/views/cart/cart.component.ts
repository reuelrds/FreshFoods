import { Component, OnInit } from '@angular/core';
import { Cart } from 'src/app/models/cart';
import { Item } from 'src/app/models/item';
import { CartService } from 'src/app/services/cart.service';

import * as _ from 'lodash';

@Component({
  selector: 'freshfood-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss'],
})
export class CartComponent implements OnInit {
  cart: Cart;

  renderItems: Item[][];

  cart1: Item;

  constructor(private cartService: CartService) {}

  ngOnInit(): void {
    this.cart = this.cartService.cart;

    this.renderItems = _.chunk(this.cart.items, 2);
    console.log(this.cart);
  }
}
