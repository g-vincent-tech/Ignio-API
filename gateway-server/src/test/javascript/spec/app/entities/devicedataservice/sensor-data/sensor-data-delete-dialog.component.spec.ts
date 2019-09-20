/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IgniogatewayTestModule } from '../../../../test.module';
import { SensorDataDeleteDialogComponent } from 'app/entities/devicedataservice/sensor-data/sensor-data-delete-dialog.component';
import { SensorDataService } from 'app/entities/devicedataservice/sensor-data/sensor-data.service';

describe('Component Tests', () => {
  describe('SensorData Management Delete Component', () => {
    let comp: SensorDataDeleteDialogComponent;
    let fixture: ComponentFixture<SensorDataDeleteDialogComponent>;
    let service: SensorDataService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgniogatewayTestModule],
        declarations: [SensorDataDeleteDialogComponent]
      })
        .overrideTemplate(SensorDataDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SensorDataDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SensorDataService);
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
