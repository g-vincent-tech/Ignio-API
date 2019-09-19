import { NgModule } from '@angular/core';

import { IgniogatewaySharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [IgniogatewaySharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [IgniogatewaySharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class IgniogatewaySharedCommonModule {}
