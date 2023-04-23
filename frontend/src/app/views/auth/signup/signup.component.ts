import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'freshfood-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss'],
})
export class SignupComponent implements OnInit {
  hide = true;

  signUpForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.signUpForm = this.formBuilder.group({
      name: '',
      email: '',
      password: '',
    });
  }

  onSubmit() {
    // console.log(this.signUpForm.value);
    this.authService.registerUser(this.signUpForm.value);
  }
}
