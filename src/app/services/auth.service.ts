import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  BACKEND_URL = 'http://localhost:8080/FreshFoods';

  userId = null;
  jwtToken = null;
  jwtExpiration = null;

  constructor(private httpClient: HttpClient, private router: Router) {}

  registerUser(user: User) {
    console.log(user);
    this.httpClient
      .post<{
        message: string;
        userId: string;
        jwtToken: string;
        expiresin: number;
      }>(`${this.BACKEND_URL}/SignUpServlet`, user)
      .subscribe((res) => {
        if (res.message === 'Signup Successfull') {
          this.userId = res.userId;
          this.jwtToken = res.jwtToken;
          this.jwtExpiration = res.expiresin;

          this.router.navigate(['/store']);
        }
      });
  }
}
