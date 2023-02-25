import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  BACKEND_URL = 'http://localhost:8080/FreshFoods';

  userId: String;
  jwtToken: String;
  jwtExpiration: Number;
  user: User;

  constructor(private httpClient: HttpClient, private router: Router) {}

  registerUser(user: User) {
    console.log(user);
    this.httpClient
      .post<{
        message: String;
        userId: String;
        jwtToken: String;
        expiresin: Number;
      }>(`${this.BACKEND_URL}/auth/signup`, user)
      .subscribe((res) => {
        if (res.message === 'Signup Successfull') {
          this.userId = res.userId;
          this.jwtToken = res.jwtToken;
          this.jwtExpiration = res.expiresin;

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
        if (res.message === 'Signup Successfull') {
          this.jwtToken = res.jwtToken;
          this.jwtExpiration = res.expiresin;

          this.router.navigate(['/store']);
        }
      });
  }
}
