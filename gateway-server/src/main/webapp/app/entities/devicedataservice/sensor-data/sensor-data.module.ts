import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IgniogatewaySharedModule } from 'app/shared';
import {
  SensorDataComponent,
  SensorDataDetailComponent,
  SensorDataUpdateComponent,
  SensorDataDeletePopupComponent,
  SensorDataDeleteDialogComponent,
  sensorDataRoute,
  sensorDataPopupRoute
} from './';

const ENTITY_STATES = [...sensorDataRoute, ...sensorDataPopupRoute];

@NgModule({
  imports: [IgniogatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SensorDataComponent,
    SensorDataDetailComponent,
    SensorDataUpdateComponent,
    SensorDataDeleteDialogComponent,
    SensorDataDeletePopupComponent
  ],
  entryComponents: [SensorDataComponent, SensorDataUpdateComponent, SensorDataDeleteDialogComponent, SensorDataDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DevicedataserviceSensorDataModule {}
