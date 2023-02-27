import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { RaveOptions, RavePaymentData } from 'angular-rave';
import { Cart } from 'src/app/models/cart';
import { CartService } from 'src/app/services/cart.service';

import * as $ from 'jquery';

@Component({
  selector: 'freshfood-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.scss'],
})
export class DetailsComponent implements OnInit {
  @Input() cart: Cart;
  @Input() raveOptions: RaveOptions;

  @Output() onSuccessPayment = new EventEmitter<RavePaymentData | String>();

  constructor(private cartService: CartService) {}

  ngOnInit(): void {}

  onClick() {
    console.log(this.raveOptions);
  }

  paymentSuccessfull($event) {
    // console.log($event);
    // this.onSuccessPayment.emit($event);
    this.onSuccessPayment.emit('wefwe');
  }

  paymentClose() {
    // Remove the Rave Pay IFrame
    $(document).find('iframe')[0].remove();
  }
}