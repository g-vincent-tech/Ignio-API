import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IgniogatewaySharedModule } from 'app/shared/shared.module';
import { BillingInfoComponent } from './billing-info.component';
import { BillingInfoDetailComponent } from './billing-info-detail.component';
import { BillingInfoUpdateComponent } from './billing-info-update.component';
import { BillingInfoDeletePopupComponent, BillingInfoDeleteDialogComponent } from './billing-info-delete-dialog.component';
import { billingInfoRoute, billingInfoPopupRoute } from './billing-info.route';

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
  entryComponents: [BillingInfoDeleteDialogComponent]
})
export class IgniogatewayBillingInfoModule {}
