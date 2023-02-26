import { Injectable } from '@angular/core';
import { Item } from '../models/item';

import itemsJson from '../data/items.json';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

import { map } from 'rxjs/operators';
import { Observable, ReplaySubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ItemService {
  BACKEND_URL = environment.BACKEND_URL;
  private _items: Item[];

  $item = new ReplaySubject<Item[]>();

  constructor(private httpClient: HttpClient) {}

  getItems(): Observable<Item[]> {
    return this.httpClient
      .get<{ items: Item[]; message: String }>(`${this.BACKEND_URL}/store`)
      .pipe(
        map((result) => {
          if (result.message == 'Items Retrieved Successfully') {
            this._items = result.items;
            this.$item.next(this._items);
            return result.items;
          }
        })
      );
  }
}
