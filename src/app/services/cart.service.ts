import { Injectable } from '@angular/core';
import { Cart } from '../models/cart';
import { DeliveryType } from '../models/delivery.enum';
import { Item } from '../models/item';

import * as _ from 'lodash';

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
    let cartItemCount = 0;

    if (!this.cart) {
      newItem.itemCount = 1;
      items = [newItem];
      cartItemCount = 1;
      subTotal = 0 + newItem.price;
    } else {
      items = this.cart.items;

      if (_.includes(items, newItem)) {
        const itemIdx = _.findIndex(items, newItem);
        console.log(itemIdx);
        items[itemIdx].itemCount += 1;
      } else {
        newItem.itemCount = 1;
        console.log(newItem);
        items.push(newItem);
      }
      cartItemCount = this.cart.itemCount + 1;
      subTotal = this.cart.subTotal + newItem.price;
    }
    const deliveryPrice = DeliveryType.Standard;
    const totalPrice = subTotal + DeliveryType.Standard;

    this.cart = {
      items: items,
      subTotal: subTotal,
      delivery: deliveryPrice,
      totalPrice: totalPrice,
      itemCount: cartItemCount,
    };

    this.cartTotalItems.next(this.cart.itemCount);

    console.log(this.cart);
  }
}
