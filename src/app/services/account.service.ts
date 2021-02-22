import { Injectable } from '@angular/core';
import { User } from '../models/user';

import userJson from '../data/user.json';

@Injectable({
  providedIn: 'root',
})
export class AccountService {
  user: User = userJson;

  constructor() {}

  getUser() {
    return this.user;
  }
}
