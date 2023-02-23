import { Injectable } from '@angular/core';
import { Item } from '../models/item';

import itemsJson from '../data/items.json';

@Injectable({
  providedIn: 'root',
})
export class ItemService {
  items: Item[] = itemsJson;

  constructor() {}

  getItems() {
    return this.items;
  }
}
