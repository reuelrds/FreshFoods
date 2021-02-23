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
  private _cart: Cart;

  private cartTotalItems = new BehaviorSubject(0);
  cartItems = this.cartTotalItems.asObservable();

  constructor() {
    this._cart = {
      items: [],
      subTotal: 0,
      delivery: 0,
      totalPrice: 0,
      itemCount: 0,
    };
  }

  get cart() {
    return this._cart;
  }

  addItem(newItem: Item) {
    let subTotal;
    let items: Item[];
    let cartItemCount = 0;

    if (!this._cart) {
      newItem.itemCount = 1;
      items = [newItem];
      cartItemCount = 1;
      subTotal = 0 + newItem.price;
    } else {
      items = this._cart.items;

      if (_.includes(items, newItem)) {
        const itemIdx = _.findIndex(items, newItem);
        console.log(itemIdx);
        items[itemIdx].itemCount += 1;
      } else {
        newItem.itemCount = 1;
        console.log(newItem);
        items.push(newItem);
      }
      cartItemCount = this._cart.itemCount + 1;
      subTotal = this._cart.subTotal + newItem.price;
    }
    const deliveryPrice = DeliveryType.Standard;
    const totalPrice = subTotal + DeliveryType.Standard;

    this._cart = {
      items: items,
      subTotal: subTotal,
      delivery: deliveryPrice,
      totalPrice: totalPrice,
      itemCount: cartItemCount,
    };

    this.cartTotalItems.next(this._cart.itemCount);

    console.log(this._cart);
  }
}
