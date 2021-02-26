import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormGroup } from '@angular/forms';

import { Card } from 'card';

declare var Card;

@Component({
  selector: 'freshfood-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.scss'],
})
export class PaymentComponent implements OnInit {
  @Input() paymentForm: FormGroup;
  @ViewChild('form') form;
  // card: Card;

  constructor() {}

  ngOnInit(): void {}

  ngAfterViewInit() {
    new Card({
      form: this.form.nativeElement,
      container: '.card-wrapper',
      formSelectors: {
        nameInput: 'input[name="firstName"], input[name="lastName"]',
      },
    });
  }
}
