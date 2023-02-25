import { Injectable } from '@angular/core';
import { User } from '../models/user';

import userJson from '../data/user.json';
import { ReplaySubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ProfileService {
  // user: User = userJson;
  BACKEND_URL = environment.BACKEND_URL;

  private _user: User;
  $user = new ReplaySubject<User>();

  constructor(private httpClient: HttpClient) {}

  setUser(user: User) {
    return this.$user.next(user);
  }

  updateUser(user: User) {
    this.httpClient
      .put<{
        message: String;
        user: User;
      }>(`${this.BACKEND_URL}/profile`, user)
      .subscribe((response) => {
        console.log(response);
        this.$user.next(response.user);
      });
  }
}
