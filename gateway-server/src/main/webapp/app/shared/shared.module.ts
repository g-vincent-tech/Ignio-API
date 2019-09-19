import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { IgniogatewaySharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [IgniogatewaySharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [IgniogatewaySharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IgniogatewaySharedModule {
  static forRoot() {
    return {
      ngModule: IgniogatewaySharedModule
    };
  }
}
