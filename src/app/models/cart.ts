import { DeliveryType } from './delivery.enum';
import { Item } from './item';

export interface CartItem extends Item {
  itemCount: number;
}

export interface Cart {
  items: CartItem[];
  subTotal: number;
  delivery: DeliveryType;
  totalPrice: number;
  itemCount: number;
}
