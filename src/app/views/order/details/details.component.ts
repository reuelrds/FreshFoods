import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
} from '@angular/core';
import { RaveOptions, RavePaymentData } from 'angular-rave';
import { Cart } from 'src/app/models/cart';
import { CartService } from 'src/app/services/cart.service';

import * as $ from 'jquery';

@Component({
  selector: 'freshfood-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.scss'],
})
export class DetailsComponent implements OnInit, OnChanges {
  @Input() cart: Cart;
  @Input() raveOptions: RaveOptions;
  @Input() isLoading: boolean;

  @Output() onSuccessPayment = new EventEmitter<RavePaymentData>();
  @Output() onPayinit = new EventEmitter<void>();

  constructor(
    private cartService: CartService,
    private cd: ChangeDetectorRef
  ) {}

  event: RavePaymentData;

  ngOnInit(): void {}

  onClick() {
    // console.log(this.raveOptions);
  }

  ngOnChanges() {
    // console.log(this.isLoading);
    // console.log('success: !! ', this.isLoading);
  }

  onPaymentInit() {}

  paymentSuccessfull($event) {
    // console.log($event);
    // this.onSuccessPayment.emit($event);
    this.event = $event;
    this.isLoading = true;
    this.cd.detectChanges();
    setTimeout(() => {
      this.onSuccessPayment.emit(this.event);
    }, 5000);
  }

  paymentClose() {
    // Remove the Rave Pay IFrame
    $(document).find('iframe')[0].remove();
  }
}
