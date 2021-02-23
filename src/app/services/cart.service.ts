import { Injectable } from '@angular/core';
import { Cart } from '../models/cart';
import { DeliveryType } from '../models/delivery.enum';
import { Item } from '../models/item';

import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  cart: Cart;

  private cartTotalItems = new BehaviorSubject(0);
  cartItems = this.cartTotalItems.asObservable();

  constructor() {}

  addItem(newItem: Item) {
    let subTotal;
    let items: Item[];

    if (!this.cart) {
      items = [newItem];
      subTotal = 0 + newItem.price;
    } else {
      items = this.cart.items;
      items.push(newItem);
      subTotal = this.cart.subTotal + newItem.price;
    }
    const deliveryPrice = DeliveryType.Standard;
    const totalPrice = subTotal + DeliveryType.Standard;

    this.cart = {
      items: items,
      subTotal: subTotal,
      delivery: deliveryPrice,
      totalPrice: totalPrice,
    };

    this.cartTotalItems.next(this.cart.items.length);

    console.log(this.cart);
  }
}
