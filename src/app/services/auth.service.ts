import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { User } from '../models/user';
import { ProfileService } from './profile.service';
import { ToastService } from './toast.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  BACKEND_URL = environment.BACKEND_URL;

  private _jwtToken: String;
  private _jwtExpiration;
  isLoggedIn: boolean = false;

  get jwtToken() {
    return this._jwtToken;
  }

  constructor(
    private httpClient: HttpClient,
    private router: Router,
    private profileService: ProfileService
  ) {}

  registerUser(user: { name: String; email: String; password: String }) {
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
          this._jwtToken = res.jwtToken;
          this._jwtExpiration = res.expiresin;

          this.isLoggedIn = true;
          this.profileService.setUser(res.user);

          setTimeout(() => {
            this.logout();
          }, this._jwtExpiration);

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

          this.isLoggedIn = true;
          this.profileService.setUser(res.user);

          setTimeout(() => {
            this.logout();
          }, this._jwtExpiration);

          this.router.navigate(['/store']);
        }
      });
  }

  logout() {
    this._jwtToken = null;
    this._jwtExpiration = null;

    this.isLoggedIn = false;
    this.router.navigate(['/login']);
  }
}
