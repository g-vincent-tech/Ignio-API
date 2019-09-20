import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'device',
        loadChildren: () => import('./devicedataservice/device/device.module').then(m => m.DevicedataserviceDeviceModule)
      },
      {
        path: 'sensor-data',
        loadChildren: () => import('./devicedataservice/sensor-data/sensor-data.module').then(m => m.DevicedataserviceSensorDataModule)
      },
      {
        path: 'device-data',
        loadChildren: () => import('./devicedataservice/device-data/device-data.module').then(m => m.DevicedataserviceDeviceDataModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IgniogatewayEntityModule {}
