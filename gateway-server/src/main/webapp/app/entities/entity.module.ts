import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'billing-info',
        loadChildren: () => import('./billing-info/billing-info.module').then(m => m.IgniogatewayBillingInfoModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class IgniogatewayEntityModule {}
