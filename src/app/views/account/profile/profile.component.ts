import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { User } from 'src/app/models/user';
import { ProfileService } from 'src/app/services/profile.service';
import { AuthService } from 'src/app/services/auth.service';

import * as _ from 'lodash';

@Component({
  selector: 'freshfood-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
  hide = true;
  disabled = true;

  user: User;

  profileForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private profileService: ProfileService
  ) {}

  ngOnInit(): void {
    this.profileService.$user.subscribe((user) => {
      this.user = user;

      this.profileForm = this.formBuilder.group({
        name: user.name,
        email: user.email,
        phone: user.phone,
        addressLine1: user.addressLine1,
        addressLine2: user.addressLine2,
        city: user.city,
        state: user.state,
        zipcode: user.zipcode,
      });
      this.toggleFormVisibility();
    });
  }

  toggleFormVisibility() {
    const state = this.disabled ? 'disable' : 'enable';
    Object.keys(this.profileForm.controls).forEach((controlName) => {
      this.profileForm.controls[controlName][state]();
    });
  }

  editAccountDetails() {
    // console.log('Edit');
    this.disabled = false;
    this.toggleFormVisibility();
  }

  submitAccountDetails() {
    this.disabled = true;
    // console.log(this.profileForm, this.disabled);
    this.toggleFormVisibility();

    const newUser = {
      id: this.user.id,
      addressId: this.user.addressID ? this.user.addressID : '',
      ...this.profileForm.value,
    };

    // console.log(newUser);

    const user = _.mapValues(newUser, (value) => {
      if (value == null) {
        return '';
      } else {
        return value;
      }
    }) as User;

    this.profileService.updateUser(user);
  }
}
