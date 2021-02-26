import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatStepper } from '@angular/material/stepper';
import { Cart } from 'src/app/models/cart';

@Component({
  selector: 'freshfood-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss'],
})
export class OrderComponent implements OnInit {
  cart: Cart = {
    subTotal: 15.99,
    totalPrice: 20.99,
    itemCount: 13,
    delivery: 1.99,
    items: [
      {
        id: '1',
        name: 'Apple',
        imageUrl: '/assets/apple.svg',
        price: 0.5,
        unit: 'count',
        itemCount: 2,
      },
      {
        id: '2',
        name: 'Banana',
        imageUrl: '/assets/banana.svg',
        price: 0.25,
        unit: 'count',
        itemCount: 5,
      },
      {
        id: '3',
        name: 'Strawberry',
        imageUrl: '/assets/strawberry.svg',
        price: 0.56,
        unit: 'count',
        itemCount: 6,
      },
    ],
  };

  phone = 1234567890;

  addressForm: FormGroup;
  optionsForm: FormGroup;
  paymentForm: FormGroup;
  orderForm: FormGroup;

  @ViewChild('stepper') stepper: MatStepper;

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.addressForm = this.formBuilder.group({
      phone: '',
      addressLine1: '',
      addressLine2: '',
      city: '',
      state: '',
      zipcode: '',
    });
    this.optionsForm = this.formBuilder.group({
      deliveryType: '',
      deliveryDate: '',
    });
  }

  ngAfterViewInit() {
    // this.stepper.selectedIndex = 1;
    console.log(this.stepper.selectedIndex);
  }
}
