import { Injectable } from '@angular/core';
import { Cart, CartItem } from '../models/cart';
import { DeliveryType } from '../models/delivery.enum';
import { Item } from '../models/item';

import * as _ from 'lodash';

import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { SnackbarService } from './snackbar.service';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  private _cart: Cart = {
    items: [],
    subTotal: 0,
    delivery: DeliveryType.Standard,
    totalPrice: 0,
    itemCount: 0,
  };

  private cartTotalItems = new BehaviorSubject(0);
  cartItems = this.cartTotalItems.asObservable();

  private cart = new BehaviorSubject<Cart>(this._cart);

  constructor(private snackBarService: SnackbarService) {}

  getCart(): Observable<Cart> {
    return this.cart.asObservable();
  }

  setCart(cart: Cart) {
    this.cartTotalItems.next(cart.itemCount);
    this.cart.next(cart);
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
    this.snackBarService.displaySnackBar(
      `${newItem.name} added to Cart`,
      'Done'
    );
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
    this.snackBarService.displaySnackBar(
      `${removedItem.name} removed from Cart`,
      'Done'
    );
  }

  clearCart() {
    this._cart = {
      items: [],
      subTotal: 0,
      delivery: DeliveryType.Standard,
      totalPrice: 0,
      itemCount: 0,
    };
    this.cart.next(this._cart);
    this.cartTotalItems.next(this._cart.itemCount);
  }
}
