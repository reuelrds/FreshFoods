import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { User } from 'src/app/models/user';
import { AccountService } from 'src/app/services/account.service';

@Component({
  selector: 'freshfood-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss'],
})
export class AccountComponent implements OnInit {
  hide = true;
  disabled = true;

  user: User;

  accountForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.user = this.accountService.getUser();

    this.accountForm = new FormGroup({
      name: new FormControl(this.user.name),
      email: new FormControl(this.user.email),
      password: new FormControl(this.user.password),
      phone: new FormControl(this.user.phone),
      addressline1: new FormControl(this.user.addressLine1),
      addressline2: new FormControl(this.user.addressLine2),
      city: new FormControl(this.user.city),
      state: new FormControl(this.user.state),
      zipcode: new FormControl(this.user.zipcode),
    });

    this.toggleFormVisibility();
  }

  toggleFormVisibility() {
    const state = this.disabled ? 'disable' : 'enable';
    Object.keys(this.accountForm.controls).forEach((controlName) => {
      this.accountForm.controls[controlName][state]();
    });
  }

  editAccountDetails() {
    console.log('Edit');
    this.disabled = false;
    this.toggleFormVisibility();
  }

  submitAccountDetails() {
    this.disabled = true;
    console.log(this.accountForm, this.disabled);
    this.toggleFormVisibility();
  }
}
