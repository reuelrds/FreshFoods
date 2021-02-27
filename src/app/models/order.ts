export interface Order {
  id: string;
  orderDate: string;
  transactionId: string;
  subTotal: number;
  totalPrice: number;
  totalItemCount: number;
  delivery: Delivery;
  orderItems?: OrderItemsEntity[] | null;
}
export interface Delivery {
  deliveryDate: string;
  deliveryCost: number;
  deliveryAddress: DeliveryAddress;
}
export interface DeliveryAddress {
  addressLine1: string;
  addressLine2: string;
  city: string;
  state: string;
  zipcode: string;
}

export interface OrderItemsEntity {
  id: string;
  name: string;
  imageUrl: string;
  price: number;
  unit: string;
  itemCount: number;
  description: string;
  storage: string;
  origin: string;
  preparation: string;
}
