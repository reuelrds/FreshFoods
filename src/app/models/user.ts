export interface User {
  id: string;
  name: string;
  email: string;
  password: string;
  phone?: string;
  addressID: string;
  addressLine1?: string;
  addressLine2?: string;
  city?: string;
  state?: string;
  zipcode?: number;
}
