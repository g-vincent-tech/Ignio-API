/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IgniogatewayTestModule } from '../../../../test.module';
import { SensorDataDetailComponent } from 'app/entities/devicedataservice/sensor-data/sensor-data-detail.component';
import { SensorData } from 'app/shared/model/devicedataservice/sensor-data.model';

describe('Component Tests', () => {
  describe('SensorData Management Detail Component', () => {
    let comp: SensorDataDetailComponent;
    let fixture: ComponentFixture<SensorDataDetailComponent>;
    const route = ({ data: of({ sensorData: new SensorData('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IgniogatewayTestModule],
        declarations: [SensorDataDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SensorDataDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SensorDataDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sensorData).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
