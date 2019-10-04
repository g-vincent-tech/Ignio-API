/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { IgniogatewayTestModule } from '../../../../test.module';
import { SensorDataUpdateComponent } from 'app/entities/devicedataservice/sensor-data/sensor-data-update.component';
import { SensorDataService } from 'app/entities/devicedataservice/sensor-data/sensor-data.service';
import { SensorData } from 'app/shared/model/devicedataservice/sensor-data.model';

describe('Component Tests', () => {
  describe('SensorData Management Update Component', () => {
    let comp: SensorDataUpdateComponent;
    let fixture: ComponentFixture<SensorDataUpdateComponent>;
    let service: SensorDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgniogatewayTestModule],
        declarations: [SensorDataUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SensorDataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SensorDataUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SensorDataService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SensorData('123');
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
        const entity = new SensorData();
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
