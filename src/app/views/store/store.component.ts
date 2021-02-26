import { Component, OnInit, ViewChild } from '@angular/core';
import { Item } from 'src/app/models/item';

import { ItemService } from 'src/app/services/item.service';

import * as _ from 'lodash';
import { CartService } from 'src/app/services/cart.service';
import { CartItem } from 'src/app/models/cart';

@Component({
  selector: 'freshfood-store',
  templateUrl: './store.component.html',
  styleUrls: ['./store.component.scss'],
})
export class StoreComponent implements OnInit {
  items: Item[];
  renderItems: Item[][];
  heroItem: Item;
  isLoading: boolean;

  constructor(
    private itemService: ItemService,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    this.isLoading = true;
    this.itemService.getItems().subscribe((items) => {
      this.items = items;
      this.heroItem = this.items[0];
      this.renderItems = _.chunk(this.items, 3);

      setTimeout(() => {
        this.isLoading = false;
      }, 1000);
    });
  }

  updateHeroItem(item: Item) {
    this.heroItem = item;
  }

  addItemToCart(item: Item) {
    const newCartItem: CartItem = {
      ...item,
      itemCount: 1,
    };
    this.cartService.addItem(newCartItem, 1);
  }
}
