import { Component, OnInit, ViewChild } from '@angular/core';
import { Item } from 'src/app/models/item';

import { ItemService } from 'src/app/services/item.service';

import * as _ from 'lodash';

@Component({
  selector: 'freshfood-store',
  templateUrl: './store.component.html',
  styleUrls: ['./store.component.scss'],
})
export class StoreComponent implements OnInit {
  items: Item[];
  renderItems: Item[][];
  heroItem: Item;

  constructor(private itemService: ItemService) {}

  ngOnInit(): void {
    this.items = this.itemService.getItems();
    this.heroItem = this.items[0];
    this.renderItems = _.chunk(this.items, 3);
  }

  updateHeroItem(item: Item) {
    this.heroItem = item;
  }
}
