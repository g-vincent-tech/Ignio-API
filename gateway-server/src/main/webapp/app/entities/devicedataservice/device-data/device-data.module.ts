import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IgniogatewaySharedModule } from 'app/shared';
import {
  DeviceDataComponent,
  DeviceDataDetailComponent,
  DeviceDataUpdateComponent,
  DeviceDataDeletePopupComponent,
  DeviceDataDeleteDialogComponent,
  deviceDataRoute,
  deviceDataPopupRoute
} from './';

const ENTITY_STATES = [...deviceDataRoute, ...deviceDataPopupRoute];

@NgModule({
  imports: [IgniogatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DeviceDataComponent,
    DeviceDataDetailComponent,
    DeviceDataUpdateComponent,
    DeviceDataDeleteDialogComponent,
    DeviceDataDeletePopupComponent
  ],
  entryComponents: [DeviceDataComponent, DeviceDataUpdateComponent, DeviceDataDeleteDialogComponent, DeviceDataDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DevicedataserviceDeviceDataModule {}
