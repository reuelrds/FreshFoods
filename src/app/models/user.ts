export interface User {
  name: string;
  email: string;
  password: string;
  phone?: string;
  addressLine1?: string;
  addressLine2?: string;
  city?: string;
  state?: string;
  zipcode?: number;
}
