import { Component, OnInit, ViewChild } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { MatStepper } from '@angular/material/stepper';
import { Cart } from 'src/app/models/cart';

import { RaveOptions, RavePaymentData } from 'angular-rave';
import { CartService } from 'src/app/services/cart.service';
import { OrderService } from 'src/app/services/order.service';

import * as shortid from 'shortid';
import { ProfileService } from 'src/app/services/profile.service';
import { User } from 'src/app/models/user';
import { Router } from '@angular/router';

@Component({
  selector: 'freshfood-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss'],
})
export class OrderComponent implements OnInit {
  // cart: Cart; = {
  //   subTotal: 15.99,
  //   totalPrice: 20.99,
  //   itemCount: 13,
  //   delivery: 1.99,
  //   items: [
  //     {
  //       id: 'item1',
  //       name: 'Apple',
  //       imageUrl: '/assets/apple.svg',
  //       price: 0.5,
  //       unit: 'count',
  //       itemCount: 2,
  //     },
  //     {
  //       id: 'item2',
  //       name: 'Banana',
  //       imageUrl: '/assets/banana.svg',
  //       price: 0.25,
  //       unit: 'count',
  //       itemCount: 5,
  //     },
  //     {
  //       id: 'item3',
  //       name: 'Strawberry',
  //       imageUrl: '/assets/strawberry.svg',
  //       price: 0.56,
  //       unit: 'count',
  //       itemCount: 6,
  //     },
  //   ],
  // };

  // phone = 1234567890;

  cart: Cart;
  user: User;

  isLoading: boolean = false;
  isOrderSuccessfull = false;
  orderId = '';

  addressForm: FormGroup;
  optionsForm: FormGroup;
  paymentForm: FormGroup;
  orderForm: FormGroup;

  raveOptions: RaveOptions;

  @ViewChild('stepper') stepper: MatStepper;

  constructor(
    private formBuilder: FormBuilder,
    private cartService: CartService,
    private orderService: OrderService,
    private profileService: ProfileService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cartService.getCart().subscribe((cart) => {
      this.cart = cart;
    });

    this.profileService.$user.subscribe((user) => {
      this.user = user;
    });

    this.addressForm = this.formBuilder.group({
      phone: this.user.phone,
      addressLine1: this.user.addressLine1,
      addressLine2: this.user.addressLine2,
      city: this.user.city,
      state: this.user.state,
      zipcode: this.user.zipcode,
    });
    this.optionsForm = this.formBuilder.group({
      deliveryType: '',
      deliveryDate: '',
    });

    this.paymentForm = this.formBuilder.group({
      transactionId: '',
    });

    this.orderForm = this.formBuilder.group({
      address: this.addressForm,
      deliveryOptions: this.optionsForm,
      payment: this.paymentForm,
    });
  }

  onAddressSubmit() {}

  onOptionsSubmit() {
    this.cart.delivery = this.optionsForm.value['deliveryType'];
    this.cart.totalPrice = this.cart.subTotal + this.cart.delivery;
    this.raveOptions = {
      customer: {
        name: this.user.name,
        email: this.user.email,
        phonenumber: this.user.phone,
      },
      amount: this.cart.totalPrice,
      tx_ref: `${shortid.generate()}`,
      customizations: {
        title: 'Fresh Foods',
        description: 'Lorem Ipsum',
        logo: 'http://localhost:4200/assets/logo-small.svg',
      },
    };
  }

  onPaymentSuccess($event: RavePaymentData | String) {
    this.isLoading = true;
    this.orderId = shortid.generate();

    setTimeout(() => {
      this.paymentForm.patchValue({
        transactionId: $event,
      });

      const orderDate = this.optionsForm.value.deliveryDate;
      console.log(orderDate);
      this.optionsForm.patchValue({
        deliveryDate: new Date(orderDate).toISOString(),
      });

      let orderDetails = this.orderForm.value;
      orderDetails = {
        id: this.orderId,
        orderDate: new Date().toISOString(),
        cart: {
          ...this.cart,
        },
        ...orderDetails,
      };

      // console.log(this.orderForm.value);
      this.orderService.placeOrder(orderDetails).subscribe((message) => {
        this.isLoading = false;
        this.cartService.clearCart();

        this.isOrderSuccessfull = true;

        this.router.navigate(['/store']);
      });
    }, 2000);
  }
}
