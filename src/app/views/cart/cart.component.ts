import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Cart, CartItem } from 'src/app/models/cart';
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

  constructor(
    private cartService: CartService,
    private changeDetector: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.cartService.getCart().subscribe((cart) => {
      this.cart = cart;
      // this.changeDetector.detectChanges();
      this.renderItems = _.chunk(this.cart.items, 2);
      console.log(this.cart);
    });
  }

  incrementItem(item: CartItem) {
    this.cartService.addItem(item, 1);
  }

  decrementItem(item: CartItem) {
    this.cartService.removeItem(item.id);
  }
}
