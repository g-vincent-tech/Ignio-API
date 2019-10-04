/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IgniogatewayTestModule } from '../../../../test.module';
import { DeviceDataDeleteDialogComponent } from 'app/entities/devicedataservice/device-data/device-data-delete-dialog.component';
import { DeviceDataService } from 'app/entities/devicedataservice/device-data/device-data.service';

describe('Component Tests', () => {
  describe('DeviceData Management Delete Component', () => {
    let comp: DeviceDataDeleteDialogComponent;
    let fixture: ComponentFixture<DeviceDataDeleteDialogComponent>;
    let service: DeviceDataService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgniogatewayTestModule],
        declarations: [DeviceDataDeleteDialogComponent]
      })
        .overrideTemplate(DeviceDataDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DeviceDataDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeviceDataService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete('123');
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith('123');
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
