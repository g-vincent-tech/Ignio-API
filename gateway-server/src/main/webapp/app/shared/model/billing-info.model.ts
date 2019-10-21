export interface IBillingInfo {
  id?: string;
  ignios?: string;
  creditCardNumber?: number;
  creditCardType?: string;
  cvv2?: string;
  expiresOn?: string;
  billingAddress?: string;
  city?: string;
  country?: string;
  postalCode?: number;
}

export class BillingInfo implements IBillingInfo {
  constructor(
    public id?: string,
    public ignios?: string,
    public creditCardNumber?: number,
    public creditCardType?: string,
    public cvv2?: string,
    public expiresOn?: string,
    public billingAddress?: string,
    public city?: string,
    public country?: string,
    public postalCode?: number
  ) {}
}
