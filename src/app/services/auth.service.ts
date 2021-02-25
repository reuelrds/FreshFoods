import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  BACKEND_URL = 'http://localhost:8080/FreshFoods';

  private _userId: String;
  private _jwtToken: String;
  private _jwtExpiration: Number;
  private _user: User;

  get user() {
    return this._user;
  }

  get jwtToken() {
    return this._jwtToken;
  }

  constructor(private httpClient: HttpClient, private router: Router) {}

  registerUser(user: User) {
    console.log(user);
    this.httpClient
      .post<{
        message: String;
        user: User;
        jwtToken: String;
        expiresin: Number;
      }>(`${this.BACKEND_URL}/auth/signup`, user)
      .subscribe((res) => {
        if (res.message === 'Signup Successfull') {
          this._user = res.user;
          this._jwtToken = res.jwtToken;
          this._jwtExpiration = res.expiresin;

          this.router.navigate(['/store']);
        }
      });
  }

  login(loginDetails: { email: String; password: String }) {
    this.httpClient
      .post<{
        message: String;
        user: User;
        jwtToken: String;
        expiresin: Number;
      }>(`${this.BACKEND_URL}/auth/login`, loginDetails)
      .subscribe((res) => {
        if (res.message === 'Login Successfull') {
          this._jwtToken = res.jwtToken;
          this._jwtExpiration = res.expiresin;
          this._user = res.user;

          this.router.navigate(['/store']);
        }
      });
  }
}
