import { Injectable } from '@angular/core';
import { Cart, CartItem } from '../models/cart';
import { DeliveryType } from '../models/delivery.enum';
import { Item } from '../models/item';

import * as _ from 'lodash';

import { BehaviorSubject, Observable, of, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  private _cart: Cart = {
    items: [],
    subTotal: 0,
    delivery: 0,
    totalPrice: 0,
    itemCount: 0,
  };

  private cartTotalItems = new BehaviorSubject(0);
  cartItems = this.cartTotalItems.asObservable();

  private cart = new BehaviorSubject<Cart>(this._cart);

  constructor() {}

  getCart(): Observable<Cart> {
    return this.cart.asObservable();
  }

  addItem(newItem: CartItem, itemCount: number) {
    let items: CartItem[] = this._cart.items;

    const subTotal = this._cart.subTotal + itemCount * newItem.price;
    const deliveryPrice = DeliveryType.Standard;
    const totalPrice = subTotal + deliveryPrice;
    const cartItemCount = this._cart.itemCount + itemCount;

    let existingCartItemIdx = _.findIndex(items, { id: newItem.id });
    if (existingCartItemIdx === -1) {
      items.push(newItem);
    } else {
      items[existingCartItemIdx].itemCount += itemCount;
    }

    this._cart = {
      items: items,
      subTotal: subTotal,
      delivery: deliveryPrice,
      totalPrice: totalPrice,
      itemCount: cartItemCount,
    };

    this.cartTotalItems.next(this._cart.itemCount);
    this.cart.next(this._cart);
  }

  removeItem(itemId: string) {
    let items: CartItem[] = this._cart.items;
    // const removedItem = _.remove(items, { id: itemId });

    let removedItem: CartItem;
    let removedItemIndex;

    items.forEach((item, i) => {
      if (item.id === itemId) {
        removedItem = {
          ...item,
        };
        removedItemIndex = i;
      }
    });

    if (removedItem.itemCount === 1) {
      _.remove(items, { id: itemId });
    } else {
      items[removedItemIndex].itemCount -= 1;
    }

    const subTotal = this._cart.subTotal - removedItem.price;
    const deliveryPrice = DeliveryType.Standard;
    const totalPrice = subTotal + deliveryPrice;
    const cartItemCount = this._cart.itemCount - 1;

    this._cart = {
      items: items,
      subTotal: subTotal,
      delivery: deliveryPrice,
      totalPrice: totalPrice,
      itemCount: cartItemCount,
    };

    this.cartTotalItems.next(this._cart.itemCount);
    this.cart.next(this._cart);
  }
}
