import { DeliveryType } from './delivery.enum';
import { Item } from './item';

export interface Cart {
  items: Item[];
  subTotal: number;
  delivery: DeliveryType.Standard;
  totalPrice: number;
}
