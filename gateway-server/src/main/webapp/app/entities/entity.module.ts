import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'billing-info',
        loadChildren: () => import('./billing-info/billing-info.module').then(m => m.IgniogatewayBillingInfoModule)
      },
      {
        path: 'device',
        loadChildren: () => import('./devicedataservice/device/device.module').then(m => m.DevicedataserviceDeviceModule)
      },
      {
        path: 'sensor-data',
        loadChildren: () => import('./devicedataservice/sensor-data/sensor-data.module').then(m => m.DevicedataserviceSensorDataModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class IgniogatewayEntityModule {}
