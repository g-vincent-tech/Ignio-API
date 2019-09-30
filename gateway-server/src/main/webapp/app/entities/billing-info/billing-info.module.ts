import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IgniogatewaySharedModule } from 'app/shared';
import {
  BillingInfoComponent,
  BillingInfoDetailComponent,
  BillingInfoUpdateComponent,
  BillingInfoDeletePopupComponent,
  BillingInfoDeleteDialogComponent,
  billingInfoRoute,
  billingInfoPopupRoute
} from './';

const ENTITY_STATES = [...billingInfoRoute, ...billingInfoPopupRoute];

@NgModule({
  imports: [IgniogatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BillingInfoComponent,
    BillingInfoDetailComponent,
    BillingInfoUpdateComponent,
    BillingInfoDeleteDialogComponent,
    BillingInfoDeletePopupComponent
  ],
  entryComponents: [BillingInfoComponent, BillingInfoUpdateComponent, BillingInfoDeleteDialogComponent, BillingInfoDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IgniogatewayBillingInfoModule {}
