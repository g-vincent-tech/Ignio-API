/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { IgniogatewayTestModule } from '../../../../test.module';
import { DeviceDataUpdateComponent } from 'app/entities/devicedataservice/device-data/device-data-update.component';
import { DeviceDataService } from 'app/entities/devicedataservice/device-data/device-data.service';
import { DeviceData } from 'app/shared/model/devicedataservice/device-data.model';

describe('Component Tests', () => {
  describe('DeviceData Management Update Component', () => {
    let comp: DeviceDataUpdateComponent;
    let fixture: ComponentFixture<DeviceDataUpdateComponent>;
    let service: DeviceDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgniogatewayTestModule],
        declarations: [DeviceDataUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DeviceDataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DeviceDataUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeviceDataService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DeviceData('123');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DeviceData();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
